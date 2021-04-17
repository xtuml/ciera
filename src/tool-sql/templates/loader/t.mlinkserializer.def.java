    public void serialize_${selector_name}( ${class_name} $l{class_name}_inst, PrintStream out ) throws XtumlException {
    	${set_type_name} links = $l{class_name}_inst.${selector_name}();
    	for ( ${referred_name} link : links ) {
            out.print( "LINK ${rnum_str} ID2(" );
            out.print($l{class_name}_inst.getInstanceId().serialize()  );
            out.print(", ");
            out.print(link.getInstanceId().serialize() );
            out.println( ");" );
    	}
    }
