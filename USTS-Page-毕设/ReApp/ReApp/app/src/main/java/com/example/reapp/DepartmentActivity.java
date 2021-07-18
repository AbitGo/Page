package com.example.reapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.reapp.adapter.PageAdapter;
import com.example.reapp.fragment.DepartmentInfoFragment;
import com.example.reapp.fragment.DeviceFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.lock.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class DepartmentActivity extends AppCompatActivity {

    private ViewPager2 viewPager2;
    private TabLayout tabLayout;
    private PageAdapter pageAdapter;
    private List<Fragment> fragmentList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department);
        Bundle bundle = getIntent().getExtras();
        String departmentCode = bundle.getString("departmentCode");
        String userCode = bundle.getString("userCode");
        pageAdapter = new PageAdapter(this, fragmentList);
        fragmentList.add(DepartmentInfoFragment.getInstance(departmentCode,userCode));
        fragmentList.add(DeviceFragment.getInstance(departmentCode, userCode));
        viewPager2 = findViewById(R.id.page);
        tabLayout = findViewById(R.id.tab_layout);
        viewPager2.setAdapter(pageAdapter);
        new TabLayoutMediator(tabLayout, viewPager2, (tab, position) -> {
            switch (position){
                case 0:
                    tab.setText(getString(R.string.DepartmentFragment));
                    break;
                case 1:
                    tab.setText(getString(R.string.DeviceFragment));
                    break;
            }
        }).attach();
    }


    public static void actionStart(Context context, String departmentCode,String userCode){
        Intent intent = new Intent(context, DepartmentActivity.class);
        intent.putExtra("departmentCode", departmentCode);
        intent.putExtra("userCode", userCode);
        context.startActivity(intent);
    }
}