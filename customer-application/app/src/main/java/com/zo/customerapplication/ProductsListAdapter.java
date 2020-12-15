package com.zo.customerapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
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
        viewHolder.amountTV.setText(Integer.toString(myProductData.getId()));
//        viewHolder.shelfTV.setText(myProductData.getShelves().toString());
        if (myProductData.getShelves().size() > 1 ) {
            viewHolder.shelfTV.setText(myProductData.getShelves().get(0).toString() + "+");
        } else {
            viewHolder.shelfTV.setText(myProductData.getShelves().get(0).toString());
        }
        //TODO after clicking on item go to the map
//        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                int pointsToSubtract = myListData.getPoints();
//                List<Points> points = pointsDao.queryBuilder()
//                        .where(PointsDao.Properties.Id.eq("main_points"))
//                        .list();
//                Points currentPoints = points.get(0);
//
//                if(currentPoints.getPoints()-pointsToSubtract<0){
//                    Toast.makeText(view.getContext(),"Sorry you don't have enough points",Toast.LENGTH_LONG).show();
//                }else{
//                    currentPoints.setPoints(currentPoints.getPoints()-pointsToSubtract);
//                    pointsDao.update(currentPoints);
//                    Toast.makeText(view.getContext(),"You chose: "+myListData.getDescription(),Toast.LENGTH_LONG).show();
//                    if(!myListData.getIsPersistent()){
//                        prizeDao.delete(myListData);
//                        listdata.remove(myListData);
//
//                        PrizeListAdapter.this.notifyDataSetChanged();
//                    }
//
//                }
//            }
//        });
    }

    @Override
    public int getItemCount() { return listData.size(); }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView productNameTV;
        public TextView manufacturerTV;
        public TextView amountTV;
        public TextView shelfTV;
        public RelativeLayout relativeLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            this.productNameTV = (TextView) itemView.findViewById(R.id.productNameTextView);
            this.manufacturerTV = (TextView) itemView.findViewById(R.id.manufacturerTextView);
            this.amountTV = (TextView) itemView.findViewById(R.id.amountTextView);
            this.shelfTV = (TextView) itemView.findViewById(R.id.shelfTextView);
        }

    }
}
