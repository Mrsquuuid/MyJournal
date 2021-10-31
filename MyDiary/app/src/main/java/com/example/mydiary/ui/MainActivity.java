package com.example.mydiary.ui;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.TextView;

import cc.trity.floatingactionbutton.FloatingActionButton;

import com.example.mydiary.db.ImageDatabaseHelper;
import com.makeramen.roundedimageview.RoundedImageView;

import com.example.mydiary.R;

public class MainActivity extends AppCompatActivity {


    private ImageButton toCamera,toVoice,toNavigation;
    private FloatingActionButton toDiary;
    private RoundedImageView image1,image2,image3;
    private ImageDatabaseHelper mySQLiteOpenHelper;
    SQLiteDatabase mydb;
    Bitmap imagebitmap;
    CalendarView calendarView;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //loadImages();
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

        //calendar
        calendarView=findViewById(R.id.calendar);
        textView=findViewById(R.id.textview);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                String date = dayOfMonth+"/"+month+"/"+year;
                textView.setText(date);
            }
        });
    }

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    protected void loadImages(){
        image1 = findViewById(R.id.imageView1);
        image2 = findViewById(R.id.imageView2);
        image3 = findViewById(R.id.imageView3);
        mySQLiteOpenHelper = new ImageDatabaseHelper(MainActivity.this, "image.db", null, 1);
        // 创建一个可读写的数据库
        mydb = mySQLiteOpenHelper.getWritableDatabase();
        image1 = findViewById(R.id.imageView1);
        //创建一个指针
        Cursor cur = mydb.query("imagetable", null, null, null, null, null, null);
        int num = 0;
        //判断cursor不为空 这个很重要

        if (cur != null) {
            while (cur.moveToNext()) {
                num +=1;
            }
            cur.close();
        }

        Cursor cursor = mydb.query("imagetable", new String[]{"_id", "image"}, "_id like ?", new String[]{String.valueOf(num)}, null, null, null);
        int i = 0;
        //判断cursor不为空 这个很重要

        if (cursor != null) {
            // 循环遍历cursor
            byte[] imagequery = null;

            while (cursor.moveToPrevious()) {
                i+=1;
                if(i<4){
                    int id = cursor.getInt(cursor.getColumnIndex("_id"));
                    imagequery = cursor.getBlob(cursor.getColumnIndex("image"));//将Blob数据转化为字节数组

                    //将字节数组转化为位图
                    imagebitmap = BitmapFactory.decodeByteArray(imagequery, 0, imagequery.length);
                    if(i==1) {
                        //将位图显示为图片
                        image1.setImageBitmap(imagebitmap);
                    }
                    else if (i==2){
                        image2.setImageBitmap(imagebitmap);
                    }
                    else if (i==3){
                        image3.setImageBitmap(imagebitmap);
                    }
                }
                else{
                    break;
                }
            }
            cursor.close();
        }
    }


}