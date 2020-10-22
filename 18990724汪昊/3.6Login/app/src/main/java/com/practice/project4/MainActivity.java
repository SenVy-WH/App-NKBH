package com.practice.project4;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.practice.project4.bean.UserInfo;
import com.practice.project4.database.UserDBHelper;
import com.practice.project4.utils.DateUtil;
import com.practice.project4.utils.ViewUtil;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnFocusChangeListener {

    private RadioGroup rg_login;
    private RadioButton rb_password;
    private RadioButton rb_verifycode;
    private Spinner sp_idtype;
    private TextView tv_idtype;
    private TextView tv_phone;
    private TextView tv_password;
    private EditText et_phone;
    private EditText et_password;
    private Button btn_forget;
    private Button btn_login;
    private Switch sw_remember;

    private String mPassword = "1111111";
    private int mRequestCode = 0;
    private boolean isRemember = false;
    private String mVerifyCode;
    private UserDBHelper mHelper;
    private SharedPreferences mShared;
    private int mType = 2;

    private String[] typeArray = {"个人用户", "公司用户", "18990724 汪昊"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rg_login = findViewById(R.id.rg_login);
        rb_password = findViewById(R.id.rb_lg_password);
        rb_verifycode = findViewById(R.id.rb_lg_verifycode);
        sp_idtype = findViewById(R.id.sp_idtype);
        tv_idtype = findViewById(R.id.tv_idtype);
        tv_phone = findViewById(R.id.tv_phone);
        et_phone = findViewById(R.id.et_phone);
        tv_password = findViewById(R.id.tv_password);
        et_password = findViewById(R.id.et_password);
        btn_forget = findViewById(R.id.btn_forget);
        btn_login = findViewById(R.id.btn_login);
        sw_remember = findViewById(R.id.sw_remember);


        rg_login.setOnCheckedChangeListener(new RadioListener());
        sw_remember.setOnCheckedChangeListener(new CheckListener());
        et_phone.addTextChangedListener(new HideTextWatcher(et_phone));
        et_password.addTextChangedListener(new HideTextWatcher(et_password));

        btn_forget.setOnClickListener(this);
        btn_login.setOnClickListener(this);

        initTypeSpinner();

        mShared = getSharedPreferences("share_login", MODE_PRIVATE);

        et_password.setOnFocusChangeListener(this); //焦点默认

    }

    private void initTypeSpinner() {
        ArrayAdapter<String> typeAdapter = new ArrayAdapter<String>(this, R.layout.item_select, typeArray);
        typeAdapter.setDropDownViewResource(R.layout.item_dropdown);
        Spinner sp_type = findViewById(R.id.sp_idtype);
        sp_type.setPrompt("请选择用户类型");
        sp_type.setAdapter(typeAdapter);
        sp_type.setSelection(mType);
        sp_type.setOnItemSelectedListener(new TypeSelectedListener());
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        String phone = et_phone.getText().toString();
        if (v.getId() == R.id.et_password) {
            if (phone.length() > 0 && hasFocus) {
                UserInfo info = mHelper.queryByPhone(phone);
                if (info != null) {
                    et_password.setText(info.pwd);
                    mPassword = info.pwd;
                }
            }
        }
    }

    private class TypeSelectedListener implements android.widget.AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            mType = position;
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    }

    @Override
    public void onClick(View v) {
        String mphone = et_phone.getText().toString();
        if (v.getId() == R.id.btn_forget) {
            if (rb_verifycode.isChecked()) {//验证码登录
                mVerifyCode = String.format("%06d", (int) ((Math.random() * 9 + 1) * 100000));
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("获取验证码：");
                builder.setMessage("验证码为" + mVerifyCode);
                builder.setPositiveButton("确定", null);
                AlertDialog alert = builder.create();
                alert.show();
            } else { //如果不是验证码登录则跳转到忘记密码界面

            }
        } else if (v.getId() == R.id.btn_login) { // 点击了“登录”按钮
            if (mphone.length() < 11) { // 手机号码不足11位
                Toast.makeText(this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
                return;
            }
            if (rb_password.isChecked()) { // 密码方式校验
                // 根据手机号码到数据库中查询用户记录
                UserInfo info = mHelper.queryByPhone(et_phone.getText().toString());
                if (!et_password.getText().toString().equals(info.pwd)) {
                    Toast.makeText(this, "请输入正确的密码", Toast.LENGTH_SHORT).show();
                } else { // 密码校验通过
                    loginSuccess(); // 提示用户登录成功
                }
            } else if (rb_verifycode.isChecked()) { // 验证码方式校验
                if (!et_password.getText().toString().equals(mVerifyCode)) {
                    Toast.makeText(this, "请输入正确的验证码", Toast.LENGTH_SHORT).show();
                } else { // 验证码校验通过
                    loginSuccess(); // 提示用户登录成功
                }
            }
        }
    }

    private void loginSuccess() {
        // 如果勾选了“记住密码”
        if (isRemember) {
            //把手机号码和密码都保存到共享参数中
            SharedPreferences.Editor editor = mShared.edit(); // 获得编辑器的对象
            editor.putString("phone", et_phone.getText().toString()); // 添加名叫phone的手机号码
            editor.putString("password", et_password.getText().toString()); // 添加名叫password的密码
            editor.commit(); // 提交编辑器中的修改

            //把手机号码和密码保存为数据库的用户表记录
            // 创建一个用户信息实体类
            UserInfo info = new UserInfo();
            info.phone = et_phone.getText().toString();
            info.pwd = et_password.getText().toString();
            info.update_time = DateUtil.getNowDateTime("yyyy-MM-dd HH:mm:ss");
            // 往用户数据库添加登录成功的用户信息（包含手机号码、密码、登录时间）
            mHelper.insert(info);
        }

        String desc = String.format("您的手机号码是%s，类型是%s。登录成功，点击“确定”按钮返回上个页面",
                et_phone.getText().toString(), typeArray[mType]);
        // 弹出提醒对话框，提示用户登录成功
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("登录成功");
        builder.setMessage(desc);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.setNegativeButton("取消", null);
        AlertDialog alert = builder.create();
        alert.show();
    }

    public class RadioListener implements RadioGroup.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.rb_lg_password) {
                tv_password.setText("登录密码");
                et_password.setHint("请输入密码");
                btn_forget.setText("忘记密码");
                sw_remember.setVisibility(View.VISIBLE);
            } else if (checkedId == R.id.rb_lg_verifycode) {
                tv_password.setText("验证码:");
                et_password.setHint("请输入验证码");
                btn_forget.setText("获取验证码");
                sw_remember.setVisibility(View.INVISIBLE);
            }
        }
    }

    private class CheckListener implements CompoundButton.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (buttonView.getId() == R.id.sw_remember) {
                isRemember = isChecked;
            }
        }
    }

    private class HideTextWatcher implements TextWatcher {

        private EditText mView;
        private CharSequence mCstr;
        private int mMaxLength;


        public HideTextWatcher(EditText editText) {
            super();
            mView = editText;
            mMaxLength = ViewUtil.getMaxLength(editText);
        }

        // 在编辑框的输入文本变化前触发
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        // 在编辑框的输入文本变化时触发
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            mCstr = s;
        }

        // 在编辑框的输入文本变化后触发
        public void afterTextChanged(Editable s) {
            if (mCstr == null || mCstr.length() == 0)
                return;
            if (mCstr.length() == 11 && mMaxLength == 11
                    || mCstr.length() == 6 && mMaxLength == 6){
                ViewUtil.hideOneInputMethod(MainActivity.this, mView);

            }
        }
    }

    // 忘记密码修改后，从后一个页面携带参数返回当前页面时触发
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == mRequestCode && data != null) {
            // 更新密码
            mPassword = data.getStringExtra("new_password");
        }
    }

    //重置页面
    @Override
    protected void onRestart() {
        et_password.setText("");
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mHelper = UserDBHelper.getInstance(this, 2);
        mHelper.openWriteLink();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mHelper.closeLink();
    }
}
