package com.sahilssoft.p1_sportsocial.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sahilssoft.p1_sportsocial.R;
import com.sahilssoft.p1_sportsocial.interfaces.OnCheckUnCheckBoxListener;
import com.sahilssoft.p1_sportsocial.models.CategoryModel;

import java.util.List;

public class SportsAdapter extends RecyclerView.Adapter<SportsAdapter.ViewHolder> {
    Context context;
    List<CategoryModel> list;
    OnCheckUnCheckBoxListener listener;

    int count = 0;

    public SportsAdapter(Context context, List<CategoryModel> list, OnCheckUnCheckBoxListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public SportsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_category, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SportsAdapter.ViewHolder holder, int position) {
        CategoryModel model = list.get(position);
        holder.cbCategory.setText(model.name);


        holder.cbCategory.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (count < 3) {
                    holder.cbCategory.setChecked(true);
                    count++;
//                    Toast.makeText(context, model.name+" is checked", Toast.LENGTH_SHORT).show();
//                    model.isSelected = true;
//                    Toast.makeText(context, ""+model.id, Toast.LENGTH_SHORT).show();
                    listener.onItemClick(model.id,true);
                } else {
                    holder.cbCategory.setChecked(false);
                    if (!isChecked){
                        count--;
//                        Toast.makeText(context, model.name+ " is unChecked", Toast.LENGTH_SHORT).show();
//                        model.isSelected = false;
                        listener.onItemClick(model.id,false);
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox cbCategory;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cbCategory = itemView.findViewById(R.id.cbCategory);
        }
    }
}

