package com.selftrain.cmedl.spendtrack;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;


/**
 * Created by chrismedl1 on 16-02-28.
 */
public class RowAdapter extends ArrayAdapter<Row> {
    Context mContext;
    public Row[] mRowItems = null;

    public RowAdapter(Context context, Row[] rowItems) {
        super(context, R.layout.view_row, rowItems);
        this.mContext = context;
        this.mRowItems = rowItems;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = ((Activity)mContext).getLayoutInflater();
        convertView = inflater.inflate(R.layout.view_row, parent, false);

        Log.i("RowAdapter", "WE ARE GETTING VIEW: " + position);
        Row row = mRowItems[position];

        if (row != null) {
            CheckBox cashView = (CheckBox) convertView.findViewById(R.id.isCash);
            TextView dateView = (TextView) convertView.findViewById(R.id.date);
            TextView typeView = (TextView) convertView.findViewById(R.id.type);
            TextView amountView = (TextView) convertView.findViewById(R.id.amount);
            TextView noteView = (TextView) convertView.findViewById(R.id.note);

            cashView.setChecked(row.mIsCash);
            cashView.setEnabled(false);
            typeView.setText(row.getType());
            dateView.setText(row.getDate().toString());

            Log.i("RowAdapter", "DATE = " + row.getDate().toString());
            amountView.setText(String.format("%.2f", row.getAmount()));
            noteView.setText(row.getNote());
        }
        return convertView;
    }

}
