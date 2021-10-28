package com.example.mydiary.cameraview;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;

import com.example.mydiary.R;


public class SelectPicturePopupWindow extends PopupWindow implements View.OnClickListener {

    private Button takePhotoBtn, pickPictureBtn, cancelBtn;
    private View mMenuView;
    private PopupWindow popupWindow;
    private OnSelectedListener mOnSelectedListener;

    public SelectPicturePopupWindow(Context context) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.layout_picture_selector, null);
        takePhotoBtn = mMenuView.findViewById(R.id.picture_selector_take_photo_btn);
        pickPictureBtn = mMenuView.findViewById(R.id.picture_selector_pick_picture_btn);
        cancelBtn = mMenuView.findViewById(R.id.picture_selector_cancel_btn);
        // set the button listener
        takePhotoBtn.setOnClickListener(this);
        pickPictureBtn.setOnClickListener(this);
        cancelBtn.setOnClickListener(this);
    }

    /**
     * Add a View widget into the PopupWindow
     *
     * @param activity
     */
    public void showPopupWindow(Activity activity) {
        popupWindow = new PopupWindow(mMenuView,    // add to popupWindow
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        // ☆ Note: the background must be set. A prerequisite for playing animation is that the form must have a background
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.showAtLocation(activity.getWindow().getDecorView(), Gravity.CENTER | Gravity.BOTTOM, 0, 0);
        popupWindow.setAnimationStyle(android.R.style.Animation_InputMethod);   // set the animation
        popupWindow.setFocusable(false);                                        // Click elsewhere to hide the keyboard popupWindow
        popupWindow.update();
    }

    /**
     * dismiss the PopupWindow
     */
    public void dismissPopupWindow() {
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
            popupWindow = null;
        }
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

    /**
     * set selected picture listener
     *
     * @param l
     */
    public void setOnSelectedListener(OnSelectedListener l) {
        this.mOnSelectedListener = l;
    }

    /**
     * set interface for selected picture listener
     */
    public interface OnSelectedListener {
        void OnSelected(View v, int position);
    }

}