package it.sevenbits.util;

public enum SortOrder {
    /**
     * Enumeration value indicating the items are sorted in increasing order.
     * For example, the set <code>1, 4, 0</code> sorted in
     * <code>ASCENDING</code> order is <code>0, 1, 4</code>.
     */
    ASCENDING,

    /**
     * Enumeration value indicating the items are sorted in decreasing order.
     * For example, the set <code>1, 4, 0</code> sorted in
     * <code>DESCENDING</code> order is <code>4, 1, 0</code>.
     */
    DESCENDING,

    /**
     * Enumeration value indicating the items are unordered.
     * For example, the set <code>1, 4, 0</code> in
     * <code>UNSORTED</code> order is <code>1, 4, 0</code>.
     */
    //UNSORTED;
    NONE;

    public static SortOrder getViceVersa(final SortOrder sortOrder) {
        if ((sortOrder == null)||(sortOrder == NONE)) {
            return ASCENDING;
        } else if (SortOrder.ASCENDING.equals(sortOrder)) {
            return DESCENDING;
        } else {
            return ASCENDING;
        }
    }
}
