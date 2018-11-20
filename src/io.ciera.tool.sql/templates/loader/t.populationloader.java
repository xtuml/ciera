package ${self.package};

${imports}

public class ${self.name} implements IPopulationLoader {

    private ${self.comp_name} population;
    private ProgressBar progressBar;

    public ${self.name}( ${self.comp_name} population ) {
        this.population = population;
    }

    @Override
    public void insert( String tableName, List<Object> values ) throws XtumlException {
        switch ( tableName ) {
${instance_loaders}        default:
            //throw new XtumlException( "Class not supported by this population." );
            break;
        }
    }

    @Override
    public void finish() throws XtumlException {
        progressBar = new ProgressBar(${relate_count});
${batch_relators}        progressBar.join();
    }

${batch_relator_definitions}

    @Override
    public void serialize( OutputStream stream ) throws XtumlException {
        progressBar = new ProgressBar(${instance_count});
        //System.err.println( "Serializing instances..." );
        PrintStream out = new PrintStream( stream );
${instance_serializers}        out.flush();
        progressBar.join();
    }

${instance_serializer_definitions}

}
