package com.example.reapp.utils;

import android.widget.Toast;

import com.example.reapp.MyApplication;

public class ToastUtil {

    private static Toast mToast;

    public static void showMsg(String content) {
        if (mToast == null) {
            mToast = Toast.makeText(MyApplication.context, content, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(content);
        }
        mToast.show();
    }

}
