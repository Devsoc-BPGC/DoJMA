package com.csatimes.dojma.utilities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import androidx.appcompat.view.ContextThemeWrapper;

import com.csatimes.dojma.R;

/**
 * Created by Android on 21-01-2016.
 */
public class SimpleAlertDialog {
    Context context;
    String title;
    String message;
    private ClickListener listener;

    public SimpleAlertDialog() {
        listener = null;
    }

    // Assign the listener implementing events interface that will receive the events
    public void setClickListener(ClickListener listener) {
        this.listener = listener;
    }

    public void showDialog(Context context, String title, String message, String positiveButtonText, String negativeButtonText, boolean POSDisplay, boolean NEGDisplay) {
        this.context = context;
        this.title = title;
        this.message = message;
        AlertDialog.Builder ad = new AlertDialog.Builder(new ContextThemeWrapper(context, R.style
                .AlertDialogTheme));
        ad.setTitle(title);
        ad.setMessage(message);
        if (POSDisplay)
            ad.setPositiveButton(positiveButtonText, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (listener != null)
                        listener.onPosButtonClick();
                }
            });
        if (NEGDisplay)
            ad.setNegativeButton(negativeButtonText, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    listener.onNegButtonClick();
                }
            });
        ad.show();

    }

    public interface ClickListener {
        // These methods are the different events and
        // need to pass relevant arguments related to the event triggered
        void onPosButtonClick();

        // or when data has been loaded
        void onNegButtonClick();
    }
}
