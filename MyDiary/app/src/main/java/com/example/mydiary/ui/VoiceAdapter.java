package com.example.mydiary.ui;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.mydiary.R;
import java.io.File;

public class VoiceAdapter extends BaseQuickAdapter<File, BaseViewHolder> {

    public VoiceAdapter() {
        super(R.layout.adapter_voice);
    }
    @Override
    protected void convert(@NonNull BaseViewHolder baseViewHolder, File file) {

    }
}
