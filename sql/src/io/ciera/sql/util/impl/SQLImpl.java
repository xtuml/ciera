package io.ciera.sql.util.impl;

import io.ciera.cairn.util.Utility;
import io.ciera.sql.util.SQL;
import io.ciera.summit.components.IComponent;
import io.ciera.summit.exceptions.XtumlException;
import io.ciera.summit.types.XtumlString;

public class SQLImpl<C extends IComponent<C>> extends Utility<C> implements SQL {

    public SQLImpl( C context ) {
        super( context );
    }

    @Override
    public void load() throws XtumlException {
    }

    @Override
    public void load_file( XtumlString file ) throws XtumlException {
    }

    @Override
    public void serialize() throws XtumlException {
    }

    @Override
    public void serialize_file( XtumlString file ) throws XtumlException {
    }

}
