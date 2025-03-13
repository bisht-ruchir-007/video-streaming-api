package com.app.practice.constants;

/**
 * Enum representing query parameters, automatically converted to lowercase.
 * <p>
 * Author: Ruchir Bisht
 */
public enum QueryParams {
    DIRECTOR,
    GENRE,
    CAST;

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
