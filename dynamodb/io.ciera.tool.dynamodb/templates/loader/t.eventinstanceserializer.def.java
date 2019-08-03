    public void serialize_${self.class_name}(Table table, IModelDelta delta) throws XtumlException {
        IEvent $l{self.class_name}_inst = (IEvent)delta.getModelElement();
        Item item = new Item();
${attribute_serializers}        table.putItem(item);
    }
