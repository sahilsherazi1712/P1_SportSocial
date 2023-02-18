package com.sahilssoft.p1_sportsocial.activities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sahilssoft.p1_sportsocial.R;
import com.sahilssoft.p1_sportsocial.interfaces.OnPodItemClickListener;
import com.sahilssoft.p1_sportsocial.models.PodcastData;

import java.util.List;

public class SearchCatClickAdapter extends RecyclerView.Adapter<SearchCatClickAdapter.ViewHolder> {
    Context context;
    List<PodcastData> list;
    OnPodItemClickListener listener;

    public SearchCatClickAdapter(Context context, List<PodcastData> list, OnPodItemClickListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public SearchCatClickAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_view,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull SearchCatClickAdapter.ViewHolder holder, int position) {
        PodcastData model = list.get(position);
        holder.tvCatSearch.setText(model.title);
        Glide.with(context)
                .load(model.image)
                .placeholder(R.drawable.placeholder)
                .into(holder.imgCatSearch);
        holder.itemView.setOnClickListener(view -> {
            listener.onItemClick(model);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgCatSearch;
        TextView tvCatSearch;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgCatSearch =itemView.findViewById(R.id.img);
            tvCatSearch =itemView.findViewById(R.id.tv);
        }
    }
}
