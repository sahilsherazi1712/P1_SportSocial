package com.sahilssoft.p1_sportsocial.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.sahilssoft.p1_sportsocial.R;
import com.sahilssoft.p1_sportsocial.fragments.HomeFragment;
import com.sahilssoft.p1_sportsocial.utils.Constants;
import com.sahilssoft.p1_sportsocial.utils.SharedPrefClass;

public class ChooseAuthActivity extends AppCompatActivity {
    Button btnSignUp,btnLogin;
    ImageView imgFacebook,imgGoogle;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_auth);
        context = ChooseAuthActivity.this;

        btnSignUp = findViewById(R.id.btnSignUp);
        btnLogin = findViewById(R.id.btnLogin);
        imgFacebook = findViewById(R.id.imgFacebook);
        imgGoogle = findViewById(R.id.imgGoogle);

        Log.d("prefData: ",SharedPrefClass.getInstance(context).getData(Constants.TOKEN));
        if (SharedPrefClass.getInstance(context).getData(Constants.TOKEN) != ""){
            startActivity(new Intent(context,MainActivity.class));
            finish();
        }

        btnLogin.setOnClickListener(view -> {
            startActivity(new Intent(context,LoginActivity.class));
        });
        btnSignUp.setOnClickListener(view -> {
            startActivity(new Intent(context,SignUpActivity.class));
        });
        imgFacebook.setOnClickListener(view -> {
            Toast.makeText(context, "Facebook Login", Toast.LENGTH_SHORT).show();
        });
        imgGoogle.setOnClickListener(view -> {
            Toast.makeText(context, "Google Login", Toast.LENGTH_SHORT).show();
        });
    }
}