package com.practice.a45shoppingcart;

import android.app.Application;
import android.graphics.Bitmap;
import android.util.Log;

import java.util.HashMap;

public class MainApplication extends Application {
    private final static String TAG = "MainApplication";
    //由于使用单例模式，声明一个静态实例，供调用
    private static MainApplication mApp;
    //声明一个公共的信息映射对象，可以作为全局变量的使用
    public HashMap<String,String> mInfoMap = new HashMap<String, String>(); //关于哈希表 https://blog.csdn.net/woshimaxiao1/article/details/83661464

    //利用单例模式方法获取当前应用的实例
    public static MainApplication getInstance(){
        return mApp;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //打开应用时对静态的应用实例赋值
        mApp = this;
        Log.d(TAG,"已打开");
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        Log.d(TAG,"已关闭");
    }

    //声明一个公共的图标映射对象。
    public HashMap<Long, Bitmap> mIconMap = new HashMap<Long, Bitmap>();
}
