{
    getLogger().trace("SMT: Enter action: ${self.original_parent_name}.${self.original_body_name}");
    int _lineNumber = -1;
    try ${code} catch (RuntimeException _e) {
        if (!(_e instanceof ActionException)) {
            _e = new ActionException(_e);
        }
        ((ActionException) _e).updateStack("${self.original_parent_name}", "${self.original_body_name}", \
.if (self.original_filename != "")
"${self.original_filename}"\
.else
"<Unknown>"\
.end if
, _lineNumber);
        throw _e;
    } finally {
        getLogger().trace("SMT: Exit action: ${self.original_parent_name}.${self.original_body_name}");
    }
}
