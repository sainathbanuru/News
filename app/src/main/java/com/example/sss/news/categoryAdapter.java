package com.example.sss.news;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by sss on 18/10/17.
 */

public class categoryAdapter extends RecyclerView.Adapter<categoryAdapter.viewHolder> {

    private Context context;
    private ArrayList<categoryItems> categoryItemsArrayList;

    public categoryAdapter(Context context, ArrayList<categoryItems> categoryItemsArrayList) {
        this.context = context;
        this.categoryItemsArrayList = categoryItemsArrayList;
    }

    @Override
    public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_each, parent, false);
        return new categoryAdapter.viewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final viewHolder holder, int position) {
        holder.title.setText(categoryItemsArrayList.get(position).getTitle());
        Glide.with(context).load(categoryItemsArrayList.get(position).getImageId()).into(holder.imageView);
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.chose_visible){
                    holder.tickImage.setVisibility(View.INVISIBLE);
                    holder.chose_visible = false;
                }
                else {
                    holder.tickImage.setVisibility(View.VISIBLE);
                    holder.chose_visible = true;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryItemsArrayList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{
        private TextView title;
        private ImageView imageView, tickImage;
        private Boolean chose_visible;
        private View view;

        public viewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            imageView = itemView.findViewById(R.id.imageView);
            tickImage = itemView.findViewById(R.id.image);
            chose_visible = true;
            view = itemView;
        }
    }

}
