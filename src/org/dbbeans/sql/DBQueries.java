package org.dbbeans.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * ...
 */
public interface DBQueries<T> {
    public T process(final PreparedStatement stat) throws SQLException;
}
