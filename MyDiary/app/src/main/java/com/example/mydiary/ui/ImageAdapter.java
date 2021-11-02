package com.example.mydiary.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.mydiary.R;
import com.example.mydiary.bean.ImageBean;

import java.util.List;

/**
 * Created by Hailin Xiong on 2021/10/31.
 *
 */
public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {

    private  List<ImageBean> mFruitList;
    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView selectedImage;

        public ViewHolder (View view)
        {
            super(view);
            selectedImage = (ImageView) view.findViewById(R.id.selected_image);
        }

    }

    public  ImageAdapter (List<ImageBean> imageList){
        mFruitList = imageList;
    }

    @Override

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){

        ImageBean seImage = mFruitList.get(position);
        holder.selectedImage.setImageBitmap(seImage.getImage());
    }

    @Override
    public int getItemCount(){
        return mFruitList.size();
    }
}