package com.sahilssoft.p1_sportsocial.utils;

import android.app.ProgressDialog;
import android.content.Context;

public class Utils {
    public static ProgressDialog dialog;
    public static void showDialog(Context context){
        dialog = new ProgressDialog(context);
        dialog.setMessage("Loading ...");
        dialog.show();
    }

    public static void dismiss(){
        if(dialog != null){
            dialog.dismiss();
        }
    }
}
