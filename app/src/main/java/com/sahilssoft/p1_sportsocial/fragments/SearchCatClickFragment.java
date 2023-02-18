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
import com.sahilssoft.p1_sportsocial.activities.SearchCatClickAdapter;
import com.sahilssoft.p1_sportsocial.callback.ObjectCallback;
import com.sahilssoft.p1_sportsocial.interfaces.OnPodItemClickListener;
import com.sahilssoft.p1_sportsocial.models.PodcastData;
import com.sahilssoft.p1_sportsocial.models.SearchCatClickModel;
import com.sahilssoft.p1_sportsocial.models.CategoryModel;
import com.sahilssoft.p1_sportsocial.utils.Utils;

public class SearchCatClickFragment extends Fragment {
    Context context;
    TextView tvBack, tvName;
    RecyclerView rvSearchCatClick;
    SearchCatClickModel searchCatClickModel;
    SearchCatClickAdapter searchCatClickAdapter;
    public SearchCatClickFragment() {
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
        View view = inflater.inflate(R.layout.fragment_search_cat_click, container, false);
        context = getActivity();
        tvBack = view.findViewById(R.id.tvBack);
        tvName = view.findViewById(R.id.tvName);
        rvSearchCatClick = view.findViewById(R.id.rvSearchCatClick);

        tvBack.setOnClickListener(view1 -> getActivity().onBackPressed());

        CategoryModel searchCategoryModel = new Gson().fromJson(getArguments().getString("searchCatData"), CategoryModel.class);

        Utils.showDialog(context);
        searchCatClickModel = new SearchCatClickModel();
        searchCatClickModel.id = searchCategoryModel.id;
        APIDao.SearchCatClick(context, searchCatClickModel, new ObjectCallback<SearchCatClickModel>() {
            @Override
            public void onData(SearchCatClickModel searchCatClickModel) {
                Utils.dismiss();
                Log.e("searchCatClickModel","searchCatClickModel"+new Gson().toJson(searchCatClickModel));
                tvName.setText(searchCatClickModel.category);
                searchCatClickAdapter = new SearchCatClickAdapter(context, searchCatClickModel.podcast, new OnPodItemClickListener() {
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
                rvSearchCatClick.setAdapter(searchCatClickAdapter);
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