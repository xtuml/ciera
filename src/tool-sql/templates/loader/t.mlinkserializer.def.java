    public void serialize_${selector_name}( ${class_name} $l{class_name}_inst, PrintStream out ) throws XtumlException {
    	${set_type_name} links = $l{class_name}_inst.${selector_name}();
    	for ( ${referred_name} ${iterator}_inst : links ) {
            out.print( "LINK ${rnum_str} ID2(" );
    ${first_out}
            out.print(", ");
    ${second_out}
            out.println( ");" );
    	}
    }
