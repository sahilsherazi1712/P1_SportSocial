package com.sahilssoft.p1_sportsocial.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.sahilssoft.p1_sportsocial.Methods.APIDao;
import com.sahilssoft.p1_sportsocial.R;
import com.sahilssoft.p1_sportsocial.callback.ObjectCallback;
import com.sahilssoft.p1_sportsocial.models.CheckEmailModel;
import com.sahilssoft.p1_sportsocial.utils.Utils;


public class SignUpActivity extends AppCompatActivity {
    Context context;
    ImageView imgBack,imgCross;
    EditText etEmail, etEmailConfirm, etPass, etPassConfirm;
    Button btnContinue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        context = SignUpActivity.this;
        imgBack = findViewById(R.id.imgBack);
        imgCross = findViewById(R.id.imgCross);
        etEmail = findViewById(R.id.etEmail);
        etEmailConfirm = findViewById(R.id.etEmailConfirm);
        etPass = findViewById(R.id.etPass);
        etPassConfirm = findViewById(R.id.etPassConfirm);
        btnContinue = findViewById(R.id.btnContinue);

        imgBack.setOnClickListener(view -> {
            onBackPressed();
        });
        imgCross.setOnClickListener(view -> {
            startActivity(new Intent(SignUpActivity.this,ChooseAuthActivity.class));
        });

        btnContinue.setOnClickListener(view -> {
            if (validateData()){
                Utils.showDialog(context);
                CheckEmailModel checkEmailModel = new CheckEmailModel();
                checkEmailModel.email = etEmail.getText().toString();
                checkEmailModel.password = etPass.getText().toString();
                APIDao.CheckEmail(context, checkEmailModel, new ObjectCallback<CheckEmailModel>() {
                    @Override
                    public void onData(CheckEmailModel checkEmailModel) {
                        Utils.dismiss();
                        Log.d("checkEmailData","checkEmailData"+new Gson().toJson(checkEmailModel));
                        Intent intent = new Intent(context,PersonalDetailsActivity.class);
                        intent.putExtra("signUpEmail", etEmail.getText().toString());
                        intent.putExtra("signUpPass", etPass.getText().toString());
                        startActivity(intent);
                    }

                    @Override
                    public void onError(String msg) {
                        Utils.dismiss();
                        Toast.makeText(context, ""+msg, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });



    }

    private Boolean validateData() {
        if (!Patterns.EMAIL_ADDRESS.matcher(etEmail.getText().toString()).matches()){
            Toast.makeText(context, "Invalid Email Address", Toast.LENGTH_SHORT).show();
            return false;
        }else if(!etEmail.getText().toString().equals(etEmailConfirm.getText().toString())){
            Toast.makeText(context, "Email and Confirm Email Address Should be Same!", Toast.LENGTH_SHORT).show();
            return false;
        }else{
            if (TextUtils.isEmpty(etPass.getText().toString())){
                Toast.makeText(context, "Password Field is Required!", Toast.LENGTH_SHORT).show();
                return false;
            }else if(!etPass.getText().toString().equals(etPassConfirm.getText().toString())){
                Toast.makeText(context, "Password and Confirm Password Should be Same!", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }
}