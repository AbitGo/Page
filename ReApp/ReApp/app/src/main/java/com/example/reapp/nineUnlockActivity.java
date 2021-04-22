package com.example.reapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.lock.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class nineUnlockActivity extends AppCompatActivity {
    private TextView[] tv_codes = new TextView[4];
    private int[] tv_code_ids = {R.id.tv_code1,R.id.tv_code2,R.id.tv_code3,R.id.tv_code4};
    private Button btnC,btnDel;
    private View[] vx=new View[4];
    private int[] vx_ids = {R.id.v1,R.id.v2,R.id.v3,R.id.v4};
    private int[] btn_ids = {R.id.btn_0,R.id.btn_1,R.id.btn_2,
            R.id.btn_3,R.id.btn_4,R.id.btn_5,
            R.id.btn_6,R.id.btn_7,R.id.btn_8,R.id.btn_9};

    private EditText et_code;
    private List<String> codes = new ArrayList<>();
    private InputMethodManager imm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nineunlock_page);
        initView();
    }

    public void creatToast(String text) {
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
    }

    public void initView() {
        et_code = this.findViewById(R.id.et_code);

        for(int i = 0;i<vx.length;i++){
            vx[i]=this.findViewById(vx_ids[i]);
            tv_codes[i] = this.findViewById(tv_code_ids[i]);
        }

        for(int i= 0;i<btn_ids.length;i++){
            final int finalI = i;
            this.findViewById(btn_ids[i]).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addCodesNum(""+ finalI);
                }
            });
        }

        btnC = this.findViewById(R.id.btn_clean);
        btnDel = this.findViewById(R.id.btn_del);
        btnC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cleanCodes();
            }
        });
        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delCodes();
            }
        });
    }
    public void delCodes(){
        if(codes.size()==0){
            return;
        }else{
            codes.remove(codes.size()-1);
            showCode();
        }

    }
    public void cleanCodes(){
        codes.clear();
        showCode();
    }
    public void addCodesNum(String num){
        if(codes.size()==3){
            codes.add(num);
            creatToast(codes.toString());
            Intent intent = new Intent();
            intent.setClass(nineUnlockActivity.this, echartTest.class);
            startActivity(intent);
        }else if(codes.size()==2||codes.size()==1||codes.size()==0){
            codes.add(num);
        }
        showCode();
    }
    public void showCode() {
        for(int i = 0;i<tv_codes.length;i++){
            if(i<codes.size()) {
                tv_codes[i].setText(codes.get(i));
            }else{
                tv_codes[i].setText("");
            }
        }
        setColor();//设置高亮颜色
    }

    private void setColor() {
        int color_default = Color.parseColor("#999999");
        int color_focus = Color.parseColor("#3F8EED");
        for(int i =0;i<vx.length;i++){
            vx[i].setBackgroundColor(color_default);
        }
        if(codes.size()==0){
            vx[0].setBackgroundColor(color_focus);
        }else{
            vx[codes.size()-1].setBackgroundColor(color_focus);
        }
    }
}