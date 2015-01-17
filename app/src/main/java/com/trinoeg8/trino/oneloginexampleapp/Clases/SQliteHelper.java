package com.trinoeg8.trino.oneloginexampleapp.Clases;

/**
 * Created by Trino on 17/01/2015.
 */
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SQliteHelper extends SQLiteOpenHelper {

    public static final String TABLE_NAME = "companies";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_CATEGORY = "category";
    public static final String COLUMN_URL = "url";
    public static final String COLUMN_ICON = "icon";
    private static final String DATABASE_NAME = "onelogin.db";
    private static final int DATABASE_VERSION =4;

    private static final String DATABASE_CREATE = "create table "
            + TABLE_NAME + "(" + COLUMN_ID
            + " integer primary key autoincrement, " + COLUMN_NAME + " text not null,"+COLUMN_CATEGORY+" text not null,"+COLUMN_ICON
            + " text not null, "+COLUMN_URL+" text not null);";

    public SQliteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}