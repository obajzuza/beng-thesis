package com.zo.warehouseapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.zxing.Result;

import java.util.ArrayList;

public class ScanProduct extends AppCompatActivity {
    protected ScanProduct scanProduct;
    private int CAMERA_REQUEST_CODE = 101;
    private CodeScanner codeScanner;
    private Button addBtn;
    private Button cancelBtn;
    private Button acceptBtn;
    private EditText amountET;
    private TextView productCodeTV;

    private String productCode;
//    TODO create a ProductData class containing name, manufacturer, id, amount, shelves OR boolean flag if getExtra.shelf exists in shelves
//    private ArrayList<ProductData> productDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_product);
        productCodeTV = (TextView) findViewById(R.id.productCodeTV);
        scanProduct = ScanProduct.this;
        Log.println(Log.DEBUG, "activities", "Scan product activity started");
        Log.println(Log.DEBUG, "activities", "gotten these values passed: ");

        if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CAMERA}, 50);

        CodeScannerView scannerView = findViewById(R.id.scanner2_csv);
        addBtn = (Button) findViewById(R.id.addBtn);
        amountET = (EditText) findViewById(R.id.amountET);
        cancelBtn = (Button) findViewById(R.id.cancelBtn);
        acceptBtn = (Button) findViewById(R.id.acceptBtn);

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
                //  - retrieve: id, name, manufacturer
                //perform GET request on item with code productCode and shelfId from getExtra
                //  - set according boolean flag based on if (id, shelf) combination already exists in db
                //add an item to ListView and to productDataList
           }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scanProduct.finish();
                //if it doesn't work try onBackPressed()
            }
        });

        acceptBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO setOnClickListener for acceptBtn
                //for each product in productDataList:
                //  - (use 2nd approach) check if product on shelf already exists:
                //      - yes: POST to update ?? does it even work like that
                //      - no: PUT to add
                //OR check if getExtra.shelf exists in shelves
                //perform PUT request to ProductsOnShelves with data of each row from
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
