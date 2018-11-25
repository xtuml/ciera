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
            ScanResult scan = getDb().scan(new ScanRequest().withTableName(tableName));
            for (Map<String, AttributeValue> inst : scan.getItems()) {
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
            if (!getTableNames().contains(delta.getElementName())) createTable(delta.getElementName());
            Table table = new DynamoDB(getDb()).getTable(delta.getElementName());
            if (delta instanceof IInstanceDeletedDelta) {
                deleteItem(table, delta.getElementId());
            }
            else if (delta instanceof IAttributeChangedDelta || delta instanceof IInstanceCreatedDelta) {
                switch (delta.getElementName()) {
${instance_serializers}                default:
                    break;
                }
            }
        }
    }

${instance_serializer_definitions}

}
