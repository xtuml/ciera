package gui;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Map;

import io.ciera.runtime.domain.SerializableMessage;
import tracking.shared.Indicator;
import tracking.shared.Unit;
import ui.shared.IUI;

/**
 * The <code>ApplicationConnection</code> is the connection to the underlying
 * application that takes input from the GUI and displays data via the GUI.
 * Messages are from the application are received on this thread. Message
 * sending occurs from the UI thread.
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

    public void sendSignal(SerializableMessage data) {
        try {
            out.write(data.toString().getBytes());
            out.write('\n');
            out.flush();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public void run() {
        try {
            app.getLogger().info("Connection received from " + connection.getInetAddress().getHostName());
            out = new DataOutputStream(connection.getOutputStream());
            out.flush();
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            // main loop
            while (true) {
                try {
                    String msg = in.readLine();
                    if (msg == null)
                        continue;

                    // data arrives as a JSON message
                    app.getLogger().trace(msg);
                    SerializableMessage data = SerializableMessage.fromString(msg);

                    // work out the data type of the incoming message
                    // and set the action (run()) to be carried out
                    switch (data.getId()) {
                    case IUI.SETDATA:
                        data = data.deserializeData(Map.<String, Class<?>>of("p_value", double.class, "p_unit", Unit.class));
                        app.getGuiDisplay().setData((double) data.get("p_value"), (Unit) data.get("p_unit"));
                        break;
                    case IUI.SETTIME:
                        data = data.deserializeData(Map.<String, Class<?>>of("p_time", int.class));
                        app.getGuiDisplay().setTime((int) data.get("p_time"));
                        break;
                    case IUI.SETINDICATOR:
                        data = data.deserializeData(Map.<String, Class<?>>of("p_indicator", Indicator.class));
                        app.getGuiDisplay().setIndicator((Indicator) data.get("p_indicator"));
                        break;
                    default:
                        break;
                    }
                    Thread.sleep(10);
                } catch (IOException ioe) {
                    app.getLogger().warn("Connection closed by client.");
                    break;
                } catch (InterruptedException e) {
                    // do nothing
                }
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            try {
                connection.close();
                app.getGuiDisplay().setTime(0);
                app.getGuiDisplay().setData(0f, Unit.KM);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }
}
