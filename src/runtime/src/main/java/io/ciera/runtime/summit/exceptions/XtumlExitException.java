package io.ciera.runtime.summit.exceptions;

public class XtumlExitException extends RuntimeException {

    private static final long serialVersionUID = 1l;
    
    private int code;

    public XtumlExitException() {
        super();
        code = 0;
    }

    public XtumlExitException(int code) {
        super();
        this.code = code;
    }

    public XtumlExitException(int code, String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.code = code;
    }

    public XtumlExitException(int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public XtumlExitException(int code, String message) {
        super(message);
        this.code = code;
    }

    public XtumlExitException(int code, Throwable cause) {
        super(cause);
        this.code = code;
    }
    
    public int getCode() {
        return code;
    }

}
