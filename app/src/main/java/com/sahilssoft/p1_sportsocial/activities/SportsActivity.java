package com.sahilssoft.p1_sportsocial.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.sahilssoft.p1_sportsocial.Methods.APIDao;
import com.sahilssoft.p1_sportsocial.R;
import com.sahilssoft.p1_sportsocial.adapters.SportsAdapter;
import com.sahilssoft.p1_sportsocial.callback.ObjectCallback;
import com.sahilssoft.p1_sportsocial.interfaces.OnCheckUnCheckBoxListener;
import com.sahilssoft.p1_sportsocial.models.SportsModel;
import com.sahilssoft.p1_sportsocial.utils.Utils;

import java.util.ArrayList;

public class SportsActivity extends AppCompatActivity {
    Context context;
    ImageView imgBack, imgCross;
    TextView tvCatDetail;
    RecyclerView rvCategory;
    Button btnNext;
    SportsAdapter sportsAdapter;
    ArrayList<Integer> selectedBoxList;

    String pEmail, pPass, pfName, pSurName, pGender, pCountry;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sports);
        context = SportsActivity.this;
        imgBack = findViewById(R.id.imgBack);
        imgCross = findViewById(R.id.imgCross);
        tvCatDetail = findViewById(R.id.tvCatDetail);
        rvCategory = findViewById(R.id.rvCategory);
        btnNext = findViewById(R.id.btnNext);

        pEmail = getIntent().getStringExtra("pEmail");
        pPass = getIntent().getStringExtra("pPass");
        pfName = getIntent().getStringExtra("pfName");
        pSurName = getIntent().getStringExtra("pSurName");
        pGender = getIntent().getStringExtra("pGender");
        pCountry = getIntent().getStringExtra("pCountry");

        selectedBoxList = new ArrayList<>();

//        tvSportDetail.setText(pEmail+" "+pPass+" "+pfName+" "+pSurName+" "+pGender+" "+pCountry);
        tvCatDetail.setText("Hi "+ pfName +"!\nThanks for joining the sport social. Before we get started let's personalise your experience. Keep your up to date with your favourite sports");

        Utils.showDialog(context);
        getCategories();
        imgBack.setOnClickListener(view -> {
            onBackPressed();
        });
        imgCross.setOnClickListener(view -> {
            startActivity(new Intent(context,ChooseAuthActivity.class));
        });

        btnNext.setOnClickListener(view -> {
            Intent intent = new Intent(context, CategoriesActivity.class);
            intent.putExtra("sportListId",selectedBoxList);
            intent.putExtra("pEmail",pEmail);
            intent.putExtra("pPass",pPass);
            intent.putExtra("pfName",pfName);
            intent.putExtra("pSurName",pSurName);
            intent.putExtra("pGender",pGender);
            intent.putExtra("pCountry",pCountry);
            startActivity(intent);
        });

    }

    private void getCategories() {
        APIDao.Category(context, new ObjectCallback<SportsModel>() {
            @Override
            public void onData(SportsModel categoryModel) {
                Utils.dismiss();
                Log.d("catData","catData"+new Gson().toJson(categoryModel));
                sportsAdapter = new SportsAdapter(context, categoryModel.categories, new OnCheckUnCheckBoxListener() {
                    @Override
                    public void onItemClick(int id, boolean b) {
                        if (b){
                            selectedBoxList.add(id);
                        }else {
                            //for loop is used because simple remove didn't know that which value has to be removed.
                            //therefore we will check complete list[0,1,2,..] one by one to check the removed id by
                            //position to remove it.
                            for (int i=0;i<selectedBoxList.size();i++) {
                                if (selectedBoxList.get(i).equals(id)) {
                                    selectedBoxList.remove(i);
                                }
                            }
                        }
                    }
                });
                rvCategory.setAdapter(sportsAdapter);
            }

            @Override
            public void onError(String msg) {
                Utils.dismiss();
                Toast.makeText(context, ""+msg, Toast.LENGTH_SHORT).show();
            }
        });
    }
}