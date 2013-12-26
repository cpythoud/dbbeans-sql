package org.dbbeans.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * This class contains static utility methods to help manage various aspects of JDBC programming.
 */
public class DBUtils {

    /**
     * Close a PreparedStatement discarding any thrown SQLException.
     * @param stat the PreparedStatement to be closed.
     */
    public static void preparedStatementSilentClose(final PreparedStatement stat) {
        if (stat != null) {
            try { stat.close(); }
            catch (final SQLException ignore) { }
        }
    }

    /**
     * Close a Connection discarding any thrown SQLException.
     * @param conn the Connection to be closed.
     */
    public static void connectionSilentClose(final Connection conn) {
        if (conn != null) {
            try { conn.close(); }
            catch (final SQLException ignore) { }
        }
    }

    public static int processUpdate(final Connection conn, final String query, final DBQuerySetup querySetup) throws SQLException {
        int count = 0;

        final PreparedStatement stat = conn.prepareStatement(query);
        try {
            querySetup.setupPreparedStatement(stat);
            count = stat.executeUpdate();
            stat.close();
        } finally {
            preparedStatementSilentClose(stat);
        }

        return count;
    }

    public static long createRecord(final Connection conn, final String query, final DBQuerySetup querySetup) throws SQLException {
        final long id;

        final PreparedStatement stat = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        try {
            querySetup.setupPreparedStatement(stat);
            final int count = stat.executeUpdate();
            if (count != 1)
                throw new IllegalArgumentException("Record creation query did not affect a single row. Rows affected: " + count + ".");
            final ResultSet rs = stat.getGeneratedKeys();
            rs.next();
            id = rs.getLong(1);
            stat.close();
        } finally {
            preparedStatementSilentClose(stat);
        }

        return id;
    }

    public static void processQuery(final Connection conn, final String query, final DBQuerySetupProcess querySetupProcess) throws SQLException {
        final PreparedStatement stat = conn.prepareStatement(query);
        try {
            querySetupProcess.setupPreparedStatement(stat);
            final ResultSet rs = stat.executeQuery();
            querySetupProcess.processResultSet(rs);
            stat.close();
        } finally {
            preparedStatementSilentClose(stat);
        }
    }

    public static void processQuery(final Connection conn, final String query, final DBQuerySetup querySetup, final DBQueryProcess queryProcess) throws SQLException {
        final PreparedStatement stat = conn.prepareStatement(query);
        try {
            querySetup.setupPreparedStatement(stat);
            final ResultSet rs = stat.executeQuery();
            queryProcess.processResultSet(rs);
            stat.close();
        } finally {
            preparedStatementSilentClose(stat);
        }
    }

    public static void processQuery(final Connection conn, final String query, final DBQueryProcess queryProcess) throws SQLException {
        final PreparedStatement stat = conn.prepareStatement(query);
        try {
            final ResultSet rs = stat.executeQuery();
            queryProcess.processResultSet(rs);
            stat.close();
        } finally {
            preparedStatementSilentClose(stat);
        }
    }

    public static <T> T processQuery(final Connection conn, final String query, final DBQuerySetupRetrieveData<T> querySetupRetrieveData) throws SQLException {
        final T data;

        final PreparedStatement stat = conn.prepareStatement(query);
        try {
            querySetupRetrieveData.setupPreparedStatement(stat);
            final ResultSet rs = stat.executeQuery();
            data = querySetupRetrieveData.processResultSet(rs);
            stat.close();
        } finally {
            preparedStatementSilentClose(stat);
        }

        return data;
    }

    public static <T> T processQuery(final Connection conn, final String query, final DBQuerySetup querySetup, final DBQueryRetrieveData<T> queryRetrieveData) throws SQLException {
        final T data;

        final PreparedStatement stat = conn.prepareStatement(query);
        try {
            querySetup.setupPreparedStatement(stat);
            final ResultSet rs = stat.executeQuery();
            data = queryRetrieveData.processResultSet(rs);
            stat.close();
        } finally {
            preparedStatementSilentClose(stat);
        }

        return data;
    }

    public static <T> T processQuery(final Connection conn, final String query, final DBQueryRetrieveData<T> queryRetrieveData) throws SQLException {
        final T data;

        final PreparedStatement stat = conn.prepareStatement(query);
        try {
            final ResultSet rs = stat.executeQuery();
            data = queryRetrieveData.processResultSet(rs);
            stat.close();
        } finally {
            preparedStatementSilentClose(stat);
        }

        return data;
    }

    public static void processUpdates(final Connection conn, final String query, final DBUpdates updates) throws SQLException {
        final PreparedStatement stat = conn.prepareStatement(query);
        try {
            updates.execute(stat);
            stat.close();
        } finally {
            preparedStatementSilentClose(stat);
        }
    }

    public static <T> T processQueries(final Connection conn, final String query, final DBQueries<T> queries) throws SQLException {
        final T data;

        final PreparedStatement stat = conn.prepareStatement(query);
        try {
            data = queries.process(stat);
            stat.close();
        } finally {
            preparedStatementSilentClose(stat);
        }

        return data;
    }

    public static void processQueries(final Connection conn, final String query, final DBQueriesNoReturn queries) throws SQLException {
        final PreparedStatement stat = conn.prepareStatement(query);
        try {
            queries.process(stat);
            stat.close();
        } finally {
            preparedStatementSilentClose(stat);
        }
    }
}
