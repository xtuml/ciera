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
    public boolean get_flag(final String name) throws XtumlException {
    	return cmd.get_flag(name);
    }

    @Override
    public String get_value(final String name) throws XtumlException {
    	return cmd.get_value(name);
    }

    @Override
    public void read_command_line() throws XtumlException {
    	cmd.read_command_line();
    }

    @Override
    public void register_flag(final String name, final String usage) throws XtumlException {
    	cmd.register_flag(name, usage);
    }

    @Override
    public void register_value(final String name, final String value_name, final String usage, final String default_value, final boolean required)
            throws XtumlException {
    	cmd.register_value(name, value_name, usage, default_value, required);
    }

}
