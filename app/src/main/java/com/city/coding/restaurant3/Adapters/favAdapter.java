package com.city.coding.restaurant3.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.city.coding.restaurant3.Activites.voucher_detail;
import com.city.coding.restaurant3.Model.voucherData;
import com.city.coding.restaurant3.R;
import com.city.coding.restaurant3.request;
import com.willy.ratingbar.ScaleRatingBar;

import java.util.ArrayList;

public class favAdapter extends RecyclerView.Adapter<favAdapter.viewHolder> {
    //Application context reference
    private Context mContext;
    private ArrayList<voucherData> voucherData;

    //user id
    private String userId ;
    //constructor


    public favAdapter(Context context ,ArrayList<voucherData> VoucherData,String userId){
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
                .inflate(R.layout.fav_voucher_item, viewGroup, false);
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
        final voucherData voucher = voucherData.get(position);
        Log.e("onBind", "onBindViewHolder: ");
        Glide.with(mContext)
                .load(voucher.getVoucherImage())
                .into(v.voucherImage);
        v.title.setText(voucher.getTitle());
        v.desc.setText("a morning breakFast spicially for you ...");
        v.newPrice.setText(voucher.getValue()+"");
        v.quantity.setText(voucher.getQty() + " Left");



    //set frame clickListener to listen for any click any where on fav item
        v.frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // pairs = new Pair[9] ;
                Intent intent = new Intent(mContext, voucher_detail.class);
                intent.putExtra("voucher_image", voucher.getVoucherImage());
                intent.putExtra("voucher_title",voucher.getTitle());
                intent.putExtra("voucher_qty",voucher.getQty());
                intent.putExtra("voucher_value",voucher.getValue());
                //ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(activity ,pairs);
                mContext.startActivity(intent);
            }
        });


    }//end binding method

    @Override
    public int getItemCount() {
        if(voucherData ==null)
            return 0;
        return voucherData.size();
    }

    public void  setVoucherData (ArrayList<voucherData> vouchers){
        this.voucherData = vouchers;
        Log.e("setVoucherData", "setVoucherData: "+this.voucherData.size());
        notifyDataSetChanged();
    }

    //ViewHolder class to hold voucher item layout
    //to render it on rcycle view
    public class viewHolder extends RecyclerView.ViewHolder {

        //set id's for voucher item fields
        ImageView voucherImage;
        TextView title , desc , newPrice ,quantity;
        TextView branch ,discount;
        ScaleRatingBar rate ;
        FrameLayout frameLayout ;


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
            frameLayout = itemView.findViewById(R.id.fav_item_frame_id);

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
    private void addToFavList(final int position){
        request.addToFavList(mContext,userId ,voucherData.get(position).getVoucherId());
    }
}
