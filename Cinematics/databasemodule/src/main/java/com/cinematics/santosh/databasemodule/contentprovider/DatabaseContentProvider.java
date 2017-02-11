package com.cinematics.santosh.databasemodule.contentprovider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.cinematics.santosh.databasemodule.DBManager;
import com.cinematics.santosh.databasemodule.DatabaseConstants;

/**
 * Created by santosh on 2/9/17.
 */

public class DatabaseContentProvider extends ContentProvider {

    private SQLiteDatabase mDatabase;

    private static final UriMatcher mUriMatcher;

    private static final int MOVIE_URI_ID = 1;

    private static final int TVSERIES_URI_ID = 2;

    private static final int CELEBRITY_URI_ID = 3;

    static {
        mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        mUriMatcher.addURI(DatabaseConstants.AUTHORITY, DatabaseConstants.MOVIE_TABLE, MOVIE_URI_ID);
        mUriMatcher.addURI(DatabaseConstants.AUTHORITY, DatabaseConstants.TVSERIES_TABLE, TVSERIES_URI_ID);
        mUriMatcher.addURI(DatabaseConstants.AUTHORITY, DatabaseConstants.CELEBRITY_TABLE, CELEBRITY_URI_ID);
    }

    @Override
    public boolean onCreate() {
        mDatabase = new DBManager(getContext()).getWritableDatabase();
        return mDatabase != null;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor cursor = mDatabase.query(getTableNameFromURI(uri), projection, selection, selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long id = -1;
        String tableName = getTableNameFromURI(uri);
        try {
            id = mDatabase.insertOrThrow(tableName, null, values);
        } catch (SQLiteConstraintException e) {
            Toast.makeText(getContext(), "This is already your favourite", Toast.LENGTH_SHORT).show();
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return ContentUris.withAppendedId(uri, id);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int rowsDeleted = mDatabase.delete(getTableNameFromURI(uri), selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int rowsUpdated = mDatabase.update(getTableNameFromURI(uri), values, selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsUpdated;
    }

    private String getTableNameFromURI(Uri uri) {
        String tableName;
        switch (mUriMatcher.match(uri)) {
            case MOVIE_URI_ID:
                tableName = DatabaseConstants.MOVIE_TABLE;
                break;
            case TVSERIES_URI_ID:
                tableName = DatabaseConstants.TVSERIES_TABLE;
                break;
            case CELEBRITY_URI_ID:
                tableName = DatabaseConstants.CELEBRITY_TABLE;
                break;
            default:
                tableName = "";
        }
        return tableName;
    }


}
