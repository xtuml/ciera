    public static final int SIGNAL_NO_$u{self.name} = ${self.id};
    public static class S$c{self.name} extends Message {
.if ( "" != parameter_list )
        public S$c{self.name}(${parameter_list}) {
            super(new Object[]{${invocation_parameter_list}});
        }
.end if
        @Override
        public int getId() {
            return SIGNAL_NO_$u{self.name};
        }
 
    }
    public ${type_name} ${self.name}(${parameter_list}) throws XtumlException;
