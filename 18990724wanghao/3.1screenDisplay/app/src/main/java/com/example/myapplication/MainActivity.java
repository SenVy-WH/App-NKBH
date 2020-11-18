package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.myapplication.util.*;

public class MainActivity extends AppCompatActivity {

    private TextView TX_screen;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        TX_screen = findViewById(R.id.TX_screen);
        
        int dip_10 = util.dip2px(this,10L);
        TX_screen.setPadding(dip_10,dip_10,dip_10,dip_10);
        TX_screen.setBackgroundColor(0xffB4EEB4);
        TX_screen.setTextColor(0xff000000);

    }

    private void showScreenInfo(){
        int width = DisplayUtil.getScreenWidth(this);
        int height = DisplayUtil.getScreenWidth(this);
        float density = DisplayUtil.getScreenDensity(this);
        String info = String.format("当前屏幕尺寸为%d*%d，当前屏幕像素密度为%f",width,height,density);
        TX_screen.setText(info);
    }
}