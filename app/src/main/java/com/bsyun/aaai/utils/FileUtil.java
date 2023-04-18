package com.bsyun.aaai.utils;

import android.app.Activity;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

public class FileUtil {
    private static final String TAG = "FlleUtil";
    /**
     * 保存json到本地
     * @param mActivity
     * @param filename
     * @param content
     */
    public static File dir = new File(Environment.getExternalStorageDirectory() + "/.Imageloader/json/");

    public static void saveToSDCard(Activity mActivity, String filename, String content) {
        String en = Environment.getExternalStorageState();
        //获取SDCard状态,如果SDCard插入了手机且为非写保护状态
        if (en.equals(Environment.MEDIA_MOUNTED)) {
            try {
                dir.mkdirs(); //create folders where write files
                File file = new File(dir, filename);

                OutputStream out = new FileOutputStream(file);
                out.write(content.getBytes());
                out.close();
                Log.e(TAG, "保存成功: ");
            } catch (Exception e) {
                e.printStackTrace();
                Log.e(TAG, "保存失败: ");
            }
        } else {
            //提示用户SDCard不存在或者为写保护状态
            Log.e(TAG, "SDCard不存在或者为写保护状态: ");
        }
    }

    /**
     * 从本地读取json
     */
    public static String readTextFile(String filePath) {
        StringBuilder sb = new StringBuilder();
        try {
            File file = new File(dir + "/" + filePath);
            InputStream in = null;
            in = new FileInputStream(file);
            int tempbyte;
            while ((tempbyte = in.read()) != -1) {
                sb.append((char) tempbyte);
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    /**
     * 返回学生名单 以String 数组形式
     *
     * @return
     */
    public static String[] getTextContent() {
        try {
            //获取输入流
            InputStream inputStream = AppUtils.getApp().getAssets().open("aaa.txt");//这里的名字是你的txt 文本文件名称
            //获取学生名单
            String str = getString(inputStream);
            //字符分割 每行为一个学生
            String[] arr = str.split("#");
            return arr;
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return null;
    }

    /**
     * 获取文件内容
     *
     * @param inputStream
     * @return
     */
    public static String getString(InputStream inputStream) {
        InputStreamReader inputStreamReader = null;
        try {
            inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        BufferedReader reader = new BufferedReader(inputStreamReader);
        //创建字符缓冲流
        StringBuffer sb = new StringBuffer("");
        String line;
        try {
            //读取每行学生
            while ((line = reader.readLine()) != null) {
                //添加到字符缓冲流中
                sb.append(line);
                //一条一行
                sb.append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //返回学生名单字符串
        return sb.toString();
    }
}
