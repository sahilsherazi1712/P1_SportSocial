package com.sahilssoft.p1_sportsocial.adapters;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sahilssoft.p1_sportsocial.R;
import com.sahilssoft.p1_sportsocial.interfaces.OnGetPodItemClickListener;
import com.sahilssoft.p1_sportsocial.models.EpisodesModel;
import com.sahilssoft.p1_sportsocial.utils.DialogMenu;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class GetPodcastAdapter extends RecyclerView.Adapter<GetPodcastAdapter.ViewHolder> {
    Context context;
    List<EpisodesModel> list;
    OnGetPodItemClickListener listener;

    public GetPodcastAdapter(Context context, List<EpisodesModel> list, OnGetPodItemClickListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public GetPodcastAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_podcast_episodes,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull GetPodcastAdapter.ViewHolder holder, int position) {
        EpisodesModel model = list.get(position);
        holder.dateEpisode.setText(model.pubDate);
        holder.titleEpisode.setText(model.title);

        int time = Integer.parseInt(model.duration);
        Date date = new Date(time * 1000L);
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss",Locale.getDefault());
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        holder.durationEpisode.setText(dateFormat.format(date));

        holder.menuEpisodes.setOnClickListener(view ->{
//            View view1 = LayoutInflater.from(context).inflate(R.layout.item_menu,null,false);
//            AlertDialog dialog = new android.app.AlertDialog.Builder(context).setView(view1).create();
//            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.R.color.transparent));
//            dialog.getWindow().setLayout(330,500);
//            dialog.getWindow().setGravity(Gravity.END);
//
////            ll_share = view.findViewById(R.id.ll_share);
////            ll_playNext = view.findViewById(R.id.ll_playNext);
////            ll_playLast = view.findViewById(R.id.ll_playLast);
////            ll_show = view.findViewById(R.id.ll_show);
////            ll_save = view.findViewById(R.id.ll_save);
//            dialog.show();
            DialogMenu.Show(context);

        });
        holder.itemView.setOnClickListener(view -> {
            listener.onItemClick(model);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView dateEpisode, titleEpisode,durationEpisode;
        ImageView menuEpisodes;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            dateEpisode = itemView.findViewById(R.id.dateEpisode);
            titleEpisode = itemView.findViewById(R.id.titleEpisode);
            durationEpisode = itemView.findViewById(R.id.durationEpisode);
            menuEpisodes = itemView.findViewById(R.id.menuEpisodes);
        }
    }
}
