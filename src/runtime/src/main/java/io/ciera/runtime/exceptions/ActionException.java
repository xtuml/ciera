package io.ciera.runtime.exceptions;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

public class ActionException extends RuntimeException {

    private static final long serialVersionUID = 1l;

    private final Object data;

    private final List<StackTraceElement> stack;

    public ActionException() {
        this(null, null);
    }

    public ActionException(String message) {
        this(message, null);
    }

    public ActionException(Throwable cause) {
        this(null, null);
        final List<StackTraceElement> trace = List.of(getStackTrace());
        cause.setStackTrace(Stream.of(cause.getStackTrace()).filter(ste -> !trace.contains(ste))
                .toArray(StackTraceElement[]::new));
        initCause(cause);
    }

    public ActionException(Object data) {
        this(null, data);
    }

    private ActionException(String message, Object data) {
        super(message);
        this.data = data;
        stack = new LinkedList<>();
        Integer i = Integer.valueOf(1);
        Double d = Double.valueOf(2);
        Stream.of(i, d);
    }

    public void updateStack(String parentName, String bodyName, String fileName, int lineNumber) {
        stack.add(new StackTraceElement(parentName, bodyName, fileName, lineNumber));
        setStackTrace(stack.toArray(new StackTraceElement[0]));
    }

    public Object getData() {
        return data;
    }

}
