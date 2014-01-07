package org.dbbeans.sql;

/**
 * This utility class allow you to store together the id of a database row and its 'name', and by that we mean
 * the expression by which the end user would refer to the entity referenced by the database row.
 *
 * This class is commonly used in applications developed with the DbBean framework. Collections of instances of this
 * class are often used to create user interface element, like select lists in HTML applications, the id being
 * used for the value of the option and the name for the information displayed to the user between option tags.
 *
 * Objects of this class are immutable.
 */
public class IdNamePair {

    /**
     * Creates an IdNamePair object.
     * @param id the id of the database row or bean.
     * @param name the name by which the end user refer to the entity represented by the database row or bean.
     * @throws IllegalArgumentException if id is negative.
     */
    public IdNamePair(final int id, final String name) {
        if (id < 0)
            throw new IllegalArgumentException("id must be zero or positive");

        this.id = Integer.toString(id);
        this.name = name;
    }

    /**
     * Creates an IdNamePair object.
     * @param id the id of the database row or bean.
     * @param name the name by which the end user refer to the entity represented by the database row or bean.
     * @throws IllegalArgumentException if id is negative.
     */
    public IdNamePair(final long id, final String name) {
        if (id < 0)
            throw new IllegalArgumentException("id must be zero or positive");

        this.id = Long.toString(id);
        this.name = name;
    }

    /**
     * Creates an IdNamePair object.
     * @param id the id of the database row or bean.
     * @param name the name by which the end user refer to the entity represented by the database row or bean.
     * @throws IllegalArgumentException if id is negative.
     */
    public IdNamePair(final String id, final String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Returns the id of the pair as a String
     * @return id of pair.
     */
    public String getId() {
        return id;
    }

    /**
     * Returns the name of the pair.
     * @return name of pair.
     */
    public String getName() {
        return name;
    }

    private final String id;
    private final String name;
}
