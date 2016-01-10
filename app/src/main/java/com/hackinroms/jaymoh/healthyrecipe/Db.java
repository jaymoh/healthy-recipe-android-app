package com.hackinroms.jaymoh.healthyrecipe;

/**
 * Created by Jaymoh on 1/10/2016.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class Db {

    public static final String DATE_FORMAT = "dd/MM/yyyy";
    public static final String DATE_TIME_FORMAT = "HH:mm:ss dd/MM/yyyy";
    public static final String MONTH_FORMAT = "MM/yyyy";
    private static final String DATABASE_NAME = "HealthyRecipe";
    private static final int DATABASE_VERSION = 1;
    public static final String KEY_ID = "_id";
    private static final String TAG = "Db";
    private DatabaseHelper mDbHelper;
    public SQLiteDatabase mDb;
    private final Context context;

    private static class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            try {
                init(db);

            } catch (SQLException e) {
                Log.e(TAG, " ERROR:  " + e.getMessage());
            }
        }

        private void init(SQLiteDatabase db) {
            try
            {
                new Recipe().init(db);
            }
            catch (Exception e)
            {
                Log.e(TAG,e.getMessage());
            }

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.d(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            onCreate(db);
        }
    }

    /**
     * Constructor - takes the context to allow the database to be
     * opened/created
     *
     * @param ctx the Context within which to work
     */
    public Db(Context ctx) {
        this.context = ctx;
    }

    /**
     * Open the database. If it cannot be opened, try to create a new
     * instance of the database. If it cannot be created, throw an exception to
     * signal the failure
     *
     * @return this (self reference, allowing this to be chained in an
     * initialization call)
     * @throws SQLException if the database could be neither opened or created
     */
    public Db open() throws SQLException {
        mDbHelper = new DatabaseHelper(context);
        mDb = mDbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        mDbHelper.close();
    }

    public long insert(String table, ContentValues initialValues) {

        try {
            long l = mDb.insert(table, null, initialValues);
            return l;

        } catch (SQLException e) {
            Log.d(TAG,
                    "DB ERROR:  " + e.getMessage());
            return -1;
        }

    }

    public boolean delete(String table,long rowId) {
        Log.e(TAG,
                "DB TEST: id :" +rowId+" table :"+table+ " KEY_ID :"+KEY_ID);
        try {
            return mDb.delete(table, KEY_ID + "= ?", new String[]{String.valueOf(rowId)}) > 0;
        } catch (SQLException e) {
            Log.e(TAG,
                    "DB ERROR:  " + e.getMessage());
            return false;
        }

    }


    public Cursor fetchAll(String table) {

        try {
            String sortOrder = KEY_ID + " DESC ";
            Cursor c = mDb.query(table, null, null, null,null, null, sortOrder);
            return c;
        } catch (Exception e) {
            Log.d(TAG, " ERROR:  " + e.getMessage());
            return null;
        }
    }

    public Cursor fetchAll(String table, String sortOrder) {

        try {
            Cursor c = mDb.query(table, null, null, null,
                    null, null, sortOrder);
            return c;
        } catch (Exception e) {
            Log.d(TAG, " ERROR:  " + e.getMessage());
            return null;
        }
    }

    public Cursor fetchAllLike(String table, String likeColumn, String likeString) {

        try {
            String sortOrder = KEY_ID + " DESC ";
            Cursor mCursor = mDb.query( table, null, likeColumn+" LIKE ?", new String[]{likeString},
                    null, null, sortOrder);
            if (mCursor != null) {
                mCursor.moveToFirst();
                return mCursor;
            }

            return mCursor;
        } catch (Exception e) {
            Log.d(TAG, " ERROR:  " + e.getMessage());
            return null;
        }

    }


    public Cursor fetchLim(String table, String lim) {

        try {
            String sortOrder = KEY_ID + " DESC ";
            Cursor c = mDb.query(table, null, null, null,
                    null, null, sortOrder, lim);
            return c;
        } catch (Exception e) {
            Log.d(TAG, " ERROR:  " + e.getMessage());
            return null;
        }

    }


    public Cursor fetch(String table,long _id) {

        Cursor mCursor = null;
        try {
            mCursor = mDb.query(true, table, null, KEY_ID + "=" + _id, null,
                    null, null, null, null);
            if (mCursor != null) {
                mCursor.moveToFirst();
                return mCursor;
            }
        } catch (SQLException e) {
            Log.d(TAG,
                    " ERROR:  " + e.getMessage());
        }
        return null;

    }

    public boolean update(String table, long _id, ContentValues args) {

        return mDb.update(table, args, KEY_ID + "=" + _id, null) > 0;
    }

    public boolean update(String table, String col, String colVal, ContentValues args) {

        return mDb.update(table, args, col + "=" + colVal, null) > 0;
    }


}

