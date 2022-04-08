package org.xtuml.masl.util;

import java.util.List;

public class CommandLine {
    
    public CommandLine() {
    }

    public CommandLine(Object domain) {
        this();
    }

    public static enum Conditionality {
        Optional, Required;
    }

    public static enum Multiplicity {
        Single, Multiple;
    }

    public void register_flag(final String option, final String usage_text) {
        // TODO Insert your implementation here
        throw new UnsupportedOperationException();
    }

    public void register_value(final String option, final String usage_text, final Conditionality option_type,
            final String value_name, final Conditionality value_type, final Multiplicity multiplicity) {
        // TODO Insert your implementation here
        throw new UnsupportedOperationException();
    }

    public boolean option_present(final String option) {
        // TODO Insert your implementation here
        throw new UnsupportedOperationException();
    }

    public String get_option_value(final String option) {
        // TODO Insert your implementation here
        throw new UnsupportedOperationException();
    }

    public String get_option_value(final String option, final String default_value) {
        // TODO Insert your implementation here
        throw new UnsupportedOperationException();
    }

    public List<String> get_option_values(final String option) {
        // TODO Insert your implementation here
        throw new UnsupportedOperationException();
    }

    public String get_command() {
        // TODO Insert your implementation here
        throw new UnsupportedOperationException();
    }

}