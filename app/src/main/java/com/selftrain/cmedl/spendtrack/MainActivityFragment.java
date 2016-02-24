package com.selftrain.cmedl.spendtrack;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "MainActivityFragment";

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main, container, false);

        view.findViewById(R.id.viewButton).setOnClickListener(this);
        view.findViewById(R.id.addButton).setOnClickListener(this);
        view.findViewById(R.id.delModButton).setOnClickListener(this);
        Log.i(TAG, "MEDL onCreateView");
        return view;
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.viewButton) {
            Log.i(TAG, "VIEW BUTTON CLICK");
            viewEntry();
        } else if (v.getId() == R.id.addButton) {
            Log.i(TAG, "ADD BUTTON CLICKY");
            //addEntry();
        } else {
            Log.i(TAG, "MOD/DEL BUTTON CLICK");
        }
    }

    void viewEntry() {
        Intent intent = new Intent(getActivity(), ViewEntry.class);
        startActivity(intent);
    }
}
