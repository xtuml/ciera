    public void serialize_${sel_name} ( ${class_name} $l{class_name}_inst, PrintStream out ) throws XtumlException {
    	${set_type} links = $l{class_name}_inst.${sel_name}();
    	for ( ${ref_name} link : links ) {
            out.print( "LINK ${rel_name} ID2(" );
            out.print($l{class_name}_inst.getInstanceId().serialize()  );
            out.print(", ");
            out.print(link.getInstanceId().serialize() );
            out.println( ");" );
    	}
    }
