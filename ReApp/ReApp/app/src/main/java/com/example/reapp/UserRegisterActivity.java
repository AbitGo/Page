package com.example.reapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.fastjson.JSONObject;
import com.example.reapp.pojo.ResultInfo;
import com.example.reapp.pojo.UserRegisterInfo;
import com.example.reapp.utils.HttpParam;
import com.example.reapp.utils.OkHttpUtils;
import com.lock.myapplication.R;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class UserRegisterActivity extends AppCompatActivity {
    private EditText userName;
    private EditText userPassword;
    private EditText userLoginName;
    private EditText userEmail;
    private Button submit;
    OkHttpUtils okHttpUtils ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userregister_page);
        userName = this.findViewById(R.id.userName);
        userPassword = this.findViewById(R.id.userPassword);
        userLoginName = this.findViewById(R.id.userLoginName);
        userEmail = this.findViewById(R.id.userEmail);
        submit = this.findViewById(R.id.userRegister);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName_t = userName.getText().toString();
                String userPassword_t = userPassword.getText().toString();
                String userLoginName_t = userLoginName.getText().toString();
                String userEmail_t = userEmail.getText().toString();

                if(userName_t.equals("")|| userPassword_t.equals("")|| userLoginName_t.equals("")|| userEmail_t.equals("")){
                    Toast.makeText(UserRegisterActivity.this, R.string.loginNull,Toast.LENGTH_LONG).show();
                }
                else{
                    UserRegisterInfo userRegisterInfo = new UserRegisterInfo(userName_t,userPassword_t,userLoginName_t,userEmail_t);
                    getHttpLink(HttpParam.userRegister,JSONObject.toJSONString(userRegisterInfo));
                }
            }
        });


    }
    public  void getHttpLink(String URI,String paramJson){
        //获得初始化之后的Https连接
        okHttpUtils = OkHttpUtils.getInstance();
        okHttpUtils.postRequest(paramJson,URI, new Callback() {
            //无操作
            @Override
            public void onFailure(Call call, IOException e) {
                toastMsg(1,"");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                System.out.println(json);
                ResultInfo resultInfo = JSONObject.parseObject(json, ResultInfo.class);
                toastMsg(2,resultInfo.getExecuteMsg());
                if(resultInfo.getExecuteStatus().equals("0")){
                    //注册失败
                }else {
                    //注册成功则需要返回
                    Intent intent = new Intent();
                    intent.setClass(UserRegisterActivity.this, UserLoginActivity.class);
                    startActivity(intent);
                    //销毁这个页面
                    finish();
                }
            }
        });
    }
    public void toastMsg(int id,String msg){
        runOnUiThread(() -> {
            if(id == 1){
                Toast.makeText(UserRegisterActivity.this, R.string.networkfail,Toast.LENGTH_LONG).show();
            }else if(id==2){
                Toast.makeText(UserRegisterActivity.this, msg,Toast.LENGTH_LONG).show();
            }

        });
    }
}
