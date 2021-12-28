public static class $c{self.name} extends Message {
    public $c{self.name}(${parameter_list}) {
        super($_u{self.name}\
.if (invocation_parameter_list != "")
, ${invocation_parameter_list}\
.end if
);
    }
}

