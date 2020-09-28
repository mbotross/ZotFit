package com.example.zotfit;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

public class Database extends SQLiteOpenHelper {
    Preferences preferences = Preferences.INSTANCE;
    public static final String database_name = "resgistered";
    public static final String table_name = "login_info";
    public static final String table_name2 = "friendslist";
    public static final String COL1 = "usernames";
    public static final String COL2 = "passwords";
    public static final String COL3 = "calories";
    public static final String COL4 = "image";




    public Database(Context context) {

        super(context, database_name, null, 2);
        SQLiteDatabase db = this.getWritableDatabase();

    }


    public boolean adduser(String username, String password) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentvalues = new ContentValues();
        contentvalues.put(COL1, username);
        contentvalues.put(COL2, password);
        contentvalues.put(COL3, 0);
        db.insert(table_name, null, contentvalues);
        db.close();
        return true;

    }

    public boolean addfriend(Integer ID, String friendname) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        return true;
    }

    public Cursor getuser() {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String sql = "SELECT * FROM " + table_name + ";";
        return sqLiteDatabase.rawQuery(sql, null);

    }

    //checking usernames and password match

    public Boolean checkuser(String username, String password) {

        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + table_name + " where usernames=? and passwords=?";
        Cursor cursor = db.rawQuery(sql, new String[]{username, password});
        if (cursor.getCount() > 0) {
            return true;
        } else return false;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + table_name + "(id INTEGER PRIMARY KEY AUTOINCREMENT, usernames TEXT, passwords TEXT, calories TEXT, image TEXT)");
        db.execSQL("create table " + table_name2 + "(id INTEGER PRIMARY KEY AUTOINCREMENT, friend TEXT)");

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + table_name);
        db.execSQL("DROP TABLE IF EXISTS " + table_name2);


        onCreate(db);
    }


    public String getimage(String username){
        SQLiteDatabase db = this.getReadableDatabase();

        String sql = "SELECT * FROM " + table_name + " where usernames=?";
        Cursor cursor = db.rawQuery(sql, new String[]{username});
        if(cursor.getCount() > 0){
            cursor.moveToFirst();
            return (cursor.getString(cursor.getColumnIndex(COL4)));
        }

        return "";
    }

    public boolean insertimage(Uri imageUri) {
        try{
            SQLiteDatabase db = this.getReadableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("image", imageUri.toString());
            db.update(table_name, contentValues, COL1+ "=?" , new String[]{preferences.getUsername()});
            return true;
        }
        catch (Exception e){
            return false;
        }

    }

    }
