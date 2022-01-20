package gui;

import io.ciera.runtime.api.application.Logger;
import io.ciera.runtime.domain.JSONMessage;

public class Gui {

	WatchGui guiDisplay = null;
	ApplicationConnection server = null;
	ConnectionHandler connHandler = null;
	Logger logger;

	public void start(String[] args) {

		// Create GUI
		if (args.length == 1 && "--console".equals(args[0])) {
			guiDisplay = new AsciiWatchGui(this);
		} else {
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

	public void sendSignal(JSONMessage message) {
		if (server != null) {
			server.sendSignal(message);
		} else {
			logger.error("Server is not connected");
		}

	}

	public WatchGui getGuiDisplay() {
		return guiDisplay;
	}

	public Logger getLogger() {
		return logger;
	}

	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	public static void main(String[] args) {
		Gui gui = new Gui();
		gui.start(args);
	}

}
