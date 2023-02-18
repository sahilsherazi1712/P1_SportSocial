package com.sahilssoft.p1_sportsocial.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sahilssoft.p1_sportsocial.Methods.APIDao;
import com.sahilssoft.p1_sportsocial.R;
import com.sahilssoft.p1_sportsocial.callback.ObjectCallback;
import com.sahilssoft.p1_sportsocial.models.UpdateAccountModel;
import com.sahilssoft.p1_sportsocial.utils.Utils;

public class ForgetPasswordFragment extends Fragment {
    Context context;
    EditText etForgetEmail;
    Button btnForgetEmail;
    public ForgetPasswordFragment() {
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
        View view = inflater.inflate(R.layout.fragment_forget_password, container, false);
        context = getActivity();
        etForgetEmail = view.findViewById(R.id.etForgetEmail);
        btnForgetEmail = view.findViewById(R.id.btnForgetEmail);

        btnForgetEmail.setOnClickListener(view1 -> {
            Utils.showDialog(context);
            UpdateAccountModel updateAccountModel = new UpdateAccountModel();
            updateAccountModel.email = etForgetEmail.getText().toString();
            APIDao.ResetPassword(context, updateAccountModel, new ObjectCallback<UpdateAccountModel>() {
                @Override
                public void onData(UpdateAccountModel updateAccountModel) {
                    Utils.dismiss();
                    Toast.makeText(context, ""+updateAccountModel.message, Toast.LENGTH_SHORT).show();
                    etForgetEmail.setText("");
                }

                @Override
                public void onError(String msg) {
                    Utils.dismiss();
                    Toast.makeText(context, ""+msg, Toast.LENGTH_SHORT).show();
                }
            });
        });
        return view;
    }
}