package com.zhuyong.supergank.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.zhuyong.supergank.R;

/**
 * Created by zhuyong on 2016/1/29.
 */
public class ShareUtils {

    public static Intent getShareIntent(String shareText) {
        Intent textIntent = new Intent();
        textIntent.setAction(Intent.ACTION_SEND);
        textIntent.putExtra(Intent.EXTRA_TEXT, shareText);
        textIntent.setType("text/plain");
        return textIntent;
    }
    public static Intent getShareImageIntent(Uri uri) {
        Intent image = new Intent();
        image.setAction(Intent.ACTION_SEND);
        image.putExtra(Intent.EXTRA_STREAM, uri);
        image.setType("image/*");
        return image;
    }
    public static Intent getShareHtmlIntent(String htmlText) {
        Intent textIntent = new Intent();
        textIntent.setAction(Intent.ACTION_SEND);
        textIntent.putExtra(Intent.EXTRA_TEXT, "This is html");
        textIntent.putExtra(Intent.EXTRA_HTML_TEXT, htmlText);
        textIntent.setType("text/plain");
        return textIntent;
    }

    public static void shareText(Context context, String text) {
        context.startActivity(
                Intent.createChooser(getShareIntent(text),
                        context.getString(R.string.share_to)));
    }
    public static void shareHtmlText(Context context, String text) {
        context.startActivity(
                Intent.createChooser(getShareHtmlIntent(text),
                        context.getString(R.string.share_to)));
    }

    public static void shareImage(Context context, Uri uri){
        context.startActivity(
                Intent.createChooser(getShareImageIntent(uri),
                        context.getString(R.string.share_to)));
    }


}
