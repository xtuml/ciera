package gui;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import io.ciera.runtime.domain.JSONMessage;
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

	public void sendSignal(JSONMessage data) {
		try {
			out.write(data.toString().getBytes());
			out.write('\n');
			out.flush();
		} catch (IOException ioException) {
			ioException.printStackTrace();
		}
	}

	@Override
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
					// app.getLogger().trace(msg);
					JSONMessage data = JSONMessage.fromString(msg, IUI.class);

					// work out the data type of the incoming message
					// and set the action (run()) to be carried out
					switch (data.getId()) {
					case IUI.SETDATA:
						app.getGuiDisplay().setData((double) data.get("p_value"), (Unit) data.get("p_unit"));
						break;
					case IUI.SETTIME:
						app.getGuiDisplay().setTime((int) data.get("p_time"));
						break;
					case IUI.SETINDICATOR:
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
