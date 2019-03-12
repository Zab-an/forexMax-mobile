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
    public static final String DATABASE_NAME = "Forex.db";

    public ForexDAO(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    String SQL_CREATION_TABLE_1 = "create table forex(id INTEGER PRIMARY KEY, paire TEXT, valeur REAL)";

//    String SQL_MISEAJOUR_TABLE_1_A_2_A = "alter table meteo add column vent TEXT";
//    String SQL_MISEAJOUR_TABLE_1_A_2_B = "alter table meteo add column humidite INTEGER";
//    String SQL_MISEAJOUR_TABLE_2_A_3 = "alter table meteo add column temperature REAL";

    @Override
    public void onCreate(SQLiteDatabase db) {

        Log.d("BASEDEDONNEES","ForexDAO.create()");
        db.execSQL(SQL_CREATION_TABLE_1);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int avant, int apres) {
        Log.d("BASEDEDONNEES","ForexDAO.onUpgrade() de " + avant + " a " + apres);
    }

    public void onDowngrade(SQLiteDatabase db, int avant, int apres) {
        Log.d("BASEDEDONNEES","ForexDAO.onDowngrade() de" + avant + " a " + apres);
//        String SQL_MISEAJOUR_TABLE_A = "alter table meteo drop column vent ";
//        db.execSQL(SQL_MISEAJOUR_TABLE_A);
//        String SQL_MISEAJOUR_TABLE_B = "alter table meteo drop column vent ";
//        db.execSQL(SQL_MISEAJOUR_TABLE_B);
    }

    public void ajouterForex(String paire, float valeur)
    {
        //Date aujourdhui = new Date();
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues coursDuJour = new ContentValues();
        coursDuJour.put("paire", "EURUSD");
//        coursDuJour.put("soleilOuNuage", soleilOuNuage);
//        coursDuJour.put("date", DateFormat.format("MMMM d, yyyy ", (new Date()).getTime()).toString());
//        coursDuJour.put("vent",vent);
//        coursDuJour.put("humidite",humidite);
        coursDuJour.put("valeur",valeur);
        long newRowId = db.insert("forex", null, coursDuJour);

    }
}
