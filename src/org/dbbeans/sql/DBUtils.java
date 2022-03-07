package org.dbbeans.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * This class contains static utility methods to help manage various aspects of JDBC programming.
 *
 * You usually don't need to call these method directly. Instead you would use an instance of the
 * {@link DBAccess} or the {@link DBTransaction} class (which call these function internally).
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

    /**
     * Process a database update.
     * @param conn database connection to use.
     * @param query SQL query.
     * @param querySetup an object implementing the {@link DBQuerySetup} interface, used to set up the parameters for the update.
     * @return how many table rows were affected by the update.
     * @throws SQLException
     */
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

    /**
     * Creates a new record in the database.
     * @param conn database connection to use.
     * @param query SQL query.
     * @param querySetup an object implementing the {@link DBQuerySetup} interface, used to set up the parameters for the record creation.
     * @return the id of the created record.
     * @throws SQLException
     * @throws java.lang.IllegalArgumentException if not exactly one row is created.
     */
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

    /**
     * Process a query on the database.
     * @param conn database connection to use.
     * @param query SQL query.
     * @param querySetup an object implementing the {@link DBQuerySetup} interface, used to set up parameters for the query.
     * @param queryProcess an object implementing the {@link DBQueryProcess} interface, used to process results from the query.
     * @throws SQLException
     * @see DBUtils#processQuery(java.sql.Connection, String, DBQueryProcess)
     */
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

    /**
     * Process a query on the database.
     * @param conn database connection to use.
     * @param query SQL query.
     * @param queryProcess an object implementing the {@link DBQueryProcess} interface, used to process results from the query.
     * @throws SQLException
     * @see DBUtils#processQuery(java.sql.Connection, String, DBQuerySetup, DBQueryProcess)
     */
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

    /**
     * Process a query on the database.
     * @param conn database connection to use.
     * @param query SQL query.
     * @param querySetup an object implementing the {@link DBQuerySetup} interface, used to set up parameters for the query.
     * @param queryRetrieveData an object implementing the {@link DBQueryRetrieveData} interface, used to get the query result.
     * @param <T> type of query result.
     * @return result of the query.
     * @throws SQLException
     * @see DBUtils#processQuery(java.sql.Connection, String, DBQueryRetrieveData)
     */
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

    /**
     * Process a query on the database.
     * @param conn database connection to use.
     * @param query SQL query.
     * @param queryRetrieveData an object implementing the {@link DBQueryRetrieveData} interface, used to get the query result.
     * @param <T> type of query result.
     * @return result of the query.
     * @throws SQLException
     * @see DBUtils#processQuery(java.sql.Connection, String, DBQuerySetup, DBQueryRetrieveData)
     */
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

    /**
     * Apply some updates to the database.
     * @param conn database connection to use.
     * @param query SQL query.
     * @param updates an object implementing the {@link DBUpdates} interface, containing the code for the updates.
     * @throws SQLException
     */
    public static void processUpdates(final Connection conn, final String query, final DBUpdates updates) throws SQLException {
        final PreparedStatement stat = conn.prepareStatement(query);
        try {
            updates.execute(stat);
            stat.close();
        } finally {
            preparedStatementSilentClose(stat);
        }
    }

    /**
     * Process some queries on the database and retrieve the result.
     * @param conn database connection to use.
     * @param query SQL query.
     * @param queries an object implementing the {@link DBQueries} interface, used to get the queries result.
     * @param <T> type of queries result.
     * @return result of the queries.
     * @throws SQLException
     * @see DBUtils#processQueries(java.sql.Connection, String, DBQueriesNoReturn)
     */
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

    /**
     * Process some queries on the database and retrieve the result.
     * @param conn database connection to use.
     * @param query SQL query.
     * @param queries an object implementing the {@link DBQueriesNoReturn} interface, used to get the queries result.
     * @throws SQLException
     * @see DBUtils#processQueries(java.sql.Connection, String, DBQueries)
     */
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
