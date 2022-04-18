package org.xtuml.masl.util;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import io.ciera.runtime.api.application.Application;

public class CommandLine {

  private final io.ciera.runtime.application.CommandLine cmd;

  public CommandLine() {
    cmd = io.ciera.runtime.application.CommandLine.getInstance();
  }

  public CommandLine(Object domain) {
    this();
  }

  public static enum Conditionality {
    Optional,
    Required;

    private io.ciera.runtime.application.CommandLine.Conditionality toRuntime() {
      return this == Optional
          ? io.ciera.runtime.application.CommandLine.Conditionality.Optional
          : io.ciera.runtime.application.CommandLine.Conditionality.Required;
    }
  }

  public static enum Multiplicity {
    Single,
    Multiple;

    private io.ciera.runtime.application.CommandLine.Multiplicity toRuntime() {
      return this == Single
          ? io.ciera.runtime.application.CommandLine.Multiplicity.Single
          : io.ciera.runtime.application.CommandLine.Multiplicity.Multiple;
    }
  }

  public void register_flag(final String option, final String usage_text) {
    cmd.registerFlag(option, usage_text);
  }

  public void register_value(
      final String option,
      final String usage_text,
      final Conditionality option_type,
      final String value_name,
      final Conditionality value_type,
      final Multiplicity multiplicity) {
    cmd.registerValue(
        option,
        usage_text,
        option_type.toRuntime(),
        value_name,
        value_type.toRuntime(),
        multiplicity.toRuntime());
  }

  public boolean option_present(final String option) {
    return cmd.optionPresent(option);
  }

  public String get_option_value(final String option) {
    return cmd.getOptionValue(option);
  }

  public String get_option_value(final String option, final String default_value) {
    return cmd.getOptionValue(option, default_value);
  }

  public List<String> get_option_values(final String option) {
    return cmd.getOptionValues(option);
  }

  public String get_command() {
    return Application.getInstance().getName()
        + " "
        + Stream.of(cmd.getArgs()).collect(Collectors.joining(" "));
  }
}
