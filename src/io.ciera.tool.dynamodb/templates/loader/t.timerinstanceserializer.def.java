    public void serialize_${self.class_name}( Timer $l{self.class_name}_inst, PrintStream out ) throws XtumlException {
        out.print( "INSERT INTO ${self.class_name} VALUES(" );
${attribute_serializers}        out.println( ");" );
    }
