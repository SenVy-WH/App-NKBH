package com.practice.a18990724;

import com.practice.a18990724.utils.*;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class BbsActivity extends AppCompatActivity implements View.OnClickListener,View.OnLongClickListener{

    private TextView tv_control;
    private TextView tv_bbs;

    private String[] Chatstr = {"18990724 汪昊 测试用例1", "18990724 汪昊 测试用例2",
            "18990724 汪昊 测试用例3", "18990724 汪昊 测试用例4", "18990724 汪昊 测试用例5"
            ,"18990724 汪昊 测试用例6"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bbs);

        tv_control = findViewById(R.id.tv_control);
        tv_bbs = findViewById(R.id.tv_bbs);
        tv_control.setOnClickListener(this);
        tv_bbs.setOnClickListener(this);
        tv_control.setOnLongClickListener(this);
        tv_bbs.setOnLongClickListener(this);

        tv_bbs.setMovementMethod(new ScrollingMovementMethod());
    }


    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.tv_control || v.getId()==R.id.tv_bbs){
            int random =(int)(Math.random() * 10) % 6;
            String newMsg = String.format("%s\n%s %s",tv_bbs.getText().toString(), DateUtils.getNowTime(),Chatstr[random]);
            tv_bbs.setText(newMsg);
        }
    }

    @Override
    public boolean onLongClick(View v) {
        if (v.getId() == R.id.tv_control || v.getId() == R.id.tv_bbs) {
            tv_bbs.setText("");
        }

        return false;
    }
}
