package com.selftrain.cmedl.spendtrack;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by chrismedl1 on 16-02-28.
 */
public class Row {
    Calendar mDate;
    String mType;
    float mAmount;
    String mNote;

    public Row(String type, float amount, String note) {
        this.mDate = Calendar.getInstance();
        this.mType = type;
        this.mAmount = amount;
        this.mNote = note;
    }

    public String getDate() {
        SimpleDateFormat date = new SimpleDateFormat("EEE-MMM-dd");
        return date.format(mDate.getTime());
    }

    public String getType() {
        return mType;
    }

    public float getAmount() {
        return mAmount;
    }

    public String getNote() {
        return mNote;
    }
}
