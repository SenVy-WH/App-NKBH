package com.practice.a18990724;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MarqueeActivity extends AppCompatActivity implements View.OnClickListener{

    private  TextView tv_cls;
    private TextView tv_marquee;
    private boolean isRun = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marquee);

        tv_marquee = findViewById(R.id.textView2);
        tv_marquee.setOnClickListener(this);
        tv_marquee.requestFocus();

        tv_cls= findViewById(R.id.textView);
        tv_cls.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.textView2 || v.getId()==R.id.textView){
            isRun=!isRun;
            if(isRun){
                v.setFocusable(true);
                v.setFocusableInTouchMode(true);
            }else{
                v.setFocusableInTouchMode(false);
                v.setFocusable(false);
            }
        }
    }
}
