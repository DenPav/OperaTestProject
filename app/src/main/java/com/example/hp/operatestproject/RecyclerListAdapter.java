package com.example.hp.operatestproject;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Denis Pavlovsky on 25.07.15.
 */
public class RecyclerListAdapter extends RecyclerView.Adapter<RecyclerListAdapter.ListViewHolder> {


    private ArrayList<Product> products;

    public RecyclerListAdapter(ArrayList<Product> list) {
        products = list;

    }

    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_listview_item, parent, false);
        return new ListViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerListAdapter.ListViewHolder listViewHolder, int position) {
        listViewHolder.update(position);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }



    public class ListViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imageView;
        private final TextView titleText;
        private final TextView shortDescrText;


        public ListViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            titleText = (TextView) itemView.findViewById(R.id.titleText);
            shortDescrText = (TextView) itemView.findViewById(R.id.shortDescrText);
        }

        public void update(final int position) {
            titleText.setText(products.get(position).getTitle());
            shortDescrText.setText(products.get(position).getShort_descr());
            Picasso.with(imageView.getContext())
                    .load(products.get(position).getIcon())
                    .into(imageView);

        }
    }

}
