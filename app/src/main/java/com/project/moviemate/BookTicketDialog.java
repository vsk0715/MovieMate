package com.project.moviemate;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

public class BookTicketDialog extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Inflate the layout for the dialog
        builder.setView(getActivity().getLayoutInflater().inflate(R.layout.dialog_layout, null));
        // You can add buttons, fields, etc. to the dialog layout
        return builder.create();
    }
}
