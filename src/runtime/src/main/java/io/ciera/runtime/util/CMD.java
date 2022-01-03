package io.ciera.runtime.util;

import io.ciera.runtime.domain.Domain;
import io.ciera.runtime.domain.Utility;

public class CMD extends Utility {

    private final CommandLine cmd;

    public CMD(Domain domain) {
        super(domain);
        cmd = new CommandLine(getContext().getApplication().getArgs(), System.err);
    }

    public boolean get_flag(final String name) {
        return cmd.get_flag(name);
    }

    public String get_value(final String name) {
        return cmd.get_value(name);
    }

    public void read_command_line() {
        cmd.read_command_line();
    }

    public void register_flag(final String name, final String usage) {
        cmd.register_flag(name, usage);
    }

    public void register_value(final String name, final String value_name, final String usage,
            final String default_value, final boolean required) {
        cmd.register_value(name, value_name, usage, default_value, required);
    }

}