package com.selftrain.cmedl.spendtrack;

import android.provider.BaseColumns;

/**
 * Created by chrismedl1 on 16-05-01.
 */
public class SpendingContract {

    public SpendingContract() {}

    public static abstract class SpendingEntry implements BaseColumns {
        public static final String TABLE_NAME = "entry";
        public static final String COLUMN_NAME_ENTRY_ID = "entryid";
        public static final String COLUMN_NAME_TYPE = "type";
        public static final String COLUMN_NAME_NOTE = "note";
        public static final String COLUMN_NAME_AMOUNT = "amount";
        public static final String COLUMN_NAME_ISCASH = "iscash";
        public static final String COLUMN_NAME_DATE = "date";


    }
}
