package com.sahilssoft.p1_sportsocial.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.sahilssoft.p1_sportsocial.Methods.APIDao;
import com.sahilssoft.p1_sportsocial.R;
import com.sahilssoft.p1_sportsocial.adapters.SearchAdapter;
import com.sahilssoft.p1_sportsocial.adapters.SearchCategoryAdapter;
import com.sahilssoft.p1_sportsocial.callback.ObjectCallback;
import com.sahilssoft.p1_sportsocial.interfaces.OnSearchCategoryClickListener;
import com.sahilssoft.p1_sportsocial.interfaces.OnSearchItemClickListener;
import com.sahilssoft.p1_sportsocial.models.PodcastData;
import com.sahilssoft.p1_sportsocial.models.PodcastModel;
import com.sahilssoft.p1_sportsocial.models.CategoryModel;
import com.sahilssoft.p1_sportsocial.utils.Utils;

import java.util.List;

public class SearchFragment extends Fragment {
    SearchView searchView;
    TextView tvSearched;
    RecyclerView rvCatSearch;

    PodcastModel podcastModel;
    SearchAdapter searchAdapter;
    SearchCategoryAdapter searchCategoryAdapter;
    Context context;

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        podcastModel = new PodcastModel();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        context = getActivity();
        searchView = view.findViewById(R.id.searchView);
        tvSearched = view.findViewById(R.id.tvSearched);
        rvCatSearch = view.findViewById(R.id.rvCatSearch);

        if (podcastModel.categories==null){
            Utils.showDialog(context);
            searchView.setQuery("All",true);
            getSearchCategory(searchView.getQuery().toString());
        }else{
            setSearchCategory(podcastModel.categories);
        }

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Utils.showDialog(context);
                if (searchView.getQuery().toString().equals("All")){
                    getSearchCategory(s);
                }else{
                    tvSearched.setText("Searched Results");
                    podcastModel.name = searchView.getQuery().toString();
                    APIDao.Search(context, podcastModel, new ObjectCallback<PodcastModel>() {
                        @Override
                        public void onData(PodcastModel podcastModel) {
                            Utils.dismiss();
                            Log.e("searchData", "searchData" + new Gson().toJson(podcastModel));
                            searchAdapter = new SearchAdapter(context, podcastModel.podcasts, new OnSearchItemClickListener() {
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
                            rvCatSearch.setAdapter(searchAdapter);
                        }

                        @Override
                        public void onError(String msg) {
                            Utils.dismiss();
                            Toast.makeText(context, ""+msg, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        return view;
    }

    private void getSearchCategory(String searchView) {
        tvSearched.setText("Browse Categories");
        podcastModel.name = searchView;
        APIDao.Search(context, podcastModel, new ObjectCallback<PodcastModel>() {
            @Override
            public void onData(PodcastModel podcastModel) {
                Utils.dismiss();
                Log.e("searchData", "searchData" + new Gson().toJson(podcastModel));
                setSearchCategory(podcastModel.categories);
            }

            @Override
            public void onError(String msg) {
                Utils.dismiss();
                Toast.makeText(context, ""+msg, Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void setSearchCategory(List<CategoryModel> categories) {
        searchCategoryAdapter = new SearchCategoryAdapter(context, categories, new OnSearchCategoryClickListener() {
            @Override
            public void onItemClick(CategoryModel searchCategoryModel) {
                Bundle bundle = new Bundle();
                bundle.putString("searchCatData", new Gson().toJson(searchCategoryModel));
                SearchCatClickFragment fragment = new SearchCatClickFragment();
                fragment.setArguments(bundle);
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fv_Container, fragment)
                        .addToBackStack("")
                        .commit();
            }
        });
        rvCatSearch.setAdapter(searchCategoryAdapter);
    }
}