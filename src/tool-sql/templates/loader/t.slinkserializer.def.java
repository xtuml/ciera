    public void serialize_${rel_name}( ${class_name} $l{class_name}_inst, PrintStream out ) throws XtumlException {
    	if ( $l{class_name}_inst.${rel_name}() != null ) {
            out.print( "LINK ${rel_name} ID2(" );
            out.print($l{class_name}_inst.getInstanceId().serialize()  );
            out.print(", ");
            out.print($l{class_name}_inst.${rel_name}().getInstanceId().serialize() );
            out.println( ");" );
    	}
    }
