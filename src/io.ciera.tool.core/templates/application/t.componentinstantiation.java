        components[$t{self.index}] = new ${self.comp_name}(this, \
.if ( -1 == self.executor_index )
executor\
.else
executors[$t{self.executor_index}]\
.end if
, $t{self.index});
