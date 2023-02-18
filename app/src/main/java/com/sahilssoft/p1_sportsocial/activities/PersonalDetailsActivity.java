package com.sahilssoft.p1_sportsocial.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.sahilssoft.p1_sportsocial.R;

import java.util.Objects;

public class PersonalDetailsActivity extends AppCompatActivity {
    Context context;
    Spinner spinnerP;
    String[] gender = {"gender","male","female","transgender"};
    ArrayAdapter<String> adapter;
    String value;

    ImageView imgBack,imgCross;
    EditText etfName, etSurname, etCountry;
    Button btnContinue;

    String signUpEmail, signUpPass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_details);
        context = PersonalDetailsActivity.this;
        imgBack = findViewById(R.id.imgBack);
        imgCross = findViewById(R.id.imgCross);
        etfName = findViewById(R.id.etfName);
        etSurname = findViewById(R.id.etSurname);
        spinnerP = findViewById(R.id.spinnerP);
        etCountry = findViewById(R.id.etCountry);
        btnContinue = findViewById(R.id.btnContinue);

        adapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_activated_1, gender);
        spinnerP.setAdapter(adapter);
        spinnerP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                value = adapterView.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        signUpEmail = getIntent().getStringExtra("signUpEmail");
        signUpPass = getIntent().getStringExtra("signUpPass");
//        Toast.makeText(this, signUpEmail+" "+signUpPass, Toast.LENGTH_SHORT).show();

        imgBack.setOnClickListener(view -> {
            onBackPressed();
        });
        imgCross.setOnClickListener(view -> {
            startActivity(new Intent(PersonalDetailsActivity.this,ChooseAuthActivity.class));
        });

        btnContinue.setOnClickListener(view -> {
            if (validateDetails()){
                Intent intent = new Intent(context, SportsActivity.class);
                intent.putExtra("pEmail",signUpEmail);
                intent.putExtra("pPass",signUpPass);
                intent.putExtra("pfName",etfName.getText().toString());
                intent.putExtra("pSurName",etSurname.getText().toString());
                intent.putExtra("pGender",value);
                intent.putExtra("pCountry",etCountry.getText().toString());
                startActivity(intent);
            }
        });
    }

    private Boolean validateDetails(){
        if (TextUtils.isEmpty(etfName.getText().toString())){
            Toast.makeText(context, "Name Field is Required!", Toast.LENGTH_SHORT).show();
            return false;
        }else if (TextUtils.isEmpty(etSurname.getText().toString())){
            Toast.makeText(context, "Surname Field is Required!", Toast.LENGTH_SHORT).show();
            return false;
        }else if (Objects.equals(value, "gender")){
            Toast.makeText(context, "Choose Your Gender!", Toast.LENGTH_SHORT).show();
            return false;
        }else if(TextUtils.isEmpty(etCountry.getText().toString())){
            Toast.makeText(context, "Country Field is Required!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}