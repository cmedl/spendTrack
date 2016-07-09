package com.selftrain.cmedl.spendtrack;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.selftrain.cmedl.spendtrack.SpendingContract;
import com.selftrain.cmedl.spendtrack.SpendingContract.SpendingEntry;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * A placeholder fragment containing a simple view.
 */
public class ViewEntryFragment extends ListFragment {

    final String TAG = "ViewEntry";

    public ViewEntryFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView");
        View view = inflater.inflate(R.layout.fragment_view_entry, container, false);

        SpendingEntryDbHelper mDbHelper = new SpendingEntryDbHelper(getActivity().getApplicationContext());
        SQLiteDatabase mDb = mDbHelper.getReadableDatabase();
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(System.currentTimeMillis());
        int month = cal.get(Calendar.MONTH);

        Cursor c = mDb.query(
                SpendingEntry.TABLE_NAME,
                SpendingContract.projection,
                null,null,null,null,
                SpendingContract.sortOrder
        );

        float totalSpent = 0;
        ArrayList<Row> rows = new ArrayList<>();

        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    String type = c.getString(c.getColumnIndex(SpendingEntry.COLUMN_NAME_TYPE));
                    String sAmount = c.getString(c.getColumnIndex(SpendingEntry.COLUMN_NAME_AMOUNT));
                    String note = c.getString(c.getColumnIndex(SpendingEntry.COLUMN_NAME_NOTE));
                    String iscash = c.getString(c.getColumnIndex(SpendingEntry.COLUMN_NAME_ISCASH));
                    String ispersonal = c.getString(c.getColumnIndex(SpendingEntry.COLUMN_NAME_ISPERSONAL));
                    Long date = c.getLong(c.getColumnIndex(SpendingEntry.COLUMN_NAME_DATE));

                    cal.setTimeInMillis(date);
                    int entryMonth = cal.get(Calendar.MONTH);



                    Log.i("MEDL", ":month:" + month);
                    if (month == entryMonth) {
                        float f = Float.valueOf(sAmount);
                        rows.add(new Row(date, type, f, note, iscash, ispersonal));
                        if (iscash.equals("false") && (ispersonal.equals("true"))) {
                            totalSpent += f;
                        }
                    }
                } while (c.moveToNext());
            }
            c.close();
        }

        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        float green = 1000/31;
        float yellow = 1000/31 * 5/4;

        TextView spendingTotal = (TextView) view.findViewById(R.id.spending_total);
        spendingTotal.setText("Total Spent: " +
                String.format(java.util.Locale.US,"%.2f",totalSpent) + " : " +
                String.format(java.util.Locale.US,"%.2f", day * green));
        if (totalSpent / day <= green) {
            spendingTotal.setBackgroundColor(Color.GREEN);
        } else if (totalSpent / day <= yellow) {
            spendingTotal.setBackgroundColor(Color.YELLOW);
        } else {
            spendingTotal.setBackgroundColor(Color.RED);
        }


        RowAdapter rowAdapter = new RowAdapter(getContext(), rows);
        ListView lv = (ListView) view.findViewById(android.R.id.list);
        // http://techlovejump.com/android-listview-with-checkbox/
        // http://techlovejump.com/android-multicolumn-listview/
        /*setListAdapter(new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_1,
                MainActivity.listy));*/

        lv.setAdapter(rowAdapter);

        return view;
    }
}
