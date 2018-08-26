
package com.bagirapp.harrypotterquiz.eredmenyData;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class EredmenyDbHelper extends SQLiteOpenHelper {

        public static final String DATABASE_NAME = "harrypotterquizresults.db";
        public static final int DATABASE_VERSION = 1;
    private static final String TAG = "EredmenyDbHelper";

        public EredmenyDbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            String SQL_CREATE_EREDMENYEK_TABLE = "CREATE TABLE " + EredmenyContract.EredmenyEntry.TABLE_NAME + " ("
                    + EredmenyContract.EredmenyEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + EredmenyContract.EredmenyEntry.COLUMN_PRODUCT_DATE + " TEXT, "
                    + EredmenyContract.EredmenyEntry.COLUMN_LEVEL + " INTEGER, "
                    + EredmenyContract.EredmenyEntry.COLUMN_POINTS + " INTEGER " + ");";

            sqLiteDatabase.execSQL(SQL_CREATE_EREDMENYEK_TABLE);
            Log.v(TAG, "Létrehoztuk a táblát sikeresen.");
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        }
    }


