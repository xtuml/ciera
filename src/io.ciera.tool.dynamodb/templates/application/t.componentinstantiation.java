        components[$t{self.index}] = new ${self.comp_name}(this, \
.if ( -1 == self.executor_index )
executor\
.else
executors[$t{self.executor_index}]\
.end if
, $t{self.index});
.if ( self.simulated_time )
  .if ( -1 == self.executor_index )
executor\
  .else
executors[$t{self.executor_index}]\
  .end if
        ..enableSimulatedTime(true);
.end if
.if ("" != self.instance_loading)
        components[${self.index}].addLoader("${self.instance_loading}", new ${self.comp_name}${self.instance_loading}Loader((${self.comp_name})components[${self.index}]));
.end if
