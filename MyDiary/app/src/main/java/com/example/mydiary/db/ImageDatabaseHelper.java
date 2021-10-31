package com.example.mydiary.db;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Hailin Xiong on 2021/10/31.
 */

public class ImageDatabaseHelper extends SQLiteOpenHelper {
    public ImageDatabaseHelper(Context context, String name,
                              SQLiteDatabase.CursorFactory cursor, int version) {
        super(context, name, cursor, version);
    }

    public void onCreate(SQLiteDatabase db) {
    // a database: table name: imagetable, with content:_id, image
        db.execSQL("CREATE TABLE imagetable (_id INTEGER PRIMARY KEY AUTOINCREMENT,image BLOB)");
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}