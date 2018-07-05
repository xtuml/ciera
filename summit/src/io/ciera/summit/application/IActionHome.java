package io.ciera.summit.application;

import io.ciera.summit.components.IComponent;
import io.ciera.summit.exceptions.XtumlException;

public interface IActionHome<C extends IComponent<C>> {

    public IRunContext getRunContext();
    public C context();

    default public void warn( String message ) {
        getRunContext().getExceptionHandler().warn( message );
    }

    default public Object setSymbol( String name, Object value ) {
        return getRunContext().setSymbol( name, value );
    }

    default public Object getSymbol( String name ) throws XtumlException {
        return getRunContext().getSymbol( name );
    }

    default public void pushSymbolTable() {
        getRunContext().pushSymbolTable();
    }

    default public void popSymbolTable() {
        getRunContext().popSymbolTable();
    }

}
