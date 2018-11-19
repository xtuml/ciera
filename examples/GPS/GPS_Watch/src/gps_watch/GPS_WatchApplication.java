package gps_watch;

import java.util.Arrays;

import io.ciera.runtime.instanceloading.sql.util.SQL;
import io.ciera.runtime.instanceloading.sql.util.impl.SQLImpl;
import io.ciera.runtime.summit.application.ApplicationExecutor;
import io.ciera.runtime.summit.application.IApplication;
import io.ciera.runtime.summit.application.IRunContext;
import io.ciera.runtime.summit.application.tasks.GenericExecutionTask;
import io.ciera.runtime.summit.application.tasks.HaltExecutionTask;
import io.ciera.runtime.summit.components.IComponent;
import io.ciera.runtime.summit.exceptions.XtumlException;
import io.ciera.runtime.summit.types.StringUtil;
import io.ciera.runtime.summit.util.CMD;
import io.ciera.runtime.summit.util.impl.CMDImpl;

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
        components[2] = new Tracking(this, executors[0], 2);
        components[0] = new HeartRateMonitor(this, executors[0], 0);
        components[1] = new UI(this, executors[0], 1);
        components[3] = new Location(this, executors[0], 3);
        ((Tracking)components[2]).UI().satisfy(((UI)components[1]).UI());
        ((UI)components[1]).UI().satisfy(((Tracking)components[2]).UI());
        ((Tracking)components[2]).LOC().satisfy(((Location)components[3]).LOC());
        ((Location)components[3]).LOC().satisfy(((Tracking)components[2]).LOC());
        ((Tracking)components[2]).HR().satisfy(((HeartRateMonitor)components[0]).HR());
        ((HeartRateMonitor)components[0]).HR().satisfy(((Tracking)components[2]).HR());
    }
    @Override
    public void initialize() {
    	executors[0].execute(new GenericExecutionTask() {
			@Override
			public void run() throws XtumlException {
				SQL trkLoader = new SQLImpl<>((Tracking)components[2]);
				SQL hrmLoader = new SQLImpl<>((HeartRateMonitor)components[0]);
				SQL locLoader = new SQLImpl<>((Location)components[3]);
				CMD cmd = new CMDImpl<>((Tracking)components[2]);
                cmd.register_value("cwd", "root_dir", "base working directory", ".", false );
                cmd.register_value("trk", "trk_input_file", "tracking input file", "", false );
                cmd.register_value("hrm", "hrm_input_file", "heart rate monitor input file", "", false );
                cmd.register_value("loc", "loc_input_file", "location input file", "", false );
                cmd.read_command_line();
                String trk_input_file = cmd.get_value("trk");
                if (StringUtil.inequality("", trk_input_file)) {
                	trkLoader.load_file(trk_input_file);
                }
                String hrm_input_file = cmd.get_value("hrm");
                if (StringUtil.inequality("", hrm_input_file)) {
                	hrmLoader.load_file(hrm_input_file);
                }
                String loc_input_file = cmd.get_value("loc");
                if (StringUtil.inequality("", loc_input_file)) {
                	locLoader.load_file(loc_input_file);
                }
			}
		});
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
    @Override
    public void stop() {
    	executors[0].execute(new GenericExecutionTask() {
			@Override
			public void run() throws XtumlException {
				SQL trkLoader = new SQLImpl<>((Tracking)components[2]);
				SQL hrmLoader = new SQLImpl<>((HeartRateMonitor)components[0]);
				SQL locLoader = new SQLImpl<>((Location)components[3]);
                trkLoader.serialize_file("Tracking.xtuml");
                hrmLoader.serialize_file("HeartRateMonitor.xtuml");
                locLoader.serialize_file("Location.xtuml");
                executors[0].execute(new HaltExecutionTask());
			}
		});
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
