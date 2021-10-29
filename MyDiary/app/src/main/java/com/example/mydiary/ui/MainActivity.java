package com.example.mydiary.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import cc.trity.floatingactionbutton.FloatingActionButton;


import com.example.mydiary.R;

public class MainActivity extends AppCompatActivity {


    private ImageButton toCamera,toVoice,toNavigation;
    private FloatingActionButton toDiary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toDiary = findViewById(R.id.diary_home_btn);
        toDiary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DiaryHomeActivity.class);
                startActivity(intent);
            }
        });

        toCamera = findViewById(R.id.camera_btn);
        toCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CameraActivity.class);
                startActivity(intent);
            }
        });

        toVoice = findViewById(R.id.voice_btn);
        toVoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, VoiceMemoActivity.class);
                startActivity(intent);
            }
        });

        toNavigation = findViewById(R.id.user_btn);
        toNavigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MapActivity.class);
                startActivity(intent);
            }
        });
    }

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

}