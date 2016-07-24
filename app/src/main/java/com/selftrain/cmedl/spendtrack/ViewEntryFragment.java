package com.selftrain.cmedl.spendtrack;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;

import android.app.ListFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
//import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;

import com.selftrain.cmedl.spendtrack.SpendingContract;
import com.selftrain.cmedl.spendtrack.SpendingContract.SpendingEntry;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * A placeholder fragment containing a simple view.
 */
public class ViewEntryFragment extends ListFragment {

    final String TAG = "ViewEntry";

    private View mRootview;
    private SpendingEntryDbHelper mDbHelper;
    private SQLiteDatabase mDb;
    private int mMonth;

    public ViewEntryFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDbHelper = new SpendingEntryDbHelper(getActivity().getApplicationContext());
        mDb = mDbHelper.getReadableDatabase();
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(System.currentTimeMillis());
        mMonth = cal.get(Calendar.MONTH);
    }

    private void populateList() {
        Cursor c = mDb.query(
                SpendingEntry.TABLE_NAME,
                SpendingContract.projection,
                null,null,null,null,
                SpendingContract.sortOrder
        );

        float totalSpent = 0;
        ArrayList<Row> rows = new ArrayList<>();
        Calendar cal = Calendar.getInstance();

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

                    Log.i("MEDL", ":month:" + mMonth);
                    if (mMonth == entryMonth) {
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
        int month = calendar.get(Calendar.MONTH);
        // If the month to view is not this month, mark the day as the last day of the month
        int day = month == mMonth ? calendar.get(Calendar.DAY_OF_MONTH) : 31;
        float green = 1000/31;
        float yellow = 1000/31 * 5/4;

        TextView spendingTotal = (TextView) mRootview.findViewById(R.id.spending_total);
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


        RowAdapter rowAdapter = new RowAdapter(getActivity().getApplicationContext(), rows);
        ListView lv = (ListView) mRootview.findViewById(android.R.id.list);

        lv.setAdapter(rowAdapter);
        lv.setClickable(true);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i(TAG, "onItemClick: " + id);
            }
        });
    }

    private void getMonth(View v) {
        class DatePickerFragment extends DialogFragment
                implements DatePickerDialog.OnDateSetListener {

            @Override
            public Dialog onCreateDialog(Bundle savedInstanceState) {
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                return new DatePickerDialog(getActivity(), this, year, month, day);
            }

            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                Log.i(TAG, "MEDL onDateSet: " + year + " " + month + " " + day);
                mMonth = month;
                populateList();
            }
        }
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), "datePicker");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView");
        mRootview = inflater.inflate(R.layout.fragment_view_entry, container, false);
        mRootview.findViewById(R.id.addButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "ADD BUTTON CLICKY");
                Intent intent = new Intent(getActivity(), AddEntry.class);
                startActivity(intent);
            }
        });
        mRootview.findViewById(R.id.change_month).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getMonth(v);
            }
        });

        return mRootview;
    }

    @Override
    public void onResume() {
        super.onResume();
        populateList();
    }
}
