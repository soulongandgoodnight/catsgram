package ru.yandex.practicum.catsgram.service;

import java.time.Instant;
import java.util.Comparator;

public enum SortOrder {
    ASCENDING, DESCENDING;

    public static SortOrder from(String order) {
        switch (order.toLowerCase()) {
            case "ascending":
            case "asc":
                return ASCENDING;
            case "descending":
            case "desc":
                return DESCENDING;
            default:
                return null;
        }
    }

    public Comparator<Instant> getComparator() {
        return this == ASCENDING ? Comparator.naturalOrder() : Comparator.reverseOrder();
    }
}
