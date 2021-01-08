package com.zo.warehouseapplication;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ProductsListAdapter extends RecyclerView.Adapter<ProductsListAdapter.ViewHolder> {
    private ArrayList<ProductData> listData;

    public ProductsListAdapter(ArrayList<ProductData> data) {
        this.listData = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.list_item, parent, false);
        return new ViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int position) {
        final ProductData myProductData = listData.get(position);
        viewHolder.productNameTV.setText(myProductData.getName());
        viewHolder.manufacturerTV.setText(myProductData.getManufacturer());
        viewHolder.amountTV.setText(Integer.toString(myProductData.getAmount()));

        viewHolder.deleteIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (ProductData data: listData) {
                    if(data.getName() == viewHolder.productNameTV.getText() &&
                        data.getManufacturer() == viewHolder.manufacturerTV.getText() &&
                        data.getAmount() == Integer.parseInt(viewHolder.amountTV.getText().toString())) {
                        listData.remove(data);
                        notifyDataSetChanged();
                        break;
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() { return listData.size(); }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView productNameTV;
        public TextView manufacturerTV;
        public TextView amountTV;
        public ImageView deleteIV;

        public ViewHolder(View itemView) {
            super(itemView);
            this.productNameTV = (TextView) itemView.findViewById(R.id.productNameTextView);
            this.manufacturerTV = (TextView) itemView.findViewById(R.id.manufacturerTextView);
            this.amountTV = (TextView) itemView.findViewById(R.id.amountTextView);
            this.deleteIV = (ImageView) itemView.findViewById(R.id.deleteIV);
        }

    }
}
