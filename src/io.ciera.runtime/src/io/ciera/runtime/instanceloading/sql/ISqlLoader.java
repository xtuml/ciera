package io.ciera.runtime.instanceloading.sql;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import io.ciera.runtime.instanceloading.IPopulationLoader;
import io.ciera.runtime.summit.exceptions.XtumlException;

public interface ISqlLoader extends IPopulationLoader {
	
	public void insert(String tableName, List<Object> values) throws XtumlException;

	public void batchRelate() throws XtumlException;
	
	public void setIn(InputStream in);
	
	public InputStream getIn();

	public void setOut(OutputStream out);

	public OutputStream getOut();

}
