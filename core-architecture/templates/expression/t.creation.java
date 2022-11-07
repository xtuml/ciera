getDomain().createInstance(${self.class_name}::new\
.if ((not_empty init_state) or (attr_initializers != ""))
, (inst, domain) -> {
    inst.initialize(domain\
  .if (not_empty init_state)
, ${self.class_name}.States.${self.initial_state_name}\
  .end if
);
  .if (attr_initializers != "")
    ${attr_initializers}\
  .end if
}\
.else
, ${self.class_name}::initialize\
.end if
)\
