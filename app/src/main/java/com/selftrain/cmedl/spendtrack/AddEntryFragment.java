package com.selftrain.cmedl.spendtrack;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.selftrain.cmedl.spendtrack.SpendingContract.SpendingEntry;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * A placeholder fragment containing a simple view.
 */
public class AddEntryFragment extends Fragment
        implements
        View.OnClickListener,
        AdapterView.OnItemSelectedListener,
        TextWatcher {

    SpendingEntryDbHelper mDbHelper;
    SQLiteDatabase mDb;
    CheckBox isCash;
    Spinner type;
    EditText amount;
    EditText note;
    Button save;
    Button dateButton;
    long mEntryDate;

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
        save = (Button) view.findViewById(R.id.save_button);
        dateButton = (Button) view.findViewById(R.id.dateButton);

        save.setEnabled(false);

        String[] spendingTypes = getResources().getStringArray(R.array.spending_types);
        ArrayAdapter<String> typeAdapter =
                new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, spendingTypes);

        Log.i(TAG, "Just before button stuff");

        type.setAdapter(typeAdapter);

        type.setOnItemSelectedListener(this);
        save.setOnClickListener(this);
        amount.addTextChangedListener(this);
        dateButton.setOnClickListener(this);
        return view;
    }

    private void setSaveEnabledIfReady() {
        boolean enable = true;
        if (mDbHelper == null) {
            mDbHelper = new SpendingEntryDbHelper(getActivity().getApplicationContext());
        }
        if (mDb == null) {
            mDb = mDbHelper.getWritableDatabase();
        }
        Log.i(TAG, "setSaveEnabledIfReady");
        if (type.getSelectedItem().toString().isEmpty() ||
                amount.getText().toString().isEmpty()) {
            Log.i(TAG, "Dude, type or amount is empty");
            enable = false;
        }
        save.setEnabled(enable);
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
        if (v == dateButton) {
            dateClick(v);
        } else if (v == save) {
            saveClick();
        }
    }

    private void saveClick() {
        dump();
        String entryCash = isCash.isChecked() ? "true" : "false";
        String entryType = type.getSelectedItem().toString();
        String entryAmount = amount.getText().toString();
        String entryNote = note.getText().toString();
        if (mEntryDate == 0) {
            mEntryDate = System.currentTimeMillis();
        }

        ContentValues values = new ContentValues();
        values.put(SpendingEntry.COLUMN_NAME_TYPE, entryType);
        values.put(SpendingEntry.COLUMN_NAME_AMOUNT, entryAmount);
        values.put(SpendingEntry.COLUMN_NAME_ISCASH, entryCash);
        values.put(SpendingEntry.COLUMN_NAME_DATE, mEntryDate);
        values.put(SpendingEntry.COLUMN_NAME_NOTE, entryNote);

        long newRowId;
        newRowId = mDb.insert(
                SpendingEntry.TABLE_NAME,
                null,
                values);

        Log.i(TAG, "...Added column with rowId: " +
                newRowId + ":" +
                mEntryDate + ":" +
                entryType + ":" +
                entryAmount + ":" +
                entryCash + ":" +
                entryNote);

        getActivity().finish();

    }
    private void dateClick(View v) {
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
                Calendar calendar = new GregorianCalendar(year, month , day);
                Date date = calendar.getTime();
                mEntryDate = date.getTime();

            }
        }
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), "datePicker");
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        Log.i(TAG, "onItemSelected" +
                "");
        dump();
        setSaveEnabledIfReady();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Log.i(TAG, "Dude, onNothingSelected............................");
        dump();
        setSaveEnabledIfReady();
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
        setSaveEnabledIfReady();
    }

}
