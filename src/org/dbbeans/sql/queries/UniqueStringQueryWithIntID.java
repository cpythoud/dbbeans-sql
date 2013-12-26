package org.dbbeans.sql.queries;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * To check uniqueness of a String in a column.
 */
public class UniqueStringQueryWithIntID extends UniqueStringQueryBase {
    final int id;

    public UniqueStringQueryWithIntID(final String s, final int id) {
        super(s);
        this.id = id;
    }

    @Override
    public void processResultSet(final ResultSet rs) throws SQLException {
        if (rs.next()) {
            if (rs.getInt(1) != id)
                answer = false;
        }
    }
}
