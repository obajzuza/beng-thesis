package com.zo.customerapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

public class MapActivity extends AppCompatActivity {
    /* Receives strings in getExtra
                    intent.putExtra("shelf", viewHolder.shelfTV.getText());
                intent.putExtra("product", viewHolder.productNameTV.getText());
                intent.putExtra("manufacturer", viewHolder.manufacturerTV.getText()); */
    private ImageView mapIV;
    private TextView productNameTV;
    private TextView shelfTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        //TODO
        productNameTV = findViewById(R.id.productNameTV);
        shelfTV = findViewById(R.id.shelfTV);
        mapIV = findViewById(R.id.mapIV);
        productNameTV.setText(getIntent().getStringExtra("product"));
        String shelf = getIntent().getStringExtra("shelf");
        Log.println(Log.DEBUG, "extras", "shelf extra: " + shelf);
        shelfTV.setText(shelf);
        switch (shelf) {
            case "1" :
                Log.println(Log.DEBUG, "switch", "case: " + shelf);
                mapIV.setImageResource(R.drawable.s1);
                break;
            case "2" :
                Log.println(Log.DEBUG, "switch", "case: " + shelf);
                mapIV.setImageResource(R.drawable.s2);
                break;
            case "3" :
                Log.println(Log.DEBUG, "switch", "case: " + shelf);
                mapIV.setImageResource(R.drawable.s3);

                break;
            case "4" :
                Log.println(Log.DEBUG, "switch", "case: " + shelf);
                mapIV.setImageResource(R.drawable.s4);
                break;
        }
    }
}
