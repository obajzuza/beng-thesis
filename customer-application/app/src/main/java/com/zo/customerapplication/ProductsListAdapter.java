package com.zo.customerapplication;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ProductsListAdapter extends RecyclerView.Adapter<ProductsListAdapter.ViewHolder> {
    private ArrayList<ProductData> listData;

    public ProductsListAdapter(List<ProductData> data) {
        this.listData = new ArrayList<>(data);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.list_item_product, parent, false);
        return new ViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int position) {
        final ProductData myProductData = listData.get(position);
        viewHolder.productNameTV.setText(myProductData.getName());
        viewHolder.manufacturerTV.setText(myProductData.getManufacturer());
        viewHolder.amountTV.setText(Integer.toString(myProductData.getAmount()));
        viewHolder.shelfTV.setText(Integer.toString(myProductData.getShelf()));

        viewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), MapActivity.class);
                intent.putExtra("shelf", viewHolder.shelfTV.getText());
                intent.putExtra("product", viewHolder.productNameTV.getText());
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() { return listData.size(); }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView productNameTV;
        public TextView manufacturerTV;
        public TextView amountTV;
        public TextView shelfTV;
        public LinearLayout linearLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            this.linearLayout = (LinearLayout) itemView.findViewById(R.id.linearLayout);
            this.productNameTV = (TextView) itemView.findViewById(R.id.productNameTextView);
            this.manufacturerTV = (TextView) itemView.findViewById(R.id.manufacturerTextView);
            this.amountTV = (TextView) itemView.findViewById(R.id.amountTextView);
            this.shelfTV = (TextView) itemView.findViewById(R.id.shelfTextView);
        }

    }
}
