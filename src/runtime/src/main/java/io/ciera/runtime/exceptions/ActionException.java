package io.ciera.runtime.exceptions;

public class ActionException extends RuntimeException {

    private static final long serialVersionUID = 1l;
    
    private final Object data;

    public ActionException() {
        data = null;
    }
    
    public ActionException(String message) {
        super(message);
        data = null;
    }
    
    public ActionException(Object data) {
        this.data = data;
    }
    
    public Object getData() {
        return data;
    }

}
