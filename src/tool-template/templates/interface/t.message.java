    public void ${self.name}(${parameter_list}) throws XtumlException;
    public static final int SIGNAL_NO_$u{self.name} = ${self.id};
    public static class $c{self.name} extends Message {
.if ( "" != parameter_list )
        public $c{self.name}(${parameter_list}) throws XtumlException {
.if ( not serialize )        	
            super(new Object[]{${invocation_parameter_list}});
.else
${addparms}\
.end if
        }
.end if
        @Override
        public int getId() {
            return SIGNAL_NO_$u{self.name};
        }
    }
    