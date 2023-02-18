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
import com.sahilssoft.p1_sportsocial.adapters.PodcastAdapter;
import com.sahilssoft.p1_sportsocial.callback.ObjectCallback;
import com.sahilssoft.p1_sportsocial.interfaces.OnPodItemClickListener;
import com.sahilssoft.p1_sportsocial.models.PodcastData;
import com.sahilssoft.p1_sportsocial.models.PodcastModel;
import com.sahilssoft.p1_sportsocial.utils.Utils;

public class HomeFragment extends Fragment {
    RecyclerView rvChosenForYou,rvNewNoteworthy,rvTopProducts;
    PodcastAdapter podcastAdapter;
    Context context;
    public HomeFragment() {
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
        View view = inflater.inflate(R.layout.fragment_home, container, false);
//        Log.d("UserData",new Gson().toJson(SharedPrefClass.getInstance(context).getData(Constants.TOKEN)));
        context = getActivity();
        rvChosenForYou = view.findViewById(R.id.rvChosenForYou);
        rvNewNoteworthy = view.findViewById(R.id.rvNewNoteworthy);
        rvTopProducts = view.findViewById(R.id.rvTopProducts);

        Utils.showDialog(context);
        chosenPodcast();
        newPodcast();
        topPodcast();
        return view;
    }

    private void chosenPodcast() {
        APIDao.PodcastAPI(context, new ObjectCallback<PodcastModel>() {
            @Override
            public void onData(PodcastModel podcastModel) {
                Utils.dismiss();
                Log.d("podcastData","podcastData"+new Gson().toJson(podcastModel));
//                userAdapter = new UserAdapter(MainActivity.this,userModel.features);
//                rv.setAdapter(userAdapter);
                podcastAdapter = new PodcastAdapter(context, podcastModel.chosenPodcast, new OnPodItemClickListener() {
                    @Override
                    public void onItemClick(PodcastData podcastData) {
                        replaceFragmentWithData(new GetPodcastFragment(),podcastData.id);
                    }
                });
                rvChosenForYou.setAdapter(podcastAdapter);
            }

            @Override
            public void onError(String msg) {
                Utils.dismiss();
                Toast.makeText(context, ""+msg, Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void newPodcast() {
        APIDao.PodcastAPI(context, new ObjectCallback<PodcastModel>() {
            @Override
            public void onData(PodcastModel podcastModel) {
                Utils.dismiss();
                Log.d("podcastData","podcastData"+new Gson().toJson(podcastModel));
//                userAdapter = new UserAdapter(MainActivity.this,userModel.features);
//                rv.setAdapter(userAdapter);
                podcastAdapter = new PodcastAdapter(context, podcastModel.newPodcast, new OnPodItemClickListener() {
                    @Override
                    public void onItemClick(PodcastData podcastData) {
                        replaceFragmentWithData(new GetPodcastFragment(),podcastData.id);
                    }
                });
                rvNewNoteworthy.setAdapter(podcastAdapter);
            }

            @Override
            public void onError(String msg) {
                Utils.dismiss();
                Toast.makeText(context, ""+msg, Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void topPodcast() {
        APIDao.PodcastAPI(context, new ObjectCallback<PodcastModel>() {
            @Override
            public void onData(PodcastModel podcastModel) {
                Utils.dismiss();
                Log.d("podcastData","podcastData"+new Gson().toJson(podcastModel));
//                userAdapter = new UserAdapter(MainActivity.this,userModel.features);
//                rv.setAdapter(userAdapter);
                podcastAdapter = new PodcastAdapter(context, podcastModel.topPodcast, new OnPodItemClickListener() {
                    @Override
                    public void onItemClick(PodcastData podcastData) {
                        replaceFragmentWithData(new GetPodcastFragment(),podcastData.id);
                    }
                });
                rvTopProducts.setAdapter(podcastAdapter);
            }

            @Override
            public void onError(String msg) {
                Utils.dismiss();
                Toast.makeText(context, ""+msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void replaceFragmentWithData(Fragment fragment, int id) {
        Bundle bundle = new Bundle();
        bundle.putInt("podcastId",id);
        fragment.setArguments(bundle);
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.fv_Container,fragment)
                .addToBackStack("")
                .commit();
    }
}