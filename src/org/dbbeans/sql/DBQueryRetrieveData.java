package org.dbbeans.sql;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * ...
 */
public interface DBQueryRetrieveData<T> {
    /**
     * @param rs the ResultSet from the execution of the query.
     * @throws java.sql.SQLException
     */
    public T processResultSet(final ResultSet rs) throws SQLException;
}
