package gui;

import io.ciera.runtime.summit.interfaces.IMessage;

public class Gui {

    WatchGui guiDisplay = null;
    ApplicationConnection server = null;
    ConnectionHandler connHandler = null;

    public void start(String[] args) {
        // Create GUI
        if (args.length == 1 && "--console".equals(args[0])) {
            guiDisplay = new AsciiWatchGui(this);
        }
        else {
            guiDisplay = new SwingWatchGui(this);
        }

        // The connection handle lives for the entire duration of this program
        connHandler = new ConnectionHandler(this);

        // Start the connection handler
        connHandler.start();

        // Display the GUI
        guiDisplay.display();
    }

    public void setApplicationConnection(ApplicationConnection server) {
        this.server = server;
    }

    public void sendSignal(IMessage message) {
        server.sendSignal(message);
    }

    public WatchGui getGuiDisplay() {
        return guiDisplay;
    }

    public static void main(String[] args) {
        Gui gui = new Gui();
        gui.start(args);
    }

}
