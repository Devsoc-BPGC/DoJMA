package com.csatimes.dojma;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

/**
 * Created by Vikramaditya Kukreja on 11-07-2016.
 */

public class SortDialogFragment extends DialogFragment {

    SortDialogListener mListener;

    public SortDialogFragment() {

    }

    public static SortDialogFragment newInstance(String title) {
        SortDialogFragment frag = new SortDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }

    public void setListener(SortDialogListener mListener) {
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        SharedPreferences preferences = getActivity().getSharedPreferences(DHC
                .USER_PREFERENCES, Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = preferences.edit();

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(getArguments().getString("title"));

        builder.setPositiveButton("SET", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                editor.apply();
                mListener.onSortDialogPositiveClick();
            }
        });
        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });

        final String[] array = getResources().getStringArray(R.array.sort_options);
        boolean[] states = new boolean[array.length];
        int initialChecked = 1;

        for (int i = 0; i < array.length; i++) {
            states[i] = preferences.getBoolean
                    (DHC.SORT_SUFFIX + array[i], false);
            if (states[i]) initialChecked = i;
        }

        builder.setSingleChoiceItems(R.array.sort_options, initialChecked, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                for (int j = 0; j < array.length; j++) {
                    if (j != i)
                        editor.putBoolean(DHC.SORT_SUFFIX + array[j], false);
                    editor.putBoolean(DHC.SORT_SUFFIX + array[i], true);
                }
            }
        });
        return builder.create();
    }

    public interface SortDialogListener {
        void onSortDialogPositiveClick();

        void onSortDialogNegativeClick(DialogFragment dialog);
    }
}
