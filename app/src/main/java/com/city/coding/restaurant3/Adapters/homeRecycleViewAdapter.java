package com.city.coding.restaurant3.Adapters;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.Application;
import android.content.Context;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.city.coding.restaurant3.Activites.homeActivity;
import com.city.coding.restaurant3.Activites.voucher_detail;
import com.city.coding.restaurant3.Helper.Helper;
import com.city.coding.restaurant3.Model.voucherData;
import com.city.coding.restaurant3.R;
import com.city.coding.restaurant3.request;
import com.like.LikeButton;
import com.like.OnLikeListener;
import com.willy.ratingbar.ScaleRatingBar;

import java.util.ArrayList;

public class homeRecycleViewAdapter extends RecyclerView.Adapter<homeRecycleViewAdapter.viewHolder> {
    private String TAG = "adapter";
    //Application context reference
    private Context mContext;
    private ArrayList<voucherData> voucherData;

    //user id
    private String userId;
    //constructor

    public homeRecycleViewAdapter(Context context, ArrayList<voucherData> VoucherData, String userId) {
        mContext = context;
        voucherData = new ArrayList<voucherData>();
        voucherData = VoucherData;
        this.userId = userId;

    }


    /*public homeRecycleViewAdapter(Context context){
        Log.e("adapter constructor", "adapter: ");

        this.mContext = context;
    }*/
    //viewHolder onCreateViewHolder method
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        //inflate a view to send it to view Holder constructor
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.voucher_item, viewGroup, false);
        //make a new object from view holder and pass to it a view to hold it
        viewHolder viewHolder = new viewHolder(view);
        //return viewHolder object
        return viewHolder;
    }//end viewHolder OnCreateViewHolder method


    /*when binding data into the view this method invoked
     * first get  a voucher object according to position of the viewHolder
     * second set a data to this viewHolder
     * set a click listener to each viewHolder
     * send viewHolder data if it get clicked to detail's activity Using intent class*/
    @Override
    public void onBindViewHolder(@NonNull final viewHolder v, final int position) {
        v.progressBar.setVisibility(View.VISIBLE);
        final voucherData voucher = voucherData.get(position);
        Log.e("onBind", "onBindViewHolder: ");
        Glide.with(mContext)
                .load(voucher.getVoucherImage())
                .into(v.voucherImage);
        v.progressBar.setVisibility(View.INVISIBLE);
        v.title.setText(voucher.getTitle());
        v.desc.setText("a morning breakFast spicially for you ...");
        v.newPrice.setText(voucher.getValue().toString());
        v.quantity.setText(voucher.getQty() + " Left");
        v.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // pairs = new Pair[9] ;
                Intent intent = new Intent(mContext, voucher_detail.class);
                intent.putExtra("voucher_image", voucher.getVoucherImage());
                intent.putExtra("voucher_title", voucher.getTitle());
                intent.putExtra("voucher_qty", voucher.getQty());
                intent.putExtra("voucher_value", voucher.getValue());
                intent.putExtra("voucher_id", voucher.getVoucherId());
                //ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(activity ,pairs);
                mContext.startActivity(intent);
            }
        });
        v.rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "rate bar " + v.getRootView().getId(), Toast.LENGTH_LONG).show();
            }
        });

        v.star.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                addToFavList(v.getAdapterPosition());
                Toast.makeText(mContext, voucherData.get(position).getTitle() + " add to Favorite", Toast.LENGTH_LONG).show();
            }

            @Override
            public void unLiked(LikeButton likeButton) {
                Toast.makeText(mContext, voucherData.get(position).getTitle() + " removed from Favorite", Toast.LENGTH_LONG).show();

            }
        });


    }//end binding method

    @Override
    public int getItemCount() {

        return voucherData.size();
    }

    public void setVoucherData(ArrayList<voucherData> vouchers) {
        this.voucherData = vouchers;
        Log.e("setVoucherData", "setVoucherData: " + this.voucherData.size());
        notifyDataSetChanged();
    }

    //ViewHolder class to hold voucher item layout
    //to render it on rcycle view
    public class viewHolder extends RecyclerView.ViewHolder {

        //set id's for voucher item fields
        ImageView voucherImage;
        TextView title, desc, newPrice, quantity;
        TextView view, branch, discount;
        ScaleRatingBar rate;
        LikeButton star;
        ProgressBar progressBar;

        // ConstraintLayout itemLayout;
        //end

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            //attach items by id's
            voucherImage = itemView.findViewById(R.id.voucher_image_id);
            discount = itemView.findViewById(R.id.voucher_discount_id);
            title = itemView.findViewById(R.id.voucher_title_id);
            desc = itemView.findViewById(R.id.voucher_desc_id);
            newPrice = itemView.findViewById(R.id.voucher_new_price_id);
            branch = itemView.findViewById(R.id.branch_id);
            quantity = itemView.findViewById(R.id.voucher_quantity_id);
            view = itemView.findViewById(R.id.voucher_view_id);
            rate = itemView.findViewById(R.id.simpleRatingBar_id);
            star = itemView.findViewById(R.id.star_button);
            progressBar = itemView.findViewById(R.id.item_progress_bar_id);

             /*
            pairs[0] = new Pair<View,String>(voucherImage,"vImage");
            pairs[1] = new Pair<View,String>(discount,"vDiscount");
            pairs[2] = new Pair<View,String>(title,"vTitle");
            pairs[3] = new Pair<View,String>(desc,"vDesc");
            pairs[4] = new Pair<View,String>(quantity,"vQuantity");
            pairs[5] = new Pair<View,String>(branch,"vBranch");
            pairs[6] = new Pair<View,String>(rate,"vRatingBar");
            pairs[7] = new Pair<View,String>(oldPrice,"vOldPrice");
            pairs[8] = new Pair<View,String>(newPrice,"vNewPrice"); */


            //itemLayout = itemView.findViewById(R.id.voucher_item_ID);
            //end attaching id's
        }//end constructor

    }//end ViewHolder Class

    //add to fav list method
    private void addToFavList(final int position) {
        request.addToFavList(mContext, userId, voucherData.get(position).getVoucherId());
    }

    //filtered method to notifiy adapter on DATA CHANGE
    public void filteredList(ArrayList<voucherData> filteredList) {
        voucherData = filteredList;
        notifyDataSetChanged();
    }

}//end RecyclerView parent