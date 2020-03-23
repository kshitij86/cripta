package com.example.cryptomarker;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder> {
    private String[] pri, img, sym;
    private boolean[] cha;

    RecyclerAdapter(String[] pri, String[] img, String[] sym, boolean[] cha){
        this.pri = pri; this.img = img; this.sym = sym; this.cha = cha;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Creates views and place in holder.
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        // Make a view from the custom layout.
        View view = inflater.inflate(R.layout.list_item_layout, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        // Connect the data resource and the view holder.
        holder.textView.setText(pri[position]);
        holder.symbol.setText(sym[position]);
        Picasso.get()
                .load(Uri.parse(img[position])).resize(200,200)
                .into(holder.imageView);
        if(!cha[position])
           holder.change.setBackgroundResource(R.drawable.re);
        else
            holder.change.setBackgroundResource(R.drawable.gr);
        // Picasso does not work for svg images. So not loading svg anymore.
        // Picasso.get().load(img[position]).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        // Get the item count.
        return img.length;
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView symbol;
        TextView textView;
        ImageView change;
        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.list_img_view);
            textView = itemView.findViewById(R.id.list_text_view);
            symbol = itemView.findViewById(R.id.symbol);
            change = itemView.findViewById(R.id.changeIV);
        }
    }
}

