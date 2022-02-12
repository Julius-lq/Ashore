package cn.lgzbs.ashore;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import android.graphics.Paint;
import android.view.View;
import android.preference.PreferenceManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;


public class LoginActivity extends AppCompatActivity {

    private EditText username_edittext, password_edittext;

    private TextView register;

    private String username;
    private String password;
    private CheckBox rememberPassword;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private boolean flag = false;
    private ImageView mail,wechat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }//隐藏顶部导航栏
        initView();//绑定控件并初始化
        //初始化BmobSDK,这里的appkey在bmob官网上可以查到，我设置为了你的bmob账号的Ashore应用的appkey
        Bmob.initialize(this, "d61c053c562cbe127f72111b5fdab05c");

        //对注册键进行监听
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //跳转到注册界面
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
        mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "由于Bmob后端云短信条数有限，暂时关闭该功能", Toast.LENGTH_SHORT).show();
            }
        });
        wechat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,WechatActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initView() {
        username_edittext = findViewById(R.id.editText);
        password_edittext = findViewById(R.id.editText2);
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        rememberPassword = (CheckBox) findViewById(R.id.rememberPassword);
        register = (TextView) findViewById(R.id.register);
        register.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);//设置注册键文本下划线
        boolean isRember = pref.getBoolean("remember_password", false);
        if (isRember) {
            //将账号密码都设置到输入框中
            String userN = pref.getString("username", "");
            String pwd = pref.getString("password", "");
            username_edittext.setText(userN);
            password_edittext.setText(pwd);
            rememberPassword.setChecked(true);
//            login();
        }
        mail = (ImageView) findViewById(R.id.mail);
        wechat = (ImageView) findViewById(R.id.wechat);
    }

    public void login(View view) {
        String username = username_edittext.getText().toString().trim();
        String password = password_edittext.getText().toString().trim();

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.login(new SaveListener<User>() {
            @Override
            public void done(User bmobUser, BmobException e) {
                if (e == null) {{
                    editor = pref.edit();
                    if (rememberPassword.isChecked()) {
                            editor.putBoolean("remember_password", true);
                            editor.putString("username", username);
                            editor.putString("password", password);
                        } else {
                            editor.clear();
                        }
                        editor.apply();
                    }
                    User user = BmobUser.getCurrentUser(User.class);
                    Toast.makeText(LoginActivity.this, "登录成功：" + user.getUsername(), Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(LoginActivity.this, "登录失败：" + e.getMessage(), Toast.LENGTH_LONG).show();
                    Log.e(String.valueOf(MainActivity.class), e.getMessage());
                }
            }
        });
    }

}
