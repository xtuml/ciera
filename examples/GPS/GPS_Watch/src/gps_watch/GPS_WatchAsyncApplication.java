package gps_watch;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Scanner;

import org.json.JSONObject;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;

import io.ciera.runtime.instanceloading.IChangeLog;
import io.ciera.runtime.summit.application.ApplicationExecutor;
import io.ciera.runtime.summit.application.IApplication;
import io.ciera.runtime.summit.application.IExceptionHandler;
import io.ciera.runtime.summit.application.IRunContext;
import io.ciera.runtime.summit.application.tasks.ReceivedMessageTask;
import io.ciera.runtime.summit.components.IComponent;
import io.ciera.runtime.summit.exceptions.XtumlException;
import io.ciera.runtime.summit.interfaces.IPort;
import io.ciera.runtime.summit.interfaces.Message;
import io.ciera.runtime.summit.util.CommandLine;
import io.ciera.runtime.summit.util.LOG;
import io.ciera.runtime.summit.util.impl.LOGImpl;

public class GPS_WatchAsyncApplication implements IApplication, RequestStreamHandler {
    private IComponent<?>[] components;
    private IRunContext executor;
    private LOG logger;
    public GPS_WatchAsyncApplication() {
        components = new IComponent<?>[4];
    }
    @Override
    public void setup( String[] args ) {
        executor = new ApplicationExecutor("GPS_WatchAsyncApplicationExecutor", args);
        executor.setExceptionHandler(new IExceptionHandler() {
			@Override
			public void warn(String arg0) {
			}
			@Override
			public void handle(XtumlException e) {
				logger.LogFailure(e.toString());
				System.exit(1);
			}
		});
        components[3] = new UI(this, executor, 3);
        components[1] = new Location(this, executor, 1);
        components[1].addLoader("DynamoDB", new LocationDynamoDBLoader((Location)components[1]));
        components[0] = new HeartRateMonitor(this, executor, 0);
        components[0].addLoader("DynamoDB", new HeartRateMonitorDynamoDBLoader((HeartRateMonitor)components[0]));
        components[2] = new Tracking(this, executor, 2);
        components[2].addLoader("DynamoDB", new TrackingDynamoDBLoader((Tracking)components[2]));
        ((Tracking)components[2]).HR().satisfy(((HeartRateMonitor)components[0]).HR());
        ((HeartRateMonitor)components[0]).HR().satisfy(((Tracking)components[2]).HR());
        ((Tracking)components[2]).LOC().satisfy(((Location)components[1]).LOC());
        ((Location)components[1]).LOC().satisfy(((Tracking)components[2]).LOC());
        ((Tracking)components[2]).UI().satisfy(((UI)components[3]).UI());
        ((UI)components[3]).UI().satisfy(((Tracking)components[2]).UI());
    }
    @Override
    public void printVersions() {
        io.ciera.runtime.Version.printVersion();
        for ( IComponent<?> c : components ) {
            System.out.printf("%s: %s (%s)", c.getClass().getName(), c.getVersion(), c.getVersionDate());
            System.out.println();
        }
    }
    public void handleSignal(final String data) {
        final JSONObject msg = new JSONObject(data);
        final int index = msg.getInt("componentId");
        final String portName = msg.getString("portName");
        // load population
        try {
            if (null!= components[index].getDefaultLoader()) components[index].getDefaultLoader().load();
        } catch (XtumlException e) {logger.LogFailure(e.toString());}
        IChangeLog changeLog = executor.performTransaction(new ReceivedMessageTask() {
            @Override
            public void run() throws XtumlException {
                // execute signal
                try {
                    Method portAccessor = components[index].getClass().getMethod(portName);
                    IPort<?> port = (IPort<?>)portAccessor.invoke(components[index]);
                    port.deliver(Message.deserialize(msg.getJSONObject("message").toString()));
                } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                    throw new XtumlException("Could not deliver message");
                }
            }
        });
        // serialize population
        try {
            if (null!= components[index].getDefaultLoader()) components[index].getDefaultLoader().serialize(changeLog);
        } catch (XtumlException e) {logger.LogFailure(e.toString());}
    }
    public void heartbeat() {
        // load population
        for ( IComponent<?> c : components ) {
        	try {
        	    if (null != c.getDefaultLoader()) c.getDefaultLoader().load();
            } catch (XtumlException e) {/* fail silently */}
        }
        // tick
        IChangeLog changeLog = executor.heartbeat();
        // serialize population
        for ( IComponent<?> c : components ) {
        	try {
        	    if (null != c.getDefaultLoader()) c.getDefaultLoader().serialize(changeLog);
            } catch (XtumlException e) {/* fail silently */}
        }
    }
    @Override
    public void initialize() {}
    @Override
    public void start() {}
    @Override
    public void stop() {}
    public static void main(String[] args) {
        GPS_WatchAsyncApplication app = new GPS_WatchAsyncApplication();
        app.setup(args);
        CommandLine cmd = new CommandLine(args);
        try {
            cmd.register_flag("v", "Print version");
            cmd.register_flag("version", "Print version");
            cmd.register_flag("heartbeat", "Check ticking timers");
            cmd.register_value("signal", "signal_file", "File containing JSON signal data", "", false);
            cmd.read_command_line();
            if (cmd.get_flag("v") || cmd.get_flag("version")) {
                app.printVersions();
            }
            else {
                if (cmd.get_flag("heartbeat")) {
                    app.heartbeat();
                }
                else if (!"".equals(cmd.get_value("signal"))) {
                    try {
                        Scanner sc = new Scanner(new File(cmd.get_value("signal")));
                        String signalData = "";
                        while (sc.hasNextLine()) signalData += sc.nextLine();
                        sc.close();
                        app.handleSignal(signalData);
                    } catch (IOException e) {/* fail silently */}
                }
            }
        } catch (XtumlException e) {/* fail silently */}
    }

	@Override
	public void handleRequest(InputStream inputStream, OutputStream outputStream, Context context) throws IOException {
		LOGImpl.setErr(new PrintStream(new LogOutputStream(context)));
		LOGImpl.setOut(new PrintStream(new LogOutputStream(context)));
		logger = new LOGImpl<Tracking>(null);
        Scanner sc = new Scanner(inputStream).useDelimiter("\\z");
        final JSONObject msg = new JSONObject(sc.hasNext() ? sc.next() : "");
		logger.LogInfo(msg.toString());
		final boolean isHeartbeat = "true".equals(msg.getString("heartbeat"));
		if (isHeartbeat) {
            setup(new String[0]);
            heartbeat();
		}
		else {
            setup(new String[0]);
            handleSignal(msg.toString());
        }
	}
	
	private static class LogOutputStream extends OutputStream {
		
		private LambdaLogger log;
		
		public LogOutputStream(Context context) {
			context.getLogger();
		}

		@Override
		public void write(int b) throws IOException {
			log.log(new byte[] {(byte)b});
		}
	}
}
