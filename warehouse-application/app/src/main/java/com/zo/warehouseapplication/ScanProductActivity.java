package com.zo.warehouseapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

// graphics
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

// networking
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
import java.net.URL;

// code scanner
import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.zxing.Result;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ScanProductActivity extends AppCompatActivity {
    public static RequestQueue queue;
    public static URL restEndpoint;
    private Cache cache;
    private Network network;

    protected ScanProductActivity scanProduct;
    private int CAMERA_REQUEST_CODE = 101;
    private CodeScanner codeScanner;
    private Button addBtn;
    private Button cancelBtn;
    private Button acceptBtn;
    private EditText amountET;
    private TextView productCodeTV;
    private ImageView deleteIV;

    private String productCode;
    private int shelfCode;
    private ArrayList<ProductData> productDataList;
    private ProductsListAdapter adapter;

    public int okResponsesCounter;
    public int errorResponsesCounter;
    public int elementsNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_product);
        productDataList = new ArrayList<ProductData>();
        shelfCode = Integer.valueOf(getIntent().getExtras().getString("shelf"));

        // network initialization
        cache = new DiskBasedCache(getCacheDir(), 1024);
        network = new BasicNetwork(new HurlStack());
        queue = Volley.newRequestQueue(this);
        try {
            restEndpoint = new URL("http://192.168.0.115:8000/api/"); }
        catch (Exception e) {
            Log.println(Log.ERROR, "exception", "Exception while conneting to restEndpoint");
            e.printStackTrace();
        }

        // scanner initialization
        productCodeTV = (TextView) findViewById(R.id.productCodeTV);
        scanProduct = ScanProductActivity.this;
        Log.println(Log.DEBUG, "activities", "Scan product activity started");
        Log.println(Log.DEBUG, "activities", "gotten these values passed: ");

        if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CAMERA}, 50);

        // UI initialization
        CodeScannerView scannerView = findViewById(R.id.scanner2_csv);
        addBtn = (Button) findViewById(R.id.addBtn);
        amountET = (EditText) findViewById(R.id.amountET);
        cancelBtn = (Button) findViewById(R.id.cancelBtn);
        acceptBtn = (Button) findViewById(R.id.acceptBtn);
//        deleteIV = (ImageView) findViewById(R.id.deleteIV);

        adapter = new ProductsListAdapter(productDataList);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.listToAddRV);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        // setting up scanner
        codeScanner = new CodeScanner(this, scannerView);
        codeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {
                scanProduct.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.println(Log.DEBUG, "SCAN", "decoded value: " + result.getText() + "\nof class: "+ result.getText().getClass());
                        productCode = result.getText();
                        setProductCodeTV(productCode);
                    }
                });
            }
        });
        scannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                codeScanner.startPreview();
            }
        });

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
           public void onClick(View view) {
               //perform GET request on item with code productCode
                queue.start();
                JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, restEndpoint.toString() + "product/?code=" + productCode, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.println(Log.INFO, "Request", "---------------------------- SUCCESS ------------------------------");
                        Log.println(Log.INFO, "Request", "Response: " + response.toString());
                        if (response.length() > 0) {
                            try {
                            JSONObject json = response.getJSONObject(0);
                            ProductData data = new ProductData();
                            data.setId(json.getInt("id"));
                            data.setName(json.getString("product_name"));
                            data.setManufacturer(json.getString("manufacturer"));
                            data.setAmount(Integer.parseInt(amountET.getText().toString()));
                            JSONArray shelves = json.getJSONArray("shelves");
                            for (int i = 0; i < shelves.length(); i++) {
                                if (shelves.getInt(i) == shelfCode) {
                                    data.setExistsInShelf(true);
                                    break;
                                }
                            }
                            Log.println(Log.DEBUG, "product","adding product");
                            productDataList.add(data);
                            adapter.notifyDataSetChanged();
                            Log.println(Log.DEBUG, "product","product added");
                            }
                            catch (Exception e) {
                                Log.println(Log.ERROR, "JSON", "Error: " + e.getMessage());
                                e.printStackTrace();
                            }
                        } else { Toast.makeText(ScanProductActivity.this, R.string.no_such_product, Toast.LENGTH_LONG).show(); }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.println(Log.ERROR, "Request Error", error.getMessage());
                        error.printStackTrace();
                        Toast.makeText(ScanProductActivity.this, R.string.no_such_product, Toast.LENGTH_LONG).show();
                    }
                });
                queue.add(request);
           }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scanProduct.finish();
            }
        });

        acceptBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                queue.start();
                try {
                    for (ProductData data : productDataList) {
                        elementsNumber++;
                        JSONObject json = new JSONObject();
                        json.put("product", data.getId());
                        json.put("shelf", shelfCode);
                        json.put("amount", data.getAmount());
                        Log.println(Log.DEBUG, "json", "elements number " +  elementsNumber + " ok number " + okResponsesCounter + " error number " + errorResponsesCounter);
                        if (data.isExistsInShelf()) {
                            // PATCH
                            Log.println(Log.DEBUG, "json", "PATCH connecting with " + restEndpoint.toString() + "products/" + data.getId() + "/" + shelfCode + "/");
                            JsonObjectRequest request = new JsonObjectRequest(Request.Method.PATCH, restEndpoint.toString() + "products/" + data.getId() + "/" + shelfCode + "/", json, new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    Log.println(Log.DEBUG, "json", "1");
                                    okResponsesCounter++;
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Intent failureIntent = new Intent(ScanProductActivity.this, FailActivity.class);
                                    startActivity(failureIntent);
                                }
                            });
                            queue.add(request);
                        } else {
                            // PUT
                            Log.println(Log.DEBUG, "json", "PUT connecting with " + restEndpoint.toString() + "products/" + data.getId() + "/" + shelfCode + "/");
                            JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, restEndpoint.toString() + "products/" + data.getId() + "/" + shelfCode + "/", json, new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    Log.println(Log.DEBUG, "json", "3");
                                    okResponsesCounter++;
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Intent failureIntent = new Intent(ScanProductActivity.this, FailActivity.class);
                                    startActivity(failureIntent);
                                }
                            });
                            queue.add(request);
                        }
                    }
                } catch (Exception e) {
                    Intent failureIntent = new Intent();
                    startActivity(failureIntent);
                    e.printStackTrace();
                }

                Log.println(Log.DEBUG, "json", "elements number " +  elementsNumber + " ok number " + okResponsesCounter + " error number " + errorResponsesCounter);

                finish();
            }
        });
    }

    protected void setProductCodeTV(String code) {
        Toast.makeText(ScanProductActivity.this, code, Toast.LENGTH_LONG).show();
        productCodeTV.setText(code);
    }

    @Override
    protected void onResume() {
        super.onResume();
        codeScanner.startPreview();
    }

    @Override
    protected void onPause() {
        codeScanner.releaseResources();
        super.onPause();
    }
}
