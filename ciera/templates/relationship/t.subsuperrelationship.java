package ${self.package};

${imports}

public class ${self.name} extends ${extends} {

    private static final int relNum = $t{num};

    // supertype class
    private UniqueId ${self.supertype_class_name};

    // subtype class
    private UniqueId subtype;

    public ${self.name}( UniqueId ${self.supertype_class_name}, UniqueId subtype ) throws XtumlException {
        this.${self.supertype_class_name} = ${self.supertype_class_name};
        this.subtype = subtype;
    }

    @Override
    public UniqueId getSupertype() {
        return ${self.supertype_class_name};
    }

    @Override
    public UniqueId getSubtype() {
        return subtype;
    }

    @Override
    public int getNumber() {
        return relNum;
    }

    @Override
    public void delete() {
    }

}
