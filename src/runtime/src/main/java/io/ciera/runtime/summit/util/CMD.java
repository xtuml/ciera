package io.ciera.runtime.summit.util;

import io.ciera.runtime.summit.exceptions.XtumlException;

public interface CMD {

    public boolean get_flag(String p_name) throws XtumlException;

    public String get_value(String p_name) throws XtumlException;

    public void read_command_line() throws XtumlException;

    public void register_flag(String p_name, String p_usage) throws XtumlException;

    public void register_value(String p_name, String p_value_name, String p_usage, String p_default, boolean p_required)
            throws XtumlException;

}
