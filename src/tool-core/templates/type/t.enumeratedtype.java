package ${self.package};

${imports}

public enum ${self.name} implements IXtumlType {

    UNINITIALIZED_ENUM( -1 )${enumerators}

    private final int value;

    ${self.name}() {
        value = -1;
    }

    ${self.name}( int value ) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public boolean equality( IXtumlType value ) throws XtumlException {
        if (value instanceof ${self.name}) {
            return null != value && this.value == ((${self.name})value).getValue();
        }
        else return false;
    }

    @Override
    public String serialize() {
        return Integer.toString(value);
    }

    public static ${self.name} deserialize(Object o) throws XtumlException {
        if (o instanceof ${self.name}) {
            return (${self.name})o;
        }
        else {
            int value;
            if (o instanceof Integer) {
                value = (int)o;
            }
            else if (o instanceof String) {
                value = Integer.parseInt((String)o);
            }
            else throw new XtumlException("Cannot deserialize value");
            return valueOf(value);
        }
    }

    public static ${self.name} valueOf(int value) {
        switch( value ) {
${enumerator_accessors}        default:
            return UNINITIALIZED_ENUM;
        }
    }

}
