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
import com.sahilssoft.p1_sportsocial.adapters.CategoriesAdapter;
import com.sahilssoft.p1_sportsocial.adapters.SubCategoriesAdapter;
import com.sahilssoft.p1_sportsocial.callback.ObjectCallback;
import com.sahilssoft.p1_sportsocial.interfaces.OnCategoryItemClickListener;
import com.sahilssoft.p1_sportsocial.interfaces.OnCheckUnCheckBoxListener;
import com.sahilssoft.p1_sportsocial.models.CategoryModel;
import com.sahilssoft.p1_sportsocial.models.SignUpModel;
import com.sahilssoft.p1_sportsocial.models.SubCategoryModel;
import com.sahilssoft.p1_sportsocial.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class CategoriesActivity extends AppCompatActivity {
    ImageView imgBack, imgCross;
    RecyclerView rvTeamCat, rvTeamList;
    Button btnStartListing;
    TextView tvTeamDetail;
    Context context;

    String pEmail, pPass, pfName, pSurName, pGender, pCountry;
    ArrayList<Integer> sportListId, subCatList;
    List<CategoryModel> categoryModelList;

    CategoriesAdapter categoriesAdapter;
    SubCategoriesAdapter subCategoriesAdapter;
    SubCategoryModel subCategoryModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        context = CategoriesActivity.this;

        imgBack = findViewById(R.id.imgBack);
        imgCross = findViewById(R.id.imgCross);
        tvTeamDetail = findViewById(R.id.tvTeamDetail);
        rvTeamCat = findViewById(R.id.rvTeamCat);
        rvTeamList = findViewById(R.id.rvTeamList);
        btnStartListing = findViewById(R.id.btnStartListing);
        subCategoryModel = new SubCategoryModel();

        categoryModelList = new ArrayList<>();
        subCatList = new ArrayList<>();

        sportListId = getIntent().getIntegerArrayListExtra("sportListId");
        Toast.makeText(context, ""+sportListId, Toast.LENGTH_SHORT).show();

        pEmail = getIntent().getStringExtra("pEmail");
        pPass = getIntent().getStringExtra("pPass");
        pfName = getIntent().getStringExtra("pfName");
        pSurName = getIntent().getStringExtra("pSurName");
        pGender = getIntent().getStringExtra("pGender");
        pCountry = getIntent().getStringExtra("pCountry");

        tvTeamDetail.setText("We noticed you've clicked football. Would you like to pick your favourite team(s) so we can make your recommendations more applicable");

        imgBack.setOnClickListener(view -> {
            onBackPressed();
        });
        imgCross.setOnClickListener(view -> {
            startActivity(new Intent(context,ChooseAuthActivity.class));
            finish();
        });


//        updateList(model);


        Utils.showDialog(context);
        subCategoryModel.cat_id = sportListId;
        APIDao.SubCategory(context, subCategoryModel, new ObjectCallback<SubCategoryModel>() {
            @Override
            public void onData(SubCategoryModel subCategoryModel) {
                Utils.dismiss();
                Log.e( "SubCategoryModel: ",new Gson().toJson(subCategoryModel) );

                for (int i=0; i<subCategoryModel.categories.size();i++){
                    if (subCategoryModel.categories.get(i).category_data.size() != 0){
                        categoryModelList.add(subCategoryModel.categories.get(i));
                    }
                }
                categoriesAdapter = new CategoriesAdapter(context, categoryModelList, new OnCategoryItemClickListener() {
                    @Override
                    public void onItemClick(CategoryModel categoryModel) {
                        subCategoriesAdapter = new SubCategoriesAdapter(context, categoryModel.category_data, new OnCheckUnCheckBoxListener() {
                            @Override
                            public void onItemClick(int id, boolean b) {
                                if (b){
                                    subCatList.add(id);
                                }else {
                                    for (int i=0;i<subCatList.size();i++) {
                                        if (subCatList.get(i).equals(id)) {
                                            subCatList.remove(i);
                                        }
                                    }
                                }
                            }
                        });
                        rvTeamList.setAdapter(subCategoriesAdapter);
                    }
                });
                rvTeamCat.setAdapter(categoriesAdapter);

            }

            @Override
            public void onError(String msg) {
                Utils.dismiss();
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
            }
        });

        btnStartListing.setOnClickListener(view -> {
            Utils.showDialog(context);
            SignUpModel signUpModel = new SignUpModel();
            signUpModel.email = pEmail;
            signUpModel.password = pPass;
            signUpModel.first_name = pfName;
            signUpModel.sur_name = pSurName;
            signUpModel.gender = pGender;
            signUpModel.country = pCountry;
            signUpModel.cat_id = sportListId;
            signUpModel.subcat_id = subCatList;
            APIDao.SignUp(context, signUpModel, new ObjectCallback<SignUpModel>() {
                @Override
                public void onData(SignUpModel signUpModel) {
                    Utils.dismiss();
                    Log.d("signUpData","signUpData"+new Gson().toJson(signUpModel));
                    Toast.makeText(context, ""+signUpModel.messgae, Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(context,MainActivity.class));
                    finishAffinity();
                }

                @Override
                public void onError(String msg) {
                    Utils.dismiss();
                    Toast.makeText(context, ""+msg, Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}