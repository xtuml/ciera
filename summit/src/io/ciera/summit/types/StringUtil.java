package io.ciera.summit.types;

public class StringUtil {

    public static boolean equality( String value1, String value2 ) {
        if ( null != value1 ) return value1.equals(value2);
        else if ( null != value2 ) return value2.equals(value1);
        else return true;
    }
    
    public static boolean inequality( String value1, String value2 ) {
        return !equality(value1, value2);
    }

    public static boolean greaterThan( String value1, String value2 ) {
        if ( null != value1 ) return value1.compareTo(value2) > 0;
        else if ( null != value2 ) return value2.compareTo(value1) < 0;
        else return false;
    }

    public static boolean lessThan( String value1, String value2 ) {
        if ( null != value1 ) return value1.compareTo(value2) < 0;
        else if ( null != value2 ) return value2.compareTo(value1) > 0;
        else return false;
    }

    public static boolean greaterThanOrEqual( String value1, String value2 ) {
        if ( null != value1 ) return value1.compareTo(value2) >= 0;
        else if ( null != value2 ) return value2.compareTo(value1) <= 0;
        else return true;
    }

    public static boolean lessThanOrEqual( String value1, String value2 ) {
        if ( null != value1 ) return value1.compareTo(value2) <= 0;
        else if ( null != value2 ) return value2.compareTo(value1) >= 0;
        else return true;
    }

}
