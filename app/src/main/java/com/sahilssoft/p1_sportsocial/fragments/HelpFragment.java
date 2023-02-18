package com.sahilssoft.p1_sportsocial.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.sahilssoft.p1_sportsocial.R;

public class HelpFragment extends Fragment {
    ImageView imgBackHelp;
    public HelpFragment() {
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
        View view = inflater.inflate(R.layout.fragment_help, container, false);
        imgBackHelp = view.findViewById(R.id.imgBackHelp);
        imgBackHelp.setOnClickListener(view1 -> {
            getActivity().onBackPressed();
        });
        return view;
    }
}