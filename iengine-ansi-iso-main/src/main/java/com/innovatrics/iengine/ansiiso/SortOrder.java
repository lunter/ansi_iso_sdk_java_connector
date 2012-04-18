package com.innovatrics.iengine.ansiiso;
/**
 * Defines sort order of minutiae points
 */
public enum SortOrder {
    /**
    No ordering required.
     */
    SORT_NONE,
    /**
    Cartesian x-coordinate is used for ordering, ascending order.
     */
    SORT_X_ASC,
    /**
    Cartesian x-coordinate is used for ordering, descending order.
     */
    SORT_X_DESC,
    /**
    Cartesian y-coordinate is used for ordering, ascending order.
     */
    SORT_Y_ASC,
    /**
    Cartesian y-coordinate is used for ordering, descending order.
     */
    SORT_Y_DESC
}
