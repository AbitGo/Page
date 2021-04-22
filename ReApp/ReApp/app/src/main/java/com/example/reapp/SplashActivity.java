package com.example.reapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.reapp.utils.RenderScriptBitmapBlur;
import com.lock.myapplication.R;

public class SplashActivity extends Activity {
    private RenderScriptBitmapBlur mRenderScriptGaussianBlur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);    //设置全屏
        setContentView(R.layout.welcome_apge);

        ImageView imageView = this.findViewById(R.id.imageView);
        Bitmap bitmap;
        int i = (int)System.currentTimeMillis()/1000%4;
        switch (i) {
            case 0:
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.default_welcomebg_0);
                break;
            case 1:
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.default_welcomebg_1);
                break;
            case 2:
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.default_welcomebg_2);
                break;
            case 3:
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.default_welcomebg_3);
                break;
            default:
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.default_welcomebg_0);
                break;

        }
        mRenderScriptGaussianBlur = new RenderScriptBitmapBlur(this);
        imageView.setImageBitmap(mRenderScriptGaussianBlur.getBlurBitmap(25, bitmap));

        new Handler().postDelayed(new Runnable() {
            public void run() {
                Intent intent = new Intent();
                intent.setClass(SplashActivity.this, UserLoginActivity.class);
                startActivity(intent);
                finish();
            }
        }, 1000 * 1);
    }
}