package gps_watch;

import gps_watch.HeartRateMonitor;
import gps_watch.Location;
import gps_watch.Tracking;
import gps_watch.UI;
import io.ciera.runtime.instanceloading.sql.util.impl.SQLImpl;
import io.ciera.runtime.summit.application.ApplicationExecutor;
import io.ciera.runtime.summit.application.IApplication;
import io.ciera.runtime.summit.application.IRunContext;
import io.ciera.runtime.summit.application.tasks.GenericExecutionTask;
import io.ciera.runtime.summit.components.IComponent;
import io.ciera.runtime.summit.exceptions.XtumlException;
import io.ciera.runtime.summit.interfaces.IPort;
import io.ciera.runtime.summit.interfaces.Message;
import io.ciera.runtime.summit.util.CommandLine;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Scanner;

import org.json.JSONObject;

public class GPS_WatchAsyncApplication implements IApplication {

    private IComponent<?>[] components;
    private IRunContext executor;

    public GPS_WatchAsyncApplication() {
        components = new IComponent<?>[4];
    }

    @Override
    public void setup( String[] args ) {
        executor = new ApplicationExecutor("GPS_WatchApplicationExecutor", args);
        components[0] = new Tracking(this, executor, 0);
        components[1] = new HeartRateMonitor(this, executor, 1);
        components[2] = new Location(this, executor, 2);
        components[3] = new UI(this, executor, 3);
        ((Tracking)components[0]).HR().satisfy(((HeartRateMonitor)components[1]).HR());
        ((HeartRateMonitor)components[1]).HR().satisfy(((Tracking)components[0]).HR());
        ((Tracking)components[0]).LOC().satisfy(((Location)components[2]).LOC());
        ((Location)components[2]).LOC().satisfy(((Tracking)components[0]).LOC());
        ((Tracking)components[0]).UI().satisfy(((UI)components[3]).UI());
        ((UI)components[3]).UI().satisfy(((Tracking)components[0]).UI());
    }

    @Override
    public void printVersions() {
        io.ciera.runtime.Version.printVersion();
        for ( IComponent<?> c : components ) {
            System.out.printf("%s: %s (%s)", c.getClass().getName(), c.getVersion(), c.getVersionDate());
            System.out.println();
        }
    }
    
    private void loadPopulation() {
        try {
            new SQLImpl<>((Tracking)components[0]).load_file("Tracking.xtuml");
            new SQLImpl<>((HeartRateMonitor)components[1]).load_file("HeartRateMonitor.xtuml");
            new SQLImpl<>((Location)components[2]).load_file("Location.xtuml");
        } catch (XtumlException e) {/* fail silently */}
    }

    private void serializePopulation() {
        try {
            new SQLImpl<>((Tracking)components[0]).serialize_file("Tracking.xtuml");
            new SQLImpl<>((HeartRateMonitor)components[1]).serialize_file("HeartRateMonitor.xtuml");
            new SQLImpl<>((Location)components[2]).serialize_file("Location.xtuml");
        } catch (XtumlException e) {/* fail silently */}
    }

    public void handleSignal(final String data) {
        // load population
        loadPopulation();
        executor.performTransaction(new GenericExecutionTask() {
            @Override
            public void run() throws XtumlException {
                // execute signal
                final JSONObject msg = new JSONObject(data);
                final int index = msg.getInt("componentId");
                final String portName = msg.getString("portName");
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
        serializePopulation();
    }

    public void heartbeat() {
        // load population
        loadPopulation();
        // tick
        executor.heartbeat();
        // serialize population
        serializePopulation();
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

}
