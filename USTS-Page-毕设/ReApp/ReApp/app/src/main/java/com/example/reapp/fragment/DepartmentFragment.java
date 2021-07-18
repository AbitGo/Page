package com.example.reapp.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reapp.DepartmentActivity;
import com.example.reapp.MyApplication;
import com.example.reapp.adapter.DepartmentAdapter;
import com.example.reapp.customView.CustomDialog;
import com.example.reapp.myinterface.DialogCallBack;
import com.example.reapp.pojo.DepartmentInner;
import com.example.reapp.pojo.ResultInfo;
import com.example.reapp.utils.FastJsonUtil;
import com.example.reapp.utils.GeneralUtil;
import com.example.reapp.utils.HttpParam;
import com.example.reapp.utils.OkHttpUtils;
import com.example.reapp.utils.StaticVariable;
import com.example.reapp.utils.ToastUtil;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.lock.myapplication.R;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class DepartmentFragment extends Fragment {

    private RecyclerView rv_department;

    private SmartRefreshLayout department_refresh;

    private OkHttpUtils okHttpUtils;

    private ResultInfo<DepartmentInner> departmentInnerResultInfo;

    private List<DepartmentInner> departmentInnerList = new ArrayList<>();

    private DepartmentAdapter departmentAdapter;

    private FloatingActionButton btn_add;

    private String userCode;

    private Context context;

    private Handler handler = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            String str = (String) msg.obj;
            switch (msg.what) {
                case StaticVariable.SHOW_MSG:
                    departmentAdapter.notifyDataSetChanged();
                    break;
                case StaticVariable.TOAST_MSG:
                    ToastUtil.showMsg(str);
                    break;
            }
        }
    };

    public static DepartmentFragment getInstance(String userCode) {
        DepartmentFragment departmentFragment = new DepartmentFragment();
        Bundle bundle = new Bundle();
        bundle.putString("userCode", userCode);
        departmentFragment.setArguments(bundle);
        return departmentFragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return LayoutInflater.from(container.getContext()).inflate(R.layout.department_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        userCode = bundle.getString("userCode");
        rv_department = view.findViewById(R.id.rv_department);
        btn_add = view.findViewById(R.id.btn_add);
        department_refresh = view.findViewById(R.id.department_refresh);
        department_refresh.setRefreshHeader(new ClassicsHeader(context));
        rv_department.setLayoutManager(new LinearLayoutManager(MyApplication.context));
        departmentAdapter = new DepartmentAdapter(departmentInnerList, position -> {
            DepartmentActivity.actionStart(view.getContext(), departmentInnerList.get(position).getDepartmentCode(), userCode);
        }, pos -> {
            CustomDialog customDialog = new CustomDialog(context, (ed1, ed2) -> {
                if (!TextUtils.isEmpty(ed1) && !TextUtils.isEmpty(ed2)) {
                    updateDepInfo(ed1, ed2, departmentInnerList.get(pos).getDepartmentCode());
                } else {
                    ToastUtil.showMsg("输入有误,重新输入");
                }
            });
            customDialog.setTitle("修改部门信息").setHint1("部门名称")
                    .setHint2("部门描述").setShowEd1(true).setShowEd2(true);
            customDialog.show();
        });
        department_refresh.setOnRefreshListener(refreshLayout -> {
            if (departmentAdapter != null) {
                departmentAdapter.removeAll();
                departmentRequest(userCode);
            }
        });
        CustomDialog addDepRequestDialog = new CustomDialog(getContext(), (ed1, ed2) -> {
            if (!TextUtils.isEmpty(ed1)) {
                addDepRequest(ed1, getArguments().getString("userCode"));
            } else {
                ToastUtil.showMsg("输入信息不能为空请重新输入.");
            }
        });
        addDepRequestDialog.setTitle("创建部门").setHint1("部门名称").setShowEd1(true);
        btn_add.setOnClickListener(v -> {
            addDepRequestDialog.show();
        });
        rv_department.setAdapter(departmentAdapter);
        okHttpUtils = OkHttpUtils.getInstance();
        departmentRequest(userCode);
    }

    private void departmentRequest(String userCode) {
        okHttpUtils.getRequest(HttpParam.userDepartment_get + userCode, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {e.printStackTrace();}
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String json = response.body().string();
                departmentInnerResultInfo = FastJsonUtil.toBean(json, ResultInfo.class);
                List<DepartmentInner> ds = FastJsonUtil.toList(departmentInnerResultInfo.getInfos()
                                .toString(), DepartmentInner.class);
                if (ds != null) {
                    departmentInnerList.addAll(ds);
                    handler.sendEmptyMessage(StaticVariable.SHOW_MSG);
                    department_refresh.finishRefresh();
                }
            }
        });
    }

    private void addDepRequest(String name, String root) {
        okHttpUtils.postRequest(FastJsonUtil.toJson(GeneralUtil.generalJsonArray("departmentName", name, "departmentRoot", root)), HttpParam.DEPARTMENT_CREATE, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String json = response.body().string();
                Log.i("addDepRequest", json);
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

    private void updateDepInfo(String departmentName, String departmentInfo, String departmentCode) {
        okHttpUtils.postRequest(FastJsonUtil.toJson(GeneralUtil.generalJsonArray("departmentCode", departmentCode,
                "departmentInfo", departmentInfo,
                "departmentName", departmentName)), HttpParam.DEPARTMENT_UPDATE, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String json = response.body().string().trim();
                Log.i("updateDepInfo", json);
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
