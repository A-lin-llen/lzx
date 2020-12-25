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

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {


    private Button register;
    private Button login;
    private EditText username;
    private EditText password;
    private Button forgive_pwd_login;

    String user,pwd,mdPwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        intiView();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.register:
                /*
                * 登录按钮左下角有注册字样，没有账号的可以跳转到注册界面
                */
                startActivity(new Intent(this,RegisterActivity.class));
                break;
            case R.id.login:
                login();

               // Toast.makeText(this,"登录成功",Toast.LENGTH_LONG).show();
                startActivity(new Intent(this,Weathre_Activity.class));
                break;
            case R.id.forgive_pwd_login:
                startActivity(new Intent(this,ForgivePwdActivity.class));
                break;
            default:
                break;
        }
    }
    private void intiView(){
        register = findViewById(R.id.register);
        register.setOnClickListener(this);
        login = findViewById(R.id.login);
        login.setOnClickListener(this);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        forgive_pwd_login=findViewById(R.id.forgive_pwd_login);
        forgive_pwd_login.setOnClickListener(this);
    }

    public void login(){
        //开始登录，获取用户名和密码 getText().toString().trim();
        user= username.getText().toString().trim();
        pwd = password.getText().toString().trim();
        String md5Psw = MD5.md5Password(pwd);
        mdPwd = readPwd(user);
        //使用字符工具判断是否为空
        if (TextUtils.isEmpty(user)) {
            Toast.makeText(LoginActivity.this, "请输入用户名", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(pwd)) {
            Toast.makeText(LoginActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
            // 判断，输入的密码加密后，是否与保存在SharedPreferences中一致
        } else if (md5Psw.equals(mdPwd)) {
            Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
            //保存登录状态，在界面保存登录的用户名 定义个方法 saveLoginStatus boolean 状态 , userName 用户名;
            saveLoginStatus(true, user);
            //登录成功后关闭此页面进入主页
            Intent data = new Intent();
            data.putExtra("isLogin", true);
            //RESULT_OK为Activity系统常量，状态码为-1
            // 表示此页面下的内容操作成功将data返回到上一页面，如果是用back返回过去的则不存在用setResult传递data值
            setResult(RESULT_OK, data);
            //销毁登录界面
            LoginActivity.this.finish();
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
        } else if ((mdPwd != null && !TextUtils.isEmpty(mdPwd) && !md5Psw.equals(mdPwd))) {
            Toast.makeText(LoginActivity.this, "输入的用户名和密码不一致", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(LoginActivity.this, "此用户名不存在", Toast.LENGTH_SHORT).show();
        }
    }

    private String readPwd(String username){
        //"loginInfo",mode_private; MODE_PRIVATE表示可以继续写入
        SharedPreferences sp=getSharedPreferences("loginInfo", MODE_PRIVATE);
        return sp.getString(username , "");
    }
    /**
     *保存登录状态和登录用户名到SharedPreferences中
     */
    private void saveLoginStatus(boolean status, String userName){
        //loginInfo表示文件名  SharedPreferences sp=getSharedPreferences("loginInfo", MODE_PRIVATE);
        SharedPreferences sp=getSharedPreferences("loginInfo", MODE_PRIVATE);
        //获取编辑器
        SharedPreferences.Editor editor=sp.edit();
        //存入boolean类型的登录状态
        editor.putBoolean("isLogin", status);
        //存入登录状态时的用户名
        editor.putString("loginUserName", userName);
        //提交修改
        editor.apply();
    }
}
