package ${self.package};

${imports}

public class ${self.name} implements IApplication {

    private IComponent<?>[] components;
    private ApplicationExecutor executor;

    public ${self.name}() {
        components = new IComponent<?>[$t{num_component_instances}];
    }

    @Override
    public void setup( String[] args, ILogger logger ) {
        if ( null != logger ) {
            executor = new ApplicationExecutor("${self.name}Executor", args, logger);
        }
        else {
            executor = new ApplicationExecutor("${self.name}Executor", args);
        }
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
        final JSONObject msg = new JSONObject(data);
        final int index = msg.getInt("componentId");
        final String portName = msg.getString("portName");
        // load population
        try {
            if (null!= components[index].getDefaultLoader()) components[index].getDefaultLoader().load();
        } catch (XtumlException e) {/* fail silently */}
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
        } catch (XtumlException e) {/* fail silently */}
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
        ${self.name} app = new ${self.name}();
        app.setup(args, null);
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
