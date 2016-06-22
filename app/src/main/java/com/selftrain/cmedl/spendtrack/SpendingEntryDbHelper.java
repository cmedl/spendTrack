package com.selftrain.cmedl.spendtrack;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.selftrain.cmedl.spendtrack.SpendingContract.SpendingEntry;

/**
 * Created by chrismedl1 on 16-05-01.
 */
public class SpendingEntryDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "SpendingData.db";

    public final String TAG = "SpendingEntryDBHelper";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + SpendingEntry.TABLE_NAME + "(" +
                    SpendingEntry._ID + " INTEGER PRIMARY KEY," +
                    SpendingEntry.COLUMN_NAME_TYPE + " TEXT, " +
                    SpendingEntry.COLUMN_NAME_AMOUNT + " TEXT, " +
                    SpendingEntry.COLUMN_NAME_ISCASH + " TEXT, " +
                    SpendingEntry.COLUMN_NAME_DATE + " INTEGER, " +
                    SpendingEntry.COLUMN_NAME_NOTE + " TEXT, " +
                    SpendingEntry.COLUMN_NAME_ISPERSONAL + " TEXT " +
                    ");";

    public SpendingEntryDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i(TAG, "onUpgrade " + oldVersion + ":" + newVersion);
        switch (oldVersion) {
            case 1:
                final String ADD_ISPERSONAL_COLUMN =
                        "ALTER TABLE " + SpendingEntry.TABLE_NAME +
                                " ADD COLUMN " + SpendingEntry.COLUMN_NAME_ISPERSONAL +
                                " TEXT;";
                final String UPDATE_ISPERSONAL =
                        "UPDATE " + SpendingEntry.TABLE_NAME +
                                " SET " + SpendingEntry.COLUMN_NAME_ISPERSONAL + "='false'";
                db.execSQL(ADD_ISPERSONAL_COLUMN);
                db.execSQL(UPDATE_ISPERSONAL);
                break;
            default:
                throw new IllegalStateException("onUpgrade()  unexpected oldVersion " + oldVersion);

        }

        // No upgrade at this time
    }

}
