package com.example.reapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.Spinner;

import com.example.reapp.utils.FastJsonUtil;
import com.example.reapp.utils.GeneralUtil;
import com.example.reapp.utils.HttpParam;
import com.example.reapp.utils.OkHttpUtils;
import com.example.reapp.utils.StaticVariable;
import com.example.reapp.utils.ToastUtil;
import com.lock.myapplication.R;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class AdminTestActivity extends AppCompatActivity {

    private Button btn_open, btn_lock;
    private OkHttpUtils okHttpUtils;

    private Handler handler = new Handler(Looper.myLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            String str = (String) msg.obj;
            switch (msg.what) {
                case StaticVariable.TOAST_MSG:
                    ToastUtil.showMsg(str);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_test);
        okHttpUtils = OkHttpUtils.getInstance();
        btn_open = findViewById(R.id.btn_open);
        btn_lock = findViewById(R.id.btn_lock);
        btn_open.setOnClickListener(v -> {
            adminLockTest("1");
        });
        btn_lock.setOnClickListener(v -> {
            adminLockTest("0");
        });
    }

    public static void actionStart(Context context){
        Intent intent = new Intent(context, AdminTestActivity.class);
        context.startActivity(intent);
    }

    private void adminLockTest(String deviceData) {
        okHttpUtils.postRequest(FastJsonUtil.toJson(GeneralUtil.generalJsonArray("deviceCode", "testDevice2", "deviceData", deviceData)), HttpParam.DEVICE_TEST, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String json = response.body().string();
                Message message = new Message();
                message.what = StaticVariable.TOAST_MSG;
                message.obj = json;
                handler.sendMessage(message);
            }
        });
    }
}