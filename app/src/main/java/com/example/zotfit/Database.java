package com.example.zotfit;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import java.util.Collections;
import java.util.List;

public class Database extends SQLiteOpenHelper {
    Preferences preferences = Preferences.INSTANCE;
    private static Database mInstance = null;
    private Context mCxt;
    public static final String database_name = "resgistered";
    public static final String table_name = "login_info";
    public static final String table_name2 = "friendslist";
    public static final String table_name3 = "health_data";

    public static final String COL1 = "usernames";
    public static final String COL2 = "passwords";
    public static final String COL3 = "calories";
    public static final String COL4 = "image";

    public static final String FRIENDS = "friend";

    public static final String DCAL = "daily_cal";
    public static final String DFAT = "daily_fat";
    public static final String DPRO = "daily_protein";
    public static final String DCARB = "daily_carb";

    private Database(Context context) {

        super(context, database_name, null, 4);
        this.mCxt = context;
    }

    public static Database getInstance(Context ctx) {
        if (mInstance == null) {
            mInstance = new Database(ctx.getApplicationContext());
        }
        return mInstance;
    }


    public boolean adduser(String username, String password) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentvalues = new ContentValues();
        contentvalues.put(COL1, username);
        contentvalues.put(COL2, password);
        contentvalues.put(COL3, 0);
        db.insert(table_name, null, contentvalues);

        contentvalues = new ContentValues();
        contentvalues.put(COL1, username);
        contentvalues.put(DCAL, 0);
        contentvalues.put(DFAT, 0);
        contentvalues.put(DPRO, 0);
        contentvalues.put(DCARB, 0);
        db.insert(table_name3, null, contentvalues);
        db.close();
        return true;

    }

    public boolean addFriend(String username, String friendname) {
        try{
            SQLiteDatabase db = this.getReadableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(FRIENDS, friendname);
            db.update(table_name2, contentValues, COL1+ "=?" , new String[]{username});
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    public List<String> getFriendsList(String username){
        List<String> friendsList = Collections.emptyList();
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + table_name2 + " where usernames=?";
        Cursor cursor = db.rawQuery(sql, new String[]{username});
        friendsList.add(cursor.getString(cursor.getColumnIndex(FRIENDS)));
        while(cursor.moveToNext()){
            friendsList.add(cursor.getString(cursor.getColumnIndex(FRIENDS)));
        }

        return friendsList;
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
        db.execSQL("create table " + table_name + "(id INTEGER PRIMARY KEY AUTOINCREMENT, usernames TEXT, passwords TEXT, fullname TEXT, major TEXT, calories TEXT, image TEXT)");
        db.execSQL("create table " + table_name2 + "(id INTEGER PRIMARY KEY AUTOINCREMENT, usernames TEXT, friend TEXT)");
        db.execSQL("create table " + table_name3 + "(id INTEGER PRIMARY KEY AUTOINCREMENT, usernames TEXT, daily_cal TEXT, daily_fat TEXT, daily_protein TEXT, daily_carb TEXT)");

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + table_name);
        db.execSQL("DROP TABLE IF EXISTS " + table_name2);
        db.execSQL("DROP TABLE IF EXISTS " + table_name3);
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

    public boolean updateDailyData(String username, String cals, String fat, String protein, String carbs){
        try{
            SQLiteDatabase db = this.getReadableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(DCAL, cals);
            contentValues.put(DPRO, protein);
            contentValues.put(DCARB, carbs);
            contentValues.put(DFAT, fat);
            db.update(table_name3, contentValues, COL1+ "=?" , new String[]{username});
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    public DailyData getDailyData(String username){
        DailyData data = new DailyData(username, 0, 0, 0, 0);
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + table_name3 + " where usernames=?";
        Cursor cursor = db.rawQuery(sql, new String[]{username});
        if(cursor.getCount() > 0){
            cursor.moveToFirst();
            data = new DailyData(username, Float.parseFloat(cursor.getString(cursor.getColumnIndex(DCAL))),
                    Float.parseFloat(cursor.getString(cursor.getColumnIndex(DFAT))),
                    Float.parseFloat(cursor.getString(cursor.getColumnIndex(DPRO))),
                    Float.parseFloat(cursor.getString(cursor.getColumnIndex(DCARB))));
        }

        return data;
    }

    }
