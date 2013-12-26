package org.dbbeans.sql;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * In conjunction with the DBAccess class, use implementations of this interface to insert the code for processing
 * the results of a database query.
 *
 * You should create an anonymous inner class (Java 6 & 7), a lambda expression (Java 8) or a closure (Groovy).
 */
public interface DBQueryProcess {

    /**
     * @param rs the ResultSet from the execution of the query.
     * @throws SQLException
     */
    public void processResultSet(final ResultSet rs) throws SQLException;
}
