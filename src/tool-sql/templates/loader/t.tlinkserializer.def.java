   public void serialize_${selector_name}( ${class_name} $l{class_name}_inst, PrintStream out ) throws XtumlException {
    	if ( $l{class_name}_inst.${selector_name}() != null ) {
            out.print( "LINK3 ${rnum_str} IDS(" );
    ${first_out}
            out.print(", ");
    ${second_out}
            out.print(", ");
    ${third_out}
            out.println( ");" );
    	}
    }
