package com.example.mydiary.ui;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.mydiary.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Yuzhe You on 2021/10/25.
 * No.1159774
 */

public class TestActivity extends AppCompatActivity {

    private static final String TAG = "TestActivity";

    @Bind(R.id.button1)
    Button mButton1;
    @Bind(R.id.button2)
    Button mButton2;
    @Bind(R.id.button3)
    Button mButton3;
    @Bind(R.id.button4)
    Button mButton4;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.button1, R.id.button2, R.id.button3, R.id.button4})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.button1:
                Log.d(TAG, "onViewClicked: You Click button1");
                break;
            case R.id.button2:
                Log.d(TAG, "onViewClicked: You Click button2");
                break;
            case R.id.button3:
                Log.d(TAG, "onViewClicked: You Click button3");
                break;
        }
    }
}
