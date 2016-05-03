package com.selftrain.cmedl.spendtrack;

import android.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import org.w3c.dom.Text;

/**
 * A placeholder fragment containing a simple view.
 */
public class AddEntryFragment extends Fragment
        implements
        View.OnClickListener,
        AdapterView.OnItemSelectedListener,
        TextWatcher {


    CheckBox isCash;
    Spinner type;
    EditText amount;
    EditText note;
    Button button;

    final String TAG = "AddEntry";
    public AddEntryFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView");
        View view = inflater.inflate(R.layout.fragment_add_entry, container, false);

        isCash = (CheckBox) view.findViewById(R.id.isCash);
        type = (Spinner) view.findViewById(R.id.inputType);
        amount = (EditText) view.findViewById(R.id.inputAmount);
        note = (EditText) view.findViewById(R.id.inputNote);
        button = (Button) view.findViewById(R.id.save_button);
        button.setEnabled(false);

        String[] spendingTypes = getResources().getStringArray(R.array.spending_types);
        ArrayAdapter<String> typeAdapter =
                new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, spendingTypes);

        Log.i(TAG, "Just before button stuff");

        type.setAdapter(typeAdapter);

        type.setOnItemSelectedListener(this);
        button.setOnClickListener(this);
        amount.addTextChangedListener(this);
        return view;
    }

    private void setButtonEnabledIfReady() {
        boolean enable = true;
        Log.i(TAG, "setButtonEnabledIfReady");
        if (type.getSelectedItem().toString().isEmpty() ||
                amount.getText().toString().isEmpty()) {
            Log.i(TAG, "Dude, type or amount is empty");
            enable = false;
        }
        button.setEnabled(enable);
    }
    public void dump() {
        boolean entryCash = isCash.isChecked();
        String entryType = type.getSelectedItem().toString();
        String entryAmount = amount.getText().toString();
        String entryNote = note.getText().toString();
        if (entryAmount.isEmpty()) {
            Log.i(TAG, "AMOUNT IS EMPTY MAN.");
        }
        if (entryType.isEmpty()) {
            Log.i(TAG, "TYPE IS EMPTY MAN.");
        }
        Log.i(TAG, "HERE WE ARE eh");
        Log.i(TAG, "cash: " + entryCash);
        Log.i(TAG, "type: " + entryType);
        Log.i(TAG, "amount: " + entryAmount);
        Log.i(TAG, "note: " + entryNote);
    }

    @Override
    public void onClick(View v) {
        Log.i(TAG, "onClick");
        dump();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        Log.i(TAG, "onItemSelected" +
                "");
        dump();
        setButtonEnabledIfReady();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Log.i(TAG, "Dude, onNothingSelected............................");
        dump();
        setButtonEnabledIfReady();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable s) {
        Log.i(TAG, "afterTextChanged");
        dump();
        setButtonEnabledIfReady();
    }

}
