.if (base_name != "")
${base_name}.\
.end if
.// for constructors, the parent name is the same as the invocation name
.if (self.invoked_parent_name == self.invoked_name)
new \
.end if
${self.invoked_name}(${parameter_list})\
