package io.ciera.runtime.template.util;

import io.ciera.runtime.summit.exceptions.XtumlException;
import io.ciera.runtime.summit.types.IXtumlType;

/**
 * The T utility provides tools for working with RSL templates. The utility
 * maintains a stack of String buffers. Each buffer accumulates content as
 * {@code append} and {@code include} calls are made. The buffer is cleared when
 * {@code clear} and {@code body} calls are made. Multiple buffers can be
 * managed at once on the stack using {@code push_buffer} and {@code pop_buffer}
 * calls.
 */
public interface T {

    /**
     * Append a string to the current buffer.
     *
     * @param s the string value to append
     */
    public void append(String s);

    /**
     * Append an object to the current buffer. By default, the object gets
     * converted to a String using the standard {@code toString} method before
     * being added to the buffer.
     *
     * @param o the object to append
     */
    default public void append(Object o) {
        append(new String(o.toString()));
    }

    /**
     * Clear the current buffer and return the contents as a String.
     *
     * @return the contents of the buffer
     */
    public String body();

    /**
     * Clear the current buffer
     */
    public void clear();

    /**
     * Clear the current buffer and emit the contents to a file specified by
     * {@code file}.
     *
     * @param file the name of the output file relative to the configured output
     * directory
     * @throws XtumlException if the file cannot be opened for writing
     * @see io.ciera.runtime.template.util.T#set_output_directory
     */
    public void emit(String file) throws XtumlException;

    /**
     * Include an RSL template. The in-scope variables in the location where
     * invoked will be captured and passed as the list of {@code symbols}.
     *
     * @param file the template file to include relative to the root template
     * directory
     * @param symbols the list of symbols to pass to the template. When invoked
     * from OAL, this list is composed by capturing all in-scope variables in
     * the context where it was invoked.
     * @throws XtumlException if an error occurs during template evaluation
     */
    public void include(String file, Object ... symbols) throws XtumlException;

    /**
     * Remove the current buffer and replace it with the next buffer on the
     * buffer stack.
     */
    public void pop_buffer();

    /**
     * Store the current buffer on the buffer stack and replace it with a new
     * buffer.
     */
    public void push_buffer();

    /**
     * Set the root directory for emitting files.
     *
     * @param dir the root location for template output
     */
    public void set_output_directory(String dir);

    /**
     * Perform a format substitution.
     *
     * The following format characters are supported:
     * <ul>
     *   <li>{@code u} (upper) - make all characters upper case</li>
     *   <li>{@code c} (capitalize) - make the first character of each word capitalized and all
     *   other characters of a word lowercase</li>
     *   <li>{@code l} (lower) - make all characters lowercase</li>
     *   <li>{@code _} (underscore) - change all whitespace characters to underscore characters</li>
     *   <li>{@code r} (remove) - remove all whitespace</li>
     *   <li>{@code o} (corba) - make the first word all lowercase, make the first character of
     *   each following word capitalized and all other characters of the words
     *   lowercase. Characters other than a-Z a-z 0-9 are ignored</li>
     *   <li>{@code t} (pass-through)</li>
     * </ul>
     *
     * Each character in the {@code format} parameter will be evaluated as a
     * format character sequentially left to right.
     *
     * @param format the string of format characters representing operations to
     * perform
     * @param s the string to format
     * @return a formatted string
     * @throws XtumlException if an invalid format character is passed
     */
    public String sub(String format, String s) throws XtumlException;

    /**
     * Perform a format substitution. By default, the object gets converted to a
     * String using the standard {@code toString} method before being passed to
     * the {@code sub} method.
     *
     * @param format the string of format characters representing operations to
     * perform
     * @param o the object to format
     * @return a formatted string
     * @throws XtumlException if an invalid format character is passed
     */
    default public String sub(String format, Object o) throws XtumlException {
        return sub(format, o.toString());
    }

}
