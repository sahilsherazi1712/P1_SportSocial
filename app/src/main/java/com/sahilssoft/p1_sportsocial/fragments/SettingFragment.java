package com.sahilssoft.p1_sportsocial.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.sahilssoft.p1_sportsocial.Methods.APIDao;
import com.sahilssoft.p1_sportsocial.R;
import com.sahilssoft.p1_sportsocial.activities.ChooseAuthActivity;
import com.sahilssoft.p1_sportsocial.callback.ObjectCallback;
import com.sahilssoft.p1_sportsocial.models.LogoutModel;
import com.sahilssoft.p1_sportsocial.utils.SharedPrefClass;
import com.sahilssoft.p1_sportsocial.utils.Utils;

public class SettingFragment extends Fragment {
    TextView tvAccount, tvPurchaseHistory, tvHelp, tvContact, tvAbout, tvLogout;
    Context context;

    public SettingFragment() {
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
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        context = getActivity();
        tvAccount = view.findViewById(R.id.tvAccount);
        tvPurchaseHistory = view.findViewById(R.id.tvPurchaseHistory);
        tvHelp = view.findViewById(R.id.tvHelp);
        tvContact = view.findViewById(R.id.tvContact);
        tvAbout = view.findViewById(R.id.tvAbout);
        tvLogout = view.findViewById(R.id.tvLogout);

        tvAccount.setOnClickListener(view1 -> {
            replaceFragment(new AccountFragment());
        });
        tvPurchaseHistory.setOnClickListener(view1 -> {

        });
        tvHelp.setOnClickListener(view1 -> {
            replaceFragment(new HelpFragment());
        });
        tvContact.setOnClickListener(view1 -> {
            replaceFragment(new ContactUsFragment());
        });
        tvAbout.setOnClickListener(view1 -> {
            replaceFragment(new AboutFragment());
        });

        tvLogout.setOnClickListener(view1 -> {
            new AlertDialog.Builder(context)
                    .setTitle("Logout")
                    .setMessage("Are you sure to Logout from Social Sport ...!")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Utils.showDialog(context);

                            APIDao.LogoutAPI(context, new ObjectCallback<LogoutModel>() {
                                @Override
                                public void onData(LogoutModel logoutModel) {
                                    Utils.dismiss();

                                    Log.e("logoutData", "LogoutData" + new Gson().toJson(logoutModel));
                                    SharedPrefClass.getInstance(context).clearPref();
                                    Intent intent = new Intent(context, ChooseAuthActivity.class);
//                                    intent.putExtra("finish",true);
//                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK
//                                            | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                    getActivity().finishAffinity();
                                }

                                @Override
                                public void onError(String msg) {
                                    Utils.dismiss();
                                    Toast.makeText(context, ""+msg, Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    })
                    .setIcon(R.drawable.alert).show();
        });

        return view;
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fv_Container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}