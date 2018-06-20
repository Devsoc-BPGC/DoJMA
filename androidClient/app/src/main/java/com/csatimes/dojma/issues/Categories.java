package com.csatimes.dojma.issues;

import java.util.Arrays;
import java.util.List;

/**
 * @author Rushikesh Jogdand.
 */
public final class Categories {
    private static final List<String> CATEGORIES = Arrays.asList("Campus",
            "National", "World", "Technology", "Sports", "Interviews", "DoJMA Recommends",
            "Bitzfeed");

    public static List<String> getCATEGORIES() {
        return CATEGORIES;
    }

}
