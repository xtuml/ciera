package io.ciera.runtime.summit2.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import io.ciera.runtime.summit2.exceptions.CommandLineException;

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

    public boolean get_flag(String name) {
        if (null == flags || null == values)
            throw new CommandLineException("Commandline has not been loaded.");
        if (!options.containsKey(name))
            throw new CommandLineException("Option '" + name + "' has not been registered.");
        if (options.get(name).type != Option.FLAG)
            throw new CommandLineException("Option '" + name + "' requires a value.");
        return flags.contains(name);
    }

    public String get_value(String name) {
        if (null == flags || null == values)
            throw new CommandLineException("Commandline has not been loaded.");
        if (!options.containsKey(name))
            throw new CommandLineException("Option '" + name + "' has not been registered.");
        if (options.get(name).type != Option.VALUE)
            throw new CommandLineException("Option '" + name + "' does not carry a value.");
        if (values.containsKey(name))
            return values.get(name);
        else
            return options.get(name).defaultValue;
    }

    public void read_command_line() {
        if (null != flags && null != values)
            throw new CommandLineException("Commandline is already loaded.");
        validateCommandLine();
    }

    public void register_flag(String name, String usage) {
        if (null != flags && null != values)
            throw new CommandLineException("Commandline is already loaded.");
        validateName(name);
        Option flag = new Option();
        flag.type = Option.FLAG;
        flag.name = name;
        flag.usage = null != usage ? usage : "";
        if (options.containsKey(name))
            throw new CommandLineException("Option '" + name + "' already registered.");
        options.put(name, flag);
    }

    public void register_value(String name, String value_name, String usage, String default_value, boolean required) {
        if (null != flags && null != values)
            throw new CommandLineException("Commandline is already loaded.");
        validateName(name);
        Option value = new Option();
        value.type = Option.VALUE;
        value.name = name;
        value.value_name = null != value_name ? value_name : "";
        value.usage = null != usage ? usage : "";
        value.defaultValue = null != default_value ? default_value : "";
        value.required = required;
        if (options.containsKey(name))
            throw new CommandLineException("Option '" + name + "' already registered.");
        options.put(name, value);
    }

    // check whether a string is a valid option name
    private void validateName(String name) throws CommandLineException {
        if (null == name || !Pattern.compile("[a-zA-Z][a-zA-Z0-9_\\-]*").matcher(name).matches())
            throw new CommandLineException(
                    "Option name must be one or more alphanumeric characters, hyphens, or underscores.");
        else if ("h".equals(name) || "help".equals(name)) {
            throw new CommandLineException(String.format("Cannot register reserved flag '%s'.", name));
        }
    }

    // assure that no unregistered options are present and that all required options
    // are present
    private void validateCommandLine() {
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
            throw new CommandLineException();
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
