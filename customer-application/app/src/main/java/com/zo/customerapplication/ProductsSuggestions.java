package com.zo.customerapplication;

import android.content.SearchRecentSuggestionsProvider;

public class ProductsSuggestions extends SearchRecentSuggestionsProvider {
    public static final String AUTHORITY = "com.zo.customerapplication.ProductsSuggestions";
    public static final int MODE = DATABASE_MODE_QUERIES;

    public ProductsSuggestions() {
        setupSuggestions(AUTHORITY, MODE);
    }
}
