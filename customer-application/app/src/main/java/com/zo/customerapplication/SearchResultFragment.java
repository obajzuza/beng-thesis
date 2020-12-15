package com.zo.customerapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.fragment.NavHostFragment;
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
import java.util.Random;

public class SearchResultFragment extends Fragment {
    List<ProductData> list;

    public SearchResultFragment(JSONArray response) {
        Log.println(Log.DEBUG, "SearchResultFragment", "in search result fragment");
        list = new ArrayList<ProductData>();
        try {
            for (int i = 0; i < response.length(); i++) {
                JSONObject json = response.getJSONObject(i);
                JSONArray shelves = json.getJSONArray("shelves");
                ArrayList<Integer> shelvesList = new ArrayList<Integer>();
                final ArrayList<Integer> amounts = new ArrayList<Integer>();

                if(shelves != null) {
                    for (int j = 0; j < shelves.length(); j++) {
                        shelvesList.add(shelves.getInt(j));
                        MainActivity.queue.start();
                        Log.println(Log.ERROR, "JSON", "connecting to url " + MainActivity.restEndpoint + "products-on-shelves/?shelf=" + shelves.getInt(j) +"&product=" + json.getInt("id"));
                        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest
                                (Request.Method.GET, MainActivity.restEndpoint + "products-on-shelves/?shelf=" + shelves.getInt(j) +"&product=" + json.getInt("id") , null, new Response.Listener<JSONArray>() {
                                    @Override
                                    public void onResponse(JSONArray response) {
                                        try {
                                        Log.println(Log.INFO, "Request", "---------------------------- SUCCESS 2 ------------------------------");
                                        Log.println(Log.INFO, "Request", "Response: " + response.toString());
                                        if (response.length() > 0) {
                                            Log.println(Log.DEBUG, "value", "value before add: " + amounts.toString());
                                            //TODO delete after debugging
                                            amounts.add(Integer.parseInt(response.getJSONObject(0).getString("amount")));
                                            Log.println(Log.DEBUG, "value", "value after add: " + amounts.toString());
                                            Iterator<ProductData> iterator = list.iterator();
                                            while (iterator.hasNext()) {
                                                ProductData product = iterator.next();
                                                if (product.getId() == response.getJSONObject(0).getInt("id")) {
                                                    Log.println(Log.DEBUG, "product before: ", "product: id=" + product.getId() +" amount="+ product.getAmount());
                                                    product.setAmount(product.getAmount() + response.getJSONObject(0).getInt("amount"));
                                                    Log.println(Log.DEBUG, "product after", "product: id=" + product.getId() +" amount="+ product.getAmount());
                                                    break;
                                                }
                                            }
                                        } } catch (Exception e) {Log.println(Log.ERROR, "JSON", e.getMessage()); e.printStackTrace();}
                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Log.println(Log.ERROR, "JSON error", error.getMessage());
                                        error.printStackTrace();
                                    }
                                });
                        MainActivity.queue.add(jsonObjectRequest);
                    }
                }

               Log.println(Log.DEBUG, "value", "value of amounts: " + amounts.toString());
                list.add(new ProductData(json.getInt("id"), json.getString("product_name"),
                        json.getString("manufacturer"),
                        i * 12, shelvesList));
            }
        } catch(Exception e) {
            Log.println(Log.ERROR, "JSON", "Error: " + e.getMessage());
            e.printStackTrace();
        }
        this.list = list;
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_search_result, container, false);
        ProductsListAdapter adapter = new ProductsListAdapter(list);
        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.resultsRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(adapter);

        return v;
    }

}
