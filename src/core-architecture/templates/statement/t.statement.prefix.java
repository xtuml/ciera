.if (not exclude_comments and actions != "")
/* ${actions} */
.end if
.if (enable_statement_logging and trace_actions != "")
getLogger().trace("SMT: [${invocable.original_parent_name}.${invocable.original_body_name} line:${statement.line_number}]: ${trace_actions}");
.end if
.if (not disable_tracing and ${statement.line_number > 0)
_lineNumber = ${statement.line_number};
.end if
${statement.prefix}\
