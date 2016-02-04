package com.wenoun.library.image;

import android.content.Context;
import android.graphics.Bitmap;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * Created by jeyhoon on 16. 2. 3..
 */
public class ImageIO {
    public static void saveBitmap(final Context ctx,final String loadPath, final String savePath, final String saveImgName){
        Glide.with(ctx).load(loadPath).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                saveBitmap(resource,savePath,saveImgName);
            }
        });
//        return saveBitmap(ctx,loadPath,savePath+saveImgName);
    }
    public static void saveBitmap(Context ctx,String loadPath, String imgName){
        saveBitmap(ctx, loadPath, getImgDirPath(ctx), imgName);
    }
    public static File loadBitmap(Context ctx,String imgName){
        return loadBitmap(ctx, getImgDirPath(ctx),imgName);
    }
    public static File loadBitmap(Context ctx,String dirPath,String imgName){
        return new File(dirPath,imgName);
    }
    public static String getImgDirPath(Context ctx){
        return "/data/data/"+ctx.getPackageName()+"/img/";
    }
    private static void saveBitmap(Bitmap bitmap, String strFilePath,
                                       String filename) {

        File file = new File(strFilePath);

        // If no folders
        if (!file.exists()) {
            file.mkdirs();
            // Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
        }
        if(!strFilePath.endsWith("/")){
            strFilePath=strFilePath+"/";
        }
        File fileCacheItem = new File(strFilePath + filename);
        OutputStream out = null;




        try {

            int height=bitmap.getHeight();
            int width=bitmap.getWidth();


            fileCacheItem.createNewFile();
            out = new FileOutputStream(fileCacheItem);
//160 부분을 자신이 원하는 크기로 변경할 수 있습니다.
            bitmap = Bitmap.createScaledBitmap(bitmap, 768, height/(width/768), true);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
