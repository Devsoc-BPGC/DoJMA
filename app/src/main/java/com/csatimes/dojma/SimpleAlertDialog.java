package com.csatimes.dojma;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by Android on 21-01-2016.
 */
public class SimpleAlertDialog {
    Context context;
    String title;
    String message;

    public void showDialog(Context context, String title, String message, String positiveButtonText, String negativeButtonText, boolean POSDisplay, boolean NEGDisplay) {
        this.context = context;
        this.title = title;
        this.message = message;
        AlertDialog.Builder ad = new AlertDialog.Builder(context);
        ad.setTitle(title);
        ad.setMessage(message);
        if (POSDisplay)
            ad.setPositiveButton(positiveButtonText, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
        if (NEGDisplay)
            ad.setNegativeButton(negativeButtonText, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
        ad.show();

    }
}
