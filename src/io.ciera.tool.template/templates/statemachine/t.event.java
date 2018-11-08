    public static class ${self.name} extends Event {
.if ( "" != parameter_list )
        public ${self.name}(${parameter_list}) {
            super(new Object[]{${invocation_parameter_list}});
        }
.end if
        @Override
        public int getId() {
            return ${self.id};
        }
    }
