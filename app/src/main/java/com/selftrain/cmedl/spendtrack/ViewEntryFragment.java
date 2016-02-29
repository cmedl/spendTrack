package com.selftrain.cmedl.spendtrack;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;

/**
 * A placeholder fragment containing a simple view.
 */
public class ViewEntryFragment extends ListFragment {


    public ViewEntryFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_entry, container, false);

        // http://techlovejump.com/android-listview-with-checkbox/
        // http://techlovejump.com/android-multicolumn-listview/
        setListAdapter(new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_1,
                MainActivity.listy));

        return view;
    }
}
