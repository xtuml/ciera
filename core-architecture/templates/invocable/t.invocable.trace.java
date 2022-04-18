{
    getApplication().getLogger().trace("SMT: Enter action: ${original_parent_name}.${original_body_name}");
    int _lineNumber = -1;
    try {
        ${code}\
    } catch (RuntimeException _e) {
        ${upgrade_exception}\
        throw _e;
    } finally {
        getApplication().getLogger().trace("SMT: Exit action: ${original_parent_name}.${original_body_name}");
    }
}
