package com.bobo.normalman.themuseandme.dialog;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

import com.bobo.normalman.themuseandme.R;
import com.bobo.normalman.themuseandme.model.Application;

/**
 * Created by xiaobozhang on 11/9/17.
 */

public class StatusDialogFragment extends DialogFragment {

    public interface onStatusSelectListener {
        void onStatusSelectListener(String item);
    }

    onStatusSelectListener mListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        mListener = (onStatusSelectListener) getActivity();
        final String[] items = new String[]{Application.Status.Wait.name(),
                Application.Status.Applied.name(),
                Application.Status.Review.name(),
                Application.Status.Interview.name(),
                Application.Status.Accept.name(),
                Application.Status.Reject.name()};
        alertDialogBuilder.setTitle(getString(R.string.status))
                .setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String item = items[i];
                        mListener.onStatusSelectListener(item);
                        dialogInterface.dismiss();
                    }
                });

        return alertDialogBuilder.create();
    }
}
