package gps_watch;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import gps_watch.ui.UIUI;
import io.ciera.runtime.api.application.Application;
import io.ciera.runtime.api.domain.Message;
import io.ciera.runtime.domain.AbstractDomain;
import io.ciera.runtime.domain.JSONMessage;
import io.ciera.runtime.domain.PortMessage;
import tracking.shared.Indicator;
import tracking.shared.Unit;
import ui.shared.IUI;

public class UI extends AbstractDomain {

	private static GuiConnection requester = null;
	private static final int SOCKET_ERROR = -1;
	private static final String HOSTNAME = "localhost";
	private static final int PORT = 2003;

	// ports
	private IUI UIUI;

	public UI(String name, Application application) {
		super(name, application);
		UIUI = new UIUI(this);
	}

	// domain functions
	public void setIndicator(final Indicator p_indicator) {
		if (requester != null) {
			try {
				requester.sendMessage(new JSONMessage(new IUI.SetIndicator(p_indicator)));
			} catch (IOException e) {
				getApplication().getLogger().warn("Connection lost.");
				requester.tearDown();
				requester = null;
			}
		}
	}

	public void setTime(final int p_time) {
		if (requester != null) {
			try {
				requester.sendMessage(new JSONMessage(new IUI.SetTime(p_time)));
			} catch (IOException e) {
				getApplication().getLogger().warn("Connection lost.");
				requester.tearDown();
				requester = null;
			}
		}
	}

	public void setData(final double p_value, final Unit p_unit) {
		getApplication().getLogger().trace("value: %.2f, unit: %s", p_value, p_unit.name());
		if (requester != null) {
			try {
				requester.sendMessage(new JSONMessage(new IUI.SetData(p_value, p_unit)));
			} catch (IOException e) {
				getApplication().getLogger().warn("Connection lost.");
				requester.tearDown();
				requester = null;
			}
		}
	}

	// port accessors
	public IUI UIUI() {
		return UIUI;
	}

	@Override
	public void initialize() {
		if (connect() != SOCKET_ERROR) {
			getContext().execute(() -> listen());
		} else {
			getContext().halt();
		}
	}

	@Override
	public UI getDomain() {
		return (UI) super.getDomain();
	}

	private void listen() {
		Message msg = poll();
		if (msg != null) {
			if (msg.getId() != SOCKET_ERROR) {
				if (UIUI().satisfied()) {
					UIUI().send(msg);
				} else {
					getApplication().getLogger().warn("UI port is not satisfied");
				}
			} else {
				getApplication().getLogger().info("Socket listener shuting down.");
				getContext().halt();
				return;
			}
		}
		// listen again
		getContext().execute(() -> listen());
	}

	private int connect() {
		if (requester == null) {
			requester = new GuiConnection();
		}
		try {
			requester.connect();
		} catch (UnknownHostException unknownHost) {
			getApplication().getLogger().warn("You are trying to connect to an unknown host.");
			requester.tearDown();
			requester = null;
			return SOCKET_ERROR;
		} catch (IOException ioException) {
			getApplication().getLogger().warn("Connection lost.");
			requester.tearDown();
			requester = null;
			return SOCKET_ERROR;
		}
		return 1;
	}

	private Message poll() {
		if (requester != null) {
			try {
				return requester.poll();
			} catch (IOException e) {
				getApplication().getLogger().warn("Connection lost.");
				requester.tearDown();
				requester = null;
				return new PortMessage(SOCKET_ERROR);
			}
		} else {
			return new PortMessage(SOCKET_ERROR);
		}
	}

	private class GuiConnection {

		private Socket requestSocket;
		private OutputStream out;
		private BufferedReader in;

		private void connect() throws IOException {
			requestSocket = new Socket(HOSTNAME, PORT);
			requestSocket.setSoTimeout(10); // set read timeout for 10 milliseconds
			getApplication().getLogger().info("Connected to localhost on port 2003");
			out = new DataOutputStream(requestSocket.getOutputStream());
			out.flush();
			in = new BufferedReader(new InputStreamReader(requestSocket.getInputStream()));
		}

		private Message poll() throws IOException {
			try {
				String data = in.readLine();
				if (data == null) {
					getApplication().getLogger().warn("Connection closed by server");
					return new PortMessage(SOCKET_ERROR);
				}
				getApplication().getLogger().trace(data);
				return JSONMessage.fromString(data);
			} catch (IOException e) {
				if (e instanceof SocketTimeoutException) {
					// do nothing
				} else {
					throw e;
				}
			}
			return null;
		}

		private void sendMessage(JSONMessage msg) throws IOException {
			out.write(msg.toString().getBytes());
			out.write('\n');
			out.flush();
		}

		private void tearDown() {
			try {
				if (requestSocket != null)
					requestSocket.close();
				if (out != null)
					out.close();
				if (in != null)
					in.close();
			} catch (IOException e) {
				// do nothing
			}
		}

	}

}
