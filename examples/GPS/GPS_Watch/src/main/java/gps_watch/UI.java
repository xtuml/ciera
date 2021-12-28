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

import io.ciera.runtime.application.Application;
import io.ciera.runtime.application.task.Halt;
import io.ciera.runtime.domain.Domain;
import io.ciera.runtime.domain.Message;
import io.ciera.runtime.domain.SerializableMessage;
import tracking.shared.Indicator;
import tracking.shared.Unit;
import ui.shared.IUI;

public class UI extends Domain {

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
                requester.sendMessage(new SerializableMessage(new IUI.SetIndicator(p_indicator)));
            } catch (IOException e) {
                getLogger().warn("Connection lost.");
                requester.tearDown();
                requester = null;
            }
        }
    }

    public void setTime(final int p_time) {
        if (requester != null) {
            try {
                requester.sendMessage(new SerializableMessage(new IUI.SetTime(p_time)));
            } catch (IOException e) {
                getLogger().warn("Connection lost.");
                requester.tearDown();
                requester = null;
            }
        }
    }

    public void setData(final double p_value, final Unit p_unit) {
        getLogger().trace("value: %.2f, unit: %s", p_value, p_unit.name());
        if (requester != null) {
            try {
                requester.sendMessage(new SerializableMessage(new IUI.SetData(p_value, p_unit)));
            } catch (IOException e) {
                getLogger().warn("Connection lost.");
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
            getContext().addTask(new Halt(getContext()));
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
                    getLogger().warn("UI port is not satisfied");
                }
            } else {
                getLogger().info("Socket listener shuting down.");
                getContext().addTask(new Halt(getContext()));
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
            getLogger().warn("You are trying to connect to an unknown host.");
            requester.tearDown();
            requester = null;
            return SOCKET_ERROR;
        } catch (IOException ioException) {
            getLogger().warn("Connection lost.");
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
                getLogger().warn("Connection lost.");
                requester.tearDown();
                requester = null;
                return new Message(SOCKET_ERROR);
            }
        } else {
            return new Message(SOCKET_ERROR);
        }
    }

    private class GuiConnection {

        private Socket requestSocket;
        private OutputStream out;
        private BufferedReader in;

        private void connect() throws IOException {
            requestSocket = new Socket(HOSTNAME, PORT);
            requestSocket.setSoTimeout(10); // set read timeout for 10 milliseconds
            getLogger().info("Connected to localhost on port 2003");
            out = new DataOutputStream(requestSocket.getOutputStream());
            out.flush();
            in = new BufferedReader(new InputStreamReader(requestSocket.getInputStream()));
        }

        private Message poll() throws IOException {
            try {
                String data = in.readLine();
                if (data == null) {
                    getLogger().warn("Connection closed by server");
                    return new Message(SOCKET_ERROR);
                }
                getLogger().trace(data);
                return SerializableMessage.fromString(data);
            } catch (IOException e) {
                if (e instanceof SocketTimeoutException) {
                    // do nothing
                } else {
                    throw e;
                }
            }
            return null;
        }

        private void sendMessage(SerializableMessage msg) throws IOException {
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
