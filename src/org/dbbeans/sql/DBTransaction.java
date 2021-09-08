package org.dbbeans.sql;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * This class is used to encapsulate JDBC transactions.
 */
public class DBTransaction {

    final DB db;
    final Connection conn;

    /**
     * @param db a {@link DB} object to obtain connections to the database.
     */
    public DBTransaction(final DB db) {
        this.db = db;
        try {
            conn = db.getConnection();
            conn.setAutoCommit(false);
        } catch (final SQLException ex) {
            throw new SQLRuntimeException(ex);
        }
    }

    /**
     * Use this method to update the database.
     * If you need to get the id of a newly created row, use the createRecord function.
     * @param query SQL query.
     * @param querySetup an object implementing the {@link DBQuerySetup} interface, used to setup the parameters for the update.
     * @return the number of database rows affected by the update.
     * @throws SQLRuntimeException if an SQLException is thrown during database access, it will be rethrown as a SQLRuntimeException.
     * @see DBTransaction#addRecordCreation(String, DBQuerySetup)
     */
    public int addUpdate(final String query, final DBQuerySetup querySetup) {
        final int count;

        try {
            count = DBUtils.processUpdate(conn, query, querySetup);
        } catch (final SQLException ex) {
            throw new SQLRuntimeException(ex);
        }

        return count;
    }

    /**
     * Use this method to insert a single new row in the database and obtain its ID.
     * If you need to insert more than one row at a time, use the processUpdate function instead.
     * @param query SQL query used to insert the row.
     * @param querySetup an object implementing the {@link DBQuerySetup} interface, used to setup the data used in the query.
     * @return the id of the newly created row as a long; if you need an int, you will have to cast it.
     * @throws SQLRuntimeException if an SQLException is thrown during database access, it will be rethrown as a SQLRuntimeException.
     * @throws IllegalArgumentException if the number of rows affected in the database is not strictly one.
     * @see DBTransaction#addUpdate(String, DBQuerySetup)
     */
    public long addRecordCreation(final String query, final DBQuerySetup querySetup) {
        final long id;

        try {
            id = DBUtils.createRecord(conn, query, querySetup);
        } catch (final SQLException ex) {
            throw new SQLRuntimeException(ex);
        }

        return id;
    }

    /**
     * Use this method to query the database.
     * If you want to use java 8 lambda syntax, you should use {@link DBTransaction#addQuery(String, DBQuerySetup, DBQueryProcess)} instead.
     * @param query SQL query.
     * @param querySetupProcess an object implementing the {@link DBQuerySetupProcess} interface, used to setup the query parameters and process its results.
     * @throws SQLRuntimeException if an SQLException is thrown during database access, it will be rethrown as a {@link SQLRuntimeException}.
     * @see DBTransaction#addQuery(String, DBQuerySetup, DBQueryProcess)
     * @see DBTransaction#addQuery(String, DBQueryProcess)
     */
    @Deprecated
    public void addQuery(final String query, final DBQuerySetupProcess querySetupProcess) {
        try {
            DBUtils.processQuery(conn, query, querySetupProcess);
        } catch (final SQLException ex) {
            throw new SQLRuntimeException(ex);
        }
    }

    /**
     * Use this method to query the database.
     * Alternatively, you can use the {@link DBTransaction#addQuery(String, DBQuerySetupProcess)} function, but you loose the option to use java 8 lambda syntax.
     * @param query SQL query.
     * @param querySetup an object implementing the {@link DBQuerySetup} interface, used to setup the query parameters.
     * @param queryProcess an object implementing the {@link DBQueryProcess} interface, used to process the query results.
     * @throws SQLRuntimeException if an SQLException is thrown during database access, it will be rethrown as a SQLRuntimeException.
     * @see DBTransaction#addQuery(String, DBQuerySetupProcess)
     * @see DBTransaction#addQuery(String, DBQueryProcess)
     */
    public void addQuery(final String query, final DBQuerySetup querySetup, final DBQueryProcess queryProcess) {
        try {
            DBUtils.processQuery(conn, query, querySetup, queryProcess);
        } catch (final SQLException ex) {
            throw new SQLRuntimeException(ex);
        }
    }

    /**
     * Use this method to query the database.
     * @param query SQL query.
     * @param queryProcess an object implementing the {@link DBQueryProcess} interface, used to process the query results.
     * @see DBTransaction#addQuery(String, DBQuerySetupProcess)
     * @see DBTransaction#addQuery(String, DBQuerySetup, DBQueryProcess)
     */
    public void addQuery(final String query, final DBQueryProcess queryProcess) {
        try {
            DBUtils.processQuery(conn, query, queryProcess);
        } catch (final SQLException ex) {
            throw new SQLRuntimeException(ex);
        }
    }

    /**
     * Use this method to query the database.
     * If you want to use java 8 lambda syntax, you should use {@link DBTransaction#addQuery(String, DBQuerySetup, DBQueryRetrieveData)} instead.
     * @param query SQL query.
     * @param querySetupRetrieveData an object implementing the {@link DBQuerySetupRetrieveData} interface, used to setup the query parameters and process its results.
     * @param <T> type of query result.
     * @return result of the query.
     * @see DBTransaction#addQuery(String, DBQuerySetup, DBQueryRetrieveData)
     * @see DBTransaction#addQuery(String, DBQueryRetrieveData)
     */
    public <T> T addQuery(final String query, final DBQuerySetupRetrieveData<T> querySetupRetrieveData) {
        final T data;

        try {
            data = DBUtils.processQuery(conn, query, querySetupRetrieveData);
        } catch (final SQLException ex) {
            throw new SQLRuntimeException(ex);
        }

        return data;
    }

    /**
     * Use this method to query the database.
     * Alternatively, you can use the {@link DBTransaction#addQuery(String, DBQuerySetupRetrieveData)} function, but you loose the option to use java 8 lambda syntax.
     * @param query SQL query.
     * @param querySetup an object implementing the {@link DBQuerySetup} interface, used to setup the query parameters.
     * @param queryRetrieveData an object implementing the {@link DBQueryRetrieveData} interface, used to process the query results.
     * @param <T> type of query result.
     * @return result of the query.
     * @see DBTransaction#addQuery(String, DBQuerySetupRetrieveData)
     * @see DBTransaction#addQuery(String, DBQueryRetrieveData)
     */
    public <T> T addQuery(final String query, final DBQuerySetup querySetup, final DBQueryRetrieveData<T> queryRetrieveData) {
        final T data;

        try {
            data = DBUtils.processQuery(conn, query, querySetup, queryRetrieveData);
        } catch (final SQLException ex) {
            throw new SQLRuntimeException(ex);
        }

        return data;
    }

    /**
     * Use this method to query the database.
     * @param query SQL query.
     * @param queryRetrieveData an object implementing the {@link DBQueryRetrieveData} interface, used to process the query results.
     * @param <T> type of query result.
     * @return result of the query.
     * @see DBTransaction#addQuery(String, DBQuerySetupRetrieveData)
     * @see DBTransaction#addQuery(String, DBQuerySetup, DBQueryRetrieveData)
     */
    public <T> T addQuery(final String query, final DBQueryRetrieveData<T> queryRetrieveData) {
        final T data;

        try {
            data = DBUtils.processQuery(conn, query, queryRetrieveData);
        } catch (final SQLException ex) {
            throw new SQLRuntimeException(ex);
        }

        return data;
    }

    /**
     * Use this method to update the database by processing multiple updates.
     * @param query SQL query.
     * @param updates an object implementing the {@link DBUpdates} interface, used to execute the updates.
     */
    public void addUpdates(final String query, final DBUpdates updates) {
        try {
            DBUtils.processUpdates(conn, query, updates);
        } catch (final SQLException ex) {
            throw new SQLRuntimeException(ex);
        }
    }

    /**
     * Use this method to process multiple queries on the database and retrieve their result.
     * @param query SQL query.
     * @param queries an object implementing the {@link DBQueries} interface, used to process the queries result.
     * @param <T> type of queries result.
     * @return result of the queries.
     * @see DBTransaction#addQueries(String, DBQueriesNoReturn)
     */
    public <T> T addQueries(final String query, final DBQueries<T> queries) {
        final T data;

        try {
            data = DBUtils.processQueries(conn, query, queries);
        } catch (final SQLException ex) {
            throw new SQLRuntimeException(ex);
        }

        return data;
    }

    /**
     * Use this method to process multiple queries on the database without returning results to the caller.
     * @param query SQL query.
     * @param queries an object implementing the {@link DBQueriesNoReturn} interface, used to process the queries result.
     * @see  DBTransaction#addQueries(String, DBQueries)
     */
    public void addQueries(final String query, final DBQueriesNoReturn queries) {
        try {
            DBUtils.processQueries(conn, query, queries);
        } catch (final SQLException ex) {
            throw new SQLRuntimeException(ex);
        }
    }

    /**
     * Use this function to commit the changes to the database, once your are done setting up the transaction with this class other functions.
     */
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

    /**
     * Roll back all changes previously set up via this class other functions.
     */
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
