package com.sahilssoft.p1_sportsocial.fragments;

import android.graphics.Paint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sahilssoft.p1_sportsocial.R;

public class AboutFragment extends Fragment {
    TextView tvPrivacyPolicy;
    public AboutFragment() {
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
        View view = inflater.inflate(R.layout.fragment_about, container, false);
        tvPrivacyPolicy = view.findViewById(R.id.tvPrivacyPolicy);
        tvPrivacyPolicy.setPaintFlags(tvPrivacyPolicy.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);

        return view;
    }
}