package ${self.package};

${imports}

public enum ${self.name} implements IXtumlType<${self.name}> {

    UNINITIALIZED_ENUM( -1 )${enumerators}

    private final int value;

    ${self.name}( int value ) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public boolean equality( ${self.name} value ) throws XtumlException {
        return null != value && this.value == value.getValue();
    }

    @Override
    public ${self.name} defaultValue() {
        return UNINITIALIZED_ENUM;
    }

}
