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
        else if (o instanceof Integer) {
            return valueOf((int)o);
        }
        else if (o instanceof String) {
            try {
                return valueOf(Integer.parseInt((String)o));
            }
            catch (NumberFormatException e1) {
                Matcher m = Pattern.compile("([A-Za-z_]+)::([A-Za-z_]+)").matcher((String)o);
                if (m.matches() && "$l{self.name}".equals(m.group(1).toLowerCase())) {
                    try {
                        return Enum.valueOf(${self.name}.class, m.group(2).toUpperCase());
                    } catch (IllegalArgumentException e2) {/* do nothing */}
                }
            }
        }
        throw new XtumlException("Cannot deserialize value");
    }

    public static ${self.name} valueOf(int value) {
        switch( value ) {
${enumerator_accessors}        default:
            return UNINITIALIZED_ENUM;
        }
    }

}
