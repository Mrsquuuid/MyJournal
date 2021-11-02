package com.example.mydiary.bean;

import android.graphics.Bitmap;

/**
 * Created by Hailin Xiong on 2021/10/31.
 *
 */

public class ImageBean {

    //private int imageId;
    private Bitmap imagebitmap;

    public ImageBean(Bitmap imagebitmap){
        this.imagebitmap = imagebitmap;

    }

    public Bitmap getImage() {
        return imagebitmap;
    }
}