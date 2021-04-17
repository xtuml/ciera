    public void serialize_${selector_name}( ${class_name} $l{class_name}_inst, PrintStream out ) throws XtumlException {
    	if ( $l{class_name}_inst.${selector_name}() != null ) {
            out.print( "LINK ${rnum_str} ID2(" );
            out.print($l{class_name}_inst.getInstanceId().serialize()  );
            out.print(", ");
            out.print($l{class_name}_inst.${selector_name}().getInstanceId().serialize() );
            out.println( ");" );
    	}
    }
