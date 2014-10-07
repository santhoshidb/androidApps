
package com.chubby.chubbySleep;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class chubbySleepDBHelper {

    public static final String KEY_CREATEDATE = "createdate";
    public static final String KEY_PERCENTILE = "percentile";
    public static final String KEY_BIRTHDATE ="birthdate";
    public static final String KEY_ROWID = "_id";
    private static final String TAG = "ChubbySleepDBAdapter";
    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;

    private static final String DATABASE_CREATE =
        "create table sleephistory (_id integer primary key autoincrement, "
        + "createdate text not null, percentile text not null, birthdate text not null);";

    private static final String DATABASE_NAME = "chubbysleep";
    private static final String DATABASE_TABLE = "sleephistory";
    private static final int DATABASE_VERSION = 1;

    private final Context mCtx;

    private int rowCount = 0;
    private static class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            db.execSQL(DATABASE_CREATE); 
            
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS sleephistory");
            onCreate(db);
        }
    }

   
    public chubbySleepDBHelper(Context ctx) {
        this.mCtx = ctx;
    }

   
    public chubbySleepDBHelper open() throws SQLException {
        mDbHelper = new DatabaseHelper(mCtx);
        mDb = mDbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        mDbHelper.close();
    }


    public long createNote(String createdate, String percentile, String birthdate) {
    	
    	//Check if any data exists in the table
    	
    	Cursor c = fetchAllNotes();
    	
    	if (c.getCount()>0){
    		ContentValues initialValues1 = new ContentValues();
            initialValues1.put(KEY_CREATEDATE, "Created");
            initialValues1.put(KEY_PERCENTILE, "Percentile");
            initialValues1.put(KEY_BIRTHDATE, "Birthday");
    	}
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_CREATEDATE, createdate);
        initialValues.put(KEY_PERCENTILE, percentile);
        initialValues.put(KEY_BIRTHDATE, birthdate);
        
        return mDb.insert(DATABASE_TABLE, null, initialValues);
    }

   
    public boolean deleteNote(long rowId) {

        return mDb.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
    }

    /**
     * Return a Cursor over the list of all rows in the database
     */
    public Cursor fetchAllNotes() {

        return mDb.query(DATABASE_TABLE, new String[] {KEY_ROWID, KEY_CREATEDATE,
                KEY_PERCENTILE, KEY_BIRTHDATE}, null, null, null, null, null);
    }

    /**
     * Return a Cursor positioned at the note that matches the given rowId
     * 
     * @param rowId id of note to retrieve
     * @return Cursor positioned to matching note, if found
     * @throws SQLException if note could not be found/retrieved
     */
    public Cursor fetchNote(long rowId) throws SQLException {

        Cursor mCursor =

            mDb.query(true, DATABASE_TABLE, new String[] {KEY_ROWID,
                    KEY_CREATEDATE, KEY_PERCENTILE, KEY_BIRTHDATE}, KEY_ROWID + "=" + rowId, null,
                    null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        this.close();
        return mCursor;

    }

    public boolean updateNote(long rowId, String createdate, String percentile, String birthdate) {
        ContentValues args = new ContentValues();
        args.put(KEY_CREATEDATE, createdate);
        args.put(KEY_PERCENTILE, percentile);
        args.put(KEY_BIRTHDATE, birthdate);

        return mDb.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
    }
}
