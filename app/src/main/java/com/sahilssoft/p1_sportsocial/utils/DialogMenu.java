package com.sahilssoft.p1_sportsocial.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import com.sahilssoft.p1_sportsocial.R;

public class DialogMenu{

    static AlertDialog dialog;
    static LinearLayout ll_share, ll_playNext,ll_playLast,ll_show,ll_save;

    public static void Show(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_menu,null,false);
        dialog = new android.app.AlertDialog.Builder(context).setView(view).create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.R.color.transparent));
        dialog.show();
        dialog.getWindow().setLayout(330,500);
        dialog.getWindow().setGravity(Gravity.END);

        ll_share = view.findViewById(R.id.ll_share);
        ll_playNext = view.findViewById(R.id.ll_playNext);
        ll_playLast = view.findViewById(R.id.ll_playLast);
        ll_show = view.findViewById(R.id.ll_show);
        ll_save = view.findViewById(R.id.ll_save);
    }


}
