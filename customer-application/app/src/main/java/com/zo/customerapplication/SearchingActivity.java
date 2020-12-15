package com.zo.customerapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class SearchingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searching);
        Log.println(Log.INFO, "Search", "Searching Activity created, whoa!");
//        Intent intent = getIntent();
//        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
//            String query = intent.getStringExtra(SearchManager.QUERY);
//            search(query);
//        }
        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            SearchRecentSuggestions searchRecentSuggestions = new SearchRecentSuggestions(this, ProductsSuggestions.AUTHORITY, ProductsSuggestions.MODE);
            searchRecentSuggestions.saveRecentQuery(query, null);
            Toast.makeText(this, query, Toast.LENGTH_SHORT).show();
        }
    }

    //searches for query
    public void search(String query) {

    }
}
