package org.dbbeans.sql;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * This class implements the DB interface from a DataSource name.
 */
public class DBFromDataSource implements DB {

    private final DataSource dataSource;

    /**
     * @param dataSourceName the name of the DataSource to be used
     * @throws RuntimeException if the name cannot be resolved to a DataSource, the NamingException can be retrieved
     * from RuntimeException.getCause()
     */
    public DBFromDataSource(final String dataSourceName) {
        try {
            InitialContext ctx = new InitialContext();
            dataSource = (DataSource) ctx.lookup(dataSourceName);
        } catch (NamingException ex) {
            throw new RuntimeException(ex.getMessage(), ex);
        }
    }

    /**
     * @return a Connection from the DataSource initialized by the constructor
     * @throws SQLException
     */
    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
