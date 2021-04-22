package com.example.reapp.utils;

import java.util.HashMap;
import java.util.Map;

public class GeneralUtil {
    //must be using k-v k-v k-v this regular
    public static Map<String, String> generalJsonArray(String... args) {
        int length = args.length;
        if (length % 2 != 0) {
            return null;
        }
        Map<String, String> json = new HashMap<>();
        for (int i = 0; i < length; i += 2) {
            json.put(args[i], args[i + 1]);
        }
        return json;
    }
}
