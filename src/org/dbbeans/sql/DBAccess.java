package org.dbbeans.sql;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * This class is used to encapsulate JDBC database accesses.
 */
public class DBAccess {

    private final DB db;

    /**
     * @param db a DB object to obtain Connections to the database.
     */
    public DBAccess(final DB db) {
        this.db = db;
    }

    /**
     * Use this method to update the database.
     * If you need to get the id of a newly created row, use the createRecord function.
     * @param query the update to execute.
     * @param querySetup to setup the parameters of update.
     * @return the number of database rows affected by the update.
     * @throws SQLRuntimeException if an SQLException is thrown during database access, it will be rethrown as a SQLRuntimeException.
     */
    public int processUpdate(final String query, final DBQuerySetup querySetup) {
        final int count;

        Connection conn = null;
        try {
            conn = db.getConnection();
            count = DBUtils.processUpdate(conn, query, querySetup);
            conn.close();
        } catch (final SQLException ex) {
            throw new SQLRuntimeException(ex);
        } finally {
            DBUtils.connectionSilentClose(conn);
        }

        return count;
    }

    /**
     * Use this method to insert a single new row in the database and obtain its ID.
     * If you need to insert more than one row at a time, use the processUpdate function instead.
     * @param query the SQL query used to insert the row.
     * @param querySetup to setup the data in the query.
     * @return the id of the newly created row as a long; if you need an int, you will have to cast it.
     * @throws SQLRuntimeException if an SQLException is thrown during database access, it will be rethrown as a SQLRuntimeException.
     * @throws IllegalArgumentException if the number of rows affected in the database is not strictly one.
     */
    public long createRecord(final String query, final DBQuerySetup querySetup) {
        final long id;

        Connection conn = null;
        try {
            conn = db.getConnection();
            id = DBUtils.createRecord(conn, query, querySetup);
            conn.close();
        } catch (final SQLException ex) {
            throw new SQLRuntimeException(ex);
        } finally {
            DBUtils.connectionSilentClose(conn);
        }

        return id;
    }

    /**
     * Use this method to query the database.
     * @param query the query to execute.
     * @param querySetupProcess to setup the query parameters and process its results.
     * @throws SQLRuntimeException if an SQLException is thrown during database access, it will be rethrown as a SQLRuntimeException.
     */
    public void processQuery(final String query, final DBQuerySetupProcess querySetupProcess) {
        Connection conn = null;
        try {
            conn = db.getConnection();
            DBUtils.processQuery(conn, query, querySetupProcess);
            conn.close();
        } catch (final SQLException ex) {
            throw new SQLRuntimeException(ex);
        } finally {
            DBUtils.connectionSilentClose(conn);
        }
    }

    public void processQuery(final String query, final DBQuerySetup querySetup, final DBQueryProcess queryProcess) {
        Connection conn = null;
        try {
            conn = db.getConnection();
            DBUtils.processQuery(conn, query, querySetup, queryProcess);
            conn.close();
        } catch (final SQLException ex) {
            throw new SQLRuntimeException(ex);
        } finally {
            DBUtils.connectionSilentClose(conn);
        }
    }

    public void processQuery(final String query, final DBQueryProcess queryProcess) {
        Connection conn = null;
        try {
            conn = db.getConnection();
            DBUtils.processQuery(conn, query, queryProcess);
            conn.close();
        } catch (final SQLException ex) {
            throw new SQLRuntimeException(ex);
        } finally {
            DBUtils.connectionSilentClose(conn);
        }
    }

    public <T> T processQuery(final String query, final DBQuerySetupRetrieveData<T> querySetupRetrieveData) {
        final T data;

        Connection conn = null;
        try {
            conn = db.getConnection();
            data = DBUtils.processQuery(conn, query, querySetupRetrieveData);
            conn.close();
        } catch (final SQLException ex) {
            throw new SQLRuntimeException(ex);
        } finally {
            DBUtils.connectionSilentClose(conn);
        }

        return data;
    }

    public <T> T processQuery(final String query, final DBQuerySetup querySetup, final DBQueryRetrieveData<T> queryRetrieveData) {
        final T data;

        Connection conn = null;
        try {
            conn = db.getConnection();
            data = DBUtils.processQuery(conn, query, querySetup, queryRetrieveData);
            conn.close();
        } catch (final SQLException ex) {
            throw new SQLRuntimeException(ex);
        } finally {
            DBUtils.connectionSilentClose(conn);
        }

        return data;
    }

    public <T> T processQuery(final String query, final DBQueryRetrieveData<T> retrieveData) {
        final T data;

        Connection conn = null;
        try {
            conn = db.getConnection();
            data = DBUtils.processQuery(conn, query, retrieveData);
            conn.close();
        } catch (final SQLException ex) {
            throw new SQLRuntimeException(ex);
        } finally {
            DBUtils.connectionSilentClose(conn);
        }

        return data;
    }

    public void processUpdates(final String query, final DBUpdates updates) {
        Connection conn = null;
        try {
            conn = db.getConnection();
            DBUtils.processUpdates(conn, query, updates);
            conn.close();
        } catch (final SQLException ex) {
            throw new SQLRuntimeException(ex);
        } finally {
            DBUtils.connectionSilentClose(conn);
        }
    }

    public <T> T processQueries(final String query, final DBQueries<T> queries) {
        final T data;

        Connection conn = null;
        try {
            conn = db.getConnection();
            data = DBUtils.processQueries(conn, query, queries);
            conn.close();
        } catch (final SQLException ex) {
            throw new SQLRuntimeException(ex);
        } finally {
            DBUtils.connectionSilentClose(conn);
        }

        return data;
    }

    public void processQueries(final String query, final DBQueriesNoReturn queries) {
        Connection conn = null;
        try {
            conn = db.getConnection();
            DBUtils.processQueries(conn, query, queries);
            conn.close();
        } catch (final SQLException ex) {
            throw new SQLRuntimeException(ex);
        } finally {
            DBUtils.connectionSilentClose(conn);
        }
    }
}
