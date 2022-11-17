.if (not exclude_comments and self.actions != "")
/* ${self.actions} */
.end if
.if (enable_statement_logging and trace_actions != "")
.//getContext().getApplication().getLogger().trace("SMT: [${invocable.original_parent_name}.${invocable.original_body_name} line:${self.line_number}]: ${trace_actions}");
getLogger().debug("SMT: [${invocable.original_parent_name}.${invocable.original_body_name} line:${self.line_number}]: ${trace_actions}");
.end if
.if (not disable_tracing and self.line_number > 0)
_lineNumber = ${self.line_number};
.end if
