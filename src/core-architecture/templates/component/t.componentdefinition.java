package ${self.package};

${imports}

public class ${self.name} extends ${self.extends} {

    private Map<String, Class<?>> classDirectory;

    public ${self.name}(IApplication app, IRunContext runContext, int populationId) {
        super(app, runContext, populationId);
${instance_extent_initializers}
${utility_initializers}
        classDirectory = new TreeMap<>();
${class_directory}
    }

    // domain functions
${functions}

    // relates and unrelates
${relationship_modifiers}

    // instance selections
${instance_selectors}

    // ports
${ports}

    // utilities
${utilities}

    // component initialization function
    @Override
    public void initialize() throws XtumlException {
${init}
    }

.if ( "" != self.version )
    @Override
    public String getVersion() {
        Properties prop = new Properties();
        try {
            prop.load(getClass().getResourceAsStream("${self.name}Properties.properties"));
        } catch (IOException e) { /* do nothing */ }
        return prop.getProperty("version", "Unknown");
    }
.end if
.if ( "" != self.version_date )
    @Override
    public String getVersionDate() {
        Properties prop = new Properties();
        try {
            prop.load(getClass().getResourceAsStream("${self.name}Properties.properties"));
        } catch (IOException e) { /* do nothing */ }
        return prop.getProperty("version_date", "Unknown");
    }
.end if

    @Override
    public boolean addInstance( IModelInstance<?,?> instance ) throws XtumlException {
        if ( null == instance ) throw new BadArgumentException( "Null instance passed." );
        if ( instance.isEmpty() ) throw new EmptyInstanceException( "Cannot add empty instance to population." );
${instance_adds}
        return false;
    }

    @Override
    public boolean removeInstance( IModelInstance<?,?> instance ) throws XtumlException {
        if ( null == instance ) throw new BadArgumentException( "Null instance passed." );
        if ( instance.isEmpty() ) throw new EmptyInstanceException( "Cannot remove empty instance from population." );
${instance_removes}
        return false;
    }

    @Override
    public ${self.name} context() {
        return this;
    }

    @Override
    public Class<?> getClassByKeyLetters(String keyLetters) {
        return classDirectory.get(keyLetters);
    }

}
