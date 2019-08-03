    public void serialize_${self.class_name}(Table table, IModelDelta delta) throws XtumlException {
        Timer $l{self.class_name}_inst = (Timer)delta.getModelElement();
        Item item = new Item();
${attribute_serializers}        table.putItem(item);
    }
