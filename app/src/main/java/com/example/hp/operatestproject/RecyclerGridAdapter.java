package com.example.hp.operatestproject;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Denis Pavlovsky on 26.07.15.
 */
public class RecyclerGridAdapter extends RecyclerView.Adapter<RecyclerGridAdapter.GridViewHolder> {

    private ArrayList<Product> products = new ArrayList<>();

    public RecyclerGridAdapter(ArrayList<Product> list, Context context) {
        products = list;
    }

    @Override
    public GridViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_grid_item, parent, false);
        return new GridViewHolder(v);
    }

    @Override
    public void onBindViewHolder(GridViewHolder holder, int position) {
        holder.update(position);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class GridViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;


        public GridViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.grid_image_view);
        }

        public void update(final int position) {
            Picasso.with(imageView.getContext())
                    .load(products.get(position).getIcon())
                    .into(imageView);


        }
    }
}
