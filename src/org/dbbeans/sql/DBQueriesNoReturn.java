package org.dbbeans.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * .
 */
public interface DBQueriesNoReturn {
    public void process(final PreparedStatement stat) throws SQLException;
}
