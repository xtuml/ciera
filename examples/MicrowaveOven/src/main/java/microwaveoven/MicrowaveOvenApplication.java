package microwaveoven;

import java.util.logging.Level;

import components.MicrowaveOven;
import io.ciera.runtime.application.Application;
import io.ciera.runtime.application.DefaultLogger;
import io.ciera.runtime.application.SimulatedClock;

public class MicrowaveOvenApplication extends Application {

    public MicrowaveOvenApplication() {
        this("MicrowaveOvenApplication", new String[0]);
    }

    public MicrowaveOvenApplication(String[] args) {
        this("MicrowaveOvenApplication", args);
    }

    public MicrowaveOvenApplication(String name, String[] args) {
        super(name, args);
    }

    @Override
    public void setup() {
        super.setup();

        // create domains
        addDomain(new MicrowaveOven("MicrowaveOven", this));

        // create custom logger with tracing enabled
        setLogger(new DefaultLogger("MicrowaveOvenTraceLogger", this, Level.FINEST));

        // create simulated clock
        setClock(new SimulatedClock());
    }

    public static void main(String[] args) {
        Application app = new MicrowaveOvenApplication();
        app.setup();
        app.initialize();
        app.start();
    }

}
