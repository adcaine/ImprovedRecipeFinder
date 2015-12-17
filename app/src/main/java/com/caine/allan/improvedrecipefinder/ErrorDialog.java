package com.caine.allan.improvedrecipefinder;

import android.support.v4.app.DialogFragment;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;

/**
 * Created by allancaine on 2015-12-16.
 */
public class ErrorDialog extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getActivity())
                .setTitle(R.string.network_error_title)
                .setMessage(R.string.network_error_description)
                .setPositiveButton(android.R.string.ok, null)
                .create();
    }
}
