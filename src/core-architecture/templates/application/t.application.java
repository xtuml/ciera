package ${self.package};

${imports}

public class ${self.name} extends BaseApplication implements Application {

    public ${self.name}() {
        this("${self.name}");
    }

    public ${self.name}(String name) {
        super(name);
    }

    @Override
    public void setup() {
        super.setup();

        // create domains
        ${domains}

        // link ports
        ${component_satisfactions}

.if (self.simulated_time != "")
        // create simulated clock
        setClock(new SimulatedClock(${self.simulated_time}));

.end if
    }

    public static void main(String[] args) {
        CommandLine.setArgs(args);
        Application app = new ${self.name}();
        app.setup();
        app.initialize();
        app.start();
    }

}
