package com.sahilssoft.p1_sportsocial.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sahilssoft.p1_sportsocial.R;
import com.sahilssoft.p1_sportsocial.interfaces.OnCategoryItemClickListener;
import com.sahilssoft.p1_sportsocial.interfaces.OnSearchCategoryClickListener;
import com.sahilssoft.p1_sportsocial.models.CategoryModel;

import java.util.ArrayList;
import java.util.List;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.ViewHolder> {
    Context context;
    List<CategoryModel> list;
    OnCategoryItemClickListener listener;
    int selectedItem;
    boolean check = true;
    public CategoriesAdapter(Context context, List<CategoryModel> list, OnCategoryItemClickListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
        selectedItem = 0;
    }

    @NonNull
    @Override
    public CategoriesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_team_cat,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriesAdapter.ViewHolder holder, int position) {
        CategoryModel model = list.get(position);
        holder.tvFavTeamCat.setText(model.name);

        //make default selected load first position data
        if (check){
            listener.onItemClick(model);
            check = false;
        }

        holder.itemView.setOnClickListener(view -> {
            listener.onItemClick(model);

            int previousItem = selectedItem;
            selectedItem = position;
            notifyItemChanged(previousItem);
            notifyItemChanged(position);
        });

        if (selectedItem == position){
//            holder.llFavTeamCat.setBackgroundColor(Color.parseColor("#343D80"));
            holder.tvFavTeamCat.setTextColor(Color.parseColor("#FF03DAC5"));
        }else{
//            holder.llFavTeamCat.setBackgroundColor(Color.parseColor("#ffffff"));
            holder.tvFavTeamCat.setTextColor(Color.parseColor("#000000"));
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvFavTeamCat;
//        LinearLayout llFavTeamCat;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvFavTeamCat = itemView.findViewById(R.id.tvFavTeamCat);
//            llFavTeamCat = itemView.findViewById(R.id.llFavTeamCat);
        }
    }
}
