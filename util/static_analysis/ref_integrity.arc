.// This archetype performs static analysis on the activities to check for
.// potential issues with referential integrity.
.//
.// For each create statement, it checks that:
.//   1. All identifiers for the instance are initialized before the end of the
.//      body
.//   2. The instance is not used in a relate statement for a formalized
.//      association in which it is the participant before all its identifying
.//      attributes are set (in the formalizing identifier)
.//
.// For each assignement of an identifying attribute (regular assignment or
.// relate statement for referential as identifying attribute), it checks that:
.//   1. There is a create statement earlier in the body
.//   2. It is not a reassigment
.//
.// The algorithm considers all "while" and "for" blocks potentially
.// unreachable. "if", "elif", and "else" blocks are considered potentially
.// unreachable, however, if an idntifing attribute is set in all blocks of an
.// "if" statement, it is considered to be guaranteed initialized.
.//
.function emit_warning
  .param inst_ref smt
  .param string msg
  .select one act related by smt->ACT_BLK[R602]->ACT_ACT[R601]
  .print "Warning: ${act.Label} line: ${smt.LineNumber} -- ${msg}"
.end function
.//
.function check_creates
  .select many bodies from instances of ACT_ACT
  .for each body in bodies
    .// get all create no variables. For these statements, they are invalide
    .// unless they have no identifying attributes
    .select many create_nv_smts related by body->ACT_BLK[R601]->ACT_SMT[R602]->ACT_CNV[R603]
    .for each create_nv_smt in create_nv_smts
      .select any id_attr related by create_nv_smt->O_OBJ[R672]->O_ID[R104]->O_OIDA[R105]
      .if ( not_empty id_attr )
        .select one smt related by create_nv_smt->ACT_SMT[R603]
        .select one obj related by create_nv_smt->O_OBJ[R672]
        .invoke emit_warning( smt, "Create no variable statement used with class that has identifying attributes: ${obj.Key_Lett}" )
      .end if
    .end for
    .// get all create statements
    .select many create_smts related by body->ACT_BLK[R601]->ACT_SMT[R602]->ACT_CR[R603]
    .for each create_smt in create_smts
      .select one smt related by create_smt->ACT_SMT[R603]
    .end for
  .end for
.end function
.//
.function check_assignments
.end function
.//
.function analyze
  .invoke check_creates()
  .invoke check_assignments()
.end function
.//
.//------ MAIN CODE -------//
.invoke analyze()
