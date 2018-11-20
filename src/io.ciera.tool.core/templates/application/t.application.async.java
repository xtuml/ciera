package ${self.package};

${imports}

public class ${self.name} implements IApplication {

    private IComponent<?>[] components;
    private IRunContext executor;

    public ${self.name}() {
        components = new IComponent<?>[$t{num_component_instances}];
    }

    @Override
    public void setup( String[] args ) {
        executor = new ApplicationExecutor("${self.name}Executor", args);
${component_instantiations}${component_satisfactions}    }

    @Override
    public void printVersions() {
        io.ciera.runtime.Version.printVersion();
        for ( IComponent<?> c : components ) {
            System.out.printf("%s: %s (%s)", c.getClass().getName(), c.getVersion(), c.getVersionDate());
            System.out.println();
        }
    }

    public void handleSignal(final String data) {
        executor.performTransaction(new GenericExecutionTask() {
            @Override
            public void run() throws XtumlException {
                // load population
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
                // serialize population
            }
        });
    }

    public void heartbeat() {
        executor.performTransaction(new GenericExecutionTask() {
            @Override
            public void run() throws XtumlException {
                // load population
            }
        });
        executor.heartbeat();
        executor.performTransaction(new GenericExecutionTask() {
            @Override
            public void run() throws XtumlException {
                // serialize population
            }
        });
    }

    @Override
    public void initialize() {}

    @Override
    public void start() {}

    @Override
    public void stop() {}

    public static void main(String[] args) {
        ${self.name} app = new ${self.name}();
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
