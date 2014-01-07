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

    public UniqueStringQuery(final String s) {
        this(s, 0);
    }

    public UniqueStringQuery(final String s, final long id) {
        if (id < 0)
            throw new IllegalArgumentException("id should be >= 0");

        this.s = s;
        this.id = id;
    }

    @Override
    public final void setupPreparedStatement(final PreparedStatement stat) throws SQLException {
        stat.setString(1, s);
    }

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
