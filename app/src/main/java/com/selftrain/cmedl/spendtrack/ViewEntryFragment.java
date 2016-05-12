package com.selftrain.cmedl.spendtrack;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.selftrain.cmedl.spendtrack.SpendingContract;
import com.selftrain.cmedl.spendtrack.SpendingContract.SpendingEntry;
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

        Cursor c = mDb.query(
                SpendingEntry.TABLE_NAME,
                SpendingContract.projection,
                null,null,null,null,
                SpendingContract.sortOrder
        );

        Row rowItems[] = null;
        if (c != null) {
            if (c.moveToFirst()) {
                int size = c.getCount();
                rowItems = new Row[size];
                int idx = 0;
                do {
                    String type = c.getString(c.getColumnIndex(SpendingEntry.COLUMN_NAME_TYPE));
                    String sAmount = c.getString(c.getColumnIndex(SpendingEntry.COLUMN_NAME_AMOUNT));
                    String note = c.getString(c.getColumnIndex(SpendingEntry.COLUMN_NAME_NOTE));
                    String iscash = c.getString(c.getColumnIndex(SpendingEntry.COLUMN_NAME_ISCASH));
                    Long date = c.getLong(c.getColumnIndex(SpendingEntry.COLUMN_NAME_DATE));
                    float f= Float.valueOf(sAmount);
                    rowItems[idx] = new Row(date, type, f, note, iscash);
                    idx++;
                } while (c.moveToNext());
            }
            c.close();
        }



        RowAdapter rowAdapter = new RowAdapter(getContext(), rowItems);
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
