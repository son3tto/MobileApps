package com.example.asus.app3_02;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initLogin();
    }

    private void initLogin() {
        findViewById(R.id.loginbtn).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        LoginDialog();
                    }
                }
        );
    }

    private void LoginDialog() {
        //打开login布局
        LayoutInflater layoutInflater = getLayoutInflater();
        View login = layoutInflater.inflate(R.layout.login, null);
        //设置dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setView(login)
                .setIcon(R.drawable.lemon)
                .setMessage(R.string.verify);
        //重写一个方法
        setNegativeButton(builder);
        //直接在函数里面set 不重写 顺便点击确认的时候可以跳转到下一个界面
        builder.setPositiveButton(R.string.sign_in, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent();
                        intent.setClass(getApplicationContext(), ChatActivity.class);
                        startActivity(intent);
                    }
                })
                .create()
                .show();
    }
//重写的setNegativeButton方法
    private AlertDialog.Builder setNegativeButton(AlertDialog.Builder builder){
        return builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getApplicationContext(), R.string.cancel, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.option_menu, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
//        item.setIcon(R.drawable.ic_launcher_foreground);
        TextView text = findViewById(R.id.text);
        switch (item.getItemId())//得到被点击的item的itemId
        {
            case R.id.menu_item1:
                Toast.makeText(getApplicationContext(),  R.string.menu_item, Toast.LENGTH_SHORT).show();
                break;
            case R.id.font_big:
                text.setTextSize(Float.parseFloat(String.valueOf(30)));
                break;
            case R.id.font_medium:
                text.setTextSize(20);
                break;
            case R.id.font_small:
                text.setTextSize(10);
                break;
            case R.id.font_red:
                text.setTextColor(Color.RED);
                break;
            case R.id.font_black:
                text.setTextColor(Color.BLACK);
                break;
        }
        return true;
    }
}

