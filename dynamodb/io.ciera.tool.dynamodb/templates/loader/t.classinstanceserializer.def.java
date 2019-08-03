    public void serialize_${self.class_name}(Table table, IModelDelta delta) throws XtumlException {
        ${self.class_name} $l{self.class_name}_inst = (${self.class_name})delta.getModelElement();
        Item item = new Item();
${attribute_serializers}        table.putItem(item);
    }
