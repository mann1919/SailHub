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
        String sqlSeries = "create Table series (series_number INTEGER , series_name TEXT PRIMARY KEY, no_of_races INTEGER, no_of_competitors INTEGER)";
        String sqlRace = "create Table race (race_id INTEGER PRIMARY KEY AUTOINCREMENT, series_name TEXT , FOREIGN KEY (series_name) REFERENCES series(series_name))";
        String sqlRaceRecords = "create Table raceRecords (raceRecords_id INTEGER PRIMARY KEY AUTOINCREMENT, race_id INTEGER, Class TEXT, sail_no INTEGER, helm_name TEXT, crew_name TEXT, PY INTEGER, elapsed TEXT, laps INTEGER, corrected TEXT, points INTEGER)";
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

    public Boolean deleteSeriesData(String series_number) {
        SQLiteDatabase ClubDB = this.getWritableDatabase();

        String[] args = new String[]{series_number};
        long result = ClubDB.delete("series","series_number=?" , args);
        if (result == -1)
            return false;
        else
            return true;
    }


    public Boolean insertRaceEntries(String series_name) {
        SQLiteDatabase ClubDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
            contentValues.put("series_name", series_name);

        long result = ClubDB.insert("race", null, contentValues);
           //for
        if (result == -1)
            return false;
        else
            return true;

    }

    public Cursor readRaceResult(Integer raceID){
        String qry= "SELECT Class,sail_no,helm_name,crew_name,PY,elapsed,laps,corrected,points FROM raceRecords WHERE race_id = '" + raceID +"'" + "ORDER BY corrected ASC";
        SQLiteDatabase ClubDB = this.getReadableDatabase();

        Cursor cursor = null;
        if(ClubDB!= null){
            cursor = ClubDB.rawQuery(qry,null);
        }//if
        return cursor;
    }

    public Cursor readSeriesResult(String seriesName){
        String qry= "SELECT raceRecords.Class, raceRecords.sail_no, raceRecords.helm_name, raceRecords.crew_name, raceRecords.PY ,SUM (raceRecords.points )  FROM raceRecords INNER JOIN race ON raceRecords.race_id=race.race_id WHERE race.series_name = '"
                + seriesName +"' Group BY raceRecords.Class,raceRecords.sail_no,raceRecords.helm_name ORDER BY raceRecords.points ASC";
        SQLiteDatabase ClubDB = this.getReadableDatabase();

        //SELECT raceRecords.Class, raceRecords.sail_no, raceRecords.helm_name, raceRecords.crew_name, raceRecords.PY ,raceRecords.points
        //FROM raceRecords
        //INNER JOIN race ON raceRecords.race_id=race.race_id;

        Cursor cursor = null;
        if(ClubDB!= null){
            cursor = ClubDB.rawQuery(qry,null);
        }//if
        return cursor;
    }

    public Cursor getRaceIds(String sName) {
        String qry = "SELECT race_id FROM race WHERE series_name = '" +  sName +"'";
        SQLiteDatabase ClubDB = this.getReadableDatabase();

        Cursor cursor = null;
        if(ClubDB!= null){
            cursor = ClubDB.rawQuery(qry,null);
        }//if
        return cursor;
    }
    public Cursor getCompetitors(Integer raceID) {
        String qry= "SELECT Class,sail_no,helm_name,PY FROM raceRecords WHERE race_id = '" + raceID +"'";
        SQLiteDatabase ClubDB = this.getReadableDatabase();

        Cursor cursor = null;
        if(ClubDB!= null){
            cursor = ClubDB.rawQuery(qry,null);
        }//if
        return cursor;
    }

    public Cursor getSeriesName(int raceId) {
        String qry = "SELECT series_name FROM race WHERE race_id = '" +  raceId +"'";
        SQLiteDatabase ClubDB = this.getReadableDatabase();

        Cursor cursor = null;
        if(ClubDB!= null){
            cursor = ClubDB.rawQuery(qry,null);
        }//if
        return cursor;
    }
    public Cursor getNoOfCompetitors(String sName) {
        String qry = "SELECT no_of_competitors FROM series WHERE series_name = '"+ sName +"'";
        SQLiteDatabase ClubDB = this.getReadableDatabase();

        Cursor cursor = null;
        if(ClubDB!= null){
            cursor = ClubDB.rawQuery(qry,null);
        }//if
        return cursor;
    }


    public Cursor readSeriesName(){
        String qry= "SELECT series_name FROM series" ;
        SQLiteDatabase ClubDB = this.getReadableDatabase();

        Cursor cursor = null;
        if(ClubDB!= null){
            cursor = ClubDB.rawQuery(qry,null);
        }//if
        return cursor;
    }//cursor

    public Boolean insertCompetitorData(int race_id, String Class, int PY, int sail_no, String helm_name, String crew_name) {
        SQLiteDatabase ClubDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("race_id", race_id);
        contentValues.put("Class", Class);
        contentValues.put("PY", PY);
        contentValues.put("sail_no", sail_no);
        contentValues.put("helm_name", helm_name);
        contentValues.put("crew_name", crew_name);
        long result = ClubDB.insert("raceRecords", null, contentValues) ;
        if (result == -1)
            return false;
        else
            return true;
    }
    public Boolean insertPoints(int race_id, String Class, int sail_no, String helm_name, int points) {
        SQLiteDatabase ClubDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("points", points);

        String[] args = new String[]{String.valueOf(race_id), Class,String.valueOf(sail_no),helm_name};

        long result = ClubDB.update("raceRecords", contentValues, "race_id=? AND Class=? AND sail_no=? AND helm_name=?" , args) ;
        if (result == -1)
            return false;
        else
            return true;
    }

    public Boolean insertRaceData(int race_id, String Class, int sail_no, String helm_name, String elapsed, int laps, String corrected) {
        SQLiteDatabase ClubDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("elapsed", elapsed);
        contentValues.put("laps", laps);
        contentValues.put("corrected", corrected);

        String[] args = new String[]{String.valueOf(race_id), Class,String.valueOf(sail_no),helm_name};

        long result = ClubDB.update("raceRecords", contentValues, "race_id=? AND Class=? AND sail_no=? AND helm_name=?" , args) ;
        if (result == -1)
            return false;
        else
            return true;
    }

}