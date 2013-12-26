package org.dbbeans.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * In conjunction with the DBAccess class, use implementations of this interface to insert the code for setting up
 * a database update.
 *
 * You should create an anonymous inner class (Java 6 & 7), a lambda expression (Java 8) or a closure (Groovy).
 */
public interface DBQuerySetup {

    /**
     * @param stat the preparedStatement to be set up by your code.
     * @throws SQLException
     */
    public void setupPreparedStatement(final PreparedStatement stat) throws SQLException;
}
