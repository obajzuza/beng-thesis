package com.zo.warehouseapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;

// graphics
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
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

public class ScanProduct extends AppCompatActivity {
    public static RequestQueue queue;
    public static URL restEndpoint;
    private Cache cache;
    private Network network;

    protected ScanProduct scanProduct;
    private int CAMERA_REQUEST_CODE = 101;
    private CodeScanner codeScanner;
    private Button addBtn;
    private Button cancelBtn;
    private Button acceptBtn;
    private EditText amountET;
    private TextView productCodeTV;
    private ImageView deleteIV;

    private String productCode;
//    TODO create a ProductData class containing name, manufacturer, id, amount, shelves OR boolean flag if getExtra.shelf exists in shelves
    private ArrayList<ProductData> productDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_product);
        productDataList = new ArrayList<ProductData>();

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
        scanProduct = ScanProduct.this;
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
        deleteIV = (ImageView) findViewById(R.id.deleteIV);

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
               Toast.makeText(ScanProduct.this, amountET.getText(), Toast.LENGTH_LONG).show();
               //perform GET request on item with code productCode
                queue.start();
                JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, restEndpoint.toString() + "product/?code=" + productCode, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.println(Log.INFO, "Request", "---------------------------- SUCCESS ------------------------------");
                        Log.println(Log.INFO, "Request", "Response: " + response.toString());
                        if (response.length() > 0) {
                            // TODO
                            //  - retrieve: id, name, manufacturer
                            try {
                            JSONObject json = response.getJSONObject(0);
                            ProductData data = new ProductData();
                            data.setId(json.getInt("id"));
                            data.setName(json.getString("name"));
                            data.setManufacturer(json.getString("manufacturer"));
                            JSONArray shelves = json.getJSONArray("shelves");
                            for (int i = 0; i < shelves.length(); i++) {
                                //TODO
//                                if (shelves.getInt(i) == shelfIDfromGetExtra) {
//                                    data.setExistsInShelf(true);
//                                    break;
//                                }
                            }
                            productDataList.add(data);
                            }
                            catch (Exception e) {
                                Log.println(Log.ERROR, "JSON", "Error: " + e.getMessage());
                                e.printStackTrace();
                            }
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.println(Log.ERROR, "Request Error", error.getMessage());
                        error.printStackTrace();
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
                //TODO setOnClickListener for acceptBtn
                for (ProductData data :productDataList) {
                    if(data.isExistsInShelf()) {
                        // PATCH
                    } else {
                        // PUT
                    }
                }

                // TODO
                // on no errors -> SuccessActivity
            }
        });

        deleteIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                deleteIV.getParent()
                // TODO
                // delte item from productDataList
            }
        });
    }

    protected void setProductCodeTV(String code) {
        Toast.makeText(ScanProduct.this, code, Toast.LENGTH_LONG).show();
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
