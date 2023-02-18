package com.sahilssoft.p1_sportsocial.fragments;

import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

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
import com.sahilssoft.p1_sportsocial.adapters.GetPodcastAdapter;
import com.sahilssoft.p1_sportsocial.callback.ObjectCallback;
import com.sahilssoft.p1_sportsocial.interfaces.OnGetPodItemClickListener;
import com.sahilssoft.p1_sportsocial.models.EpisodesModel;
import com.sahilssoft.p1_sportsocial.models.FavouriteModel;
import com.sahilssoft.p1_sportsocial.models.GetPodcastModel;
import com.sahilssoft.p1_sportsocial.utils.DialogMenu;
import com.sahilssoft.p1_sportsocial.utils.Utils;

public class GetPodcastFragment extends Fragment {
    TextView description, title, tvPlay;
    RecyclerView rvEpisodes;
    GetPodcastAdapter getPodcastAdapter;
    ImageView imgHome, imgFavourite, imgMenu;
    int podcastId;
    Context context;

    //model variable is made global to avoid the loading (again api call)
    // from PlayFragment to this fragment again on back press
    GetPodcastModel model;

    FavouriteModel favouriteModel;
    //at the same time we want to favourite or un favourite the episode make this global boolean variable
    boolean isFavourite = false;
    public GetPodcastFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = new GetPodcastModel();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_get_podcast, container, false);
        context = getActivity();
        rvEpisodes = view.findViewById(R.id.rvEpisodes);
        description = view.findViewById(R.id.description);
        imgHome = view.findViewById(R.id.imgHome);
        imgFavourite = view.findViewById(R.id.imgFavourite);
        title = view.findViewById(R.id.title);
        tvPlay = view.findViewById(R.id.tvPlay);
        imgMenu = view.findViewById(R.id.more);
        favouriteModel = new FavouriteModel();

//        image = getArguments().getInt("homeImg");
//        catTitle = getArguments().getString("catTitle");
        podcastId = getArguments().getInt("podcastId");

        rvEpisodes.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));

        if (model.podcast==null){
            Utils.showDialog(context);
            getPodcast();
        }else{
            Utils.dismiss();
            setPodcast(model);
        }

        imgMenu.setOnClickListener(view1 -> DialogMenu.Show(context));
        //play latest episode
        tvPlay.setOnClickListener(view1 -> {
            Utils.showDialog(context);
            model.id = podcastId;
            APIDao.GetPodCast(context, model, new ObjectCallback<GetPodcastModel>() {
                @Override
                public void onData(GetPodcastModel getPodcastModel) {
                    Utils.dismiss();
                    Bundle bundle = new Bundle();
                    bundle.putInt("episodesId", getPodcastModel.episode.get(0).id);
//                    bundle.putString("podcastImg", model.podcast.image);
                    PlayFragment fragment = new PlayFragment();
                    fragment.setArguments(bundle);
                    getFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fv_Container, fragment)
                            .addToBackStack("")
                            .commit();
                }
                @Override
                public void onError(String msg) {
                    Utils.dismiss();
                    Toast.makeText(context, "Something Went Wrong ..!", Toast.LENGTH_SHORT).show();
                }
            });
        });

        //Send episode to Favourites/Unfavourites
        imgFavourite.setOnClickListener(view1 -> {
            Utils.showDialog(context);
            if (!model.favourite){
                imgFavourite.setImageResource(R.drawable.red_heart);
                favouriteModel.episode_id = model.episode.get(0).id;
                APIDao.Favourite(context, favouriteModel, new ObjectCallback<FavouriteModel>() {
                    @Override
                    public void onData(FavouriteModel favouriteModel) {
                        Utils.dismiss();
                        Log.e("FavData","FavData"+new Gson().toJson(favouriteModel));
                        Toast.makeText(context, ""+favouriteModel.message, Toast.LENGTH_SHORT).show();
                        isFavourite = true;
                        model.favourite = isFavourite;
                    }

                    @Override
                    public void onError(String msg) {
                        Utils.dismiss();
                        Toast.makeText(context, ""+msg, Toast.LENGTH_SHORT).show();
                    }
                });
            }else{
                imgFavourite.setImageResource(R.drawable.heart);
                favouriteModel.episode_id = model.episode.get(0).id;
                APIDao.UnFavourite(context, favouriteModel, new ObjectCallback<FavouriteModel>() {
                    @Override
                    public void onData(FavouriteModel favouriteModel) {
                        Utils.dismiss();
                        Toast.makeText(context, ""+favouriteModel.message, Toast.LENGTH_SHORT).show();
                        isFavourite = false;
                        model.favourite = isFavourite;
                    }

                    @Override
                    public void onError(String msg) {
                        Utils.dismiss();
                        Toast.makeText(context, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        return view;
    }

    private void getPodcast() {
//        GetPodcastModel getPodcastModel = new GetPodcastModel();
        model.id = podcastId;
        APIDao.GetPodCast(context, model, new ObjectCallback<GetPodcastModel>() {
            @Override
            public void onData(GetPodcastModel getPodcastModel) {
                Utils.dismiss();
                model = getPodcastModel;
                Log.e("GetPodData", "GetPodData" + new Gson().toJson(model));
                setPodcast(model);
            }
            @Override
            public void onError(String msg) {
                Utils.dismiss();
                Toast.makeText(context, ""+msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setPodcast(GetPodcastModel model) {
        Glide.with(this)
                .load(model.podcast.image)
                .placeholder(R.drawable.placeholder)
                .into(imgHome);
        title.setText(model.podcast.title);
        description.setText(model.podcast.sub_title);

        getPodcastAdapter = new GetPodcastAdapter(context, model.episode, new OnGetPodItemClickListener() {
            @Override
            public void onItemClick(EpisodesModel episodesModel) {
                Bundle bundle = new Bundle();
                bundle.putInt("episodesId", episodesModel.id);
//                bundle.putString("podcastImg", model.podcast.image);
                PlayFragment fragment = new PlayFragment();
                fragment.setArguments(bundle);
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fv_Container, fragment)
                        .addToBackStack("")
                        .commit();
            }
        });
        rvEpisodes.setAdapter(getPodcastAdapter);

        if (model.favourite){
            imgFavourite.setImageResource(R.drawable.red_heart);
        }else{
            imgFavourite.setImageResource(R.drawable.heart);
        }
    }

}