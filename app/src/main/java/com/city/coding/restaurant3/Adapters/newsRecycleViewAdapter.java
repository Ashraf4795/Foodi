package com.city.coding.restaurant3.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.city.coding.restaurant3.R;
import com.city.coding.restaurant3.Model.news;

import java.util.ArrayList;

public class newsRecycleViewAdapter extends RecyclerView.Adapter<newsRecycleViewAdapter.ViewHolder> {

    /*data source that from frament*/
    private ArrayList<news> nData;
    private ArrayList<Integer> nImage ;
    private Context nContext ;


    /*constructor to recive data source and Context*/
    public newsRecycleViewAdapter(Context context , ArrayList<news> data , ArrayList<Integer> Image){
        nContext = context;
        nData = data;
        nImage = Image;
    }
    //end


    /*inflating news layout item and send it to viewHolder constructor*/
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.news_item ,viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }
    //end

    /*binding viewHolder with news item view's and set data to theme*/
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.newsImage.setImageResource(nImage.get(position));
        viewHolder.newsTitle.setText(nData.get(position).getTitle());
        viewHolder.newsContent.setText(nData.get(position).getContent());
    }
    //end

    // return number of news object
    @Override
    public int getItemCount() {
        return nData.size();
    }
    //end


    //view holder class
    public class ViewHolder extends RecyclerView.ViewHolder{

        //news Item fields
        ImageView newsImage , likeIcon , shareIcon , markIcon;
        TextView newsTitle , newsContent;

        /*viewHolder constuctor to find view's ID's
         and send the itemView to RecyclerView parent*/
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            newsImage= itemView.findViewById(R.id.news_image_id);
            newsTitle = itemView.findViewById(R.id.news_title_id);
            newsContent =itemView.findViewById(R.id.news_content_id);
            likeIcon = itemView.findViewById(R.id.news_like_icon_id);
            shareIcon = itemView.findViewById(R.id.news_share_icon_id);
            markIcon = itemView.findViewById(R.id.news_mark_icon_id);
        }//end ViewHolder constructor

    }//end ViewHolder class

}//end newsRecycleViewAdapter class
