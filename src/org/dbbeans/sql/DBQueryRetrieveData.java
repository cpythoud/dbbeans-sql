package org.dbbeans.sql;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * In conjunction with the {@link DBAccess} or {@link DBTransaction} class, use implementations of this interface to insert the code for processing
 * the results of a database query.
 *
 * You should create an anonymous inner class (Java 6 & 7), a lambda expression (Java 8) or a closure (Groovy).
 */
public interface DBQueryRetrieveData<T> {
    /**
     * Implement this function to process the ResultSet that will be handed to your class by {@link DBAccess} or {@link DBTransaction}.
     * @param rs the ResultSet from the execution of the query.
     * @throws java.sql.SQLException
     */
    public T processResultSet(final ResultSet rs) throws SQLException;
}
