package com.bobo.normalman.themuseandme.dialog;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;

import java.util.Calendar;

/**
 * Created by xiaobozhang on 11/9/17.
 */

public class DateDialogFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    DatePickerDialog.OnDateSetListener listener;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        listener = (DatePickerDialog.OnDateSetListener)getActivity();
        listener.onDateSet(datePicker, year, month, day);
    }
}
