package io.ciera.runtime.instanceloading.sql.util.impl;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import io.ciera.runtime.instanceloading.IPopulationLoader;
import io.ciera.runtime.instanceloading.sql.parser.SQLLexer;
import io.ciera.runtime.instanceloading.sql.parser.SQLParser;
import io.ciera.runtime.instanceloading.sql.parser.SQLParser.Sql_fileContext;
import io.ciera.runtime.instanceloading.sql.parser.XtumlSQLListener;
import io.ciera.runtime.instanceloading.sql.util.SQL;
import io.ciera.runtime.summit.components.IComponent;
import io.ciera.runtime.summit.exceptions.InstancePopulationException;
import io.ciera.runtime.summit.exceptions.XtumlException;
import io.ciera.runtime.summit.util.Utility;

public class SQLImpl<C extends IComponent<C>> extends Utility<C> implements SQL {

    IPopulationLoader loader;

    public SQLImpl(C context) {
        super(context);
        try {
            Class<?> componentClass = context.getClass();
            Class<?> instanceLoaderClass = Class.forName(componentClass.getName() + "PopulationLoader");
            Constructor<?> instanceLoaderConstructor = instanceLoaderClass.getConstructor(componentClass);
            loader = (IPopulationLoader) instanceLoaderConstructor.newInstance(context);
        } catch (Exception e) {
            loader = null;
        }
    }

    @Override
    public void load() throws XtumlException {
        load(System.in);
    }

    @Override
    public void load_file(String file) throws XtumlException {
        if (null != file) {
            try {
                load(new FileInputStream(file));
            } catch (IOException e) {
                throw new InstancePopulationException("Could not read input file.");
            }
        }
    }

    private void load(InputStream in) throws XtumlException {
        if (null != loader && null != in) {
            try {
                SQLLexer lexer = new SQLLexer(CharStreams.fromStream(in));
                CommonTokenStream tokens = new CommonTokenStream(lexer);
                SQLParser parser = new SQLParser(tokens);
                Sql_fileContext ctx = parser.sql_file();
                ParseTreeWalker walker = new ParseTreeWalker();
                XtumlSQLListener listener = new XtumlSQLListener(loader);
                walker.walk(listener, ctx);
            } catch (IOException e) {
                throw new InstancePopulationException("Could not read input file.");
            }
        }
    }

    @Override
    public void serialize() throws XtumlException {
        if (null != loader)
            loader.serialize(System.out);
    }

    @Override
    public void serialize_file(String file) throws XtumlException {
        if (null != loader && null != file) {
            try {
                FileOutputStream outFile = new FileOutputStream(file);
                outFile.write("-- SQL data\n".getBytes());
                loader.serialize(outFile);
                outFile.close();
            } catch (IOException e) {
                throw new XtumlException("Could not write output file.");
            }
        }
    }

}
