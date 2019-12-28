    public static class ${self.name} extends Event {
        public ${self.name}(IRunContext runContext, int populationId\
.if ( "" != parameter_list )
, ${parameter_list}\
.end if
) {
            super(runContext, populationId\
.if ( "" != parameter_list )
, new Object[]{${invocation_parameter_list}}\
.end if
);
        }
        @Override
        public int getId() {
            return ${self.id};
        }
        @Override
        public String getClassName() {
            return "${class_name}";
        }
    }
