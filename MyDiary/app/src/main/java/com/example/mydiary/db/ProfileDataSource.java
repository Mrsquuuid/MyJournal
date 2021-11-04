package com.example.mydiary.db;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.mydiary.utils.User;

public class ProfileDataSource {
    private SQLiteDatabase database;
    private MyDBHandlerProfile dbHelper;
    public	ProfileDataSource(Context context)	{
        dbHelper	=	new MyDBHandlerProfile(context);
    }
    public	boolean	open()	throws SQLException {
        try {
            database = dbHelper.getWritableDatabase();
            return true;
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isDbEmpty(){

  if (dbHelper.isDbEmpty(dbHelper.getWritableDatabase()))
  {
      return true;
  }else return false;

    }
    public	void	addUser(User user)	throws SQLException {
        dbHelper.addHandler(user);
    }
    public	void	close()	{
        database.close();
    }

    public String loadName ( User user){

        return dbHelper.loadNameHandler(user);
    }


    public User loadUser (User user)
    {
          open();
          return   dbHelper.loadUser(user,database);

    }





}
