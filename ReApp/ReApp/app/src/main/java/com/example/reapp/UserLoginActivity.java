package com.example.reapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.fastjson.JSONObject;
import com.example.reapp.pojo.LoginResultData;
import com.example.reapp.pojo.ResultInfo;
import com.example.reapp.pojo.UserLoginInfo;
import com.example.reapp.utils.FastJsonUtil;
import com.example.reapp.utils.HttpParam;
import com.example.reapp.utils.OkHttpUtils;
import com.lock.myapplication.R;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class UserLoginActivity extends AppCompatActivity {
    private TextView userLoginInfo;
    private TextView userPassword;
    private TextView userLogin;
    private TextView userRegister;
    private String okUserCode;
    private OkHttpUtils okHttpUtils ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.uselogin_page);
        userLoginInfo = this.findViewById(R.id.userLoginInfo);
        userPassword = this.findViewById(R.id.userPassword);
        userLogin = this.findViewById(R.id.userLogin);
        userRegister = this.findViewById(R.id.userRegister);
        okHttpUtils = OkHttpUtils.getInstance();
        userRegister.setOnClickListener(view -> {
            Intent intent = new Intent();
            intent.setClass(UserLoginActivity.this, UserRegisterActivity.class);
            startActivity(intent);
        });
        userLogin.setOnClickListener(view -> {
            String loginInfo =  userLoginInfo.getText().toString();
            String loginPassword = userPassword.getText().toString();
            if(loginInfo.equals("")){
                Toast.makeText(UserLoginActivity.this, R.string.loginNull,Toast.LENGTH_LONG).show();
            }else if(loginPassword.equals("")){
                Toast.makeText(UserLoginActivity.this, R.string.passwordNull,Toast.LENGTH_LONG).show();
            }else{
                UserLoginInfo userLoginInfo = new UserLoginInfo();
                userLoginInfo.setUserLoginInfo(loginInfo);
                userLoginInfo.setUserPassword(loginPassword);
                getHttpLink(HttpParam.userLogin,JSONObject.toJSONString(userLoginInfo));
            }
        });
    }
    public  void getHttpLink(String URI,String paramJson){
        if (okHttpUtils == null) return;
        okHttpUtils.postRequest(paramJson,URI, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                toastMsg(1,"");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                System.out.println(json);
                ResultInfo resultInfo = FastJsonUtil.toBean(json, ResultInfo.class);
                if (resultInfo.getInfo() != null) {
                    LoginResultData loginResultData = FastJsonUtil.toBean(resultInfo.getInfo().toString(), LoginResultData.class);
                    toastMsg(2,resultInfo.getExecuteMsg());
                    if(resultInfo.getExecuteStatus().equals("0")){

                    }else {
                        disposeLogin(loginResultData);
                    }
                }
            }
        });
    }

    public void disposeLogin(LoginResultData data){
        runOnUiThread(() -> {
            //需要对数据进行存储
            Intent intent = new Intent();
            intent.putExtra("userCode",data.getUserCode());
            intent.setClass(UserLoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }

    public void toastMsg(int id,String msg){
        runOnUiThread(() -> {
            if(id == 1){//网络连接失败
                Toast.makeText(UserLoginActivity.this, R.string.networkfail,Toast.LENGTH_LONG).show();
            }else if(id==2){
                Toast.makeText(UserLoginActivity.this, msg,Toast.LENGTH_LONG).show();
            }

        });
    }
}