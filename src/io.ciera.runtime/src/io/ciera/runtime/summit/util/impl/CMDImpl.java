package io.ciera.runtime.summit.util.impl;

import io.ciera.runtime.summit.components.IComponent;
import io.ciera.runtime.summit.exceptions.XtumlException;
import io.ciera.runtime.summit.util.CMD;
import io.ciera.runtime.summit.util.CommandLine;
import io.ciera.runtime.summit.util.Utility;

public class CMDImpl<C extends IComponent<C>> extends Utility<C> implements CMD {
	
	private CommandLine cmd;

    public CMDImpl(C context) {
        super(context);
        cmd = new CommandLine(context.getRunContext().args());
    }
    @Override
    public boolean get_flag(String p_name) throws XtumlException {
    	return cmd.get_flag(p_name);
    }

    @Override
    public String get_value(String p_name) throws XtumlException {
    	return cmd.get_value(p_name);
    }

    @Override
    public void read_command_line() throws XtumlException {
    	cmd.read_command_line();
    }

    @Override
    public void register_flag(String p_name, String p_usage) throws XtumlException {
    	cmd.register_flag(p_name, p_usage);
    }

    @Override
    public void register_value(String p_name, String p_value_name, String p_usage, String p_default, boolean p_required)
            throws XtumlException {
    	cmd.register_value(p_name, p_value_name, p_usage, p_default, p_required);
    }

}
