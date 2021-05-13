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

import com.example.reapp.adapter.LogAdapter;
import com.example.reapp.pojo.LogInfo;
import com.example.reapp.pojo.ResultInfo;
import com.example.reapp.utils.FastJsonUtil;
import com.example.reapp.utils.GeneralUtil;
import com.example.reapp.utils.HttpParam;
import com.example.reapp.utils.OkHttpUtils;
import com.example.reapp.utils.StaticVariable;
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

public class LogFragment extends Fragment {

    private OkHttpUtils okHttpUtils;
    private RecyclerView rv_log;
    private LogAdapter logAdapter;
    private List<LogInfo> logInfoList = new ArrayList<>();
    private Context context;
    private SmartRefreshLayout log_refresh;

    private Handler handler = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case StaticVariable.SHOW_MSG:
                    logAdapter.notifyDataSetChanged();
                    break;
            }
        }
    };

    public static LogFragment getInstance(String userCode) {
        LogFragment logFragment = new LogFragment();
        Bundle bundle = new Bundle();
        bundle.putString("userCode", userCode);
        logFragment.setArguments(bundle);
        return logFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return LayoutInflater.from(container.getContext()).inflate(R.layout.log_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        okHttpUtils = OkHttpUtils.getInstance();
        Bundle bundle = getArguments();
        String userCode = bundle.getString("userCode");
        logAdapter = new LogAdapter(context, logInfoList);
        rv_log = view.findViewById(R.id.rv_log);
        log_refresh = view.findViewById(R.id.log_refresh);
        log_refresh.setRefreshHeader(new ClassicsHeader(context));
        log_refresh.setOnRefreshListener(refreshLayout -> {
            if (logAdapter != null) {
                logRequest(userCode);
                logAdapter.removeAll();
            }
        });
        rv_log.setLayoutManager(new LinearLayoutManager(context));
        rv_log.setAdapter(logAdapter);
        logRequest(userCode);
    }

    private void logRequest(String userCode) {
        okHttpUtils.postRequest(FastJsonUtil.toJson(GeneralUtil.generalJsonArray(
                "index", "1",
                "limit", "100",
                "proposeCode", userCode)), HttpParam.DEVICE_SEARCH_RECORD, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String json = response.body().string().trim();
                Log.i("Log", json);
                ResultInfo<LogInfo> resultInfo = FastJsonUtil.toBean(json, ResultInfo.class);
                List<LogInfo> li = FastJsonUtil.toList(resultInfo.getInfos().toString(), LogInfo.class);
                if (li != null) {
                    logInfoList.addAll(li);
                    handler.sendEmptyMessage(StaticVariable.SHOW_MSG);
                    log_refresh.finishRefresh();
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
