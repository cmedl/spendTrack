package com.selftrain.cmedl.spendtrack;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.selftrain.cmedl.spendtrack.SpendingContract.SpendingEntry;

/**
 * Created by chrismedl1 on 16-05-01.
 */
public class SpendingEntryDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "SpendingData.db";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + SpendingEntry.TABLE_NAME + "(" +
                    SpendingEntry._ID + " INTEGER PRIMARY KEY," +
                    SpendingEntry.COLUMN_NAME_TYPE + " TEXT, " +
                    SpendingEntry.COLUMN_NAME_AMOUNT + " TEXT, " +
                    SpendingEntry.COLUMN_NAME_ISCASH + " TEXT, " +
                    SpendingEntry.COLUMN_NAME_DATE + " INTEGER, " +
                    SpendingEntry.COLUMN_NAME_NOTE + " TEXT " +
                    ");";

    public SpendingEntryDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // No upgrade at this time
    }

}
