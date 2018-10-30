package io.ciera.runtime.template.util;

import io.ciera.summit.exceptions.BadArgumentException;
import io.ciera.summit.exceptions.XtumlException;
import io.ciera.summit.types.IXtumlType;
import io.ciera.summit.types.InstRefMapping;

public class SymbolTable extends InstRefMapping<SymbolTable> implements IXtumlType<SymbolTable> {
    
    private Object[] symbols;
    
    public SymbolTable() {
        symbols = new Object[0];
    }
    
    public SymbolTable( Object ... symbols ) {
        this.symbols = symbols;
    }
    
    @Override
    public SymbolTable value() {
        return this;
    }
    
    public Object get( int i ) throws XtumlException {
        if ( i < 0 || i >= symbols.length ) throw new BadArgumentException("Index out of bounds");
        return symbols[i];
    }
    
}