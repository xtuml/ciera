package components.microwaveoven.datatypes;


import io.ciera.runtime.summit.exceptions.XtumlException;
import io.ciera.runtime.summit.types.IXtumlType;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public enum Tube_Wattage implements IXtumlType {

    UNINITIALIZED_ENUM( -1 ),
    HIGH( 0 ),
    LOW( 1 ),
    MEDIUM( 4 ),
    MED_HIGH( 2 ),
    MED_LOW( 3 );

    private final int value;

    Tube_Wattage() {
        value = -1;
    }

    Tube_Wattage( int value ) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public boolean equality( IXtumlType value ) throws XtumlException {
        if (value instanceof Tube_Wattage) {
            return null != value && this.value == ((Tube_Wattage)value).getValue();
        }
        else return false;
    }

    @Override
    public String serialize() {
        return Integer.toString(value);
    }

    public static Tube_Wattage deserialize(Object o) throws XtumlException {
        if (o instanceof Tube_Wattage) {
            return (Tube_Wattage)o;
        }
        else if (o instanceof Integer) {
            return valueOf((int)o);
        }
        else if (o instanceof String) {
            try {
                return valueOf(Integer.parseInt((String)o));
            }
            catch (NumberFormatException e1) {
                Matcher m = Pattern.compile("([A-Za-z][A-Za-z0-9_]*)::([A-Za-z][A-Za-z0-9_]*)").matcher((String)o);
                if (m.matches() && "tube_wattage".equals(m.group(1).toLowerCase())) {
                    try {
                        return Enum.valueOf(Tube_Wattage.class, m.group(2).toUpperCase());
                    } catch (IllegalArgumentException e2) {/* do nothing */}
                }
            }
        }
        throw new XtumlException("Cannot deserialize value: " + o != null ? o.toString() : "null");
    }

    public static Tube_Wattage valueOf(int value) {
        switch( value ) {
        case 0:
            return HIGH;
        case 1:
            return LOW;
        case 4:
            return MEDIUM;
        case 2:
            return MED_HIGH;
        case 3:
            return MED_LOW;
        default:
            return UNINITIALIZED_ENUM;
        }
    }

}
