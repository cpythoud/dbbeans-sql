package org.dbbeans.sql.queries;

import org.dbbeans.sql.DBQuerySetupRetrieveData;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class address the common case when you need to be sure that a String is unique in a database table column.
 *
 * For example: you need to ensure that usernames are unique in a login table before registering a new user.
 */
public class UniqueStringQuery implements DBQuerySetupRetrieveData<Boolean> {
    final String s;
    final long id;

    /**
     * Creates a query to verify the uniqueness of the string.
     * This constructor should be used for new entries in the database. For existing entries, it's important to
     * use the other constructor that also takes the id of the existing record as an argument.
     * @param s the string to be checked.
     * @see {@link UniqueStringQuery#UniqueStringQuery(String, long)}
     */
    public UniqueStringQuery(final String s) {
        this(s, 0);
    }

    /**
     * Creates a query to verify the uniqueness of the string.
     * This constructor should be used for existing entries in the database as it requires the id of the row to be
     * passed as an argument.
     * For a new entry, you can either pass an id of 0 or use the other constructor that only takes a single String
     * argument.
     * @param s the string to be checked.
     * @param id the id of the existing row being changed or 0 for a new row.
     * @throws IllegalArgumentException if passed a negative id.
     * @see {@link UniqueStringQuery#UniqueStringQuery(String)}
     */
    public UniqueStringQuery(final String s, final long id) {
        if (id < 0)
            throw new IllegalArgumentException("id should be >= 0");

        this.s = s;
        this.id = id;
    }

    /**
     * Setup the PreparedStatement that will be used by the query.
     * @param stat the preparedStatement received from {@link org.dbbeans.sql.DBAccess} or {@link org.dbbeans.sql.DBTransaction}.
     * @throws SQLException
     */
    @Override
    public final void setupPreparedStatement(final PreparedStatement stat) throws SQLException {
        stat.setString(1, s);
    }

    /**
     * Process the results of the query.
     * @param rs the ResultSet from the execution of the query.
     * @return true if the string submitted to the constructor does not exist in the database table, false otherwise.
     * @throws SQLException
     */
    @Override
    public final Boolean processResultSet(final ResultSet rs) throws SQLException {
        boolean  answer = true;

        if (rs.next()) {
            if (rs.getLong(1) != id)
                answer = false;
        }

        return answer;
    }
}
