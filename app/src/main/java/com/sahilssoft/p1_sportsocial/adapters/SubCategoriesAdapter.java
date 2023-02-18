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
import com.sahilssoft.p1_sportsocial.models.CategoryData;

import java.util.ArrayList;
import java.util.List;

public class SubCategoriesAdapter extends RecyclerView.Adapter<SubCategoriesAdapter.ViewHolder> {
    Context context;
    List<CategoryData> list;
    OnCheckUnCheckBoxListener listener;

    public SubCategoriesAdapter(Context context, List<CategoryData> list, OnCheckUnCheckBoxListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public SubCategoriesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_category,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull SubCategoriesAdapter.ViewHolder holder, int position) {
        CategoryData model = list.get(position);
        holder.cbCategory.setText(model.name);

        //if I check one checkbox, some other are automatically checked. To control this, I make a boolean variable in CategoryData
        //in some cases, it will prevent unwanted conditions
        holder.cbCategory.setOnCheckedChangeListener(null);
        //if true your checkbox will be selected, else unselected
        holder.cbCategory.setChecked(model.isSelected);
        holder.cbCategory.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked){
//                    Toast.makeText(context, model.name+" is checked", Toast.LENGTH_SHORT).show();
//                    model.isSelected = true;
                    Toast.makeText(context, ""+model.id, Toast.LENGTH_SHORT).show();
                    listener.onItemClick(model.id,true);
                    model.isSelected = true;
                }else{
//                        Toast.makeText(context, model.name+ " is unChecked", Toast.LENGTH_SHORT).show();
//                        model.isSelected = false;
                    listener.onItemClick(model.id,false);
                    model.isSelected = false;
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
