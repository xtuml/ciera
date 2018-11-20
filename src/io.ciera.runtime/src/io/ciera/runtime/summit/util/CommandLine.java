package io.ciera.runtime.summit.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import io.ciera.runtime.summit.exceptions.XtumlException;
import io.ciera.runtime.summit.exceptions.XtumlInterruptedException;

public class CommandLine {

    private Map<String, Option> options;
    private Set<String> flags;
    private Map<String, String> values;
    String[] args;

    public CommandLine(String[] args) {
    	this.args = args;
        options = new HashMap<>();
        flags = null;
        values = null;
    }

    // class used for registering options
    private static class Option {

        static final int FLAG = 0;
        static final int VALUE = 1;

        int type;
        String name;
        String value_name;
        String usage;
        String defaultValue;
        boolean required;

    }

    public boolean get_flag(String p_name) throws XtumlException {
        if (null == flags || null == values)
            throw new XtumlException("Commandline has not been loaded.");
        validateName(p_name);
        if (!options.containsKey(p_name))
            throw new XtumlException("Option '" + p_name + "' has not been registered.");
        if (options.get(p_name).type != Option.FLAG)
            throw new XtumlException("Option '" + p_name + "' requires a value.");
        return flags.contains(p_name);
    }

    public String get_value(String p_name) throws XtumlException {
        if (null == flags || null == values)
            throw new XtumlException("Commandline has not been loaded.");
        validateName(p_name);
        if (!options.containsKey(p_name))
            throw new XtumlException("Option '" + p_name + "' has not been registered.");
        if (options.get(p_name).type != Option.VALUE)
            throw new XtumlException("Option '" + p_name + "' does not carry a value.");
        if (values.containsKey(p_name))
            return values.get(p_name);
        else
            return options.get(p_name).defaultValue;
    }

    public void read_command_line() throws XtumlException {
        if (null != flags && null != values)
            throw new XtumlException("Commandline is already loaded.");
        validateCommandLine();
    }

    public void register_flag(String p_name, String p_usage) throws XtumlException {
        if (null != flags && null != values)
            throw new XtumlException("Commandline is already loaded.");
        validateName(p_name);
        Option flag = new Option();
        flag.type = Option.FLAG;
        flag.name = p_name;
        flag.usage = null != p_usage ? p_usage : "";
        if (options.containsKey(p_name))
            throw new XtumlException("Option '" + p_name + "' already registered.");
        options.put(p_name, flag);
    }

    public void register_value(String p_name, String p_value_name, String p_usage, String p_default, boolean p_required)
            throws XtumlException {
        if (null != flags && null != values)
            throw new XtumlException("Commandline is already loaded.");
        validateName(p_name);
        Option value = new Option();
        value.type = Option.VALUE;
        value.name = p_name;
        value.value_name = null != p_value_name ? p_value_name : "";
        value.usage = null != p_usage ? p_usage : "";
        value.defaultValue = null != p_default ? p_default : "";
        value.required = p_required;
        if (options.containsKey(p_name))
            throw new XtumlException("Option '" + p_name + "' already registered.");
        options.put(p_name, value);
    }

    // check whether a string is a valid option name
    private void validateName(String name) throws XtumlException {
        if (null == name || !Pattern.compile("[a-zA-Z]+").matcher(name).matches())
            throw new XtumlException("Option name must be one or more alphabetic characters.");
    }

    // assure that no unregistered options are present and that all required options
    // are present
    private void validateCommandLine() throws XtumlException {
        boolean errors = false;
        if (null == flags || null == values)
            errors = parseCommandLine();
        for (String flag : flags)
            if (!options.containsKey(flag) || Option.FLAG != options.get(flag).type)
                errors = true;
        for (String value : values.keySet())
            if (!options.containsKey(value) || Option.VALUE != options.get(value).type)
                errors = true;
        for (Option opt : options.values())
            if (Option.VALUE == opt.type && opt.required && !values.containsKey(opt.name))
                errors = true;
        if (errors || flags.contains("h") || flags.contains("help")) {
            printUsage();
            throw new XtumlInterruptedException();
        }
    }

    // read the command line args and parse into flags and values
    private boolean parseCommandLine() {
        flags = new HashSet<>();
        values = new HashMap<>();
        Set<String> possibleFlags = new HashSet<>();
        for (String arg : args) {
            if (arg.startsWith("--")) {
                if (!possibleFlags.isEmpty())
                    flags.addAll(possibleFlags); // if another option is hit before a value, the previous options were
                                                 // flags
                possibleFlags.add(arg.substring(2));
            } else if (arg.startsWith("-")) {
                if (!possibleFlags.isEmpty())
                    flags.addAll(possibleFlags); // if another option is hit before a value, the previous options were
                                                 // flags
                for (int i = 1; i < arg.length(); i++)
                    possibleFlags.add(arg.substring(i, i + 1));
            } else {
                if (1 != possibleFlags.size())
                    return true;
                values.put(possibleFlags.iterator().next(), arg);
                possibleFlags.clear();
            }
        }
        flags.addAll(possibleFlags);
        return false;
    }

    // print usage of all registered options
    private void printUsage() {
        System.err.println("Usage:");
        for (Option opt : options.values()) {
            String name = opt.name.length() > 1 ? "--" + opt.name : "-" + opt.name;
            if (Option.FLAG == opt.type)
                System.err.printf("  %-20s : %s\n", name, opt.usage);
            else
                System.err.printf("  %-20s : %s\n", name + " <" + opt.value_name + ">", opt.usage);
        }
        System.err.printf("  %-20s : Print usage information.\n", "-h, --help");
    }

}
