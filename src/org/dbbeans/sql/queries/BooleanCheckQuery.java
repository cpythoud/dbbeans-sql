package org.dbbeans.sql.queries;

import org.dbbeans.sql.DBQuerySetupRetrieveData;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * For the common case when you need to know if a query generates results or not.
 *
 * This abstract class is intended to give you a head start in the implementation of a common case scenario:
 * a given situation is true if a certain query generate results (like a name is already present in the database)
 * and false otherwise (the name doesn't exist yet).
 *
 * The user of this class only need to implement the setupPreparedStatement part of the {@link org.dbbeans.sql.DBQueryProcess} interface.
 */
public abstract class BooleanCheckQuery implements DBQuerySetupRetrieveData<Boolean> {

    /**
     * Check if the query produced results.
     * @param rs the ResultSet from the execution of the query.
     * @return true if the ResultSet contains results, false otherwise.
     * @throws SQLException
     */
    @Override
    public final Boolean processResultSet(final ResultSet rs) throws SQLException {
        return rs.next();
    }
}
