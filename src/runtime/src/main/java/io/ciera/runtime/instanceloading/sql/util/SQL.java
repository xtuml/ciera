package io.ciera.runtime.instanceloading.sql.util;

import io.ciera.runtime.summit.exceptions.XtumlException;

/**
 * The SQL utility provides an interface to loading/persisting intstances as SQL
 * insert statements.
 */
public interface SQL {

    /**
     * Load an instance population from the standard input stream.
     *
     * @throws XtumlException if an error occurs during parsing, loading, or
     * relating of the SQL insert statements.
     */
    public void load() throws XtumlException;

    /**
     * Load an instance population from a file specified by {@code file}.
     *
     * @param file The path to an instance population file relative to the
     * current working directory.
     * @throws XtumlException if an error occurs during parsing, loading, or
     * relating of the SQL insert statements.
     */
    public void load_file(String file) throws XtumlException;

    /**
     * Dump an instance population to the standard output stream.
     *
     * @throws XtumlException if an error occurs during serialization of the
     * instances.
     */
    public void serialize() throws XtumlException;

    /**
     * Dump an instance population to a file specified by {@code file}.
     *
     * @param file The path to the destination instance population file relative
     * to the current working directory.
     * @throws XtumlException if an error occurs during serialization of the
     * instances.
     */
    public void serialize_file(String file) throws XtumlException;

}
