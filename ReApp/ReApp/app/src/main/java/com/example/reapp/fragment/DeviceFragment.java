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
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reapp.adapter.DeviceAdapter;
import com.example.reapp.adapter.SimpleItemTouchHelperCallback;
import com.example.reapp.customView.CustomDialog;
import com.example.reapp.myinterface.IMEICallback;
import com.example.reapp.myinterface.ItemTouchAdapterHelper;
import com.example.reapp.pojo.DeviceInfo;
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

public class DeviceFragment extends Fragment implements IMEICallback {

    private OkHttpUtils okHttpUtils;
    private List<DeviceInfo> deviceInfoList = new ArrayList<>();
    private DeviceAdapter deviceAdapter;
    private Context context;
    private RecyclerView rv_device;
    private FloatingActionButton btn_add;
    private String departmentCode;
    private String userCode;
    private SmartRefreshLayout device_refresh;

    private Handler handler = new Handler(Looper.myLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            String str = (String) msg.obj;
            switch (msg.what){
                case StaticVariable.SHOW_MSG:
                    deviceAdapter.notifyDataSetChanged();
                    break;
                case StaticVariable.TOAST_MSG:
                    ToastUtil.showMsg(str);
                    break;
            }
        }
    };

    public static DeviceFragment getInstance(String departmentCode, String userCode) {
        DeviceFragment deviceFragment = new DeviceFragment();
        Bundle bundle = new Bundle();
        bundle.putString("departmentCode", departmentCode);
        bundle.putString("userCode", userCode);
        deviceFragment.setArguments(bundle);
        return deviceFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.device_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        departmentCode = bundle.getString("departmentCode");
        userCode = bundle.getString("userCode");
        okHttpUtils = OkHttpUtils.getInstance();
        deviceAdapter = new DeviceAdapter(context,this, deviceInfoList,position -> {
            CustomDialog customDialog = new CustomDialog(getContext(), (ed1, ed2) -> {
                addTask(userCode, deviceInfoList.get(position).getDeviceIMEI());
            });
            customDialog.setTitle("创建任务");
            customDialog.show();
        });
        rv_device = view.findViewById(R.id.rv_device);
        btn_add = view.findViewById(R.id.btn_add);
        device_refresh = view.findViewById(R.id.device_refresh);
        device_refresh.setRefreshHeader(new ClassicsHeader(context));
        rv_device.setLayoutManager(new LinearLayoutManager(context));
        rv_device.setAdapter(deviceAdapter);
        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(deviceAdapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(rv_device);
        //listener can't cover
        btn_add.setOnClickListener(v -> {
            CustomDialog customDialog = new CustomDialog(getContext(),(ed1, ed2) -> {
                if (!TextUtils.isEmpty(ed1) && !TextUtils.isEmpty(ed2)) {
                    addDevice(departmentCode, userCode ,ed1, ed2);
                } else {
                    ToastUtil.showMsg("输入信息不能为空请重新输入.");
                }
            });
            customDialog.setTitle("添加设备").setHint1("设备名称").setHint2("设备IMEI").setShowEd1(true).setShowEd2(true);
            customDialog.show();
        });
        device_refresh.setOnRefreshListener(refreshLayout -> {
           if (deviceAdapter != null) {
               deviceAdapter.removeAll();
               deviceRequest(departmentCode);
           }
        });
        deviceRequest(departmentCode);
    }

    private void deviceRequest(String departmentCode) {
        okHttpUtils.postRequest(FastJsonUtil.toJson(GeneralUtil.generalJsonArray("departmentCode", departmentCode,
                "deviceName", "",
                "deviceIMEI", "",
                "index", "1",
                "limit", "10")), HttpParam.DEVICE_SEARCH, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String json = response.body().string().trim();
                Log.i("deviceRequest", json);
                ResultInfo<DeviceInfo> resultInfo = FastJsonUtil.toBean(json, ResultInfo.class);
                List<DeviceInfo> dv = FastJsonUtil.toList(resultInfo.getInfos().toString(), DeviceInfo.class);
                if (dv != null){
                    deviceInfoList.addAll(dv);
                    handler.sendEmptyMessage(StaticVariable.SHOW_MSG);
                    device_refresh.finishRefresh();
                }
            }
        });
    }

    private void addTask(String processCode, String deviceIMEI) {
        okHttpUtils.postRequest(FastJsonUtil.toJson(GeneralUtil.generalJsonArray("proposerCode", processCode, "deviceIMEI", deviceIMEI)), HttpParam.TASK_ADD, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String json = response.body().string();
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

    private void addDevice(String departmentCode, String userCode, String deviceName, String deviceIMEI){
        okHttpUtils.postRequest(FastJsonUtil.toJson(GeneralUtil.generalJsonArray(
                "departmentCode",departmentCode,
                "userCode",userCode,
                "deviceName",deviceName,
                "deviceIMEI",deviceIMEI)), HttpParam.DEVICE_ADD, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String json = response.body().string();
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

    private void deleteDevice(String departmentCode, String userCode, String deviceIMEI){
        okHttpUtils.postRequest(FastJsonUtil.toJson(GeneralUtil.generalJsonArray(
                "departmentCode",departmentCode,
                "userCode",userCode,
                "deviceIMEI",deviceIMEI)), HttpParam.DEVICE_DELETE, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String json = response.body().string();
                Log.i("deleteDevice", json);
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

    @Override
    public void callIMEI(String imei) {
        deleteDevice(departmentCode, userCode, imei);
    }
}
