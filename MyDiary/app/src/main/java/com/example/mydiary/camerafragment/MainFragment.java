package com.example.mydiary.camerafragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.mydiary.R;
import com.example.mydiary.ui.CameraActivity;
import com.example.mydiary.ui.DiaryHomeActivity;
import com.example.mydiary.ui.MainActivity;

/**
 * Created by Hailin Xiong on 2021/10/25.
 *
 */

public class MainFragment extends PictureSelectorFragment {

    Toolbar toolbar;
    ImageView mPictureIv;
    ImageView go_back;

    public static MainFragment newInstance() {
        return new MainFragment();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        toolbar = view.findViewById(R.id.toolbar);
        go_back = toolbar.findViewById(R.id.go_back);
        mPictureIv = view.findViewById(R.id.main_frag_picture_iv);
        initEvents();
    }

    public void initEvents() {
        go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        // Set picture click listener
        mPictureIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPicture();
            }
        });

        // Set the listener for the resized image
        setOnPictureSelectedListener(new OnPictureSelectedListener() {
            @Override
            public void onPictureSelected(Uri fileUri, Bitmap bitmap) {
                mPictureIv.setImageBitmap(bitmap);

                String filePath = fileUri.getEncodedPath();
                String imagePath = Uri.decode(filePath);
                Toast.makeText(getContext(), "This picture has been saved to:" + imagePath, Toast.LENGTH_LONG).show();
            }
        });
    }

}