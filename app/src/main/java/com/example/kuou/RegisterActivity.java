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

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    private Button register_btn;
    private EditText register_name_edittext;
    private EditText register_tell_edittext;
    private EditText register_password_edittext;
    String user,pwd,tel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        initRegisterBtn();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.register_btn:
                /*register();*/
                //startActivity(new Intent(this,LoginActivity.class));
                initString();
                if(TextUtils.isEmpty(user)){
                    Toast.makeText(RegisterActivity.this, "请输入昵称", Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(tel)){
                    Toast.makeText(RegisterActivity.this, "请输入电话号码", Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(pwd)){
                    Toast.makeText(RegisterActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
                }else if(isExistUserName(register_name_edittext.getText().toString().trim())){
                    Toast.makeText(RegisterActivity.this, "此账户名已经存在", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                    /**
                     * 保存账号和密码到SharedPreferences中
                     */
                    saveRegisterInfo(tel, pwd);
                    //注册成功后把账号传递到LoginActivity.java中
                    // 返回值到loginActivity显示
                    Intent data = new Intent();
                    data.putExtra("userName", user);
                    setResult(RESULT_OK, data);
                    //RESULT_OK为Activity系统常量，状态码为-1，
                    // 表示此页面下的内容操作成功将data返回到上一页面，如果是用back返回过去的则不存在用setResult传递data值
                    RegisterActivity.this.finish();
                }
                break;
            default:
                break;
        }
    }

    private void initRegisterBtn(){
        register_btn = findViewById(R.id.register_btn);
        register_btn.setOnClickListener(this);
        register_name_edittext = findViewById(R.id.register_name_edittext);
        register_tell_edittext = findViewById(R.id.register_tell_edittext);
        register_password_edittext = findViewById(R.id.register_password_edittext);
    }

    public void initString() {
        user = register_name_edittext.getText().toString().trim();
        tel = register_tell_edittext.getText().toString().trim();
        pwd = register_password_edittext.getText().toString().trim();
    }
    /**
     * 从SharedPreferences中读取输入的用户名，判断SharedPreferences中是否有此用户名
     */
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
    /**
     * 加密后用SharedPreferences数据类保存账户和密码
     */
    private void saveRegisterInfo(String Tel, String psw){
        String md5Psw = MD5.md5Password(psw); //把密码用MD5加密
        SharedPreferences sharedPreferences=getSharedPreferences("loginInfo", MODE_PRIVATE);

        //获取编辑器
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(Tel, md5Psw);
        //提交修改 editor.commit();
        editor.apply();
    }
}
