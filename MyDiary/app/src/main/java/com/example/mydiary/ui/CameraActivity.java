package com.example.mydiary.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mydiary.Main;
import com.example.mydiary.R;
import com.example.mydiary.camerafragment.MainFragment;

/**
 * Created by Hailin Xiong on 2021/10/25.
 *
 */
public class CameraActivity extends AppCompatActivity {
    private ImageView go_back;
    Toolbar toolbar;
    ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera_main);
        this.setTitle("Select a image");
        initMainFragment();
    }

    /**
     * Initialize the Fragment
     *
     */
    public void initMainFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        MainFragment mFragment = MainFragment.newInstance();
        transaction.replace(R.id.main_act_container, mFragment, mFragment.getClass().getSimpleName());
        transaction.commit();
    }
}