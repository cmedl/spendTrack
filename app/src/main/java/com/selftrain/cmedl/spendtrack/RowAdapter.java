package com.selftrain.cmedl.spendtrack;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;


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
        convertView = inflater.inflate(R.layout.fragment_view_entry, parent, false);

        Row row = mRowItems[position];
        TextView dateView = (TextView) convertView.findViewById(R.id.date);
        TextView typeView = (TextView) convertView.findViewById(R.id.type);
        TextView amountView = (TextView) convertView.findViewById(R.id.amount);
        TextView noteView = (TextView) convertView.findViewById(R.id.note);

        dateView.setText(row.getDate().toString());
        typeView.setText(row.getType());
        amountView.setText(row.getAmount());
        noteView.setText(row.getNote());

        return convertView;
    }

}
