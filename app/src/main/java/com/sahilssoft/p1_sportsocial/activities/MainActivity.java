package com.sahilssoft.p1_sportsocial.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationBarView;
import com.sahilssoft.p1_sportsocial.R;
import com.sahilssoft.p1_sportsocial.databinding.ActivityMainBinding;
import com.sahilssoft.p1_sportsocial.fragments.LibraryFragment;
import com.sahilssoft.p1_sportsocial.fragments.NewsFragment;
import com.sahilssoft.p1_sportsocial.fragments.HomeFragment;
import com.sahilssoft.p1_sportsocial.fragments.SearchFragment;
import com.sahilssoft.p1_sportsocial.fragments.SettingFragment;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        context = MainActivity.this;

        replaceFragment(new HomeFragment());
//        Log.d("UserData",new Gson().toJson(SharedPrefClass.getInstance(context).LoadLogin(Constants.TOKEN)));
        binding.bnv.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home: replaceFragment(new HomeFragment());
                        break;
                    case R.id.search: replaceFragment(new SearchFragment());
                        break;
                    case R.id.news: replaceFragment(new NewsFragment());
                        break;
                    case R.id.podcast: replaceFragment(new LibraryFragment());
                        break;
                    case R.id.setting: replaceFragment(new SettingFragment());
                        break;
                }
                return true;
            }
        });
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fv_Container,fragment);
        transaction.commit();
    }

}