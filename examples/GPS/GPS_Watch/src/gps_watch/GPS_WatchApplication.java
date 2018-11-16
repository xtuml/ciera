package gps_watch;

import gps_watch.HeartRateMonitor;
import gps_watch.Location;
import gps_watch.Tracking;
import gps_watch.UI;

import io.ciera.runtime.summit.application.ApplicationExecutor;
import io.ciera.runtime.summit.application.IApplication;
import io.ciera.runtime.summit.application.IRunContext;
import io.ciera.runtime.summit.application.tasks.GenericExecutionTask;
import io.ciera.runtime.summit.components.IComponent;
import io.ciera.runtime.summit.exceptions.XtumlException;

import java.util.Arrays;

public class GPS_WatchApplication implements IApplication {
    IComponent<?>[] components;
    IRunContext[] executors;
    public GPS_WatchApplication() {
        components = new IComponent<?>[4];
        executors = new IRunContext[1];
    }
    @Override
    public void setup( String[] args ) {
        for ( int i = 0; i < executors.length; i++ )
            executors[i] = new ApplicationExecutor( "GPS_WatchApplicationExecutor" + i, args );
        components[0] = new Tracking( executors[0] );
        components[2] = new UI( executors[0] );
        components[3] = new Location( executors[0] );
        components[1] = new HeartRateMonitor( executors[0] );
        ((Tracking)components[0]).UI().satisfy(((UI)components[2]).UI());
        ((UI)components[2]).UI().satisfy(((Tracking)components[0]).UI());
        ((Tracking)components[0]).HR().satisfy(((HeartRateMonitor)components[1]).HR());
        ((HeartRateMonitor)components[1]).HR().satisfy(((Tracking)components[0]).HR());
        ((Tracking)components[0]).LOC().satisfy(((Location)components[3]).LOC());
        ((Location)components[3]).LOC().satisfy(((Tracking)components[0]).LOC());
    }
    @Override
    public void initialize() {
        for ( IComponent<?> component : components ) {
            component.getRunContext().execute( new GenericExecutionTask() {
                @Override
                public void run() throws XtumlException {
                    component.initialize();
                }
            });
        }
    }
    @Override
    public void start() {
        for ( IRunContext executor : executors ) {
            executor.start();
        }
    }
    @Override
    public void printVersions() {
        io.ciera.runtime.Version.printVersion();
        for ( IComponent<?> c : components ) {
            System.out.printf("%s: %s (%s)", c.getClass().getName(), c.getVersion(), c.getVersionDate());
            System.out.println();
        }
    }
    public static void main( String[] args ) {
        IApplication app = new GPS_WatchApplication();
        app.setup( args );
        if ( Arrays.asList(args).contains("-v") || Arrays.asList(args).contains("--version") ) {
            app.printVersions();
        }
        else {
            app.initialize();
            app.start();
        }
    }
}
