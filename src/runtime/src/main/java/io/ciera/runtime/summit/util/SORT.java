package io.ciera.runtime.summit.util;

/**
 * <p>Provides an architectural hook to return sorted selections.</p>
 *
 * <p>
 * The bridges in this external entity are not bridges in the normal sense that
 * the provide an interface into hand written code, but they serve as a proxy
 * for a missing OAL feature to sort the result of a {@code select} statement.
 * </p>
 *
 * <p>
 * Inject a call to one of the {@code SORT} bridges in a "where" clause to cause
 * the result to be sorted. The {@code SORT} bridges return {@code boolean}
 * which allows them to be included with the rest of a "where" expression via a
 * logical {@code and}.
 * </p>
 *
 * <p>
 * Example:
 * </p>
 *
 * <p>
 * {@code
 *   select many persons from instances of Person where (SORT::ascending(attr:"name") and selected.age >= 21);
 * }
 * </p>
 *
 * <p>
 * This selection will result in a set of {@code Person}s greater than or equal
 * to 21 years of age, sorted by name. The {@code SORT} bridges only support
 * sorting by a single attribute at a time.
 * </p>
 */
public interface SORT {

    /**
     * Sort the selection ascending by the attribute specified.
     *
     * @param attr the attribute to sort by
     * @return {@literal true}
     */
    public boolean ascending(final String attr);

    /**
     * Sort the selection descending by the attribute specified.
     *
     * @param attr the attribute to sort by
     * @return {@literal true}
     */
    public boolean descending(final String attr);

}
