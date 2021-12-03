package io.ciera.runtime.template.util.impl;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.ciera.runtime.summit.components.IComponent;
import io.ciera.runtime.summit.exceptions.XtumlException;
import io.ciera.runtime.summit.util.Utility;
import io.ciera.runtime.template.util.ITemplateRegistry;
import io.ciera.runtime.template.util.T;

public class TImpl<C extends IComponent<C>> extends Utility<C> implements T {

    /**
     * Matches all horizontal whitespace characters at the beginning of a line
     */
    private static final Pattern LEADING_WHITESPACE = Pattern.compile("^(\\h)*", Pattern.MULTILINE);
    private static final Pattern LINE = Pattern.compile("^.*$", Pattern.MULTILINE);

    private Stack<StringBuilder> buffers;
    private ITemplateRegistry registry;

    private String rootDir;

    public TImpl(C context) {
        super(context);
        rootDir = ".";
        buffers = new Stack<>();
        try {
            Class<?> componentClass = context.getClass();
            Class<?> registryClass = Class.forName(componentClass.getName() + "TemplateRegistry");
            Constructor<?> registryConstructor = registryClass.getConstructor(componentClass);
            registry = (ITemplateRegistry) registryConstructor.newInstance(context);
        } catch (Exception e) {
            registry = null;
        }
        push_buffer();
    }

    /**
     * Scan the current buffer from the end until the most recent newline and return
     * all the whitespace. If non-whitespace characters exist between the end of the
     * buffer and the beginning of the line, only return the whitespace characters
     * from the beginning of the line up to the first non-whitespace character. This
     * effectively gets the indentation of the current line.
     * 
     * @return The whitespace representing the indentation of the current line
     */
    private String currentIndentation() {
        String whitespace = "";
        Matcher m = LEADING_WHITESPACE.matcher(buffers.peek());
        while (m.find()) {
            whitespace = m.group();
        }
        return whitespace;
    }

    @Override
    public void append(String s) {
        append(s, false);
    }

    @Override
    public void append(String s, boolean includeIndent) {
        if (includeIndent) {
            final String indent = currentIndentation();
            StringBuilder buffer = buffers.peek();
            int end = -1;
            Matcher m = LINE.matcher(s);
            while (m.find()) {
                end = m.end();
                String line = m.group();
                if (m.start() > 0) {
                    buffer.append(System.lineSeparator());
                    if (!line.isEmpty()) { // don't insert the indent for otherwise blank lines
                        buffer.append(indent);
                    }
                }
                buffer.append(line);
            }
            if (end >= 0) {
                buffer.append(s.substring(end)); // append any trailing characters (handles line separator at the end)
            }
        } else {
            buffers.peek().append(s);
        }
    }

    @Override
    public String body() {
        StringBuilder buffer = buffers.pop();
        push_buffer();
        return buffer.toString();
    }

    @Override
    public void clear() {
        pop_buffer();
        push_buffer();
    }

    @Override
    public void emit(String file) throws XtumlException {
        if (null != file) {
            try {
                File outputFile = new File(new File(rootDir), file);
                if (!outputFile.getParentFile().exists()) {
                    outputFile.getParentFile().mkdirs();
                }
                boolean preExists = outputFile.exists();
                PrintStream out = new PrintStream(outputFile);
                out.print(buffers.peek().toString());
                out.flush();
                out.close();
                if (preExists)
                    getRunContext().getLog().info("File: %s REPLACED.", file);
                else
                    getRunContext().getLog().info("File: %s CREATED.", file);
                clear();
            } catch (IOException e) {
                throw new XtumlException("Could not open output file.");
            }
        }
    }

    @Override
    public void include(String file, Object ... symbols) throws XtumlException {
        if (null != file && null != registry)
            registry.getTemplate(file).evaluate(symbols);
    }

    @Override
    public void pop_buffer() {
        buffers.pop();
    }

    @Override
    public void push_buffer() {
        buffers.push(new StringBuilder());
    }

    @Override
    public void set_output_directory(String dir) {
        if (null != dir) {
            rootDir = dir;
        }
    }

    @Override
    public String sub(String format, String s) throws XtumlException {
        if (null != format) {
            for (int i = 0; i < format.length(); i++) {
                switch (format.charAt(i)) {
                case 'U':
                case 'u':
                    s = upper(s);
                    break;
                case 'C':
                case 'c':
                    s = capitalize(s);
                    break;
                case 'L':
                case 'l':
                    s = lower(s);
                    break;
                case '_':
                    s = underscore(s);
                    break;
                case 'R':
                case 'r':
                    s = remove(s);
                    break;
                case 'O':
                case 'o':
                    s = cobra(s);
                    break;
                case 'T':
                case 't':
                    break;
                default:
                    throw new XtumlException("Unrecognized format character '" + format.charAt(i) + "'");
                }
            }
        }
        return s;
    }

    private String upper(String s) {
        if (null == s)
            return s;
        else
            return s.toUpperCase();
    }

    private String capitalize(String s) {
        if (null == s)
            return s;
        else {
            String newString = "";
            boolean startOfWord = true;
            for (char c : s.toCharArray()) {
                if (Character.isWhitespace(c)) {
                    newString += c;
                    startOfWord = true;
                } else {
                    if (startOfWord) {
                        newString += Character.toUpperCase(c);
                        startOfWord = false;
                    } else
                        newString += c;
                }
            }
            s = newString;
        }
        return s;
    }

    private String lower(String s) {
        if (null == s)
            return s;
        else
            return s.toLowerCase();
    }

    private String underscore(String s) {
        if (null == s)
            return s;
        else
            return s.replaceAll("\\s", "_");
    }

    private String remove(String s) {
        if (null == s)
            return s;
        else
            return s.replaceAll("\\s+", "");
    }

    private String cobra(String s) {
        if (null == s)
            return s;
        else {
            s = capitalize(s);
            return s.substring(0, 1).toLowerCase() + s.substring(1);
        }
    }

}
