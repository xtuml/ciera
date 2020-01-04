package gui;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * The <code>ConnectionHandler</code> sits and waits for clients
 * to connect. As soon as a new connection is detected, any existing
 * connections are released, allowing the most recently connected client
 * to control and be controlled by the GUI.
 */
public class ConnectionHandler extends Thread {

    private ServerSocket providerSocket;
    private Gui app;

    public ConnectionHandler(Gui app) {
        this.app = app;
    }

    @Override
    public void run() {
        try {
            providerSocket = new ServerSocket(2003);
            System.err.println("Waiting for connection");
            Socket connection = providerSocket.accept();
            while (true) {
                new ApplicationConnection(app, connection).start();
                Socket newConnection = providerSocket.accept();
                connection.close();
                connection = newConnection;
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {}
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        } finally {
            try {
                providerSocket.close();
            } catch(IOException ioException){
                ioException.printStackTrace();
            }
            new ConnectionHandler(app).start();
        }
    }

}
