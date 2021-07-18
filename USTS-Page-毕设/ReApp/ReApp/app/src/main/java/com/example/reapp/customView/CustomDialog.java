package com.example.reapp.customView;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.reapp.myinterface.DialogCallBack;
import com.example.reapp.myinterface.ItemClickListener;
import com.lock.myapplication.R;

import java.util.List;

public class CustomDialog extends Dialog {

    private String title;
    private String hint1;
    private String hint2;
    private boolean isShowEd1 = false;
    private boolean isShowEd2 = false;
    private DialogCallBack dialogCallBack;

    private TextView tv_title;
    private TextView tv_cancel;
    private TextView tv_enter;
    private EditText ed_1;
    private EditText ed_2;
    private Context context;

    public CustomDialog(@NonNull Context context, DialogCallBack dialogCallBack) {
        super(context, R.style.CustomDiaLog);
        this.context = context;
        this.dialogCallBack = dialogCallBack;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_dia);
        setDialogSize(0.8);
        tv_title = findViewById(R.id.tv_title);
        tv_cancel = findViewById(R.id.tv_cancel);
        tv_enter = findViewById(R.id.tv_enter);
        ed_1 = findViewById(R.id.ed1);
        ed_2 = findViewById(R.id.ed2);
        if (isShowEd1) {
            ed_1.setVisibility(View.VISIBLE);
        } else {
            ed_1.setVisibility(View.GONE);
        }
        if (isShowEd2) {
            ed_2.setVisibility(View.VISIBLE);
        } else {
            ed_2.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(title)) {
            tv_title.setText(title);
        }
        if (!TextUtils.isEmpty(hint1)) {
            ed_1.setHint(hint1);
        }
        if (!TextUtils.isEmpty(hint2)) {
            ed_2.setHint(hint2);
        }
        tv_cancel.setOnClickListener(v -> {
            dismiss();
        });
        tv_enter.setOnClickListener(v -> {
            String str1 = ed_1.getText().toString().trim();
            String str2 = ed_2.getText().toString().trim();
            dialogCallBack.call(str1, str2);
            dismiss();
        });

    }

    private void setDialogSize(double xPercent) {
        WindowManager m = getWindow().getWindowManager();
        Display d = m.getDefaultDisplay();
        WindowManager.LayoutParams p = getWindow().getAttributes();
        Point size = new Point();
        d.getSize(size);
        p.width = (int) (size.x * xPercent);
        getWindow().setAttributes(p);
    }

    public CustomDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    public CustomDialog setHint1(String hint1) {
        this.hint1 = hint1;
        return this;
    }

    public CustomDialog setHint2(String hint2) {
        this.hint2 = hint2;
        return this;
    }

    public CustomDialog setShowEd1(boolean showEd1) {
        this.isShowEd1 = showEd1;
        return this;
    }

    public CustomDialog setShowEd2(boolean showEd2) {
        this.isShowEd2 = showEd2;
        return this;
    }


}
