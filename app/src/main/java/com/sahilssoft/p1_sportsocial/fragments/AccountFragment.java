package com.sahilssoft.p1_sportsocial.fragments;

import android.content.Context;
import android.graphics.Paint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.sahilssoft.p1_sportsocial.Methods.APIDao;
import com.sahilssoft.p1_sportsocial.R;
import com.sahilssoft.p1_sportsocial.callback.ObjectCallback;
import com.sahilssoft.p1_sportsocial.models.ProfileModel;
import com.sahilssoft.p1_sportsocial.models.UpdateAccountModel;
import com.sahilssoft.p1_sportsocial.utils.Utils;

import java.util.Objects;

public class AccountFragment extends Fragment {
    Context context;
    Spinner spinnerG;
    String[] gender = {"gender","male","female","transgender"};
    ArrayAdapter<String> adapter;
    String value;

    EditText etFistName, etSurname, etEmail, etOldPassword, etNewPassword, etConfirmPassword;
    Button btnSaveAccount, btnSaveGender, btnVerifyEmail, btnSavePassword;
    TextView tvForgetPassword;

    UpdateAccountModel updateAccountModel;
    public AccountFragment() {
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
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        context = getActivity();
        spinnerG = view.findViewById(R.id.spinnerG);
        etFistName = view.findViewById(R.id.etFistName);
        etSurname = view.findViewById(R.id.etSurname);
        btnSaveAccount = view.findViewById(R.id.btnSaveAccount);
        btnSaveGender = view.findViewById(R.id.btnSaveGender);
        etEmail = view.findViewById(R.id.etEmail);
        btnVerifyEmail = view.findViewById(R.id.btnVerifyEmail);
        etOldPassword = view.findViewById(R.id.etOldPassword);
        etNewPassword = view.findViewById(R.id.etNewPassword);
        etConfirmPassword = view.findViewById(R.id.etConfirmPassword);
        btnSavePassword = view.findViewById(R.id.btnSavePassword);
        tvForgetPassword = view.findViewById(R.id.tvForgetPassword);
        updateAccountModel = new UpdateAccountModel();

        tvForgetPassword.setPaintFlags(tvForgetPassword.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        tvForgetPassword.setOnClickListener(view1 -> {
            ForgetPasswordFragment fragment = new ForgetPasswordFragment();
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fv_Container,fragment)
                    .addToBackStack(null)
                    .commit();
        });

        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_activated_1, gender);
        spinnerG.setAdapter(adapter);

        spinnerG.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                value = adapterView.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Utils.showDialog(context);
        APIDao.Profile(context, new ObjectCallback<ProfileModel>() {
            @Override
            public void onData(ProfileModel profileModel) {
                Utils.dismiss();
                Log.e("profileData","profileData"+new Gson().toJson(profileModel));
                setProfileData(profileModel);
            }

            @Override
            public void onError(String msg) {
                Utils.dismiss();
                Toast.makeText(context, ""+msg, Toast.LENGTH_SHORT).show();
            }
        });

        btnSaveAccount.setOnClickListener(view1 -> {
            //&& is used because user just change its fName or surName then it can fill only that box
            if (TextUtils.isEmpty(etFistName.getText().toString()) && TextUtils.isEmpty(etSurname.getText().toString())){
                Toast.makeText(context, "Please fill at least one box ..!", Toast.LENGTH_SHORT).show();
            }else {
                updateAccountModel.first_name = etFistName.getText().toString();
                updateAccountModel.sur_name = etSurname.getText().toString();
                updateAccount(updateAccountModel);
            }
        });
        btnSaveGender.setOnClickListener(view1 -> {
            if (Objects.equals(value, "gender")){
                Toast.makeText(context, "Please select your gender", Toast.LENGTH_SHORT).show();
            }else {
                updateAccountModel.gender = value;
                updateAccount(updateAccountModel);
            }
        });
        btnVerifyEmail.setOnClickListener(view1 -> {
            if (!Patterns.EMAIL_ADDRESS.matcher(etEmail.getText().toString()).matches()){
                Toast.makeText(context, "Invalid Email Address", Toast.LENGTH_SHORT).show();
            }else {
                updateAccountModel.email = etEmail.getText().toString();
                updateAccount(updateAccountModel);
            }
        });
        btnSavePassword.setOnClickListener(view1 -> {
            if (TextUtils.isEmpty(etOldPassword.getText().toString()) || TextUtils.isEmpty(etNewPassword.getText().toString()) || TextUtils.isEmpty(etConfirmPassword.getText().toString()) ){
                Toast.makeText(context, "Please give all credentials", Toast.LENGTH_SHORT).show();
            }else if(!etNewPassword.getText().toString().equals(etConfirmPassword.getText().toString())){
                Toast.makeText(context, "New & confirm password are not same", Toast.LENGTH_SHORT).show();
            }else {
                updateAccountModel.old_password = etOldPassword.getText().toString();
                updateAccountModel.new_password = etNewPassword.getText().toString();
                updateAccountModel.confirm_password = etConfirmPassword.getText().toString();
                updateAccount(updateAccountModel);
                etOldPassword.setText("");
                etConfirmPassword.setText("");
            }
        });
        return view;
    }

    private void setProfileData(ProfileModel profileModel) {
        etFistName.setText(profileModel.user.first_name);
        etSurname.setText(profileModel.user.sur_name);
        etEmail.setText(profileModel.user.email);

        String compareValue = profileModel.user.gender;
        if (compareValue != null){
            int spinnerPosition = adapter.getPosition(compareValue);
            spinnerG.setSelection(spinnerPosition);
        }
    }

    private void updateAccount(UpdateAccountModel updateAccountModel) {
        Utils.showDialog(context);
        APIDao.UpdateAccount(context, updateAccountModel, new ObjectCallback<UpdateAccountModel>() {
            @Override
            public void onData(UpdateAccountModel updateAccountModel) {
                Utils.dismiss();
                Log.e("updateAccount","updateAccount"+new Gson().toJson(updateAccountModel));
                Toast.makeText(context, ""+updateAccountModel.message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(String msg) {
                Utils.dismiss();
                Toast.makeText(context, ""+msg, Toast.LENGTH_SHORT).show();
            }
        });
    }
}