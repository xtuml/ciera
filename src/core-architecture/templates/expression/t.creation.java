getDomain().createInstance(${self.class_name}.class\
.if ((not_empty init_state) or (attr_initializers != ""))
, inst -> {
  .if (not_empty init_state)
    inst.setCurrentState(${self.sm_name}.States.${self.initial_state_name});
  .end if
  .if (attr_initializers != "")
    ${attr_initializers}\
  .end if
}\
.end if
)\
