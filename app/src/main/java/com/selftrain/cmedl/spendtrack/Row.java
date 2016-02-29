package com.selftrain.cmedl.spendtrack;

import java.util.Date;

/**
 * Created by chrismedl1 on 16-02-28.
 */
public class Row {
    Date mDate;
    String mType;
    int mAmount;
    String mNote;

    public void Row(Date date, String type, int amount, String note) {
        this.mDate = date;
        this.mType = type;
        this.mAmount = amount;
        this.mNote = note;
    }

    public Date getDate() {
        return mDate;
    }

    public String getType() {
        return mType;
    }

    public int getAmount() {
        return mAmount;
    }

    public String getNote() {
        return mNote;
    }
}
