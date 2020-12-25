package com.example.kuou;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ForgivePwdActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText forgive_tel_one;
    private EditText forgive_pwd_one;
    private EditText forgive_pwd_two;
    private Button forgive_btn;
    String tel,pwd,newpwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgive_pwd);
        initView();
    }

    public void initView()
    {
        forgive_tel_one = findViewById(R.id.forgive_tel_one);
        forgive_pwd_one = findViewById(R.id.forgive_pwd_one);
        forgive_pwd_two = findViewById(R.id.forgive_pwd_two);
        forgive_btn = findViewById(R.id.forgive_btn);
        forgive_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.forgive_btn:
                intiThieView();
                if (TextUtils.isEmpty(tel)){
                    Toast.makeText(ForgivePwdActivity.this, "请输入账户名", Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(pwd)){
                    Toast.makeText(ForgivePwdActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
                }else if(!pwd.equals(newpwd)){
                    Toast.makeText(ForgivePwdActivity.this, "输入两次的密码不一样", Toast.LENGTH_SHORT).show();
                }else if(!isExistUserName(tel)){
                    Toast.makeText(ForgivePwdActivity.this, "此账户名不存在", Toast.LENGTH_SHORT).show();
                }else {
                    savePwdInfo(tel,pwd);
                    Toast.makeText(ForgivePwdActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(ForgivePwdActivity.this,LoginActivity.class));
                }
                break;
            default:
                break;
        }
    }

    public void intiThieView(){
        tel=forgive_tel_one.getText().toString().trim();
        pwd=forgive_pwd_one.getText().toString().trim();
        newpwd=forgive_pwd_two.getText().toString().trim();
    }
    private boolean isExistUserName(String userName){
        boolean has_userName=false;
        SharedPreferences sp=getSharedPreferences("loginInfo", MODE_PRIVATE);
        String mdPwd=sp.getString(userName, "");//传入用户名获取密码
        //若mdPwd不为空则说明数据中由此用户信息，则返回true
        if(!TextUtils.isEmpty(mdPwd)) {
            has_userName=true;
        }
        return has_userName;
    }

    private void savePwdInfo(String Tel, String psw){
        String md5Psw = MD5.md5Password(psw); //把密码用MD5加密
        SharedPreferences sharedPreferences=getSharedPreferences("loginInfo", MODE_PRIVATE);

        //获取编辑器
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(Tel, md5Psw);
        //提交修改 editor.commit();
        editor.apply();
    }
}