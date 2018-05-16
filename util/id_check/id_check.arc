.// This archetype performs static analysis on the activities to check for
.// potential issues with model integrity.
.//
.// For each create statement, it checks that:
.//   1. All identifier attributes for the instance are initialized before the
.//      end of the body.
.//   2. The instance is not used in a relate statement for a formalized
.//      association in which it is the participant before all its identifying
.//      attributes are set (in the formalizing identifier).
.//   3. None of the identifier attributes are reassigned by assignment
.//      statement.
.//
.// For each assignment of an identifying attribute (regular assignment or
.// relate statement for referential attribute as identifier), it checks that:
.//   1. There is a create statement earlier in the body
.//
.// The algorithm considers all statements within "while" and "for" blocks
.// potentially unreachable. Statements within "if", "elif", and "else" blocks
.// are considered potentially unreachable, however, if an identifying attribute
.// is set in all blocks of an "if" statement, it is considered to be
.// initialized. Any statement after a "return" statement is considered
.// potentially unreachable.
.//
.// The algorithm considers assignment statements and relate statements for
.// assigning identifier attributes. It does not consider assignments as part
.// of expressions.
.//
.// For relate statements that initialize referential attributes as identifier,
.// reassignments are not flagged -- it is valid to unrelate and re-relate
.// instances in this situation.
.//
.// For relates that refer to the created instance (number 2 above), it is
.// possible to have a valid situation where not all identifying attributes
.// are initialized, but all identifying attributes for the the ID used to
.// formalize the relationship are initialized. For simplicity, this algorithm
.// still throws a warning.
.//
.include "util_functions.inc"
.//
.function is_initialized_in_block
  .param inst_ref var
  .param inst_ref block
  .param inst_ref oida
  .assign attr_ret = false
  .select any oidi related by oida->O_OIDI[R199] where ( ( selected.Var_ID == var.Var_ID ) and ( selected.Block_ID == block.Block_ID ) )
  .if ( not_empty oidi )
    .assign attr_ret = oidi.initialized 
  .end if
.end function
.//
.function is_initialized_in_if
  .param inst_ref var
  .param inst_ref oida
  .param inst_ref if_smt
  .assign attr_ret = true
  .select one if_blk related by if_smt->ACT_BLK[R607]
  .select many elif_blks related by if_smt->ACT_EL[R682]->ACT_BLK[R658]
  .select one else_blk related by if_smt->ACT_E[R683]->ACT_BLK[R606]
  .// check "if" block
  .select any oidi related by oida->O_OIDI[R199] where ( ( selected.Var_ID == var.Var_ID ) and ( selected.Block_ID == if_blk.Block_ID ) )
  .assign attr_ret = ( ( attr_ret ) and ( oidi.initialized ) )
  .// check "elif" blocks
  .for each elif_blk in elif_blks
    .select any oidi related by oida->O_OIDI[R199] where ( ( selected.Var_ID == var.Var_ID ) and ( selected.Block_ID == elif_blk.Block_ID ) )
    .if ( not_empty oidi )
      .assign attr_ret = ( ( attr_ret ) and ( oidi.initialized ) )
    .else
      .assign attr_ret = false
    .end if
  .end for
  .// check "else" block
  .if ( not_empty else_blk )
    .select any oidi related by oida->O_OIDI[R199] where ( ( selected.Var_ID == var.Var_ID ) and ( selected.Block_ID == else_blk.Block_ID ) )
    .if ( not_empty oidi )
      .assign attr_ret = ( ( attr_ret ) and ( oidi.initialized ) )
    .else
      .assign attr_ret = false
    .end if
  .end if
.end function
.//
.function get_unique_id_dt_id
  .select any dt from instances of S_DT where ( selected.Name == "unique_id" )
  .assign attr_dt_id = dt.DT_ID
.end function
.//
.function descend_block
  .param inst_ref smt
  .param boolean in_for_while
  .param inst_ref_set oidas
  .param inst_ref var
  .select any attr_block from instances of ACT_BLK where ( false )
  .select any attr_smt from instances of ACT_SMT where ( false )
  .assign attr_in_for_while = in_for_while
  .select one current_block related by smt->ACT_BLK[R602]
  .select one if_smt related by smt->ACT_IF[R603]
  .select one elif_smt related by smt->ACT_EL[R603]
  .select one else_smt related by smt->ACT_E[R603]
  .select one for_smt related by smt->ACT_FOR[R603]
  .select one while_smt related by smt->ACT_WHL[R603]
  .if ( ( ( ( not_empty if_smt ) or ( not_empty elif_smt ) ) or ( ( not_empty else_smt ) or ( not_empty for_smt ) ) ) or ( not_empty while_smt ) )
    .if ( not_empty if_smt )
      .select one attr_block related by if_smt->ACT_BLK[R607]
    .end if
    .if ( not_empty elif_smt )
      .select one attr_block related by elif_smt->ACT_BLK[R658]
    .end if
    .if ( not_empty else_smt )
      .select one attr_block related by else_smt->ACT_BLK[R606]
    .end if
    .if ( not_empty for_smt )
      .select one attr_block related by for_smt->ACT_BLK[R605]
      .assign attr_in_for_while = true
    .end if
    .if ( not_empty while_smt )
      .select one attr_block related by while_smt->ACT_BLK[R608]
      .assign attr_in_for_while = true
    .end if
    .// create initializations for the new block
    .for each oida in oidas
      .// set each oida to the initialization state
      .// of the parent block
      .select any parent_oidi related by oida->O_OIDI[R199] where ( ( selected.Var_ID == var.Var_ID ) and ( selected.Block_ID == current_block.Block_ID ) )
      .select one attr related by oida->O_ATTR[R105]
      .create object instance oidi of O_OIDI
      .assign oidi.initialized = parent_oidi.initialized
      .relate oidi to var across R199
      .relate oidi to oida across R199
      .relate oidi to attr_block across R198
    .end for
    .// select first statement in inner block
    .select any attr_smt related by attr_block->ACT_SMT[R602]
    .select one prev_smt related by attr_smt->ACT_SMT[R661.'succeeds']
    .while ( not_empty prev_smt )
      .assign attr_smt = prev_smt
      .select one prev_smt related by attr_smt->ACT_SMT[R661.'succeeds']
    .end while
  .end if
.end function
.//
.function ascend_block_forward
  .param inst_ref block
  .param boolean in_for_while
  .select any attr_block from instances of ACT_BLK where ( false )
  .select any attr_smt from instances of ACT_SMT where ( false )
  .assign attr_in_for_while = in_for_while
  .select one if_smt related by block->ACT_IF[R607]
  .select one elif_smt related by block->ACT_EL[R658]
  .select one else_smt related by block->ACT_E[R606]
  .select one for_smt related by block->ACT_FOR[R605]
  .select one while_smt related by block->ACT_WHL[R608]
  .if ( ( ( ( not_empty if_smt ) or ( not_empty elif_smt ) ) or ( ( not_empty else_smt ) or ( not_empty for_smt ) ) ) or ( not_empty while_smt ) )
    .if ( not_empty if_smt )
      .// select the "elif" or "else" if there is one
      .select many next_elif_smts related by if_smt->ACT_EL[R682]->ACT_SMT[R603]
      .if ( not_empty next_elif_smts )
        .select any smallest_smt related by if_smt->ACT_EL[R682]->ACT_SMT[R603]
        .for each next_elif_smt in next_elif_smts
          .if ( next_elif_smt.LineNumber < smallest_smt.LineNumber )
            .assign smallset_smt = next_elif_smt
          .end if
        .end for
        .assign attr_smt = smallest_smt
        .select one attr_block related by attr_smt->ACT_BLK[R602]
      .else
        .select one next_else_smt related by if_smt->ACT_E[R683]
        .if ( not_empty next_else_smt )
          .select one attr_smt related by next_else_smt->ACT_SMT[R603]
          .select one attr_block related by attr_smt->ACT_BLK[R602]
        .else
          .// no elif or else
          .select one smt related by if_smt->ACT_SMT[R603]
          .select one attr_block related by smt->ACT_BLK[R602]
          .select one attr_smt related by smt->ACT_SMT[R661.'precedes']
        .end if
      .end if
    .end if
    .if ( not_empty elif_smt )
      .// select the next "elif" or "else" if there is one
      .select one current_smt related by elif_smt->ACT_SMT[R603]
      .select many next_elif_smts related by elif_smt->ACT_IF[R682]->ACT_EL[R682]->ACT_SMT[R603] where ( selected.LineNumber > current_smt.LineNumber )
      .if ( not_empty next_elif_smts )
        .select any smallest_smt related by elif_smt->ACT_IF[R682]->ACT_EL[R682]->ACT_SMT[R603] where ( selected.LineNumber > current_smt.LineNumber )
        .for each next_elif_smt in next_elif_smts
          .if ( next_elif_smt.LineNumber < smallest_smt.LineNumber )
            .assign smallset_smt = next_elif_smt
          .end if
        .end for
        .assign attr_smt = smallest_smt
        .select one attr_block related by attr_smt->ACT_BLK[R602]
      .else
        .select one next_else_smt related by elif_smt->ACT_IF[R682]->ACT_E[R683]
        .if ( not_empty next_else_smt )
          .select one attr_smt related by next_else_smt->ACT_SMT[R603]
          .select one attr_block related by attr_smt->ACT_BLK[R602]
        .else
          .// no elif or else
          .select one smt related by elif_smt->ACT_IF[R682]->ACT_SMT[R603]
          .select one attr_block related by smt->ACT_BLK[R602]
          .select one attr_smt related by smt->ACT_SMT[R661.'precedes']
        .end if
      .end if
    .end if
    .if ( not_empty else_smt )
      .select one smt related by else_smt->ACT_IF[R683]->ACT_SMT[R603]
      .select one attr_block related by smt->ACT_BLK[R602]
      .select one attr_smt related by smt->ACT_SMT[R661.'precedes']
    .end if
    .if ( not_empty for_smt )
      .select one smt related by for_smt->ACT_SMT[R603]
      .assign attr_in_for_while = false
      .select one attr_block related by smt->ACT_BLK[R602]
      .select one attr_smt related by smt->ACT_SMT[R661.'precedes']
    .end if
    .if ( not_empty while_smt )
      .select one smt related by while_smt->ACT_SMT[R603]
      .assign attr_in_for_while = false
      .select one attr_block related by smt->ACT_BLK[R602]
      .select one attr_smt related by smt->ACT_SMT[R661.'precedes']
    .end if
  .end if
.end function
.//
.function ascend_block_backward
  .param inst_ref block
  .select any attr_block from instances of ACT_BLK where ( false )
  .select any attr_smt from instances of ACT_SMT where ( false )
  .select one if_smt related by block->ACT_IF[R607]
  .select one elif_smt related by block->ACT_EL[R658]
  .select one else_smt related by block->ACT_E[R606]
  .select one for_smt related by block->ACT_FOR[R605]
  .select one while_smt related by block->ACT_WHL[R608]
  .if ( ( ( ( not_empty if_smt ) or ( not_empty elif_smt ) ) or ( ( not_empty else_smt ) or ( not_empty for_smt ) ) ) or ( not_empty while_smt ) )
    .if ( not_empty if_smt )
      .select one attr_smt related by if_smt->ACT_SMT[R603]
    .end if
    .if ( not_empty elif_smt )
      .select one attr_smt related by elif_smt->ACT_IF[R682]->ACT_SMT[R603]
    .end if
    .if ( not_empty else_smt )
      .select one attr_smt related by else_smt->ACT_IF[R683]->ACT_SMT[R603]
    .end if
    .if ( not_empty for_smt )
      .select one attr_smt related by for_smt->ACT_SMT[R603]
    .end if
    .if ( not_empty while_smt )
      .select one attr_smt related by while_smt->ACT_SMT[R603]
    .end if
    .select one attr_block related by attr_smt->ACT_BLK[R602]
  .end if
.end function
.//
.function check_creates
  .select many bodies from instances of ACT_ACT
  .invoke result = get_unique_id_dt_id()
  .assign uniq_id = result.dt_id
  .for each body in bodies
    .// get all create no variables. For these statements, they are invalide
    .// unless they have no identifying attributes
    .select many create_nv_smts related by body->ACT_BLK[R601]->ACT_SMT[R602]->ACT_CNV[R603]
    .for each create_nv_smt in create_nv_smts
      .select any id_attr related by create_nv_smt->O_OBJ[R672]->O_ID[R104]->O_OIDA[R105]->O_ATTR[R105] where ( selected.DT_ID != uniq_id )
      .if ( not_empty id_attr )
        .select one smt related by create_nv_smt->ACT_SMT[R603]
        .select one obj related by create_nv_smt->O_OBJ[R672]
        .invoke create_warning( smt, "Create no variable of class with identifying attributes: ${obj.Key_Lett}" )
      .end if
    .end for
    .// get all create statements
    .select many create_smts related by body->ACT_BLK[R601]->ACT_SMT[R602]->ACT_CR[R603]
    .for each create_smt in create_smts
      .select one smt related by create_smt->ACT_SMT[R603]
      .select one obj related by create_smt->O_OBJ[R671]
      .select one var related by create_smt->V_VAR[R633]
      .select one block related by smt->ACT_BLK[R602]
      .select many oidas related by obj->O_ID[R104]->O_OIDA[R105]
      .for each oida in oidas
        .// set each oida uninitialized
        .// unique_id attrs are automatically initialized
        .select one attr related by oida->O_ATTR[R105]
        .create object instance oidi of O_OIDI
        .assign oidi.initialized = ( attr.DT_ID == uniq_id )
        .relate oidi to var across R199
        .relate oidi to oida across R199
        .relate oidi to block across R198
      .end for
      .// iterate through all following statements
      .select one smt related by smt->ACT_SMT[R661.'precedes']
      .assign done = false
      .assign original_block = block
      .assign in_for_while = false
      .assign after_return = false
      .while ( ( not_empty block ) and ( not done ) )
        .while ( ( not_empty smt ) and ( not done ) )
          .//.print "Processing statement on line: ${smt.LineNumber}"
          .// check assignment statement initializations
          .select one assign_smt related by smt->ACT_AI[R603]
          .if ( not_empty assign_smt )
            .select one assigned_to_oattr related by assign_smt->V_VAL[R689]->V_AVL[R801]->O_ATTR[R806]
            .select one assigned_to_obj related by assigned_to_oattr->O_OBJ[R102]
            .if ( assigned_to_obj == obj )
              .select many assigned_to_oidas related by assigned_to_oattr->O_OIDA[R105]
              .for each assigned_to_oida in assigned_to_oidas
                .select any oidi related by assigned_to_oida->O_OIDI[R199] where ( ( selected.Var_ID == var.Var_ID ) and ( selected.Block_ID == block.Block_ID ) )
                .if ( oidi.initialized )
                  .invoke create_warning( smt, "Reassignment of identifying attribute: ${var.Name}.${assigned_to_oattr.Name}" )
                .else
                  .if ( ( not in_for_while ) and ( not after_return ) )
                    .assign oidi.initialized = true
                  .end if
                .end if
              .end for
            .end if
          .end if
          .// check relate statement initializations
          .select one relate_smt related by smt->ACT_REL[R603]
          .select one relate_using_smt related by smt->ACT_RU[R603]
          .if ( ( not_empty relate_smt ) or ( not_empty relate_using_smt ) )
            .select one one_var related by relate_using_smt->V_VAR[R617]
            .select one oth_var related by relate_using_smt->V_VAR[R618]
            .select one use_var related by relate_using_smt->V_VAR[R619]
            .if ( ( empty one_var ) and ( empty oth_var ) )
              .select one one_var related by relate_smt->V_VAR[R615]
              .select one oth_var related by relate_smt->V_VAR[R616]
            .end if
            .if ( ( ( var == one_var ) or ( var == oth_var ) ) or ( var == use_var ) )
              .select one rel related by relate_using_smt->R_REL[R654]
              .if ( empty rel )
                .select one rel related by relate_smt->R_REL[R653]
              .end if
              .select any rgo related by rel->R_OIR[R201]->R_RGO[R203] where ( selected.Obj_ID == obj.Obj_ID )
              .select any rto related by rel->R_OIR[R201]->R_RTO[R203] where ( selected.Obj_ID == obj.Obj_ID )
              .// for RGOs, the referential attributes as identifier are being initialized
              .if ( not_empty rgo )
                .select many ref_idas related by obj->O_ATTR[R102]->O_RATTR[R106]->O_ATTR[R106]->O_OIDA[R105]
                .for each ref_ida in ref_idas
                  .select any oidi related by ref_ida->O_OIDI[R199] where ( ( selected.Var_ID == var.Var_ID ) and ( selected.Block_ID == block.Block_ID ) )
                  .if ( ( not in_for_while ) and ( not after_return ) )
                    .assign oidi.initialized = true
                  .end if
                .end for
              .// for RTOs, the identifying attributes are being referenced and the
              .// identifier must be initialized
              .elif ( not_empty rto )
                .for each oida in oidas
                  .invoke result = is_initialized_in_block( var, block, oida )
                  .if ( not result.ret )
                    .select one attr related by oida->O_ATTR[R105]
                    .invoke create_warning( smt, "Instance referred to in association before identifying attribute is initialized: ${var.Name}.${attr.Name}" )
                  .end if
                .end for
              .end if
            .end if
          .end if
          .// check if this is a return statement
          .select one return_smt related by smt->ACT_RET[R603]
          .if ( not_empty return_smt )
            .assign after_return = true
          .end if
          .// select inner block for "if", "for", and "while" statements
          .invoke result = descend_block( smt, in_for_while, oidas, var )
          .if ( not_empty result.block )
            .assign block = result.block
            .assign smt = result.smt
            .assign in_for_while = result.in_for_while
          .// select next statement
          .else
            .select one smt related by smt->ACT_SMT[R661.'precedes']
          .end if
        .end while
        .// select outer block and next statment
        .if ( block != original_block )
          .// for "if", "elif", and "else" statements, if the initialization
          .// is in every block, it is in the outer block
          .select one if_smt related by block->ACT_IF[R607]
          .if ( empty if_smt )
            .select one if_smt related by block->ACT_EL[R658]->ACT_IF[R682]
            .if ( empty if_smt )
              .select one if_smt related by block->ACT_E[R606]->ACT_IF[R683]
            .end if
          .end if
          .if ( not_empty if_smt )
            .for each oida in oidas
              .select one outer_block related by if_smt->ACT_SMT[R603]->ACT_BLK[R602]
              .invoke result = is_initialized_in_if( var, oida, if_smt )
              .select any oidi related by oida->O_OIDI[R199] where ( ( selected.Var_ID == var.Var_ID ) and ( selected.Block_ID == outer_block.Block_ID ) )
              .assign oidi.initialized = ( oidi.initialized or result.ret )
            .end for
          .end if
          .// ascend to the outer block or go to the next block (for if and elif statements)
          .invoke result = ascend_block_forward( block, in_for_while )
          .assign block = result.block
          .assign smt = result.smt
          .assign in_for_while = result.in_for_while
        .else
          .assign done = true
        .end if
      .end while
      .// check if the identifiers are potentially uninitialized
      .for each oida in oidas
        .invoke result = is_initialized_in_block( var, original_block, oida )
        .if ( not result.ret )
          .select one smt related by create_smt->ACT_SMT[R603]
          .select one attr related by oida->O_ATTR[R105]
          .invoke create_warning( smt, "Instance identifier may not have been initialized after create statement: ${var.Name}.${attr.Name}" )
        .end if
      .end for
    .end for .// for each create_smt in create_smts
  .end for .// for each body in bodies
.end function
.//
.function has_create_in_body
  .param inst_ref var
  .param inst_ref smt
  .assign attr_ret = false
  .// iterate through all preceding statements
  .select one block related by smt->ACT_BLK[R602]
  .select one smt related by smt->ACT_SMT[R661.'succeeds']
  .while ( ( not_empty block ) and ( not attr_ret ) )
    .while ( ( not_empty smt ) and ( not attr_ret ) )
      .//.select one act related by block->ACT_ACT[R601]
      .//.print "Processing statement in body: ${act.Label} on line: ${smt.LineNumber}"
      .// check if this is a create statement
      .select one create_smt related by smt->ACT_CR[R603]
      .select one create_var related by create_smt->V_VAR[R633]
      .if ( not_empty create_var )
        .if ( var.Var_ID == create_var.Var_ID )
          .assign attr_ret = true
        .end if
      .end if
      .// select previous statement
      .select one smt related by smt->ACT_SMT[R661.'succeeds']
    .end while
    .// ascend to the outer block or go to the next block (for if and elif statements)
    .invoke result = ascend_block_backward( block )
    .assign block = result.block
    .assign smt = result.smt
  .end while
.end function
.//
.function check_assignments
  .select many bodies from instances of ACT_ACT
  .for each body in bodies
    .select many assign_smts related by body->ACT_BLK[R601]->ACT_SMT[R602]->ACT_AI[R603]
    .for each assign_smt in assign_smts
      .select one assigned_to_attr related by assign_smt->V_VAL[R689]->V_AVL[R801]->O_ATTR[R806]
      .select one assigned_to_oida related by assigned_to_attr->O_OIDA[R105]
      .select one assigned_to_var related by assign_smt->V_VAL[R689]->V_AVL[R801]->V_VAL[R807]->V_IRF[R801]->V_VAR[R808]
      .if ( not_empty assigned_to_oida )
        .select one smt related by assign_smt->ACT_SMT[R603]
        .if ( not_empty assigned_to_var )
          .invoke result = has_create_in_body( assigned_to_var, smt )
          .if ( not result.ret )
            .invoke create_warning( smt, "Assignment of identifying attribute of non-local instance: ${assigned_to_var.Name}.${assigned_to_attr.Name}" )
          .end if
        .else
          .select one assigned_to_param related by assign_smt->V_VAL[R689]->V_AVL[R801]->V_VAL[R807]->V_PVL[R801]
          .if ( not_empty assigned_to_param )
            .assign param_name = ""
            .select one c_pp related by assigned_to_param->C_PP[R843]
            .if ( not_empty c_pp )
              .assign param_name = c_pp.Name
            .end if
            .select one s_bparm related by assigned_to_param->S_BPARM[R831]
            .if ( not_empty s_bparm )
              .assign param_name = s_bparm.Name
            .end if
            .select one s_sparm related by assigned_to_param->S_SPARM[R832]
            .if ( not_empty s_sparm )
              .assign param_name = s_sparm.Name
            .end if
            .select one o_tparm related by assigned_to_param->O_TPARM[R833]
            .if ( not_empty o_tparm )
              .assign param_name = o_tparm.Name
            .end if
            .invoke create_warning( smt, "Assignment of identifying attribute of non-local instance: param.${param_name}.${assigned_to_attr.Name}" )
          .else
            .invoke create_warning( smt, "Assignment of identifying attribute of non-local instance: ${assigned_to_attr.Name}" )
          .end if
        .end if
      .end if
    .end for .// for each assign_smt in assign_smts
  .end for .// for each body in bodies
.end function
.//
.function analyze
  .invoke check_creates()
  .invoke check_assignments()
.end function
.//
.//------ MAIN CODE -------//
.invoke analyze()
.invoke display_warnings( "id_check" )
