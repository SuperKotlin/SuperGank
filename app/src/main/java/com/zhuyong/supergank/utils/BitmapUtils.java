package com.zhuyong.supergank.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;

import com.zhuyong.supergank.R;

/**cao
 * 高斯模糊工具类
 * Created by loongggdroid on 2016/5/12.
 */
public class BitmapUtils {
    //获取指定Bitmap的指定模糊程度的Bitmap 上下文 ,Bitmap图片 ,图片缩放比例 ,模糊程度 0~25
    public static Bitmap fastblur(Context context, Bitmap sentBitmap, float scale, float radius) {
        // 计算图片缩小后的长宽
        int width = Math.round(sentBitmap.getWidth() * scale);
        int height = Math.round(sentBitmap.getHeight() * scale);

        // 将缩小后的图片做为预渲染的图片。
        Bitmap inputBitmap = Bitmap.createScaledBitmap(sentBitmap, width, height, false);
        // 创建一张渲染后的输出图片。
        Bitmap outputBitmap = Bitmap.createBitmap(inputBitmap);

        // 创建RenderScript内核对象
        RenderScript rs = RenderScript.create(context);
        // 创建一个模糊效果的RenderScript的工具对象
        ScriptIntrinsicBlur blurScript = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));

        // 由于RenderScript并没有使用VM来分配内存,所以需要使用Allocation类来创建和分配内存空间。
        // 创建Allocation对象的时候其实内存是空的,需要使用copyTo()将数据填充进去。
        Allocation tmpIn = Allocation.createFromBitmap(rs, inputBitmap);
        Allocation tmpOut = Allocation.createFromBitmap(rs, outputBitmap);

        // 设置渲染的模糊程度, 25f是最大模糊度
        blurScript.setRadius(radius);
        // 设置blurScript对象的输入内存
        blurScript.setInput(tmpIn);
        // 将输出数据保存到输出内存中
        blurScript.forEach(tmpOut);

        // 将数据填充到Allocation中
        tmpOut.copyTo(outputBitmap);

        return outputBitmap;
    }

    public static int getBirdsResource(int times){
        switch (times % 16){
            case 1:
                return R.mipmap.blue01;
            case 2:
                return R.mipmap.blue02;
            case 3:
                return R.mipmap.blue03;
            case 4:
                return R.mipmap.blue04;
            case 5:
                return R.mipmap.blue05;
            case 6:
                return R.mipmap.blue06;
            case 7:
                return R.mipmap.blue07;
            case 8:
                return R.mipmap.blue08;
            case 9:
                return R.mipmap.blue09;
            case 10:
                return R.mipmap.blue10;
            case 11:
                return R.mipmap.blue11;
            case 12:
                return R.mipmap.blue12;
            case 13:
                return R.mipmap.blue13;
            case 14:
                return R.mipmap.blue14;
            case 15:
                return R.mipmap.blue15;
            case 16:
                return R.mipmap.blue16;
            default:
                return R.mipmap.blue01;
        }
    }
}
