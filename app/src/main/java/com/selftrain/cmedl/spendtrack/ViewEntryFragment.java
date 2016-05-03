package com.selftrain.cmedl.spendtrack;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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

        Row[] rowItems = new Row[10];

        rowItems[0] = new Row("Restaurant", 130.25f, "F'in good ham");
        rowItems[1] = new Row("Clothes", 45.62f, "New hat");
        rowItems[2] = new Row("LCBO", 99.99f, "Cognac");
        rowItems[3] = new Row("LCBO2", 99.99f, "Cognac");
        rowItems[4] = new Row("LCBO3", 99.99f, "Cognac");
        rowItems[5] = new Row("LCBO4", 2345.99f, "Cognac");
        rowItems[6] = new Row("LCBO5", 99.99f, "Cognac");
        rowItems[7] = new Row("Grocer/Pharmacy", 99.99f, "Chips");
        rowItems[8] = new Row("LCBO7", 99.99f, "Wine");
        rowItems[9] = new Row("LCBO8", 99.99f, "Cognac");


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
