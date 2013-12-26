package org.dbbeans.sql.queries;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Base class for...
 */
abstract class UniqueStringQueryBase extends BooleanCheckBase {
    final String s;

    protected UniqueStringQueryBase(final String s) {
        this.s = s;
        answer = true;
    }

    @Override
    public final void setupPreparedStatement(final PreparedStatement stat) throws SQLException {
        stat.setString(1, s);
    }
}
