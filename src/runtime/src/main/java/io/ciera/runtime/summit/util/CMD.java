package io.ciera.runtime.summit.util;

import io.ciera.runtime.summit.exceptions.XtumlException;
import io.ciera.runtime.summit.exceptions.XtumlInterruptedException;

/**
 * <p>
 * Provides a mechanism for defining command line interfaces and reading values
 * and flags from the command line arguments.
 * </p>
 *
 * <p>
 * The CMD external entity is used in two phases: defining and reading. First,
 * the interface is defined using the {@link
 * io.ciera.runtime.summit.util.CMD#register_flag register_flag} and {@link
 * io.ciera.runtime.summit.util.CMD#register_value register_value} bridges.
 * Once the interface is defined, the {@link
 * io.ciera.runtime.summit.util.CMD#read_command_line read_command_line} bridge
 * must be invoked to finalize the usage and parse the command line arguments.
 * After the command line arguments are parsed, the {@link
 * io.ciera.runtime.summit.util.CMD#get_flag get_flag} and {@link
 * io.ciera.runtime.summit.util.CMD#get_value get_value} bridges can be used to
 * access the values passed by the user on the command line.
 * </p>
 *
 * <p>
 * The utility expects that all invocations of the "register" bridges are before
 * the {@link io.ciera.runtime.summit.util.CMD#read_command_line
 * read_command_line} bridge is invoked, that the {@link
 * io.ciera.runtime.summit.util.CMD#read_command_line read_command_line} bridge
 * is invoked at most once, and that all invocations of the "get" bridges  are
 * after the {@link io.ciera.runtime.summit.util.CMD#read_command_line
 * read_command_line} bridge is invoked. Violation of this order will result in
 * an exception.
 * </p>
 *
 * <p>
 * Flags and values can be defined by one or more alphanumeric characters,
 * underscores, and hyphens (starting with an alphabetic character). Single
 * character flags/values are specified on the command line with a single hyphen
 * (e.g. {@code -i}), while multi-character flags/values must be specified with
 * two hypens (e.g. {@code --input_file}). The flags {@code -h} and {@code
 * --help} are reserved and are always included in order to print usage text.
 * </p>
 *
 * <p>
 * A flag is considered to be {@literal true} if the flag appears anywhere on
 * the command line. A value is defined as the argument that directly follows
 * the registered option name (e.g. {@code --input_file in.sql}). Values may not
 * be repeated on the command line and are always returned to the caller as a
 * string. Numerical or other types of values must be converted after the fact
 * by the application modeler.
 * </p>
 */
public interface CMD {

    /**
     * Get the value of a registered command line flag.
     *
     * @param name the name of the flag
     * @return {@literal true} if the flag was passed as a command line
     * argument, {@literal false} if otherwise
     * @throws XtumlException if the command line has not been read, the flag
     * name has not been registered, or if the flag has been registered as a
     * value.
     */
    public boolean get_flag(final String name) throws XtumlException;

    /**
     * Get the value of a registered command line value.
     *
     * @param name the name of the value
     * @return the value of the option passed on the command line or the
     * registered default value if not present and not required
     * @throws XtumlException if the command line has not been read, the value
     * name has not been registered, or if the value has been registered as a
     * flag.
     */
    public String get_value(final String name) throws XtumlException;

    /**
     * Parse and validate the command line arguments based on the previously
     * registered flags and values. If an excption is thrown, the usage is
     * printed to the command line and the application will exit.
     *
     * @throws XtumlException if the command line has already been read
     * @throws XtumlInterruptedException if the command line arguments are
     * incorrectly formatted or if an option that requires a value does not
     * provide a value.
     */
    public void read_command_line() throws XtumlException, XtumlInterruptedException;

    /**
     * Register a command line flag.
     *
     * @param name the name of the flag
     * @param usage the help text to display in the usage
     * @throws XtumlException if the command line has already been read, the flag
     * name has already been registered, or if the flag name is invalid.
     */
    public void register_flag(final String name, final String usage) throws XtumlException;

    /**
     * Register a command line value. If the flag is required, the {@code
     * default_value} parameter is ignored.
     *
     * @param name the name of the value
     * @param value_name the placeholder name of the value to display in the
     * usage
     * @param usage the help text to display in the usage
     * @param default_value the default value of the option if not required and
     * no value is specified
     * @param required whether or not a value is required
     * @throws XtumlException if the command line has already been read, the option
     * name has already been registered, or if the option name is invalid.
     */
    public void register_value(final String name, final String value_name, final String usage, final String default_value, final boolean required)
            throws XtumlException;

}
