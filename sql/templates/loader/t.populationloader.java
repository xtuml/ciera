package ${self.package};

${imports}

public class ${self.name} implements IPopulationLoader {

    private ${self.comp_name} population;

    public ${self.name}( ${self.comp_name} population ) {
        this.population = population;
    }

    public void insert( String tableName, List<Object> values ) throws XtumlException {
        switch ( tableName ) {
${instance_loaders}        default:
            throw new XtumlException( "Class not supported by this population." );
        }
    }
    public void finish() throws XtumlException {
${batch_relators}    }

    public void serialize( OutputStream stream ) throws XtumlException {
        PrintStream out = new PrintStream( stream );
${instance_serializers}        out.flush();
    }

}
