package com.example.reapp.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;

/**
 * 作者    yunyang
 * 时间    2018/11/12 14:39
 * 文件    Demo
 * 描述   处理图片类——高斯模糊
 */
public class RenderScriptBitmapBlur {

    private RenderScript renderScript;

    public RenderScriptBitmapBlur(@NonNull Context context) {
        // 实例化一个新的渲染脚本
        this.renderScript = RenderScript.create(context);
    }

    public Bitmap getBlurBitmap(@IntRange(from = 1, to = 25) int radius, Bitmap original) {
        // 使用Renderscript和in/out位图创建分配(in/out)
        Allocation input = Allocation.createFromBitmap(renderScript, original);
        Allocation output = Allocation.createTyped(renderScript, input.getType());
        // 使用Renderscript创建一个固有的模糊脚本
        ScriptIntrinsicBlur scriptIntrinsicBlur = ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript));
        // 设置模糊半径:0 < radius <= 25
        scriptIntrinsicBlur.setRadius(radius);
        // 执行渲染脚本
        scriptIntrinsicBlur.setInput(input);
        scriptIntrinsicBlur.forEach(output);
        // 将out分配创建的最终位图复制到original
        output.copyTo(original);
        return original;
    }

}