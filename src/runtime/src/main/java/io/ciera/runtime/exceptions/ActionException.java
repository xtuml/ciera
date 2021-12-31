package io.ciera.runtime.exceptions;

public class ActionException extends RuntimeException {

    private static final long serialVersionUID = 1l;

    private final Object data;

    private String bodyName;
    private int lineNumber;
    private String actions;

    public ActionException() {
        data = null;
        bodyName = null;
        lineNumber = 0;
        actions = null;
    }

    public ActionException(String message) {
        super(message);
        data = null;
        bodyName = null;
        lineNumber = 0;
        actions = null;
    }

    public ActionException(Object data) {
        this.data = data;
        bodyName = null;
        lineNumber = 0;
        actions = null;
    }

    public Object getData() {
        return data;
    }

    public String getBodyName() {
        return bodyName;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public String getActions() {
        return actions;
    }

}
