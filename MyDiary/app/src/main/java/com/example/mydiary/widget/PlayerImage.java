package com.example.mydiary.widget;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class PlayerImage extends androidx.appcompat.widget.AppCompatImageView{

    private Context context;

    public PlayerImage(@NonNull Context context) {
        super(context);
    }
    public PlayerImage(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }
    public PlayerImage(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
