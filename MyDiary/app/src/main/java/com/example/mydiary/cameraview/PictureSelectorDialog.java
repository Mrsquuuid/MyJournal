package com.example.mydiary.cameraview;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.example.mydiary.R;
import com.kevin.dialog.BaseDialog;

/**
 * Created by Hailin Xiong on 2021/10/25.
 *
 */

public class PictureSelectorDialog extends BaseDialog implements View.OnClickListener {

    private static final String TAG = "SelectPictureDialog";
    private Button takePhotoBtn, pickPictureBtn, cancelBtn;

    private OnSelectedListener mOnSelectedListener;

    public static PictureSelectorDialog getInstance() {
        PictureSelectorDialog dialog = new PictureSelectorDialog();
        // Set back button
        dialog.canceledBack(false)
                // Click external to close the screen setting dialog box
                .canceledOnTouchOutside(false)
                // Set the dialog box at the bottom
                .gravity(Gravity.BOTTOM)
                // Set width to screen width
                .width(1f)
                // Set black transparent background
                .dimEnabled(false)
                // Set animation
                .animations(android.R.style.Animation_InputMethod);
        return dialog;
    }

    @Override
    public View createView(Context context, LayoutInflater inflater, ViewGroup parent) {
        return inflater.inflate(R.layout.layout_picture_selector, parent, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        takePhotoBtn = view.findViewById(R.id.picture_selector_take_photo_btn);
        pickPictureBtn = view.findViewById(R.id.picture_selector_pick_picture_btn);
        cancelBtn = view.findViewById(R.id.picture_selector_cancel_btn);
        // set button listener
        takePhotoBtn.setOnClickListener(this);
        pickPictureBtn.setOnClickListener(this);
        cancelBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.picture_selector_take_photo_btn:
                if (null != mOnSelectedListener) {
                    mOnSelectedListener.OnSelected(v, 0);
                }
                break;
            case R.id.picture_selector_pick_picture_btn:
                if (null != mOnSelectedListener) {
                    mOnSelectedListener.OnSelected(v, 1);
                }
                break;
            case R.id.picture_selector_cancel_btn:
                if (null != mOnSelectedListener) {
                    mOnSelectedListener.OnSelected(v, 2);
                }
                break;
            default:
                break;
        }
    }

    public void show(FragmentActivity activity) {
        super.show(activity.getSupportFragmentManager(), TAG);
    }

    public void show(Fragment fragment) {
        super.show(fragment.getChildFragmentManager(), TAG);
    }

    /**
     * set the selected picture listener
     *
     * @param l
     */
    public void setOnSelectedListener(OnSelectedListener l) {
        this.mOnSelectedListener = l;
    }

    /**
     * set the interface for the selected picture listener
     */
    public interface OnSelectedListener {
        void OnSelected(View v, int position);
    }
}