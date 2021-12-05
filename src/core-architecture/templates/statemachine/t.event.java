public static final class ${self.name} extends Event {
    public ${self.name}(\
.if ( "" != parameter_list )
${parameter_list}\
.end if
) {
        super(Events.$u_{self.name}\
.if ( "" != parameter_list )
, new Object[]{${invocation_parameter_list}}\
.end if
);
    }
}
