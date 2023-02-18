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
import android.widget.Toast;

import com.google.gson.Gson;
import com.sahilssoft.p1_sportsocial.Methods.APIDao;
import com.sahilssoft.p1_sportsocial.R;
import com.sahilssoft.p1_sportsocial.callback.ObjectCallback;
import com.sahilssoft.p1_sportsocial.models.LoginModel;
import com.sahilssoft.p1_sportsocial.utils.Constants;
import com.sahilssoft.p1_sportsocial.utils.SharedPrefClass;
import com.sahilssoft.p1_sportsocial.utils.Utils;

public class LoginActivity extends AppCompatActivity {
    EditText etEmail, etPassword;
    Button btnLogin;
    Context context;
    String email, password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        context = LoginActivity.this;

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(view -> {
            if (validate()){
                Utils.showDialog(context);
                LoginModel loginModel = new LoginModel();
                loginModel.email = etEmail.getText().toString();
                loginModel.password = etPassword.getText().toString();

                APIDao.LoginAPI(context, loginModel, new ObjectCallback<LoginModel>() {
                    @Override
                    public void onData(LoginModel loginModel) {
                        Utils.dismiss();
                        SharedPrefClass.getInstance(context).SaveData(Constants.USERDATA,new Gson().toJson(loginModel));
                        SharedPrefClass.getInstance(context).SaveData(Constants.TOKEN,loginModel.token);
                        Log.e("LoginData","LoginData: "+new Gson().toJson(loginModel));

                        startActivity(new Intent(LoginActivity.this,MainActivity.class));
                        finishAffinity();

                    }

                    @Override
                    public void onError(String msg) {
                        Utils.dismiss();
                        Toast.makeText(context, ""+loginModel.message, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }


    private Boolean validate() {
        email = etEmail.getText().toString();
        password = etPassword.getText().toString();
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Toast.makeText(this, "Invalid email address", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(password)){
            Toast.makeText(this, "Enter Password", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

}