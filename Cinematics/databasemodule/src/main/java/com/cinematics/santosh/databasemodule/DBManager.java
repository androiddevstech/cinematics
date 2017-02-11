package com.cinematics.santosh.databasemodule;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by 511470 on 2/9/17.
 */

public class DBManager extends SQLiteOpenHelper {

    private final static int DB_VERSION = 2;

    public DBManager(Context context) {
        super(context, DatabaseConstants.DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DatabaseConstants.CREATE_MOVIE_TABLE);
        db.execSQL(DatabaseConstants.CREATE_TVSERIES_TABLE);
        db.execSQL(DatabaseConstants.CREATE_CELEBRITY_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        switch (oldVersion) {
            case 1:
                db.execSQL(DatabaseConstants.CREATE_MOVIE_TABLE.replaceFirst(DatabaseConstants.MOVIE_TABLE, DatabaseConstants.MOVIE_BACKUP));
                db.execSQL(DatabaseConstants.COPY_MOVIE_RECORDS);
                db.execSQL("DROP TABLE IF EXISTS " + DatabaseConstants.MOVIE_TABLE);
                db.execSQL("ALTER TABLE " + DatabaseConstants.MOVIE_BACKUP + " RENAME TO " + DatabaseConstants.MOVIE_TABLE);
        }
    }
}
