package com.example.sailhub;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;
/*
This class is for all database tables and queries.
It is implemented as a singleton
 */
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

    // create the tables and prepopulate table with user ids
    @Override
    public void onCreate(SQLiteDatabase ClubDB) {
        String sqlUsers = "create Table users (user_id INTEGER PRIMARY KEY, password TEXT)";
        String sqlSeries = "create Table series (series_name TEXT PRIMARY KEY, no_of_races INTEGER, no_of_competitors INTEGER)";
        String sqlRace = "create Table race (race_id INTEGER PRIMARY KEY AUTOINCREMENT, series_name TEXT , FOREIGN KEY (series_name) REFERENCES series(series_name))";
        String sqlRaceRecords = "create Table raceRecords (raceRecords_id INTEGER PRIMARY KEY AUTOINCREMENT, race_id INTEGER, Class TEXT, sail_no INTEGER, helm_name TEXT, crew_name TEXT, PY INTEGER, elapsed TEXT, laps INTEGER, corrected TEXT, points INTEGER)";
        ClubDB.execSQL(sqlUsers);
        ClubDB.execSQL(sqlSeries);
        ClubDB.execSQL(sqlRace);
        ClubDB.execSQL(sqlRaceRecords);

        String insertUser1 = "INSERT INTO users (user_id) VALUES ('777')";
        ClubDB.execSQL(insertUser1);
        String insertUser2 = "INSERT INTO users (user_id) VALUES ('999')";
        ClubDB.execSQL(insertUser2);
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

    // insert user credentials
    public Boolean insertUserData(String userId, String password) {
        SQLiteDatabase ClubDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("password", password);
        String[] args = new String[]{userId};
        long result = ClubDB.update("users", contentValues, "user_id=?" , args) ;
        if (result == -1)
            return false;
        else
            return true;
    }

    // check if user id exists ans can register
    public Boolean checkUserId(String userId) {
        SQLiteDatabase ClubDB = this.getReadableDatabase();
        String[] args = new String[]{userId};
        Cursor cursor = ClubDB.rawQuery("Select user_id from users where user_id=? AND password IS NULL", args);
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    // check if the inputted credentials match and exist in table
    public Boolean checkUserIdPassword(String userId, String password) {
        SQLiteDatabase ClubDB = this.getWritableDatabase();
        Cursor cursor = ClubDB.rawQuery("Select * from users " +
                "where user_id = ? and password = ?", new String[]{userId, password});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    // insert series details
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

    // delete series
    public Boolean deleteSeriesData(String series_name) {
        SQLiteDatabase ClubDB = this.getWritableDatabase();

        String[] args = new String[]{series_name};
        long result = ClubDB.delete("series","series_name=?" , args);
        if (result == -1)
            return false;
        else
            return true;
    }


    // create races for each series
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

    // read race results
    public Cursor readRaceResult(Integer raceID){
        String qry= "SELECT Class,sail_no,helm_name,crew_name,PY,elapsed,laps,corrected,points FROM raceRecords WHERE race_id = '" + raceID +"'" + "ORDER BY corrected ASC, laps DESC";
        SQLiteDatabase ClubDB = this.getReadableDatabase();

        Cursor cursor = null;
        if(ClubDB!= null){
            cursor = ClubDB.rawQuery(qry,null);
        }//if
        return cursor;
    }

    // read series result
    public Cursor readSeriesResult(String seriesName){
        String qry= "SELECT raceRecords.Class, raceRecords.sail_no, raceRecords.helm_name," +
                " raceRecords.crew_name, " + "raceRecords.PY ,SUM (raceRecords.points ) " +
                " FROM raceRecords INNER JOIN race ON raceRecords.race_id=race.race_id " +
                "WHERE race.series_name = '" + seriesName +"' Group BY raceRecords.Class," +
                "raceRecords.sail_no,raceRecords.helm_name " +
                "ORDER BY SUM(raceRecords.points) ASC";

        SQLiteDatabase ClubDB = this.getReadableDatabase();

        Cursor cursor = null;
        if(ClubDB!= null){
            cursor = ClubDB.rawQuery(qry,null);
        }//if
        return cursor;
    }

    // get race ids for the series name given
    public Cursor getRaceIds(String sName) {
        String qry = "SELECT race_id FROM race WHERE series_name = '" +  sName +"'";
        SQLiteDatabase ClubDB = this.getReadableDatabase();

        Cursor cursor = null;
        if(ClubDB!= null){
            cursor = ClubDB.rawQuery(qry,null);
        }//if
        return cursor;
    }

    // get competitor details
    public Cursor getCompetitors(Integer raceID) {
        String qry= "SELECT Class,sail_no,helm_name,PY FROM raceRecords WHERE race_id = '" + raceID +"'";
        SQLiteDatabase ClubDB = this.getReadableDatabase();

        Cursor cursor = null;
        if(ClubDB!= null){
            cursor = ClubDB.rawQuery(qry,null);
        }//if
        return cursor;
    }

    // get series name
    public Cursor getSeriesName(int raceId) {
        String qry = "SELECT series_name FROM race WHERE race_id = '" +  raceId +"'";
        SQLiteDatabase ClubDB = this.getReadableDatabase();

        Cursor cursor = null;
        if(ClubDB!= null){
            cursor = ClubDB.rawQuery(qry,null);
        }//if
        return cursor;
    }

    //get number of competitors
    public Cursor getNoOfCompetitors(String sName) {
        String qry = "SELECT no_of_competitors FROM series WHERE series_name = '"+ sName +"'";
        SQLiteDatabase ClubDB = this.getReadableDatabase();

        Cursor cursor = null;
        if(ClubDB!= null){
            cursor = ClubDB.rawQuery(qry,null);
        }//if
        return cursor;
    }

    //read series name
    public Cursor readSeriesName(){
        String qry= "SELECT series_name FROM series" ;
        SQLiteDatabase ClubDB = this.getReadableDatabase();

        Cursor cursor = null;
        if(ClubDB!= null){
            cursor = ClubDB.rawQuery(qry,null);
        }//if
        return cursor;
    }//cursor

    //insert details about the competitor
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


    // insert points for competitors
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

    // insert elapsed and laps for competitors
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