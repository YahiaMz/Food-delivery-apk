package com.aplication.dilevery_app;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.aplication.dilevery_app.Fragments.Activity_phone_number;

public class Custom_Dialog {

    String message;
    LayoutInflater mInflater;
    private Activity mActivity;
    private AlertDialog mAlertDialog;

    public Custom_Dialog(Activity mActivity , String msg) {
        this.mActivity = mActivity;
        mInflater = this.mActivity.getLayoutInflater();
        this.message = msg;
    }

    public  void show ( ) {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this.mActivity);


        mBuilder.setView(mInflater.inflate(R.layout.my_custom_dialog , null));
        mBuilder.setCancelable(false);
        this.mAlertDialog = mBuilder.create();
        this.mAlertDialog.getWindow().setBackgroundDrawableResource(R.drawable.favourite_item_bg);
        this.mAlertDialog.setMessage(message);

        this.mAlertDialog.show();


    }

    public  void dismiss( ) {
        this.mAlertDialog.dismiss();
    }

}
