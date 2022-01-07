_schedule\
.if (self.is_recurring)
Recurring\
.end if
Event(${evt_expr}, ${target_expr}, ${time_expr}\
.if (self.is_recurring)
, ${period_expr}\
.end if
${parameter_list})\
