package com.example.reapp.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reapp.adapter.AllOfDepartmentAdapter;
import com.example.reapp.pojo.DepartmentInfo;
import com.example.reapp.pojo.ResultInfo;
import com.example.reapp.utils.FastJsonUtil;
import com.example.reapp.utils.GeneralUtil;
import com.example.reapp.utils.HttpParam;
import com.example.reapp.utils.OkHttpUtils;
import com.example.reapp.utils.StaticVariable;
import com.example.reapp.utils.ToastUtil;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.lock.myapplication.R;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class DepartmentInfoFragment extends Fragment {

    private OkHttpUtils okHttpUtils;
    private RecyclerView rv_department;
    private AllOfDepartmentAdapter allOfDepartmentAdapter;
    private List<DepartmentInfo> departmentInfoList = new ArrayList<>();
    private Context context;
    private FloatingActionButton btn_add;
    private SmartRefreshLayout member_refresh;
    private Handler handler = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            String str = (String) msg.obj;
            switch (msg.what) {
                case StaticVariable.SHOW_MSG:
                    allOfDepartmentAdapter.notifyDataSetChanged();
                    break;
                case StaticVariable.TOAST_MSG:
                    ToastUtil.showMsg(str);
                    break;
            }
        }
    };

    public static DepartmentInfoFragment getInstance(String departmentCode, String userCode) {
        DepartmentInfoFragment departmentInfoFragment = new DepartmentInfoFragment();
        Bundle bundle = new Bundle();
        bundle.putString("departmentCode", departmentCode);
        bundle.putString("userCode", userCode);
        departmentInfoFragment.setArguments(bundle);
        return departmentInfoFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.department_info, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        String departmentCode = bundle.getString("departmentCode");
        String userCode = bundle.getString("userCode");
        rv_department = view.findViewById(R.id.rv_department);
        member_refresh = view.findViewById(R.id.member_refresh);
        member_refresh.setRefreshHeader(new ClassicsHeader(context));
        //btn_add = view.findViewById(R.id.btn_add);
        okHttpUtils = OkHttpUtils.getInstance();
        rv_department.setLayoutManager(new LinearLayoutManager(context));
        allOfDepartmentAdapter = new AllOfDepartmentAdapter(context, departmentInfoList);
        rv_department.setAdapter(allOfDepartmentAdapter);
//        btn_add.setOnClickListener(v -> {
//            //感觉这个api有点问题 详解接口文档 8
//            addDepartmentMember(userCode, departmentCode, "2");
//        });
        member_refresh.setOnRefreshListener(refreshLayout -> {
            if (allOfDepartmentAdapter != null) {
                allOfDepartmentAdapter.removeAll();
                departmentRequest("", departmentCode, "");
            }
        });
        departmentRequest("", departmentCode, "");
    }


    private void departmentRequest(String userInfo, String departmentCode, String userRole) {
        okHttpUtils.postRequest(FastJsonUtil.toJson(GeneralUtil.generalJsonArray("userInfo", userInfo, "departmentCode", departmentCode, "userRole", userRole)), HttpParam.DEPARTMENT_SEARCH, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String json = response.body().string().trim();
                ResultInfo<DepartmentInfo> resultInfo = FastJsonUtil.toBean(json, ResultInfo.class);
                List<DepartmentInfo> deptList = FastJsonUtil.toList(resultInfo.getInfos().toString(), DepartmentInfo.class);
                if (deptList != null) {
                    departmentInfoList.addAll(deptList);
                    handler.sendEmptyMessage(StaticVariable.SHOW_MSG);
                    member_refresh.finishRefresh();
                }
            }
        });
    }

    private void addDepartmentMember(String userCode, String departmentCode, String userRole) {
        okHttpUtils.postRequest(FastJsonUtil.toJson(GeneralUtil.generalJsonArray("userCode", userCode, "departmentCode", departmentCode, "userRole", userRole)), HttpParam.PERSON_Authorization, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String json = response.body().string();
                Log.i("addDepartmentMember", json);
                ResultInfo resultInfo = FastJsonUtil.toBean(json, ResultInfo.class);
                if (resultInfo != null) {
                    Message message = new Message();
                    message.obj = resultInfo.getExecuteMsg();
                    message.what = StaticVariable.TOAST_MSG;
                    handler.sendMessage(message);
                }
            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }
}
