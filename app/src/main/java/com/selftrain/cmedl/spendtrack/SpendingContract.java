package com.selftrain.cmedl.spendtrack;

import android.provider.BaseColumns;

/**
 * Created by chrismedl1 on 16-05-01.
 */
public class SpendingContract {

    public SpendingContract() {}

    public static String[] projection = {
            SpendingEntry._ID,
            SpendingEntry.COLUMN_NAME_TYPE,
            SpendingEntry.COLUMN_NAME_NOTE,
            SpendingEntry.COLUMN_NAME_AMOUNT,
            SpendingEntry.COLUMN_NAME_ISCASH,
            SpendingEntry.COLUMN_NAME_DATE
    };

    public static String sortOrder =
            SpendingEntry.COLUMN_NAME_DATE + " DESC";

    public static abstract class SpendingEntry implements BaseColumns {
        public static final String TABLE_NAME = "SpendingEntry";
        public static final String COLUMN_NAME_TYPE = "type";
        public static final String COLUMN_NAME_NOTE = "note";
        public static final String COLUMN_NAME_AMOUNT = "amount";
        public static final String COLUMN_NAME_ISCASH = "iscash";
        public static final String COLUMN_NAME_DATE = "date";
    }
}
