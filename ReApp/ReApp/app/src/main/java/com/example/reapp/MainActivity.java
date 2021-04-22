package com.example.reapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.example.reapp.fragment.DepartmentFragment;
import com.example.reapp.fragment.LogFragment;
import com.example.reapp.fragment.TaskFragment;
import com.example.reapp.pojo.DepartmentInner;
import com.example.reapp.utils.HttpParam;
import com.example.reapp.utils.OkHttpUtils;
import com.example.reapp.utils.ToastUtil;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.lock.myapplication.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static android.widget.Toast.LENGTH_SHORT;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    private BottomNavigationView navigationView;
    private String okUserCode;
    private List<Fragment> fragmentList = new ArrayList<>();
    private int lastIndex;
    private static final int DEPARTMENT = 0;
    private static final int LOG = 1;
    private static final int TASK = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        okUserCode =intent.getStringExtra("userCode");
        Log.d("okUserCode",okUserCode);
        navigationView = findViewById(R.id.nav_navigation);
        navigationView.setOnNavigationItemSelectedListener(this);
        fragmentList.add(DepartmentFragment.getInstance(okUserCode));
        fragmentList.add(LogFragment.getInstance(okUserCode));
        fragmentList.add(TaskFragment.getInstance(okUserCode));
        switchFragment(DEPARTMENT);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.nav_department:
                switchFragment(DEPARTMENT);
                return true;
            case R.id.nav_task:
                switchFragment(TASK);
                return true;
            case R.id.nav_record:
                switchFragment(LOG);
                return true;
        }
        return false;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    //不用加入回退栈中直接hide
    public void switchFragment(int showFragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        //要显示的Fragment
        Fragment currentFragment = fragmentList.get(showFragment);
        //要隐藏的Fragment
        Fragment hideFragment = fragmentList.get(lastIndex);
        //将当前的展示Fragment赋值给隐藏的索引下次执行此方法就可隐藏
        lastIndex = showFragment;
        transaction.hide(hideFragment);
        if (!currentFragment.isAdded()) {
            getSupportFragmentManager().beginTransaction().remove(currentFragment).commit();
            transaction.add(R.id.framelayout, currentFragment);
        }
        transaction.show(currentFragment);
        transaction.commitAllowingStateLoss();
    }

}


