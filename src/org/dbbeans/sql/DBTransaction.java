package org.dbbeans.sql;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * This class is used to encapsulate JDBC transactions.
 */
public class DBTransaction {

    final DB db;
    final Connection conn;

    public DBTransaction(final DB db) {
        this.db = db;
        try {
            conn = db.getConnection();
            conn.setAutoCommit(false);
        } catch (final SQLException ex) {
            throw new SQLRuntimeException(ex);
        }
    }

    public int addUpdate(final String query, final DBQuerySetup querySetup) {
        final int count;

        try {
            count = DBUtils.processUpdate(conn, query, querySetup);
        } catch (final SQLException ex) {
            throw new SQLRuntimeException(ex);
        }

        return count;
    }

    public long addRecordCreation(final String query, final DBQuerySetup querySetup) {
        final long id;

        try {
            id = DBUtils.createRecord(conn, query, querySetup);
        } catch (final SQLException ex) {
            throw new SQLRuntimeException(ex);
        }

        return id;
    }

    public void addQuery(final String query, final DBQuerySetupProcess querySetupProcess) {
        try {
            DBUtils.processQuery(conn, query, querySetupProcess);
        } catch (final SQLException ex) {
            throw new SQLRuntimeException(ex);
        }
    }

    public void addQuery(final String query, final DBQuerySetup querySetup, final DBQueryProcess queryProcess) {
        try {
            DBUtils.processQuery(conn, query, querySetup, queryProcess);
        } catch (final SQLException ex) {
            throw new SQLRuntimeException(ex);
        }
    }

    public void addQuery(final String query, final DBQueryProcess queryProcess) {
        try {
            DBUtils.processQuery(conn, query, queryProcess);
        } catch (final SQLException ex) {
            throw new SQLRuntimeException(ex);
        }
    }

    public <T> T addQuery(final String query, final DBQuerySetupRetrieveData<T> querySetupRetrieveData) {
        final T data;

        try {
            data = DBUtils.processQuery(conn, query, querySetupRetrieveData);
        } catch (final SQLException ex) {
            throw new SQLRuntimeException(ex);
        }

        return data;
    }

    public <T> T addQuery(final String query, final DBQuerySetup querySetup, final DBQueryRetrieveData<T> queryRetrieveData) {
        final T data;

        try {
            data = DBUtils.processQuery(conn, query, querySetup, queryRetrieveData);
        } catch (final SQLException ex) {
            throw new SQLRuntimeException(ex);
        }

        return data;
    }

    public <T> T addQuery(final String query, final DBQueryRetrieveData<T> queryRetrieveData) {
        final T data;

        try {
            data = DBUtils.processQuery(conn, query, queryRetrieveData);
        } catch (final SQLException ex) {
            throw new SQLRuntimeException(ex);
        }

        return data;
    }

    public void addUpdates(final String query, final DBUpdates updates) {
        try {
            DBUtils.processUpdates(conn, query, updates);
        } catch (final SQLException ex) {
            throw new SQLRuntimeException(ex);
        }
    }

    public <T> T addQueries(final String query, final DBQueries<T> queries) {
        final T data;

        try {
            data = DBUtils.processQueries(conn, query, queries);
        } catch (final SQLException ex) {
            throw new SQLRuntimeException(ex);
        }

        return data;
    }

    public void addQueries(final String query, final DBQueriesNoReturn queries) {
        try {
            DBUtils.processQueries(conn, query, queries);
        } catch (final SQLException ex) {
            throw new SQLRuntimeException(ex);
        }
    }

    public void commit() {
        try {
            conn.commit();
            conn.close();
        } catch (final SQLException ex) {
            throw new SQLRuntimeException(ex);
        } finally {
            DBUtils.connectionSilentClose(conn);
        }
    }

    public void rollback() {
        try {
            conn.rollback();
            conn.close();
        } catch (final SQLException ex) {
            throw new SQLRuntimeException(ex);
        } finally {
            DBUtils.connectionSilentClose(conn);
        }
    }
}
