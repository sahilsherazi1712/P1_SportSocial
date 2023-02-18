package com.sahilssoft.p1_sportsocial.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.sahilssoft.p1_sportsocial.Methods.APIDao;
import com.sahilssoft.p1_sportsocial.R;
import com.sahilssoft.p1_sportsocial.adapters.LibraryAdapter;
import com.sahilssoft.p1_sportsocial.callback.ObjectCallback;
import com.sahilssoft.p1_sportsocial.interfaces.OnSearchItemClickListener;
import com.sahilssoft.p1_sportsocial.models.PodcastData;
import com.sahilssoft.p1_sportsocial.models.PodcastModel;
import com.sahilssoft.p1_sportsocial.utils.Utils;


public class LibraryFragment extends Fragment {
    TextView tvFavourites, tvDownloads, tvLatestEpisodes;
    RecyclerView rvLibrary;

    Context context;
    PodcastModel podcastModel;
    LibraryAdapter libraryAdapter;

    public LibraryFragment() {
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
        View view = inflater.inflate(R.layout.fragment_library, container, false);
        context = getActivity();
        tvFavourites = view.findViewById(R.id.tvAccount);
        tvDownloads = view.findViewById(R.id.tvPurchaseHistory);
        tvLatestEpisodes = view.findViewById(R.id.tvHelp);
        rvLibrary = view.findViewById(R.id.rvLibrary);

        tvFavourites.setOnClickListener(view1 -> {
            replaceFragmentWithKey("Favourites","Favourites");
        });
        tvDownloads.setOnClickListener(view1 -> {
            replaceFragmentWithKey("Downloads", "Downloads");
        });
        tvLatestEpisodes.setOnClickListener(view1 -> {
            replaceFragmentWithKey("Latest", "Latest Episodes");
        });


        Utils.showDialog(context);
        podcastModel = new PodcastModel();
        podcastModel.library = "library";
        APIDao.Library(context, podcastModel, new ObjectCallback<PodcastModel>() {
            @Override
            public void onData(PodcastModel podcastModel) {
                Utils.dismiss();
                Log.d("libraryData", "libraryData: "+new Gson().toJson(podcastModel));
                libraryAdapter = new LibraryAdapter(context, podcastModel.podcast, new OnSearchItemClickListener() {
                    @Override
                    public void onItemClick(PodcastData podcastData) {
                        Bundle bundle = new Bundle();
                        bundle.putInt("podcastId", podcastData.id);
                        GetPodcastFragment fragment = new GetPodcastFragment();
                        fragment.setArguments(bundle);
                        getFragmentManager()
                                .beginTransaction()
                                .replace(R.id.fv_Container, fragment)
                                .addToBackStack("")
                                .commit();
                    }
                });
                rvLibrary.setAdapter(libraryAdapter);
            }

            @Override
            public void onError(String msg) {
                Utils.dismiss();
                Toast.makeText(context, ""+msg, Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    private void replaceFragmentWithKey(String key, String title) {
        Bundle bundle = new Bundle();
        bundle.putString("library", key);
        bundle.putString("title", title);
        FDLFragment fragment = new FDLFragment();
        fragment.setArguments(bundle);
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.fv_Container, fragment)
                .addToBackStack("")
                .commit();
    }
}