package org.dbbeans.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * In conjunction with the {@link DBAccess} or {@link DBTransaction} class, you use implementations of this interface to insert the code for setting up
 * a database update.
 *
 * You should create an anonymous inner class (Java 6 & 7), a lambda expression (Java 8) or a closure (Groovy).
 */
public interface DBQuerySetup {

    /**
     * Implement this function to setup the parameters of the PreparedStatement that will be handed to your class by {@link DBAccess} or {@link DBTransaction}.
     * @param stat the preparedStatement to be set up by your code.
     * @throws SQLException
     */
    public void setupPreparedStatement(final PreparedStatement stat) throws SQLException;
}
