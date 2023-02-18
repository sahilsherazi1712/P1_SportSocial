package com.sahilssoft.p1_sportsocial.fragments;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.sahilssoft.p1_sportsocial.Methods.APIDao;
import com.sahilssoft.p1_sportsocial.R;
import com.sahilssoft.p1_sportsocial.callback.ObjectCallback;
import com.sahilssoft.p1_sportsocial.models.FavouriteModel;
import com.sahilssoft.p1_sportsocial.models.PlayModel;
import com.sahilssoft.p1_sportsocial.utils.DialogMenu;
import com.sahilssoft.p1_sportsocial.utils.Utils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class PlayFragment extends Fragment {
    String desc,dur,title,date;
    int image;
    TextView tvDurationPlay, tvDescription, tvTitlePlay;
    ImageView imgPlay, imgFavouritePlay, ivPlay, morePlay, ivMore;

    int episodesId;
    Context context;
    FavouriteModel favouriteModel;
    PlayModel pModel;
    MediaPlayer mp;
    //at the same time we want to favourite or un favourite the episode make this global boolean variable
    boolean isFavourite = false;

    AudioManager audioManager;
    public PlayFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_play, container, false);
        context = getActivity();

        tvDurationPlay = view.findViewById(R.id.tvDurationPlay);
        tvDescription = view.findViewById(R.id.tvDescriptionPlay);
        tvTitlePlay = view.findViewById(R.id.tvTitlePlay);
        imgPlay = view.findViewById(R.id.imgPlay);
        ivPlay = view.findViewById(R.id.ivPlay);
        morePlay = view.findViewById(R.id.morePlay);
        ivMore = view.findViewById(R.id.ivMore);
        imgFavouritePlay = view.findViewById(R.id.imgFavouritePlay);
        pModel = new PlayModel();
        favouriteModel = new FavouriteModel();
        mp = new MediaPlayer();

        episodesId = getArguments().getInt("episodesId");

        Utils.showDialog(context);
        pModel.id = episodesId;
        APIDao.Play(context, pModel, new ObjectCallback<PlayModel>() {
            @Override
            public void onData(PlayModel playModel) {
                Utils.dismiss();
                pModel = playModel;
                Log.e("playData","playData"+new Gson().toJson(playModel));

                setData(playModel);
            }

            @Override
            public void onError(String msg) {
                Utils.dismiss();
                Toast.makeText(context, ""+msg, Toast.LENGTH_SHORT).show();
            }
        });

        morePlay.setOnClickListener(view1 -> {
            DialogMenu.Show(context);
        });
        ivMore.setOnClickListener(view1 -> {
            DialogMenu.Show(context);
        });
        //Send episode to Favourites/Un Favourites
        imgFavouritePlay.setOnClickListener(view1 -> {
            Utils.showDialog(context);
            if (!pModel.favourite){
                imgFavouritePlay.setImageResource(R.drawable.red_heart);
                favouriteModel.episode_id = pModel.episode.id;
                APIDao.Favourite(context, favouriteModel, new ObjectCallback<FavouriteModel>() {
                    @Override
                    public void onData(FavouriteModel favouriteModel) {
                        Utils.dismiss();
                        Log.e("FavData","FavData"+new Gson().toJson(favouriteModel));
                        Toast.makeText(context, ""+favouriteModel.message, Toast.LENGTH_SHORT).show();
                        isFavourite = true;
                        pModel.favourite = isFavourite;
                    }

                    @Override
                    public void onError(String msg) {
                        Utils.dismiss();
                        Toast.makeText(context, ""+msg, Toast.LENGTH_SHORT).show();
                    }
                });
            }else {
                imgFavouritePlay.setImageResource(R.drawable.heart);
                favouriteModel.episode_id = pModel.episode.id;
                APIDao.UnFavourite(context, favouriteModel, new ObjectCallback<FavouriteModel>() {
                    @Override
                    public void onData(FavouriteModel favouriteModel) {
                        Utils.dismiss();
                        Toast.makeText(context, ""+favouriteModel.message, Toast.LENGTH_SHORT).show();
                        isFavourite = false;
                        pModel.favourite = isFavourite;
                    }

                    @Override
                    public void onError(String msg) {
                        Utils.dismiss();
                        Toast.makeText(context, ""+msg, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });



        return view;
    }

    private void setData(PlayModel playModel) {
        Glide.with(context)
                .load(playModel.podcast.image)
                .placeholder(R.drawable.placeholder)
                .into(imgPlay);

        int time = Integer.parseInt(playModel.episode.duration);
        Date date = new Date(time * 1000L);
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        tvDurationPlay.setText(dateFormat.format(date));

        tvTitlePlay.setText(playModel.episode.title);
        tvDescription.setText(playModel.episode.description);

        if (playModel.favourite){
            imgFavouritePlay.setImageResource(R.drawable.red_heart);
        }else{
            imgFavouritePlay.setImageResource(R.drawable.heart);
        }

//        MediaPlayer mp = playModel.podcast.feed_url;
        //play work
        ivPlay.setOnClickListener(view -> {
            String url = playModel.episode.url;
//            mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
            try {
                mp.setDataSource(url);
                mp.prepare();

                if (mp == null){
                    mp.start();
                }else{
                    mp.pause();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void play(View v){
        if (mp==null){
            mp.start();
        }
    }
    private void pause(View v){
        if (mp != null){
            mp.pause();
        }
    }
}