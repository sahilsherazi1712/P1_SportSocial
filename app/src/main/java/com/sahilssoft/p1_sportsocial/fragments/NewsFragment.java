package com.sahilssoft.p1_sportsocial.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.sahilssoft.p1_sportsocial.Methods.APIDao;
import com.sahilssoft.p1_sportsocial.R;
import com.sahilssoft.p1_sportsocial.adapters.NewsAdapter;
import com.sahilssoft.p1_sportsocial.callback.ObjectCallback;
import com.sahilssoft.p1_sportsocial.interfaces.OnPodItemClickListener;
import com.sahilssoft.p1_sportsocial.models.PodcastData;
import com.sahilssoft.p1_sportsocial.models.PodcastModel;
import com.sahilssoft.p1_sportsocial.utils.Utils;

public class NewsFragment extends Fragment {
    RecyclerView rvNews;

    Context context;
    NewsAdapter newsAdapter;
    public NewsFragment() {
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
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        context = getActivity();
        rvNews = view.findViewById(R.id.rvNews);

        Utils.showDialog(context);
        APIDao.News(context, new ObjectCallback<PodcastModel>() {
            @Override
            public void onData(PodcastModel podcastModel) {
                Utils.dismiss();
                Log.d("NewsData","NewsData"+new Gson().toJson(podcastModel));
                newsAdapter = new NewsAdapter(context, podcastModel.Newspodcast, new OnPodItemClickListener() {
                    @Override
                    public void onItemClick(PodcastData podcastData) {
                        Bundle bundle = new Bundle();
                        bundle.putInt("podcastId",podcastData.id);
                        GetPodcastFragment fragment = new GetPodcastFragment();
                        fragment.setArguments(bundle);
                        getFragmentManager()
                                .beginTransaction()
                                .replace(R.id.fv_Container,fragment)
                                .addToBackStack("")
                                .commit();
                    }
                });
                rvNews.setAdapter(newsAdapter);
            }

            @Override
            public void onError(String msg) {
                Utils.dismiss();
                Toast.makeText(context, ""+msg, Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
}