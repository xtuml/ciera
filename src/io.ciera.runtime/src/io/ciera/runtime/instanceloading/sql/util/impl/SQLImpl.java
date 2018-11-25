package io.ciera.runtime.instanceloading.sql.util.impl;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import io.ciera.runtime.instanceloading.sql.ISqlLoader;
import io.ciera.runtime.instanceloading.sql.util.SQL;
import io.ciera.runtime.summit.components.IComponent;
import io.ciera.runtime.summit.exceptions.InstancePopulationException;
import io.ciera.runtime.summit.exceptions.XtumlException;
import io.ciera.runtime.summit.util.Utility;

public class SQLImpl<C extends IComponent<C>> extends Utility<C> implements SQL {

    ISqlLoader loader;

    public SQLImpl(C context) {
        super(context);
        loader = (ISqlLoader)context.getLoader("Sql");
    }

    @Override
    public void load() throws XtumlException {
    	if (null != loader) {
    		loader.setIn(System.in);
    		loader.load();
    	}
    }

    @Override
    public void load_file(String file) throws XtumlException {
        if (null != loader && null != file) {
            try {
                loader.setIn(new FileInputStream(file));
                loader.load();
            } catch (IOException e) {
                throw new InstancePopulationException("Could not read input file.");
            }
        }
    }

    @Override
    public void serialize() throws XtumlException {
        if (null != loader) {
        	loader.setOut(System.out);
            loader.serialize();
        }
    }

    @Override
    public void serialize_file(String file) throws XtumlException {
        if (null != loader && null != file) {
            try {
                FileOutputStream outFile = new FileOutputStream(file);
                outFile.write("-- SQL data\n".getBytes());
        	    loader.setOut(outFile);
                loader.serialize();
                outFile.close();
            } catch (IOException e) {
                throw new XtumlException("Could not write output file.");
            }
        }
    }

}
