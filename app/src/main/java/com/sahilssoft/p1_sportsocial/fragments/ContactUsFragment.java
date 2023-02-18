package com.sahilssoft.p1_sportsocial.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.sahilssoft.p1_sportsocial.Methods.APIDao;
import com.sahilssoft.p1_sportsocial.R;
import com.sahilssoft.p1_sportsocial.callback.ObjectCallback;
import com.sahilssoft.p1_sportsocial.models.ContactUsModel;
import com.sahilssoft.p1_sportsocial.utils.Utils;

public class ContactUsFragment extends Fragment {
    ImageView imgCUBack;
    EditText etCUName, etCUEmail, etCUSubj, etCUMsg;
    Button btnCUSubmit;
    ContactUsModel contactUsModel;
    Context context;
    public ContactUsFragment() {
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
        View view = inflater.inflate(R.layout.fragment_contact_us, container, false);
        context = getActivity();
        imgCUBack = view.findViewById(R.id.imgCUBack);
        etCUName = view.findViewById(R.id.etCUName);
        etCUEmail = view.findViewById(R.id.etCUEmail);
        etCUSubj = view.findViewById(R.id.etCUSubj);
        etCUMsg = view.findViewById(R.id.etCUMsg);
        btnCUSubmit = view.findViewById(R.id.btnCUSubmit);
        contactUsModel = new ContactUsModel();

        imgCUBack.setOnClickListener(view1 -> getActivity().onBackPressed());

        btnCUSubmit.setOnClickListener(view1 -> {
            Utils.showDialog(context);
            contactUsModel.name = etCUName.getText().toString();
            contactUsModel.email = etCUEmail.getText().toString();
            contactUsModel.subject = etCUSubj.getText().toString();
            contactUsModel.message = etCUMsg.getText().toString();
            APIDao.ContactUs(context, contactUsModel, new ObjectCallback<ContactUsModel>() {
                @Override
                public void onData(ContactUsModel contactUsModel) {
                    Utils.dismiss();
                    Log.e("contactData", "contactData" + new Gson().toJson(contactUsModel));
                    Toast.makeText(context, ""+contactUsModel.message, Toast.LENGTH_SHORT).show();
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