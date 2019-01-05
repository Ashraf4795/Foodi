package com.city.coding.restaurant3.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.city.coding.restaurant3.Activites.MapsActivity;
import com.city.coding.restaurant3.Model.store;
import com.city.coding.restaurant3.R;

import java.util.ArrayList;
import java.util.List;

public class storeAdapter extends RecyclerView.Adapter<storeAdapter.sViewHolder> {
    Context mContext;
    List<store> storeList;

    public storeAdapter(Context context, List<store> data) {
        mContext = context;
        storeList = data;
    }

    @NonNull
    @Override
    public sViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.store_item, parent, false);
        sViewHolder viewHolder = new sViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull sViewHolder holder, int position) {
        final store s = storeList.get(position);
        holder.storeName.setText(s.getStore_name());
        holder.storeAddress.setText(s.getStore_address());
        holder.location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext, MapsActivity.class);
                intent.putExtra("lati", s.getLati());
                intent.putExtra("longi", s.getLongi());
                intent.putExtra("storeName", s.getStore_name());
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        if (storeList != null && storeList.size() > 0) {
            return storeList.size();
        }
        return 0;
    }

    //viewHolder
    public class sViewHolder extends RecyclerView.ViewHolder {
        TextView storeName, storeAddress;
        ImageView location;

        public sViewHolder(View itemView) {
            super(itemView);
            storeName = itemView.findViewById(R.id.store_name_id);
            storeAddress = itemView.findViewById(R.id.store_add_id);
            location = itemView.findViewById(R.id.location_id);

        }
    }


    // set filtered list and notifiy for data changes
    public void filteredList(ArrayList<store> filteredList) {
        storeList = filteredList;
        notifyDataSetChanged();
    }
}
