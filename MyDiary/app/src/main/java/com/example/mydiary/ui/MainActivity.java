package com.example.mydiary.ui;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import cc.trity.floatingactionbutton.FloatingActionButton;

import com.example.mydiary.bean.ImageBean;
import com.example.mydiary.db.ImageDatabaseHelper;
//import com.makeramen.roundedimageview.RoundedImageView;

import com.example.mydiary.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private ImageButton toCamera,toVoice,toNavigation;
    private FloatingActionButton toDiary;
    private Button toDiary2,toCamera2,toVoice2,toNavigation2;
    private ImageView image1,image2,image3;
    private ImageDatabaseHelper mySQLiteOpenHelper;
    SQLiteDatabase mydb;

    CalendarView calendarView;
    TextView textView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //loadImages();
        loadDiary();
        loadCamera();
        loadVoice();
        loadMap();
        loadImages();

        //calendar
        calendarView=findViewById(R.id.calendar);
        textView1=findViewById(R.id.textview1);

        // show current date
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        int curruntMonth = month +1;
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        String date = day+"/"+curruntMonth+"/"+year;
        textView1.setText(date);

        // choose date
        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        month = month +1;
                        String date = day+"-"+month+"-"+year;
                        textView1.setText(date);
                    }
                },year,month,day
                );
                datePickerDialog.show();
            }
        });

    }

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    protected void loadDiary(){
        toDiary = findViewById(R.id.diary_home_btn);
        toDiary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DiaryHomeActivity.class);
                startActivity(intent);
            }
        });
        toDiary2 = findViewById(R.id.diary_image_btn);
        toDiary2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DiaryHomeActivity.class);
                startActivity(intent);
            }
        });
    }

    protected void loadCamera(){
        toCamera = findViewById(R.id.camera_btn);
        toCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CameraActivity.class);
                startActivity(intent);
            }
        });
        toCamera2 = findViewById(R.id.image_image_btn);
        toCamera2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CameraActivity.class);
                startActivity(intent);
            }
        });
    }

    protected void loadVoice(){
        toVoice = findViewById(R.id.voice_btn);
        toVoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, VoiceMemoActivity.class);
                startActivity(intent);
            }
        });
        toVoice2 = findViewById(R.id.voice_image_btn);
        toVoice2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, VoiceMemoActivity.class);
                startActivity(intent);
            }
        });
    }

    protected void loadMap(){
        toNavigation = findViewById(R.id.map_btn);
        toNavigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MapActivity.class);
                startActivity(intent);
            }
        });
        toNavigation2 = findViewById(R.id.map_image_btn);
        toNavigation2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MapActivity.class);
                startActivity(intent);
            }
        });
    }

    private List<ImageBean> imageList = new ArrayList<>();
    protected void loadImages(){
        initImage();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.imageList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        ImageAdapter adapter = new ImageAdapter(imageList);
        recyclerView.setAdapter(adapter);

    }

    protected void initImage(){
        mySQLiteOpenHelper = new ImageDatabaseHelper(MainActivity.this, "image.db", null, 1);
        // Create a read-write database
        mydb = mySQLiteOpenHelper.getWritableDatabase();
        // Create a pointer
        Cursor cur = mydb.query("imagetable", null, null, null, null, null, null);
        int num = 0;

        if (cur != null) {
            Bitmap imagebitmap;
            byte[] imagequery = null;
            while (cur.moveToNext()) {
                num +=1;
                int id = cur.getInt(cur.getColumnIndex("_id"));
                imagequery = cur.getBlob(cur.getColumnIndex("image"));//将Blob数据转化为字节数组
                //Convert byte array to bitmap
                imagebitmap = BitmapFactory.decodeByteArray(imagequery, 0, imagequery.length);
                ImageBean newImage = new ImageBean(imagebitmap);
                imageList.add(newImage);
            }
            cur.close();
        }
        Collections.reverse(imageList);
    }

}