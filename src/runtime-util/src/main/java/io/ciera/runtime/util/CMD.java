package io.ciera.runtime.util;

import io.ciera.runtime.application.CommandLine;
import io.ciera.runtime.application.CommandLine.Conditionality;
import io.ciera.runtime.application.CommandLine.Multiplicity;

public class CMD {

  private final CommandLine cmd;

  public CMD() {
    cmd = CommandLine.getInstance();
  }

  public CMD(Object domain) {
    this();
  }

  public void register_flag(final String option, final String usage_text) {
    cmd.registerFlag(option, usage_text);
  }

  public void register_value(
      final String option,
      final String usage_text,
      final boolean required,
      final String value_name,
      final boolean value_required,
      final boolean allow_multiple) {
    cmd.registerValue(
        option,
        usage_text,
        required ? Conditionality.Required : Conditionality.Optional,
        value_name,
        value_required ? Conditionality.Required : Conditionality.Optional,
        allow_multiple ? Multiplicity.Multiple : Multiplicity.Single);
  }

  public boolean option_present(final String option) {
    return cmd.optionPresent(option);
  }

  public String get_option_value(final String option, final String default_value) {
    return cmd.getOptionValue(option, default_value);
  }

  public String[] get_option_values(final String option) {
    return cmd.getOptionValues(option).toArray(new String[0]);
  }

  public String[] get_args() {
    return cmd.getArgs();
  }

  public void validate() {
    cmd.validate();
  }
}
