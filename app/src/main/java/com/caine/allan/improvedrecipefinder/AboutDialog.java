package com.caine.allan.improvedrecipefinder;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

/**
 * Created by allancaine on 2015-10-28.
 */
public class AboutDialog extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getActivity())
                .setTitle(R.string.attribution_dialog_title)
                .setMessage(R.string.attribution_dialog_content)
                .setPositiveButton(android.R.string.ok, null)
                .create();
    }
}
