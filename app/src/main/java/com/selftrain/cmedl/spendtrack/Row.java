package com.selftrain.cmedl.spendtrack;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by chrismedl1 on 16-02-28.
 */
public class Row {
    Long mDate;
    String mType;
    float mAmount;
    String mNote;
    boolean mIsCash;

    public Row(Long date, String type, float amount, String note, String isCash) {
        this.mDate = date;
        this.mType = type;
        this.mAmount = amount;
        this.mNote = note;
        this.mIsCash = isCash.equals("true") ? true : false;
    }

    public String getDate() {
        DateFormat dateformat = DateFormat.getDateInstance();
        Date date = new Date(mDate);

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(mDate);
        return dateformat.format(date);
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

    public boolean isCash() {
        return mIsCash;
    }
}
