    public void serialize_${selector_name}( ${class_name} $l{class_name}_inst, PrintStream out ) throws XtumlException {
        ${init}
        ${self_serialize}
${links} \
          out.println( ");" );
    	}
    }