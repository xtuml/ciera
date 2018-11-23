package ${self.package};

${imports}

public class ${self.name} extends SqlLoader {

    private ${self.comp_name} population;

    public ${self.name}( ${self.comp_name} population ) {
        this.population = population;
    }

    @Override
    public void insert(String tableName, List<Object> values) throws XtumlException {
        switch ( tableName ) {
${instance_loaders}        default:
            //throw new XtumlException( "Class not supported by this population." );
            break;
        }
    }

    @Override
    public void batchRelate() throws XtumlException {
${batch_relators}    }

${batch_relator_definitions}

    @Override
    public void serialize() throws XtumlException {
        PrintStream out = new PrintStream(getOut());
${instance_serializers}        out.flush();
    }

${instance_serializer_definitions}

}
