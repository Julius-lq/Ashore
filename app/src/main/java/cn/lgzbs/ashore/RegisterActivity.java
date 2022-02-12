package cn.lgzbs.ashore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;

public class RegisterActivity extends AppCompatActivity {

    private EditText username, password, confirm, e_email, e_icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }//隐藏顶部导航栏
        username = (EditText) findViewById(R.id.editText);
        password = (EditText) findViewById(R.id.editText2);
        confirm = (EditText) findViewById(R.id.editText3);
//        e_email = findViewById(R.id.editText8);
//        e_icon = findViewById(R.id.editText9);
    }

    public void register(View view) {
        final String name = username.getText().toString().trim();
        final String pass = password.getText().toString().trim();
        final String pass_confirm = confirm.getText().toString().trim();
        if(!pass.equals(pass_confirm)){//如果两次密码输入不相同
            Toast.makeText(getApplicationContext(), "两次输入密码不符，请再次输入", Toast.LENGTH_SHORT).show();
            confirm.setText("");
            password.setText("");
            return;
        }
        User user = new User();
        user.setUsername(name);
        user.setPassword(pass);
        //进行用户注册
        user.signUp(new SaveListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                if (e == null) {
                    Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(RegisterActivity.this, "注册失败" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}


//        final String icon = e_icon.getText().toString();


//获取图片文件路径
//        String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/test.jpg";
//        String path = getResources().getDrawable()
//图片上传
        /*final BmobFile bmobFile = new BmobFile(new File(path));
        bmobFile.uploadblock(new UploadFileListener() {

            @Override
            public void done(BmobException e) {
                if (e == null) {
                    //bmobFile.getFileUrl()--返回的上传文件的完整地址
                    Toast.makeText(RegisterActivity.this, "上传成功", Toast.LENGTH_SHORT).show();
                    User user = new User();
                    user.setUsername(name);
                    user.setPassword(pass);
                    user.setEmail(email);
                    user.setIcon(bmobFile);
                    //进行用户注册
                    user.signUp(new SaveListener<User>() {
                        @Override
                        public void done(User user, BmobException e) {
                            if (e == null) {
                                Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(RegisterActivity.this, "注册失败" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(RegisterActivity.this, "注册失败2", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onProgress(Integer value) {
                // 返回的上传进度（百分比）
            }
        });*/