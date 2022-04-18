package io.ciera.runtime.application;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.regex.Pattern;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import io.ciera.runtime.api.application.Application;

public class CommandLine {

  private static CommandLine instance = null;

  private static final Pattern OPTION_PATTERN = Pattern.compile("-[A-Za-z0-9]+");

  public static enum Conditionality {
    Optional,
    Required
  }

  public static enum Multiplicity {
    Single,
    Multiple
  }

  private static class Flag {

    private Flag(String option, String usageText) {
      this.option = option;
      this.usageText = usageText;
    }

    String option;
    String usageText;

    String getUsage1() {
      return String.format("[%s]", option);
    }

    String getUsage2() {
      return String.format("%s\t: %s", option, usageText);
    }
  }

  private static class Value extends Flag {

    private Value(
        String option,
        String usageText,
        Conditionality optionType,
        String valueName,
        Conditionality valueType,
        Multiplicity multiplicity) {
      super(option, usageText);
      this.optionType = optionType;
      this.valueName = valueName;
      this.valueType = valueType;
      this.multiplicity = multiplicity;
    }

    Conditionality optionType;
    String valueName;
    Conditionality valueType;
    Multiplicity multiplicity;

    String getUsage1() {
      final boolean optOptional = optionType == Conditionality.Optional;
      final boolean valOptional = valueType == Conditionality.Optional;
      final boolean valMultiple = multiplicity == Multiplicity.Multiple;
      return String.format(
          "%s%s %s<%s>%s%s%s",
          optOptional ? "[" : "",
          option,
          valOptional ? "[" : "",
          valueName,
          valOptional ? "]" : "",
          valMultiple ? "..." : "",
          optOptional ? "]" : "");
    }

    String getUsage2() {
      return String.format("%s <%s>\t: %s", option, valueName, usageText);
    }
  }

  public static class CommandLineException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public CommandLineException(String message) {
      super(message);
    }

    public String getUsageText() {
      return CommandLine.getInstance().getUsageText();
    }
  }

  private final String[] args;
  private final Map<String, List<String>> parsedArgs;

  private final Map<String, Flag> options;

  private CommandLine(String args[]) {
    this.args = args;
    this.parsedArgs =
        Stream.of(args)
            .collect(
                new Collector<String, Map<String, List<String>>, Map<String, List<String>>>() {

                  private String currentOption = null;
                  private boolean parsingOption = true;

                  @Override
                  public Supplier<Map<String, List<String>>> supplier() {
                    return () -> new HashMap<>();
                  }

                  @Override
                  public BiConsumer<Map<String, List<String>>, String> accumulator() {
                    return (parsedArgs, arg) -> {
                      if (OPTION_PATTERN.matcher(arg).matches()) {
                        currentOption = arg;
                        if (!parsedArgs.containsKey(currentOption)) {
                          parsedArgs.put(currentOption, new ArrayList<>());
                        }
                        parsingOption = false;
                      } else {
                        if (!parsingOption) {
                          parsedArgs.get(currentOption).add(arg);
                          parsingOption = true;
                        } else {
                          throw new IllegalStateException(
                              "Expected flag or value identifier but found: " + arg);
                        }
                      }
                    };
                  }

                  @Override
                  public BinaryOperator<Map<String, List<String>>> combiner() {
                    return (parsedArgs1, parsedArgs2) -> {
                      for (String key : parsedArgs2.keySet()) {
                        if (!parsedArgs1.containsKey(key)) {
                          parsedArgs1.put(key, new ArrayList<>());
                        }
                        parsedArgs1.get(key).addAll(parsedArgs2.get(key));
                      }
                      return parsedArgs1;
                    };
                  }

                  @Override
                  public Function<Map<String, List<String>>, Map<String, List<String>>> finisher() {
                    return parsedArgs -> Collections.unmodifiableMap(parsedArgs);
                  }

                  @Override
                  public Set<Characteristics> characteristics() {
                    return Set.of();
                  }
                });
    this.options = new HashMap<>();
  }

  public void registerFlag(String option, String usageText) {
    if (OPTION_PATTERN.matcher(option).matches()) {
      if (!options.containsKey(option)) {
        options.put(option, new Flag(option, usageText));
      } else {
        throw new IllegalStateException("Option has already been registered: " + option);
      }
    } else {
      throw new IllegalArgumentException(
          "Option name must be a hypen followed by one or more alphanumeric characters");
    }
  }

  public void registerValue(
      String option,
      String usageText,
      Conditionality optionType,
      String valueName,
      Conditionality valueType,
      Multiplicity multiplicity) {
    if (OPTION_PATTERN.matcher(option).matches()) {
      if (!options.containsKey(option)) {
        options.put(
            option, new Value(option, usageText, optionType, valueName, valueType, multiplicity));
      } else {
        throw new IllegalStateException("Option has already been registered: " + option);
      }
    } else {
      throw new IllegalArgumentException(
          "Option name must be a hypen followed by one or more alphanumeric characters");
    }
  }

  public boolean optionPresent(String option) {
    if (options.containsKey(option)) {
      return parsedArgs.containsKey(option);
    } else {
      throw new IllegalStateException("Option is not registered: " + option);
    }
  }

  public String getOptionValue(String option) {
    return getOptionValue(option, "");
  }

  public String getOptionValue(String option, String defaultValue) {
    if (options.containsKey(option)) {
      Flag opt = options.get(option);
      if (opt instanceof Value && ((Value) opt).multiplicity == Multiplicity.Single) {
        return parsedArgs.get(option) != null
            ? parsedArgs.get(option).stream().findAny().orElseThrow()
            : defaultValue;
      } else {
        throw new IllegalStateException("Option is not registered as a singleton value: " + option);
      }
    } else {
      throw new IllegalStateException("Option is not registered: " + option);
    }
  }

  public List<String> getOptionValues(String option) {
    if (options.containsKey(option)) {
      Flag opt = options.get(option);
      if (opt instanceof Value) {
        return Collections.unmodifiableList(parsedArgs.get(option));
      } else {
        throw new IllegalStateException("Option is not registered as a value: " + option);
      }
    } else {
      throw new IllegalStateException("Option is not registered: " + option);
    }
  }

  public String getUsageText() {
    return String.format(
        "Usage: %s %s\n %s\n",
        Application.getInstance().getName(),
        options.keySet().stream()
            .sorted()
            .map(options::get)
            .map(Flag::getUsage1)
            .collect(Collectors.joining(" ")),
        options.keySet().stream()
            .sorted()
            .map(options::get)
            .map(Flag::getUsage2)
            .collect(Collectors.joining("\n ")));
  }

  public void validate() {
    options.keySet().stream()
        .map(options::get)
        .filter(Value.class::isInstance)
        .map(Value.class::cast)
        .forEach(
            value -> {
              if (value.optionType == Conditionality.Required
                  && !parsedArgs.containsKey(value.option)) {
                throw new CommandLineException(
                    "Command line does not contain required option: " + value.option);
              }
              if (value.valueType == Conditionality.Required
                  && parsedArgs.containsKey(value.option)
                  && parsedArgs.get(value.option).isEmpty()) {
                throw new CommandLineException(
                    "Command line does not provide required value for option: " + value.option);
              }
              if (value.multiplicity == Multiplicity.Single
                  && parsedArgs.containsKey(value.option)
                  && parsedArgs.get(value.option).size() > 1) {
                throw new CommandLineException(
                    "Command line provides multiple values for singleton option: " + value.option);
              }
            });
  }

  public String[] getArgs() {
    return Arrays.copyOf(args, args.length);
  }

  public static void initialize(String[] args) {
    instance = new CommandLine(args);
  }

  public static CommandLine getInstance() {
    if (instance == null) {
      initialize(new String[0]);
    }
    return instance;
  }
}
