.if (self.native)
public native ${type_name} ${self.name}(${parameter_list});
.else
public ${type_name} ${self.name}(${parameter_list})\
  .if (body == "")
 {
    // TODO Insert your implementation here
    .if (return_type.name != "void")
    return ${return_type.default_value};
    .end if
}
  .else
 ${body}
  .end if
.end if

