package com.zo.customerapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SearchResultFragment extends Fragment {
    List<ProductData> list;
    ProductsListAdapter adapter;

    public SearchResultFragment(JSONArray response) {
        list = new ArrayList<ProductData>();
        try {
            for (int i = 0; i < response.length(); i++) {
                JSONObject json = response.getJSONObject(i);
                JSONArray shelves = json.getJSONArray("shelves");

                if(shelves != null) {
                    for (int j = 0; j < shelves.length(); j++) {
                        list.add(new ProductData(json.getInt("id"), json.getString("product_name"),
                                json.getString("manufacturer"),
                                0, shelves.getInt(j)));
                        MainActivity.queue.start();
                        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest
                                (Request.Method.GET, MainActivity.restEndpoint + "products-on-shelves/?shelf=" + shelves.getInt(j) +"&product=" + json.getInt("id") , null, new Response.Listener<JSONArray>() {
                                    @Override
                                    public void onResponse(JSONArray response) {
                                        try {
                                        if (response.length() > 0) {
                                            Iterator<ProductData> iterator = list.iterator();
                                            while (iterator.hasNext()) {
                                                ProductData product = iterator.next();
                                                if (product.getId() == response.getJSONObject(0).getInt("product") &&
                                                        product.getShelf() == response.getJSONObject(0).getInt("shelf")) {
                                                    product.setAmount(response.getJSONObject(0).getInt("amount"));
                                                    adapter.notifyDataSetChanged();
                                                }
                                            }
                                        }
                                        } catch (Exception e) {Log.println(Log.ERROR, "JSON", e.getMessage()); e.printStackTrace();}
                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Log.println(Log.ERROR, "JSON", error.getMessage());
                                        error.printStackTrace();
                                    }
                                });
                        MainActivity.queue.add(jsonObjectRequest);
                    }
                }

            }
        } catch(Exception e) {
            Log.println(Log.ERROR, "JSON", "Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_search_result, container, false);
        adapter = new ProductsListAdapter(list);
        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.resultsRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(adapter);

        return v;
    }

}
