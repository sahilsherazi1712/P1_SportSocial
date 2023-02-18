package com.sahilssoft.p1_sportsocial.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sahilssoft.p1_sportsocial.R;
import com.sahilssoft.p1_sportsocial.interfaces.OnPodItemClickListener;
import com.sahilssoft.p1_sportsocial.models.PodcastData;

import java.util.List;

public class PodcastAdapter extends RecyclerView.Adapter<PodcastAdapter.VH> {
    Context context;
    List<PodcastData> list;
    OnPodItemClickListener listener;

    public PodcastAdapter(Context context, List<PodcastData> list, OnPodItemClickListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public PodcastAdapter.VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VH(LayoutInflater.from(context).inflate(R.layout.item_home_rv,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull PodcastAdapter.VH holder, int position) {
        PodcastData model = list.get(position);
        Glide.with(context)
                .load(model.image)
                .placeholder(R.drawable.placeholder)
                .into(holder.img_home);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                listener.onItemClick(model);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class VH extends RecyclerView.ViewHolder {
        ImageView img_home;
        public VH(@NonNull View itemView) {
            super(itemView);
            img_home = itemView.findViewById(R.id.img_home);
        }
    }
}
