package org.dbbeans.sql.queries;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * To check uniqueness of a String in a column.
 */
public class UniqueStringQueryWithLongID extends UniqueStringQueryBase {
    final long id;

    public UniqueStringQueryWithLongID(final String s, final long id) {
        super(s);
        this.id = id;
    }

    @Override
    public void processResultSet(final ResultSet rs) throws SQLException {
        if (rs.next()) {
            if (rs.getLong(1) != id)
                answer = false;
        }
    }
}
