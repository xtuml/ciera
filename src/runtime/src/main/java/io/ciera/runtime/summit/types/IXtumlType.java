package io.ciera.runtime.summit.types;

import io.ciera.runtime.summit.exceptions.BadArgumentException;
import io.ciera.runtime.summit.exceptions.NotImplementedException;
import io.ciera.runtime.summit.exceptions.XtumlException;

public interface IXtumlType {

    default public boolean equality(IXtumlType value) throws XtumlException {
        return equals(value);
    }

    default public boolean inequality(IXtumlType value) throws XtumlException {
        return !equality(value);
    }

    default public IXtumlType oneWhere(IWhere<IXtumlType> condition) throws XtumlException {
        if (null == condition)
            throw new BadArgumentException("Null condition passed to selection.");
        if (condition.evaluate(this))
            return this;
        else
            return null;
    }
    
    default public String serialize() throws XtumlException {
    	throw new NotImplementedException("Type cannot be directly serialized");
    }

}
