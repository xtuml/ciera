package ui;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.StringTokenizer;

import org.xtuml.bp.core.ComponentInstance_c;
import org.xtuml.bp.core.CorePlugin;

import lib.SetData;
import lib.SetIndicator;
import lib.SetTime;
import lib.SignalData;
import tracking.shared.Indicator;
import tracking.shared.Unit;
import ui.shared.IUIFromProvider;
import ui.shared.IUIToProvider;

public class UI implements IUIToProvider {
	
    private static GuiConnection requester = null;
    private static final int SOCKET_ERROR = -1;
	
	private Thread socketListener;
	
	public UI(IUIFromProvider peer) {
		if ( SOCKET_ERROR != connect() ) {
			socketListener = new Thread() {
				@Override
				public void run() {
					while (true) {
						int signal_no = poll();
						switch (signal_no) {
						case SignalData.SIGNAL_NO_LAP_RESET_PRESSED:
							//CorePlugin.err.println("UI: lapResetPressed");
							peer.lapResetPressed(null);
					    	break;
						case SignalData.SIGNAL_NO_LIGHT_PRESSED:
							//CorePlugin.err.println("UI: lightPressed");
							peer.lightPressed(null);
					    	break;
						case SignalData.SIGNAL_NO_MODE_PRESSED:
							//CorePlugin.err.println("UI: modePressed");
							peer.modePressed(null);
					    	break;
						case SignalData.SIGNAL_NO_START_STOP_PRESSED:
							//CorePlugin.err.println("UI: startStopPressed");
							peer.startStopPressed(null);
					    	break;
						case SignalData.SIGNAL_NO_TARGET_PRESSED:
							//CorePlugin.err.println("UI: targetPressed");
							peer.setTargetPressed(null);
					    	break;
						case SOCKET_ERROR:
							//CorePlugin.err.println("Socket listener shuting down.");
							return;
					    default:
					    	break;
						}
						try {
							Thread.sleep(100);
						} catch (InterruptedException e) {/* do nothing */}
					}
				}
			};
			socketListener.start();
		}
	}

	@Override
	public void setTime(ComponentInstance_c senderReceiver, int time) {
		//CorePlugin.err.println("UI: setTime");
        if (requester != null) {
            try {
                requester.sendMessage(new SetTime(time));
            } catch ( IOException e ) {
                CorePlugin.out.println("Connection lost.");
                requester.tearDown();
                requester = null;
            }
        }
	}

	@Override
	public void setData(ComponentInstance_c senderReceiver, Float value, Unit unit) {
		//CorePlugin.err.println("UI: setData");
        if (requester != null) {
            try {
                requester.sendMessage(new SetData(value, unit.getValue()));
            } catch ( IOException e ) {
                CorePlugin.out.println("Connection lost.");
                requester.tearDown();
                requester = null;
            }
        }
	}

	@Override
	public void setIndicator(ComponentInstance_c senderReceiver, Indicator indicator) {
		//CorePlugin.err.println("UI: setIndicator");
        if (requester != null) {
            try {
                requester.sendMessage( new SetIndicator(indicator.getValue()));
            } catch ( IOException e ) {
                CorePlugin.out.println("Connection lost.");
                requester.tearDown();
                requester = null;
            }
        }
	}
	
    private static int connect() {
        if ( null == requester ) requester = new GuiConnection();
        try {
            requester.connect();
        } catch (UnknownHostException unknownHost) {
            CorePlugin.out.println("You are trying to connect to an unknown host.");
            requester.tearDown();
            requester = null;
            return SOCKET_ERROR;
        } catch (IOException ioException) {
            CorePlugin.out.println("Connection lost.");
            requester.tearDown();
            requester = null;
            return SOCKET_ERROR;
        }
        return 1;
    }
    
    private static int poll() {
        if ( null != requester ) {
            try {
                return requester.poll();
            } catch ( IOException e ) {
                CorePlugin.out.println("Connection lost.");
                requester.tearDown();
                requester = null;
                return SOCKET_ERROR;
            }
        }
        else return SOCKET_ERROR;
    }

    private static class GuiConnection {
        Socket requestSocket;
        DataOutputStream out;
        BufferedReader in;

        private void connect() throws IOException {
            requestSocket = new Socket("localhost", 2003);
            requestSocket.setSoTimeout( 10 ); // set read timeout for 10 milliseconds
            CorePlugin.out.println("Connected to localhost on port 2003");
            out = new DataOutputStream(requestSocket.getOutputStream());
            out.flush();
            in = new BufferedReader(new InputStreamReader(requestSocket.getInputStream()));
        }
        
        private int poll() throws IOException {
            try {
                String msg = in.readLine();
                if (msg == null) {
                    CorePlugin.out.println("Connection closed by server");
                    return SOCKET_ERROR;
                }
                //CorePlugin.err.println(msg);
                StringTokenizer tokenizer = new StringTokenizer(msg, ",");
                return Integer.parseInt(tokenizer.nextToken());
            } catch ( IOException e ) {
                if ( e instanceof SocketTimeoutException ) { /* do nothing */ }
                else throw e;
            }
            return SignalData.SIGNAL_NO_NULL_SIGNAL;
        }
        
        private void sendMessage( SignalData data ) throws IOException {
            data.serialize(out);
            out.write('\n');
            out.flush();
        }
        
        private void tearDown() {
            try {
                if ( null != requestSocket ) requestSocket.close();
                if ( null != out ) out.close();
                if ( null != in ) in.close();
            } catch ( IOException e ) {}
        }
    }
}