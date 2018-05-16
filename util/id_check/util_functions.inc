.function create_warning
  .param inst_ref smt
  .param string msg
  .select one act related by smt->ACT_BLK[R602]->ACT_ACT[R601]
  .create object instance warning of INT_WARN
  .assign warning.BodyLabel = act.Label
  .assign warning.LineNumber = smt.LineNumber
  .assign warning.Message = msg
.end function
.//
.function create_warning_no_line_number
  .param string msg
  .create object instance warning of INT_WARN
  .assign warning.BodyLabel = ""
  .assign warning.LineNumber = 0
  .assign warning.Message = msg
.end function
.//
.function display_warnings
  .param string source
  .// display all the warnings sorting them first by body label
  .// then by line number. This is an extremely simple and inefficient
  .// sort.
  .select any warning from instances of INT_WARN
  .while ( not_empty warning )
    .// get the smallest alphanumeric body
    .select many other_warnings from instances of INT_WARN
    .for each other_warning in other_warnings
      .if ( other_warning.BodyLabel < warning.BodyLabel )
        .assign warning = other_warning
      .end if
    .end for
    .// get the smallest line number in that body
    .select many other_warnings from instances of INT_WARN where ( selected.BodyLabel == warning.BodyLabel )
    .for each other_warning in other_warnings
      .if ( other_warning.LineNumber < warning.LineNumber )
        .assign warning = other_warning
      .end if
    .end for
    .//
    .// display the warning
    .if ( ( "" == warning.BodyLabel ) and ( 0 == warning.LineNumber ) )
"Warning: ${warning.Message}"\
    .else
"Warning: ${warning.BodyLabel} line: ${warning.LineNumber} -- ${warning.Message}"\
    .end if
    .// delete the warning
    .delete object instance warning
    .select any warning from instances of INT_WARN
  .end while
  .emit to file "${source}.warnings.txt"
.end function
