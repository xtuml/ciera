package io.ciera.runtime.template.util.impl;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import io.ciera.runtime.summit.components.IComponent;
import io.ciera.runtime.summit.exceptions.XtumlException;
import io.ciera.runtime.summit.util.Utility;
import io.ciera.runtime.template.util.ITemplateRegistry;
import io.ciera.runtime.template.util.T;

public class TImpl<C extends IComponent<C>> extends Utility<C> implements T {

    private Stack<List<String>> buffers;
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

    @Override
    public void append(String s) throws XtumlException {
        buffers.peek().add(s);
    }

    @Override
    public String body() throws XtumlException {
        List<String> strings = buffers.pop();
        push_buffer();
        return String.join("", strings);
    }

    @Override
    public void clear() throws XtumlException {
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
                for (String str : buffers.peek()) {
                    out.print(str);
                }
                out.flush();
                out.close();
                if (preExists)
                    getRunContext().getLog().info("File: %s REPLACED.", file);
                else
                    getRunContext().getLog().info("File: %s CREATED.", file);
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
        buffers.push(new LinkedList<>());
    }

    @Override
    public void set_output_directory(String dir) throws XtumlException {
        if (null != dir) {
            rootDir = dir;
        }
    }

    /**
     * u Upper - make all characters upper case c Capitalize - make the first
     * character of each word capitalized and all other characters of a word
     * lowercase l Lower - make all characters lowercase _ Underscore - change all
     * whitespace characters to underscore characters r Remove - remove all
     * whitespace. o cOrba - make the first word all lowercase, make the first
     * character of each following word capitalized and all other characters of the
     * words lowercase. Characters other than a-Z a-z 0-9 are ignored. t no-op
     */
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
