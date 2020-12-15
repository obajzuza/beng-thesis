package com.zo.customerapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.FragmentTransaction;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.util.Log;
import android.view.Menu;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Console;
import java.net.MalformedURLException;
import java.net.URL;


public class MainActivity extends AppCompatActivity {
    public static RequestQueue queue;
    public static URL restEndpoint;
    //private URL restEndpoint;
    private Cache cache;
    private Network network;
    private boolean firstTimeOpened = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // set-up needed for connection to API
        cache = new DiskBasedCache(getCacheDir(), 1024-1024);
        network = new BasicNetwork(new HurlStack()); // setting up the network to use HttpURLConnection
        queue = Volley.newRequestQueue(this);
        try {
            restEndpoint = new URL("http://192.168.0.115:8000/api/"); }
        catch (Exception e) {
            Log.println(Log.ERROR, "exception", "Exception while connecting to restEndpoint");
            e.printStackTrace();
        }

        // display set-up and
        setContentView(R.layout.activity_main);
        if (firstTimeOpened) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.mainPlaceholder, new FirstFragment());
            ft.commit();
            firstTimeOpened = false;
        }

        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            SearchRecentSuggestions searchRecentSuggestions = new SearchRecentSuggestions(this, ProductsSuggestions.AUTHORITY, ProductsSuggestions.MODE);
            searchRecentSuggestions.saveRecentQuery(query, null);

            String url = restEndpoint.toString();
            queue.start();
            JsonArrayRequest jsonObjectRequest = new JsonArrayRequest
                    (Request.Method.GET, url + "product/?name=" + query.replace(" ", "+"), null, new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            Log.println(Log.INFO, "Request", "---------------------------- SUCCESS ------------------------------");
                            Log.println(Log.INFO, "Request", "Response: " + response.toString());
                            if (response.length() > 0) {
                                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                                ft.replace(R.id.mainPlaceholder, new SearchResultFragment(response));
                                ft.commit();
                            } else {
                                // prompt "no such product found" in the fragment
                                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                                ft.replace(R.id.mainPlaceholder, new NoResultsFoundFragment());
                                ft.commit();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
//                            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//                            ft.replace(R.id.mainPlaceholder, new ConnectivityErrorFragment());
//                            ft.commit();
                            Log.println(Log.ERROR, "Request Error", error.getMessage());
                            error.printStackTrace();
                        }
                    });
            queue.add(jsonObjectRequest);

        }
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setSubmitButtonEnabled(true);
        return super.onCreateOptionsMenu(menu);
    }
//    //starts SearchingActivity
//    public void startSearching(View view) {
//        Intent intent = new Intent(this, SearchingActivity.class);
//        startActivity(intent);
//    }
}
