    public static class ${self.name} extends Event {
.if ( "" != parameter_list )
        public ${self.name}(IRunContext runContext, ${parameter_list}) {
            super(runContext, new Object[]{${invocation_parameter_list}});
.else
        public ${self.name}(IRunContext runContext) {
            super(runContext);
.end if
        }
        @Override
        public int getId() {
            return ${self.id};
        }
    }
