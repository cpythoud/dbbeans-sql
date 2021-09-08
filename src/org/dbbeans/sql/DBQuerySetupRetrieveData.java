package org.dbbeans.sql;

/**
 * In conjunction with the {@link DBAccess} or {@link DBTransaction} class, use implementations of this interface to insert the code for setting up
 * a database query and process its results.
 *
 * You should create an anonymous inner class (Java) or a closure (Groovy).
 */
@Deprecated
public interface DBQuerySetupRetrieveData<T> extends DBQuerySetup, DBQueryRetrieveData<T> { }
