package org.dbbeans.sql;

/**
 * In conjunction with the DBAccess class, use implementations of this interface to insert the code for setting up
 * a database query and process its results.
 *
 * You should create an anonymous inner class (Java 6 & 7), a lambda expression (Java 8) or a closure (Groovy).
 */
public interface DBQuerySetupProcess extends DBQuerySetup, DBQueryProcess { }
