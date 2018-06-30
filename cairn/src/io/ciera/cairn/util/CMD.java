package io.ciera.cairn.util;

import io.ciera.summit.exceptions.XtumlException;
import io.ciera.summit.types.XtumlString;

public interface CMD {

    public boolean get_flag( XtumlString p_name ) throws XtumlException;
    public XtumlString get_value( XtumlString p_name ) throws XtumlException;
    public void read_command_line() throws XtumlException;
    public void register_flag( XtumlString p_name, XtumlString p_usage ) throws XtumlException;
    public void register_value( XtumlString p_name, XtumlString p_value_name, XtumlString p_usage, XtumlString p_default, boolean p_required ) throws XtumlException;

}
