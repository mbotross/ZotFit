package com.example.zotfit;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Database extends SQLiteOpenHelper {

    public static final String database_name = "resgistered";
    public static final String table_name = "login_info";
    public static final String table_name2 = "friendslist";
    public static final String COL1 = "usernames";
    public static final String COL2 = "passwords";


    public Database(Context context) {

        super(context, database_name, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();

    }


    public boolean adduser(String username, String password) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentvalues = new ContentValues();
        contentvalues.put(COL1, username);
        contentvalues.put(COL2, password);
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
        db.execSQL("create table " + table_name + "(id INTEGER PRIMARY KEY AUTOINCREMENT, usernames TEXT, passwords TEXT, image BLOB not null)");
        db.execSQL("create table " + table_name2 + "(id INTEGER PRIMARY KEY AUTOINCREMENT, friend TEXT)");

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + table_name);
        db.execSQL("DROP TABLE IF EXISTS " + table_name2);


        onCreate(db);
    }


    public boolean insertimage(String image, int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            FileInputStream fileInputStream = new FileInputStream(image);
            byte[] imagebyte = new byte[fileInputStream.available()];
            fileInputStream.read(imagebyte);
            ContentValues contentValues = new ContentValues();
            contentValues.put("id", id);
            contentValues.put("image", imagebyte);
            db.insert(table_name, null, contentValues);
            fileInputStream.close();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
