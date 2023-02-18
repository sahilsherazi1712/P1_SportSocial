package com.sahilssoft.p1_sportsocial.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sahilssoft.p1_sportsocial.R;
import com.sahilssoft.p1_sportsocial.adapters.ClickEpisodesAdapter;
import com.sahilssoft.p1_sportsocial.models.SearchCatModel;

import java.util.ArrayList;

public class ClickEpisodesFragment extends Fragment {
    ImageView imgBack;
    TextView tvVTitle, tvTitle;
    RecyclerView rvClickEpisode;

    String catTitle;

    ArrayList<SearchCatModel> searchCatModelList;
    ClickEpisodesAdapter clickEpisodesAdapter;

    public ClickEpisodesFragment() {
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
        View view = inflater.inflate(R.layout.fragment_click_episodes, container, false);
        imgBack = view.findViewById(R.id.imgBack);
        tvVTitle = view.findViewById(R.id.tvVTitle);
        tvTitle = view.findViewById(R.id.tvTitle);
        rvClickEpisode = view.findViewById(R.id.rvClickEpisode);

        catTitle = getArguments().getString("catTitle");
        tvTitle.setText(catTitle);

        imgBack.setOnClickListener(view1 -> {
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fv_Container,new SearchFragment())
                    .addToBackStack(null)
                    .commit();
        });

        getEpisodes();
        return view;
    }

    private void getEpisodes() {
        searchCatModelList = new ArrayList<>();
        searchCatModelList.add(new SearchCatModel(R.drawable.ic_stream1,"sdf"));
        searchCatModelList.add(new SearchCatModel(R.drawable.ic_stream2,"axs"));
        searchCatModelList.add(new SearchCatModel(R.drawable.ic_stream3,"dfs"));
        searchCatModelList.add(new SearchCatModel(R.drawable.ic_stream4,"srs"));
        searchCatModelList.add(new SearchCatModel(R.drawable.ic_stream5,"dfs"));
        searchCatModelList.add(new SearchCatModel(R.drawable.ic_stream6,"esg"));
        searchCatModelList.add(new SearchCatModel(R.drawable.ic_stream7,"gfd"));
        searchCatModelList.add(new SearchCatModel(R.drawable.ic_stream8,"bhg"));
        searchCatModelList.add(new SearchCatModel(R.drawable.ic_stream9,"dgs"));
        searchCatModelList.add(new SearchCatModel(R.drawable.ic_stream10,"khb"));
        clickEpisodesAdapter = new ClickEpisodesAdapter(getActivity(),searchCatModelList);
        rvClickEpisode.setAdapter(clickEpisodesAdapter);
    }
}