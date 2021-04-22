package com.example.reapp.utils;

import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * create by ZyiqueZhou on 08/22/2019
 * consult zhy OkHttpUtils
 */
public class OkHttpUtils {

    private volatile static OkHttpUtils mInstance;

    private OkHttpClient mokHttpClient;

    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    private OkHttpUtils(OkHttpClient okHttpClient) {
        if (okHttpClient == null) {
            this.mokHttpClient = new OkHttpClient();
        } else {
            this.mokHttpClient = okHttpClient;
        }
    }

    private static OkHttpUtils initClient(OkHttpClient okHttpClient) {
        if (mInstance == null) {
            synchronized (OkHttpUtils.class) {
                if (mInstance == null) {
                    mInstance = new OkHttpUtils(okHttpClient);
                }
            }
        }
        return mInstance;
    }

    public static OkHttpUtils getInstance() {
        return initClient(null);
    }

    /**
     * Get Request
     *
     * @param getUrl   request url(get)
     * @param callback callback interface by okHttp
     */
    public void getRequest(String getUrl, final Callback callback) {
        new Thread(() -> {
            Request request = new Request.Builder()
                    .url(getUrl)
                    .build();
            mokHttpClient.newCall(request).enqueue(callback);
        }).start();
    }

    /**
     * POST Request
     *
     * @param data     afferent json data we can be afferent Map List pojo...
     * @param postUrl  request url(post)
     * @param callback callback interface by okHttp
     */
    public void postRequest(String data, String postUrl, final Callback callback) {
        new Thread(() -> {
            RequestBody body = RequestBody.create(JSON, data);
            Request request = new Request.Builder()
                    .url(postUrl)
                    .post(body)
                    .build();
            mokHttpClient.newCall(request).enqueue(callback);
        }).start();
    }
}
