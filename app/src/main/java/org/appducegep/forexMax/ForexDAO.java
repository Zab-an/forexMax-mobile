package org.appducegep.forexMax;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.format.DateFormat;
import android.util.Log;

import java.util.Date;

// https://developer.android.com/training/data-storage/sqlite.html

public class ForexDAO extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "Meteo.db";

    public ForexDAO(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    String SQL_CREATION_TABLE_1 = "create table meteo(id INTEGER PRIMARY KEY, ville TEXT, soleilOuNuage TEXT, date TEXT)";
    String SQL_CREATION_TABLE_2 = "create table meteo(id INTEGER PRIMARY KEY, ville TEXT, soleilOuNuage TEXT, date TEXT, vent TEXT)";
    String SQL_CREATION_TABLE_3 = "create table meteo(id INTEGER PRIMARY KEY, ville TEXT, soleilOuNuage TEXT, date TEXT, vent TEXT, temperature REAL)";

    String SQL_MISEAJOUR_TABLE_1_A_2_A = "alter table meteo add column vent TEXT";
    String SQL_MISEAJOUR_TABLE_1_A_2_B = "alter table meteo add column humidite INTEGER";
    String SQL_MISEAJOUR_TABLE_2_A_3 = "alter table meteo add column temperature REAL";

    @Override
    public void onCreate(SQLiteDatabase db) {

        Log.d("BASEDEDONNEES","ForexDAO.create()");
        db.execSQL(SQL_CREATION_TABLE_3);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int avant, int apres) {
        Log.d("BASEDEDONNEES","ForexDAO.onUpgrade() de " + avant + " a " + apres);

        if(avant == 1 && apres >= 2) {
            db.execSQL(SQL_MISEAJOUR_TABLE_1_A_2_A);
            db.execSQL(SQL_MISEAJOUR_TABLE_1_A_2_B);
        }
        if(avant <= 2 && apres == 3) {
            db.execSQL(SQL_MISEAJOUR_TABLE_2_A_3);
        }
    }

    public void onDowngrade(SQLiteDatabase db, int avant, int apres) {
        Log.d("BASEDEDONNEES","ForexDAO.onDowngrade() de" + avant + " a " + apres);
//        String SQL_MISEAJOUR_TABLE_A = "alter table meteo drop column vent ";
//        db.execSQL(SQL_MISEAJOUR_TABLE_A);
//        String SQL_MISEAJOUR_TABLE_B = "alter table meteo drop column vent ";
//        db.execSQL(SQL_MISEAJOUR_TABLE_B);
    }

    public void ajouterMeteo(String soleilOuNuage, int humidite, String vent, float temperature)
    {
        //Date aujourdhui = new Date();
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues meteoDuJour = new ContentValues();
        meteoDuJour.put("ville", "Matane");
        meteoDuJour.put("soleilOuNuage", soleilOuNuage);
        meteoDuJour.put("date", DateFormat.format("MMMM d, yyyy ", (new Date()).getTime()).toString());
        meteoDuJour.put("vent",vent);
        meteoDuJour.put("humidite",humidite);
        meteoDuJour.put("temperature",temperature);
        long newRowId = db.insert("meteo", null, meteoDuJour);

    }
}