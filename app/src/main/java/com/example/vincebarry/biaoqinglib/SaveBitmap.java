package com.example.vincebarry.biaoqinglib;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.Toast;

import com.squareup.picasso.LruCache;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by VinceBarry on 2016/6/3.
 */
public class SaveBitmap {
    private LruCache lruCache;
    private Context context;
    public SaveBitmap(Context context, LruCache lruCache){
        this.lruCache = lruCache;
        this.context = context;
    }
    public void saveToSD(int position){
        String fileName = "biaoqing"+position +".jpg";
        File appDir = new File(Environment.getExternalStorageDirectory(),"Tencent/MicroMsg/WeiXin");
        if(!appDir.exists()){
            Toast.makeText(context,"您未安装微信`.`！",Toast.LENGTH_SHORT).show();
            appDir.mkdirs();
        }
        File file = new File(appDir,fileName);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            lruCache.get(position+"").compress(Bitmap.CompressFormat.JPEG,100,fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
            Toast.makeText(context,"弹药已经装到库中！",Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void sendTo(int position){
        String fileName = "biaoqing"+position +".jpg";
        File appDir = new File(Environment.getExternalStorageDirectory(),"Tencent/MicroMsg/WeiXin");
        if(!appDir.exists()){
            Toast.makeText(context,"您未安装微信`.`！",Toast.LENGTH_SHORT).show();
            appDir.mkdirs();
        }
        File file = new File(appDir,fileName);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            lruCache.get(position+"").compress(Bitmap.CompressFormat.JPEG,100,fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
            if(file.exists()){
                Intent intent = new Intent("android.intent.action.VIEW");
                intent.addCategory("android.intent.category.DEFAULT");
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Uri uri = Uri.fromFile(file);
                intent.setDataAndType(uri,"image/*");
                context.startActivity(intent);
            }else{
                Toast.makeText(context,"出了点问题。。。",Toast.LENGTH_SHORT).show();
            }

            //Toast.makeText(context,"弹药已经装到库中！",Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
