.if (finalization_num > 0)
_final${finalization_num}_\
.end if
${var_name}\
.if (is_passed_by_ref and (empty by_ref_act_parm) and (empty assign_lhs) and (not self.declaration))
..get()\
.end if
.if (is_passed_by_ref and self.declaration and (empty assign_lhs))
 = new LocalVariable<>()\
.end if
