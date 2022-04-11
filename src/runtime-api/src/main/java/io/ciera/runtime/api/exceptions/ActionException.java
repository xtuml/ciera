package io.ciera.runtime.api.exceptions;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

public class ActionException extends RuntimeException {

    private static final long serialVersionUID = 1l;

    private final Object data;

    private final List<StackTraceElement> stack;

    public ActionException() {
        this(null, null, null);
    }

    public ActionException(String message) {
        this(message, null, null);
    }

    public ActionException(Throwable cause) {
        this(null, null, cause);
    }

    public ActionException(Object data) {
        this(null, data, null);
    }

    public ActionException(String message, Throwable cause) {
        this(message, null, cause);
    }

    public ActionException(Object data, Throwable cause) {
        this(null, data, cause);
    }

    private ActionException(String message, Object data, Throwable cause) {
        super(message);
        this.data = data;
        stack = new LinkedList<>();
        if (cause != null) {
            final List<StackTraceElement> trace = List.of(getStackTrace());
            cause.setStackTrace(
                    Stream.of(cause.getStackTrace()).filter(ste -> !trace.contains(ste)).toArray(StackTraceElement[]::new));
            initCause(cause);
        }
    }

    public void updateStack(String parentName, String bodyName, String fileName, int lineNumber) {
        stack.add(new StackTraceElement(parentName, bodyName, fileName, lineNumber));
        setStackTrace(stack.toArray(new StackTraceElement[0]));
    }

    public Object getData() {
        return data;
    }

}
