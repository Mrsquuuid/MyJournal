package com.example.mydiary.db;

import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import android.util.Log;


import com.example.mydiary.utils.Story;
import com.example.mydiary.utils.User;

import static androidx.constraintlayout.widget.Constraints.TAG;


public class MyDBHandlerProfile extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final  String DATABASE_NAME = "myDiaryDB.db";
    private static final String TABLE_NAME = "User";
    private static final String TABLE_NAME_II = "Story";
    private static final String COLUMN_NAME = "UserName";
    private static final String COLUMN_ID = "ID";
    private static final String COLUMN_STORY_CONTENT = "Content";
    private static final String COLUMN_SURNAME = "UserSurname";
    private static final String COLUMN_ADDRESS = "UserAddress";
    private static final String COLUMN_EMAIL = "UserEmail";
    private static final String COLUMN_OIB  = "UserOib";
    private static final String COLUMN_PASSPORT  = "UserPassport";
    private static final String COLUMN_IMG  = "IMAGE";
    private static final String COLUMN_TITLE  = "StoryTitle";


    //initialize database

    public MyDBHandlerProfile(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);


    }
    @Override
    public void onCreate(SQLiteDatabase db){
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_NAME + " TEXT NOT NULL, " +
                COLUMN_SURNAME + " TEXT NOT NULL, " + COLUMN_ADDRESS +
                " TEXT NOT NULL, " + COLUMN_EMAIL + " TEXT NOT NULL, " + COLUMN_OIB + " TEXT NOT NULL, " + COLUMN_PASSPORT + " TEXT NOT NULL )";
        String CREATE_TABLE_II = "CREATE TABLE " + TABLE_NAME_II + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_STORY_CONTENT + " TEXT NOT NULL, " +
                COLUMN_IMG + " BLOB NOT NULL, " + COLUMN_TITLE + " TEXT NOT NULL  )";
   try {

       db.execSQL(CREATE_TABLE);
       db.execSQL(CREATE_TABLE_II);
   }
   catch (SQLException e){
       e.printStackTrace();
   }


}
    @Override
    public void onUpgrade(SQLiteDatabase db, int newVersion, int oldVersion) {

    }
//load functions
public String loadNameHandler(User user){

        String query= "Select "+COLUMN_NAME + " FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        String name ;
        if(cursor.getCount() >= 1 && cursor.moveToFirst())
        {
            do {
                name = cursor.getString(0);
                user.setUserName(name);
            }while(cursor.moveToNext());

        }
        cursor.close();
        db.close();

    return user.getUserName();
}



    public User loadUser(User user, SQLiteDatabase dbb) {
        String query = "Select * FROM " + "User";
        Cursor cursor = dbb.rawQuery(query, null);
        if (cursor.getCount() >= 1 && cursor.moveToFirst()) {

            do
        {
                int result_0 = cursor.getInt(0);
                user.setUserName(cursor.getString(1));
                user.setUserSurname(cursor.getString(2));
                user.setUserAddress(cursor.getString(3));
                user.setUserEmail(cursor.getString(4));
                user.setUserOib(cursor.getString(5));
                user.setUserPassport(cursor.getString(6));
                user.setUserID(result_0);

            }while (cursor.moveToNext());
        }
            cursor.close();


        return user;
    }

    public Cursor loadStory ( SQLiteDatabase dbb){
        String query  = "Select * FROM "+ TABLE_NAME_II;
        Cursor cursor  = dbb.rawQuery(query, null);

     return cursor;
    }


    public void  addHandler (User user){
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME,user.getUserName());
        values.put(COLUMN_SURNAME,user.getUserSurname());
        values.put(COLUMN_ADDRESS,user.getUserAddress());
        values.put(COLUMN_EMAIL,user.getUserEmail());
        values.put(COLUMN_OIB,user.getUserOib());
        values.put(COLUMN_PASSPORT,user.getUserPassport());
        SQLiteDatabase db = this.getWritableDatabase();

        db.insert(TABLE_NAME, null, values);

        db.close();
    }
public void addStoryHandler(Story story)
{
    ContentValues values = new ContentValues();
    values.put(COLUMN_STORY_CONTENT, story.getStory());
    values.put(COLUMN_TITLE, story.getStoryTitle());
    values.put(COLUMN_IMG, story.getStoryImage());
    SQLiteDatabase db =  this.getWritableDatabase();
    db.insert(TABLE_NAME_II, null, values);

}

    public static boolean isDbEmpty(SQLiteDatabase Db) {
        try {
            Cursor c = Db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
            if (c.moveToFirst()) {    // c.getCount() == 0  is also not working
                Log.d(TAG, "isDbEmpty: not empty");
                return false;
            }
            c.close();
        } catch (SQLiteException e) {
            Log.d(TAG, "isDbEmpty: doesn't exist");
            return true;
        }
        return true;
    }
    public void delete(Story story) {

        SQLiteDatabase db = this.getWritableDatabase();
        int id = story.getStoryID();
        db.delete(TABLE_NAME_II,  COLUMN_ID + " = " + id   , null);

    }
}

