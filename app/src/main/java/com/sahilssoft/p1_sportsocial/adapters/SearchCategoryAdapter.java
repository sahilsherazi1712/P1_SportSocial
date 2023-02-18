package com.sahilssoft.p1_sportsocial.adapters;

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
import com.sahilssoft.p1_sportsocial.interfaces.OnSearchCategoryClickListener;
import com.sahilssoft.p1_sportsocial.models.CategoryModel;

import java.util.List;

public class SearchCategoryAdapter extends RecyclerView.Adapter<SearchCategoryAdapter.ViewHolder> {
    Context context;
    List<CategoryModel> list;
    OnSearchCategoryClickListener listener;

    public SearchCategoryAdapter(Context context, List<CategoryModel> list, OnSearchCategoryClickListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public SearchCategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_view,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull SearchCategoryAdapter.ViewHolder holder, int position) {
        CategoryModel model = list.get(position);
        Glide.with(context)
                .load(model.webImage)
                .placeholder(R.drawable.placeholder)
                .into(holder.imgCatSearch);
        holder.tvCatSearch.setText(model.name);

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
            imgCatSearch = itemView.findViewById(R.id.img);
            tvCatSearch = itemView.findViewById(R.id.tv);
        }
    }
}
