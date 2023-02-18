package com.sahilssoft.p1_sportsocial.adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sahilssoft.p1_sportsocial.R;
import com.sahilssoft.p1_sportsocial.activities.MainActivity;
import com.sahilssoft.p1_sportsocial.fragments.GetPodcastFragment;
import com.sahilssoft.p1_sportsocial.models.SearchCatModel;

import java.util.ArrayList;

public class ClickEpisodesAdapter extends RecyclerView.Adapter<ClickEpisodesAdapter.ViewHolder> {
    Context context;
    ArrayList<SearchCatModel> list;

    public ClickEpisodesAdapter(Context context, ArrayList<SearchCatModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ClickEpisodesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_view,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ClickEpisodesAdapter.ViewHolder holder, int position) {
        Glide.with(context)
                .load(list.get(position).getImage())
                .placeholder(R.drawable.placeholder)
                .into(holder.imgCatSearch);
        holder.tvCatSearch.setText(list.get(position).getCategory());
        holder.itemView.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putString("catTitle",list.get(position).getCategory());
            bundle.putInt("homeImg",list.get(position).getImage());
            GetPodcastFragment fragment = new GetPodcastFragment();
            fragment.setArguments(bundle);

            MainActivity activity = (MainActivity) context;
            activity.getSupportFragmentManager().beginTransaction().replace(R.id.fv_Container,fragment).addToBackStack(null).commit();
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
            imgCatSearch = itemView.findViewById(R.id.img);
            tvCatSearch = itemView.findViewById(R.id.tv);
        }
    }
}
