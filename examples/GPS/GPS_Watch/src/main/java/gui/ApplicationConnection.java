package gui;

import io.ciera.runtime.summit.exceptions.XtumlException;
import io.ciera.runtime.summit.interfaces.IMessage;
import io.ciera.runtime.summit.interfaces.Message;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import tracking.shared.Indicator;
import tracking.shared.Unit;
import ui.shared.IUI;

/**
 * The <code>ApplicationConnection</code> is the connection to the underlying 
 * application that takes input from the GUI and displays data via the GUI.
 * Messages are from the application are received on this thread. Message sending 
 * occurs from the UI thread. 
 */
public class ApplicationConnection extends Thread {

    Gui app = null;
    Socket connection = null;
    DataOutputStream out;
    BufferedReader in;
    String message = "";
    
    public ApplicationConnection(Gui app, Socket connection) {
         this.app = app;
         app.setApplicationConnection(this);
         this.connection = connection;
    }

    public void sendSignal(IMessage data) {
        try{
            out.write(data.serialize().getBytes());
            out.write('\n');
            out.flush();
        }
        catch(IOException ioException){
            ioException.printStackTrace();
        }
        catch (XtumlException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        try {
            System.err.println("Connection received from " + connection.getInetAddress().getHostName());
            out = new DataOutputStream(connection.getOutputStream());
            out.flush();
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            
            // main loop
            while (true) {
                try {
                    String msg = in.readLine();
                    if ( null == msg ) continue;
                    
                    // data arrives in a comma separated list
                    IMessage data = Message.deserialize(msg);
                    
                    // work out the data type of the incoming message
                    // and set the action (run()) to be carried out
                    switch (data.getId()) {
                    case IUI.SIGNAL_NO_SETDATA:
                        app.getGuiDisplay().setData((float)Double.parseDouble((String)data.get(0)), Unit.deserialize(data.get(1)));
                        break;
                    case IUI.SIGNAL_NO_SETTIME:
                        app.getGuiDisplay().setTime(Integer.parseInt((String)data.get(0)));
                        break;
                    case IUI.SIGNAL_NO_SETINDICATOR:
                        app.getGuiDisplay().setIndicator(Indicator.deserialize(data.get(0)));
                        break;
                    default:
                        break;
                    }
                    Thread.sleep(10);
                } catch (IOException ioe) {
                    System.err.println("Connection closed by client.");
                    break;
                } catch (InterruptedException e) { // do nothing
                } catch (XtumlException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            try {
                connection.close();
                app.getGuiDisplay().setTime(0);
                app.getGuiDisplay().setData(0f, Unit.KM);
            } catch (IOException ioException){
                ioException.printStackTrace();
            }
        }
    }
}
