package ${self.package};

${imports}

public class ${self.name} extends DynamoDBLoader {

    private ${self.comp_name} population;

    public ${self.name}(${self.comp_name} population) {
        super();
        this.population = population;
    }

    @Override
    public void load() throws XtumlException {
        for (String tableName : getTableNames()) {
            for (Item values : getItems(tableName, population.getId())) {
                switch (tableName) {
${instance_loaders}                default:
                    break;
                }
            }
        }
        batchRelate();
    }

    private void batchRelate() throws XtumlException {
${batch_relators}    }

${batch_relator_definitions}

    @Override
    public void serialize(IChangeLog changeLog) throws XtumlException {
        for (IModelDelta delta : changeLog.getChanges()) {
            Table table = getDb().getTable(delta.getElementName());
            if (delta instanceof IInstanceDeletedDelta) {
                deleteItem(table, population.getId(), delta.getElementId());
            }
            else if (delta instanceof IAttributeChangedDelta || delta instanceof IInstanceCreatedDelta) {
                switch (delta.getElementName()) {
${instance_serializers}                default:
                    break;
                }
            }
        }
    }

    @Override
    public void initialize() {
        super.initialize();
${instance_serializer_initializers}    }

${instance_serializer_definitions}

}
