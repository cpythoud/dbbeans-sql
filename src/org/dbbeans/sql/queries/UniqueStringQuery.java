package org.dbbeans.sql.queries;

import org.dbbeans.sql.DBQuerySetupRetrieveData;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by chris on 1/7/14.
 */
public class UniqueStringQuery implements DBQuerySetupRetrieveData<Boolean> {
    final String s;
    final long id;

    public UniqueStringQuery(final String s, final long id) {
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
