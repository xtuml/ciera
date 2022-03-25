package ${self.package};

${imports}

public class ${self.name} extends BaseApplication implements Application {

    private static volatile Application instance;

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
        findDomains(${domains}).forEach(this::addDomain);

        // link ports
        ${component_satisfactions}

.if (self.simulated_time != "")
        // create simulated clock
        setClock(new SimulatedClock(${self.simulated_time}));

.end if
    }

    public static Application provider() {
        if (instance == null) {
            instance = new ${self.name}();
            instance.setup();
        }
        return instance;
    }

    public static void main(String[] args) {
        CommandLine.setArgs(args);
        Application app = Application.getInstance();
        app.initialize();
        app.start();
    }

}
