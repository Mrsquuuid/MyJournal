package com.example.mydiary.ui;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.ActionBar;
//import android.support.annotation.Nullable;
//import android.support.v7.app.ActionBar;
//import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mydiary.R;
import com.example.mydiary.db.DiaryDatabaseHelper;
import com.example.mydiary.utils.AppManager;
import com.example.mydiary.utils.GetDate;
import com.example.mydiary.utils.StatusBarCompat;
import com.example.mydiary.widget.LinedEditText;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cc.trity.floatingactionbutton.FloatingActionButton;
import cc.trity.floatingactionbutton.FloatingActionsMenu;

/**
 * Created by Yuzhe You on 2021/10/25.
 * No.1159774
 */

public class UpdateDiaryActivity extends AppCompatActivity {

    @Bind(R.id.update_diary_tv_date)
    TextView mUpdateDiaryTvDate;
    @Bind(R.id.update_diary_et_title)
    EditText mUpdateDiaryEtTitle;
    @Bind(R.id.update_diary_et_content)
    LinedEditText mUpdateDiaryEtContent;
    @Bind(R.id.update_diary_fab_back)
    FloatingActionButton mUpdateDiaryFabBack;
    @Bind(R.id.update_diary_fab_add)
    FloatingActionButton mUpdateDiaryFabAdd;
    @Bind(R.id.update_diary_fab_delete)
    FloatingActionButton mUpdateDiaryFabDelete;
    @Bind(R.id.right_labels)
    FloatingActionsMenu mRightLabels;
    @Bind(R.id.tv_title)
    TextView mCommonTvTitle;
    @Bind(R.id.common_title_ll)
    LinearLayout mCommonTitleLl;
    @Bind(R.id.common_iv_back)
    ImageView mCommonIvBack;
    @Bind(R.id.common_iv_test)
    ImageView mCommonIvTest;
    @Bind(R.id.update_diary_tv_tag)
    TextView mTvTag;

    private DiaryDatabaseHelper mHelper;

    public static void startActivity(Context context, String title, String content, String tag) {
        Intent intent = new Intent(context, UpdateDiaryActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("content", content);
        intent.putExtra("tag", tag);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_diary);
        AppManager.getAppManager().addActivity(this);
        ButterKnife.bind(this);
        mHelper = new DiaryDatabaseHelper(this, "Diary.db", null, 1);
        initTitle();
        StatusBarCompat.compat(this, Color.parseColor("#161414"));

        Intent intent = getIntent();
        mUpdateDiaryTvDate.setText("Today, " + GetDate.getDate());
        mUpdateDiaryEtTitle.setText(intent.getStringExtra("title"));
        mUpdateDiaryEtContent.setText(intent.getStringExtra("content"));
        mTvTag.setText(intent.getStringExtra("tag"));



    }

    private void initTitle() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        mCommonTvTitle.setText("Edit Diary");
    }

    @OnClick({R.id.common_iv_back, R.id.update_diary_tv_date, R.id.update_diary_et_title, R.id.update_diary_et_content, R.id.update_diary_fab_back, R.id.update_diary_fab_add, R.id.update_diary_fab_delete})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.common_iv_back:
                DiaryHomeActivity.startActivity(this);
            case R.id.update_diary_tv_date:
                break;
            case R.id.update_diary_et_title:
                break;
            case R.id.update_diary_et_content:
                break;
            case R.id.update_diary_fab_back:
                androidx.appcompat.app.AlertDialog.Builder alertDialogBuilder = new androidx.appcompat.app.AlertDialog.Builder(this);
                alertDialogBuilder.setMessage("Delete this journal?").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

//                        String title = mUpdateDiaryEtTitle.getText().toString();
                        String tag = mTvTag.getText().toString();
                        SQLiteDatabase dbDelete = mHelper.getWritableDatabase();
                        dbDelete.delete("Diary", "tag = ?", new String[]{tag});
                        DiaryHomeActivity.startActivity(UpdateDiaryActivity.this);
                    }
                }).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).show();
                break;
            case R.id.update_diary_fab_add:
                SQLiteDatabase dbUpdate = mHelper.getWritableDatabase();
                ContentValues valuesUpdate = new ContentValues();
                String title = mUpdateDiaryEtTitle.getText().toString();
                String content = mUpdateDiaryEtContent.getText().toString();
                valuesUpdate.put("title", title);
                valuesUpdate.put("content", content);
                dbUpdate.update("Diary", valuesUpdate, "title = ?", new String[]{title});
                dbUpdate.update("Diary", valuesUpdate, "content = ?", new String[]{content});
                DiaryHomeActivity.startActivity(this);
                break;
            case R.id.update_diary_fab_delete:
                DiaryHomeActivity.startActivity(this);

                break;
        }
    }

    @OnClick(R.id.tv_title)
    public void onClick() {
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        DiaryHomeActivity.startActivity(this);
    }
}