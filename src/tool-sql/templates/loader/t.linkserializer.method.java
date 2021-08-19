    public void serialize_${rnum_str}( ${class_name} $l{class_name}_inst, PrintStream out ) throws XtumlException {
${init}
    ${self_serialize} \
${referred_ids} \
          out.println( ");" );
    	}
    }
