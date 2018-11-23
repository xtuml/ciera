package io.ciera.runtime.instanceloading.sql;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import io.ciera.runtime.instanceloading.IChangeLog;
import io.ciera.runtime.instanceloading.sql.parser.SQLLexer;
import io.ciera.runtime.instanceloading.sql.parser.SQLParser;
import io.ciera.runtime.instanceloading.sql.parser.SQLParser.Sql_fileContext;
import io.ciera.runtime.summit.exceptions.InstancePopulationException;
import io.ciera.runtime.summit.exceptions.XtumlException;

public abstract class SqlLoader implements ISqlLoader {
	
	private InputStream in;
	private OutputStream out;
	
	public SqlLoader() {
		in = System.in;
		out = System.out;
	}

	@Override
	public void load() throws XtumlException {
        if (null != in) {
            try {
                SQLLexer lexer = new SQLLexer(CharStreams.fromStream(in));
                CommonTokenStream tokens = new CommonTokenStream(lexer);
                SQLParser parser = new SQLParser(tokens);
                Sql_fileContext ctx = parser.sql_file();
                ParseTreeWalker walker = new ParseTreeWalker();
                SqlListener listener = new SqlListener(this);
                walker.walk(listener, ctx);
            } catch (IOException e) {
                throw new InstancePopulationException("Could not read input file.");
            }
        }
	}


	@Override
	public void serialize(IChangeLog changeLog) throws XtumlException {
		serialize();
	}

	@Override
	public void setIn(InputStream in) {
		this.in = in;
	}

	@Override
	public InputStream getIn() {
		return in;
	}

	@Override
	public void setOut(OutputStream out) {
		this.out = out;
	}

	@Override
	public OutputStream getOut() {
		return out;
	}

}
