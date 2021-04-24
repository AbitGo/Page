package com.example.reapp.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reapp.AdminTestActivity;
import com.example.reapp.MyApplication;
import com.example.reapp.adapter.PopAdapter;
import com.example.reapp.adapter.TaskAdapter;
import com.example.reapp.customView.CustomDialog;
import com.example.reapp.myinterface.DialogCallBack;
import com.example.reapp.pojo.ResultInfo;
import com.example.reapp.pojo.TaskInfo;
import com.example.reapp.utils.FastJsonUtil;
import com.example.reapp.utils.GeneralUtil;
import com.example.reapp.utils.HttpParam;
import com.example.reapp.utils.OkHttpUtils;
import com.example.reapp.utils.StaticVariable;
import com.example.reapp.utils.ToastUtil;
import com.lock.myapplication.R;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class TaskFragment extends Fragment {

    private OkHttpUtils okHttpUtils;

    private RecyclerView rv_task;

    private CheckBox checkBox;

    private TextView tv_title;

    private TaskAdapter taskAdapter;

    private List<TaskInfo> taskInfos = new ArrayList<>();

    private boolean isAdmin = false;

    private List<String> statusList;

    private TextView tv_pop;

    private Context context;

    private Handler handler = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            String str = (String) msg.obj;
            switch (msg.what) {
                case StaticVariable.SHOW_MSG:
                    taskAdapter.notifyDataSetChanged();
                    break;
                case StaticVariable.TOAST_MSG:
                    ToastUtil.showMsg(str);
                    break;
            }
        }
    };

    public static TaskFragment getInstance(String userCode) {
        TaskFragment taskFragment = new TaskFragment();
        Bundle bundle = new Bundle();
        bundle.putString("userCode", userCode);
        taskFragment.setArguments(bundle);
        return taskFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return LayoutInflater.from(container.getContext()).inflate(R.layout.task_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        okHttpUtils = OkHttpUtils.getInstance();
        initStatusList();
        String userCode = getArguments().getString("userCode");
        rv_task = view.findViewById(R.id.rv_task);
        checkBox = view.findViewById(R.id.cb);
        tv_title = view.findViewById(R.id.tv_title);
        tv_pop = view.findViewById(R.id.tv_pop);
        taskAdapter = new TaskAdapter(taskInfos, position -> {
            //任务状态
            int taskStatus = taskInfos.get(position).getTaskStatus();
            //非管理员操作
            if (!isAdmin) {
                if(taskStatus==0){
                    ToastUtil.showMsg("请开启管理员模式");
                }else if(taskStatus==1){
                    //TO DO
                    //下发代码
                }else{
                    ToastUtil.showMsg("无需操作");
                }
                return;
            }else{
                //任务开始审核
                if(taskStatus==0){
                    CustomDialog taskVerifyDialog = new CustomDialog(context, (ed1, ed2) -> {
                        taskVerify(taskInfos.get(position).getTaskCode(), "1");
                    });
                    taskVerifyDialog.setTitle("请审核");
                    taskVerifyDialog.show();
                }else{
                    ToastUtil.showMsg("无需操作");
                }
            }
        });
        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            isAdmin = isChecked;
        });
        tv_title.setOnClickListener(v -> {
            AdminTestActivity.actionStart(context);
        });
        tv_pop.setOnClickListener(v -> {
            View popView = LayoutInflater.from(context).inflate(R.layout.pop_view, null);
            RecyclerView recyclerView = popView.findViewById(R.id.rv_select);
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            PopupWindow popupWindow = new PopupWindow(popView, tv_pop.getWidth(), ViewGroup.LayoutParams.WRAP_CONTENT, true);
            popupWindow.showAsDropDown(tv_pop);
            recyclerView.setAdapter(new PopAdapter(statusList, pos -> {
                taskRequest(userCode, pos+"");
                taskAdapter.removeAll();
                popupWindow.dismiss();
            }));
        });
        rv_task.setLayoutManager(new LinearLayoutManager(MyApplication.context));
        rv_task.setAdapter(taskAdapter);
        taskRequest(userCode, "0");
    }


    private void taskRequest(String userCode, String taskStatus) {
        okHttpUtils.postRequest(FastJsonUtil.toJson(GeneralUtil.generalJsonArray("proposerCode", userCode,
                "taskStatus", taskStatus,
                "index", "1",
                "limit", "100",
                "isUser", "0")), HttpParam.SEARCH_DEVICE_TASK, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String json = response.body().string();
                ResultInfo<TaskInfo> resultInfo = FastJsonUtil.toBean(json, ResultInfo.class);
                List<TaskInfo> ts = FastJsonUtil.toList(resultInfo.getInfos().toString(), TaskInfo.class);
                if (ts != null) {
                    taskInfos.addAll(ts);
                    handler.sendEmptyMessage(StaticVariable.SHOW_MSG);
                }
                System.out.println("TaskFragment: " + ts);
            }
        });
    }

    private void taskVerify(String taskCode, String taskStatus) {
        okHttpUtils.postRequest(FastJsonUtil.toJson(GeneralUtil.generalJsonArray("taskCode", taskCode,
                "taskStatus", taskStatus)), HttpParam.TASK_VERIFY, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String json = response.body().string().trim();
                ResultInfo resultInfo = FastJsonUtil.toBean(json, ResultInfo.class);
                Message message = new Message();
                message.what = StaticVariable.TOAST_MSG;
                message.obj = resultInfo.getExecuteMsg();
                handler.sendMessage(message);
                System.out.println("taskVerify:" + json);
            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    private void initStatusList() {
        if (statusList == null) {
            statusList = new ArrayList<>();
        }
        statusList.add("未审核");
        statusList.add("已通过");
        statusList.add("已拒绝");
        statusList.add("已过时");
        statusList.add("已执行");
    }
}
