.// This archetype performs analysis on the data model to check that every
.// class has at least one identifier and every relationship is formalized.
.//
.include "util_functions.inc"
.//
.function check_ids
  .select many objs from instances of O_OBJ
  .for each obj in objs
    .select any oida related by obj->O_ID[R104]->O_OIDA[R105]
    .if ( empty oida )
      .invoke create_warning_no_line_number( "Class has no identifier: ${obj.Key_Lett}" )
    .end if
  .end for
.end function
.//
.function is_formalized
  .param inst_ref rel
  .assign attr_ret = false
  .select one simp related by rel->R_SIMP[R206]
  .if ( not_empty simp )
    .select one form related by simp->R_FORM[R208]
    .assign attr_ret = ( not_empty form )
  .else
    .select one assoc related by rel->R_ASSOC[R206]
    .if ( not_empty assoc )
      .select any rtida1 related by assoc->R_AONE[R209]->R_RTO[R204]->O_RTIDA[R110]
      .select any rtida2 related by assoc->R_AOTH[R210]->R_RTO[R204]->O_RTIDA[R110]
      .assign attr_ret =  ( ( not_empty rtida1 ) and ( not_empty rtida2 ) )
    .else
      .select one subsup related by rel->R_SUBSUP[R206]
      .if ( not_empty subsup )
        .select any rtida related by subsup->R_SUPER[R212]->R_RTO[R204]->O_RTIDA[R110]
        .assign attr_ret = ( not_empty rtida )
      .end if
    .end if
  .end if
.end function
.//
.function check_rels
  .select many rels from instances of R_REL
  .for each rel in rels
    .invoke result = is_formalized( rel )
    .if ( not result.ret )
      .invoke create_warning_no_line_number( "Relationship is not formalized: R${rel.Numb}" )
    .end if
  .end for
.end function
.//
.function analyze
  .invoke check_ids()
  .invoke check_rels()
.end function
.//
.//------ MAIN CODE -------//
.invoke analyze()
.invoke display_warnings( "model_check" )
