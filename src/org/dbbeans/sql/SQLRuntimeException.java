package org.dbbeans.sql;

import java.sql.SQLException;

/**
 * This class is the unchecked equivalent of SQLException.
 *
 * The JDK forces us to catch SQLExceptions in many places. Most of the time, these exceptions come from a runtime condition
 * and not a logic error. To not lose the information while at the same time not forcing the clients of our code to handle
 * SQLExceptions, we usually rethrow the exception as a RuntimeException.
 *
 * We suggest you use SQLRuntimeException instead.
 */
public class SQLRuntimeException extends RuntimeException {

    /**
     * @param ex the SQLException to be rethrown as a SQLRuntimeException
     */
    public SQLRuntimeException(final SQLException ex) {
        super(ex.getMessage(), ex);
    }

    /**
     * @return the SQLException that was passed as an argument to the constructor that accept an SQLException as argument
     * or null if any other constructor inherited from RuntimeException was used instead.
     */
    public SQLException getSQLException() {
        return (SQLException) getCause();
    }

}
