package org.dbbeans.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * ...
 */
public interface DBUpdates {
    public void execute(final PreparedStatement stat) throws SQLException;
}
