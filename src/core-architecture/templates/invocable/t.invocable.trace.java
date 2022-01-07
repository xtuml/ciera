{
    getApplication().getLogger().trace("SMT: Enter action: ${original_parent_name}.${original_body_name}");
    int _lineNumber = -1;
    try ${code} catch (RuntimeException _e) {
        if (!(_e instanceof ActionException)) {
            _e = new ActionException(_e);
        }
        ((ActionException) _e).updateStack("${original_parent_name}", "${original_body_name}", \
.if (self.original_filename != "")
"${self.original_filename}"\
.else
"<Unknown>"\
.end if
, _lineNumber);
        throw _e;
    } finally {
        getApplication().getLogger().trace("SMT: Exit action: ${original_parent_name}.${original_body_name}");
    }
}
