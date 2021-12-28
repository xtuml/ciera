package ${self.package};

${imports}

public class ${self.name} extends Application {

    public ${self.name}() {
        this("${self.name}", new String[0]);
    }

    public ${self.name}(String[] args) {
        this("${self.name}", args);
    }

    public ${self.name}(String name, String[] args) {
        super(name, args);
    }

    @Override
    public void setup() {
        super.setup();

        // create domains
        ${domains}

        // link ports
        ${component_satisfactions}

.if (self.simulated_time)
        // create simulated clock
        setClock(new SimulatedClock());

.end if
    }

    public static void main(String[] args) {
        Application app = new ${self.name}();
        app.setup();
        app.initialize();
        app.start();
    }

}
