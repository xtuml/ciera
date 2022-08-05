getDomain().createInstance(${self.class_name}::new\
.if ((not_empty init_state) or (attr_initializers != ""))
, inst -> {
  .if (not_empty init_state)
    //inst.setCurrentState(${self.sm_name}.States.${self.initial_state_name});  TODO
  .end if
  .if (attr_initializers != "")
    ${attr_initializers}\
  .end if
}\
.end if
)\
