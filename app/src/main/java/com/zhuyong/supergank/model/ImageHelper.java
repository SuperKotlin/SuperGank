package com.zhuyong.supergank.model;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhuyong on 2017/5/13.
 */
public class ImageHelper {

    public static List<String> saveImage(String[] urls) {
        List<String> images = new ArrayList<>();
        for (int i = 0; i < urls.length; i++) {
            String image = urls[i];
            images.add(image);
        }
        return images;
    }

    public static List<String> saveImages(String[] urls) {
        return saveImage(urls);
    }

}
