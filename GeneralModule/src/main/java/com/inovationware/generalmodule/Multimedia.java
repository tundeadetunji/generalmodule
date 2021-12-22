package com.inovationware.generalmodule;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class Multimedia {
    public static void loadImage(Context context, Activity activity, ImageView imageView, int DrawableResourceInt){
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), DrawableResourceInt);
        Glide.with(activity).load(bitmap).circleCrop().into(imageView);
    }

    public static void loadImage(Context context, Activity activity, ImageView imageView, String url){
        Glide.with(activity).load(String.valueOf(url)).circleCrop().into(imageView);
    }

    public static void loadPolygonImage(Context context, Activity activity, ImageView imageView, int DrawableResourceInt){
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), DrawableResourceInt);
        Glide.with(activity).load(bitmap).into(imageView);
    }

    public static void loadPolygonImage(Context context, Activity activity, ImageView imageView, String url){
        Glide.with(activity).load(String.valueOf(url)).into(imageView);
    }

}
