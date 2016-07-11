package com.csatimes.dojma;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.ContextThemeWrapper;
import android.util.Log;

/**
 * Created by Vikramaditya Kukreja on 09-07-2016.
 */

public class FilterDialogFragment extends DialogFragment {

    FilterDialogListener mListener;

    public FilterDialogFragment() {
    }

    public static FilterDialogFragment newInstance(String title) {
        Log.e("TAG", "new Instance requested");
        FilterDialogFragment frag = new FilterDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        SharedPreferences preferences = getActivity().getSharedPreferences(DHC
                .USER_PREFERENCES, Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = preferences.edit();

        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(getContext(), R.style
                .FilterDialogThemeLight));
        builder.setTitle(getArguments().getString("title"));
        builder.setPositiveButton("SET", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                mListener.onDialogPositiveClick(FilterDialogFragment.this);
                editor.apply();

            }
        });
        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                mListener.onFilterDialogNegativeClick(FilterDialogFragment.this);
            }
        });
        final String[] array = getResources().getStringArray(R.array.filter_options);
        boolean[] states = new boolean[array.length];
        for (int i = 0; i < array.length; i++) {
            states[i] = getActivity().getSharedPreferences(DHC.USER_PREFERENCES, Context.MODE_PRIVATE).getBoolean
                    (DHC.FILTER_SUFFIX + array[i], false);

        }
        builder.setMultiChoiceItems(R.array.filter_options, states, new DialogInterface
                .OnMultiChoiceClickListener() {


            @Override
            public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                editor.putBoolean(DHC.FILTER_SUFFIX + array[i], b);
            }
        });

        // Create the AlertDialog object and return it
        return builder.create();
    }

    public void setListener(FilterDialogListener mListener) {
        this.mListener = mListener;
    }

    /* The activity that creates an instance of this dialog fragment must
         * implement this interface in order to receive event callbacks.
         * Each method passes the DialogFragment in case the host needs to query it. */
    public interface FilterDialogListener {
        void onDialogPositiveClick(DialogFragment dialog);

        void onFilterDialogNegativeClick(DialogFragment dialog);
    }

}
