package com.jnu.lxq;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class StartActivity extends AppCompatActivity {

    private static int SPLASH_DISPLAY_LENGHT= 3000;//延迟3秒

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去除导航栏（标题）
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        //渲染layout
        setContentView(R.layout.activity_start);

        new Handler().postDelayed(new Runnable() {
            public void run() {
                Intent intent = new Intent(StartActivity.this, MainActivity.class);
                startActivity(intent);
                StartActivity.this.finish();   //关闭StartActivity，将其回收
            }
        }, SPLASH_DISPLAY_LENGHT);
    }

}





