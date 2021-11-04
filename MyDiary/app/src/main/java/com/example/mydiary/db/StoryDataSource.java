package com.example.mydiary.db;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.mydiary.utils.Story;


public class StoryDataSource {
    private SQLiteDatabase database;
    private MyDBHandlerProfile dbHelper;
    private Story story;

    public StoryDataSource(Context context) {
        dbHelper = new MyDBHandlerProfile(context);
    }

    public boolean open() throws SQLException {
        try {
            database = dbHelper.getWritableDatabase();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isDbEmpty() {

        if (dbHelper.isDbEmpty(dbHelper.getWritableDatabase())) {
            return true;
        } else return false;

    }

    public Story setStory(SQLiteDatabase database, Story data) {
        this.story.setStoryTitle(data.getStoryTitle());
        this.story.setStory(data.getStory());
        this.story.setStoryID(data.getStoryID());
        this.story.setStoryImage(data.getStoryImage());
        return this.story;
    }
    public	boolean	addStory(Story story)	throws SQLException {
        try {
            dbHelper.addStoryHandler(story);
            return true;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }


    public Cursor initializeData() {
      open();
       return dbHelper.loadStory(database);


    }
}