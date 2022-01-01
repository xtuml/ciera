package io.ciera.runtime.exceptions;

import java.util.LinkedList;
import java.util.List;

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

    public ActionException(Object data) {
        this(null, null, data);
    }

    public ActionException(Throwable cause) {
        this(null, cause, null);
    }

    private ActionException(String message, Throwable cause, Object data) {
        super(message, cause);
        this.data = data;
        stack = new LinkedList<>();
    }
    
    public void updateStack(String parentName, String bodyName, String fileName, int lineNumber) {
        stack.add(new StackTraceElement(parentName, bodyName, fileName, lineNumber));
        setStackTrace(stack.toArray(new StackTraceElement[0]));
    }

    public Object getData() {
        return data;
    }
    
}
