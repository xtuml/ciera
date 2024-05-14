package ${self.package};

${imports}

public class ${self.name} extends ${extends_type} implements IXtumlType {

    public ${self.name}() {
        super();
    }

    public ${self.name}(Object value) throws XtumlException {
        super(value);
    }

    @SuppressWarnings("unchecked")
    public ${self.name} promote(Object o) throws XtumlException {
        return new ${self.name}(cast(o));
    }

    public static ${self.name} deserialize(Object o) throws XtumlException {
        throw new NotImplementedException("Type cannot be deserialized");
    }
}
