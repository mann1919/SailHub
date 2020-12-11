package com.example.sailhub;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DBNAME = "SailingClub.db";
    private static DBHelper sInstance;
    public static synchronized DBHelper  getInstance(Context context) {

        if (sInstance == null) {
            sInstance = new DBHelper (context.getApplicationContext());
        }
        return sInstance;
    }
    private DBHelper(Context context) {

        super(context, "SailingClub.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase ClubDB) {
        String sqlUsers = "create Table users (userId INTEGER PRIMARY KEY, password TEXT)";
        String sqlSeries = "create Table series (series_id INTEGER PRIMARY KEY AUTOINCREMENT, series_name TEXT, no_of_races INTEGER, no_of_competitors INTEGER)";
        String sqlRace = "create Table race (race_id INTEGER PRIMARY KEY AUTOINCREMENT, series_id INTEGER, FOREIGN KEY (series_id) REFERENCES series(series_id))";
        String sqlRaceRecords = "create Table raceRecords (raceRecords_id INTEGER PRIMARY KEY AUTOINCREMENT, race_id INTEGER, Class TEXT, PY INTEGER, sail_no INTEGER, helm_name TEXT, crew_name TEXT, elapsed INTEGER, laps INTEGER)";
        ClubDB.execSQL(sqlUsers);
        ClubDB.execSQL(sqlSeries);
        ClubDB.execSQL(sqlRace);
        ClubDB.execSQL(sqlRaceRecords);
    }

    @Override
    public void onUpgrade(SQLiteDatabase ClubDB, int i, int i1) {
        String sqlUsers = "drop Table if exists users";
        String sqlSeries = "drop Table if exists series";
        String sqlRace = "drop Table if exists race";
        String sqlRaceRecords = "drop Table if exists raceRecords";
        ClubDB.execSQL(sqlUsers);
        ClubDB.execSQL(sqlSeries);
        ClubDB.execSQL(sqlRace);
        ClubDB.execSQL(sqlRaceRecords);

        onCreate(ClubDB);
    }

    public Boolean insertUserData(String userId, String password) {
        SQLiteDatabase ClubDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("userId", userId);
        contentValues.put("password", password);
        long result = ClubDB.insert("users", null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }


    public Boolean checkUserId(String userId) {
        SQLiteDatabase ClubDB = this.getWritableDatabase();
        Cursor cursor = ClubDB.rawQuery("Select * from users where userId = ?", new String[]{userId});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    public Boolean checkUserIdPassword(String userId, String password) {
        SQLiteDatabase ClubDB = this.getWritableDatabase();
        Cursor cursor = ClubDB.rawQuery("Select * from users where userId = ? and password = ?", new String[]{userId, password});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    public Boolean insertSeriesData(String series_name, int no_of_races, int no_of_competitors) {
        SQLiteDatabase ClubDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("series_name", series_name);
        contentValues.put("no_of_races", no_of_races);
        contentValues.put("no_of_competitors", no_of_competitors);
        long result = ClubDB.insert("series", null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    Cursor readSeriesName(){
        String qry= "SELECT * FROM series ORDER BY series_id DESC" ;
        SQLiteDatabase ClubDB = this.getReadableDatabase();

        Cursor cursor = null;
        if(ClubDB!= null){
            cursor = ClubDB.rawQuery(qry,null);
        }//if
        return cursor;
    }//cursor

    public Boolean insertCompetitorData(String Class, int PY, int sail_no, String helm_name, String crew_name) {
        SQLiteDatabase ClubDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Class", Class);
        contentValues.put("PY", PY);
        contentValues.put("sail_no", sail_no);
        contentValues.put("helm_name", helm_name);
        contentValues.put("crew_name", crew_name);
        long result = ClubDB.insert("raceRecords", null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

}