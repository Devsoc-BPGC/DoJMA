package com.csatimes.dojma;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

/**
 * Created by Vikramaditya Kukreja on 21-07-2016.
 */

class UtilitiesViewHolder2 extends RecyclerView.ViewHolder implements View.OnClickListener {
    Button a;
    Context context;
    Activity activity;
    private Button c;
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference amessRef = storage.getReference().child("images").child("mess").child
            ("amess" +
                    ".jpg");
    private StorageReference cmessRef = storage.getReference().child("images").child("mess").child
            ("cmess" +
                    ".jpg");
    private boolean hasWritePermission = true;

    UtilitiesViewHolder2(View itemView, Context context, boolean hasWritePermission, Activity activity) {
        super(itemView);
        a = (Button) itemView.findViewById(R.id.viewholder_mess_format_a);
        c = (Button) itemView.findViewById(R.id.viewholder_mess_format_c);

        this.context = context;
        this.hasWritePermission = hasWritePermission;
        this.activity = activity;

        a.setOnClickListener(this);
        c.setOnClickListener(this);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        if (id == a.getId()) {
            if (isOnline()) {
                Toast.makeText(context, "Loading please wait", Toast.LENGTH_SHORT).show();
                amessRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        CustomTabsIntent customTabsIntent = new CustomTabsIntent.Builder()
                                .setShowTitle(true)
                                .setToolbarColor(ContextCompat.getColor(context, R.color
                                        .colorPrimary))
                                .setCloseButtonIcon(BitmapFactory.decodeResource(context
                                        .getResources(), R.drawable.ic_arrow_back_white_24dp))
                                .setStartAnimations(context, R.anim.slide_in_right, R.anim.fade_out)
                                .setExitAnimations(context, R.anim.fade_in, R.anim.slide_out_right)
                                .addDefaultShareMenuItem()
                                .enableUrlBarHiding()
                                .build();

                        CustomTabActivityHelper.openCustomTab(activity, customTabsIntent,
                                uri,
                                new CustomTabActivityHelper.CustomTabFallback() {
                                    @Override
                                    public void openUri(Activity activity, Uri uri) {
                                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                        context.startActivity(intent);
                                    }
                                });

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "Link is invalid!", Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                Toast.makeText(context, "Offline feature will be added soon. Please keep saving " +
                        "the weekly mess menu for now", Toast
                        .LENGTH_LONG)
                        .show();
            }
        } else if (id == c.getId()) {
            if (isOnline()) {
                Toast.makeText(context, "Loading please wait", Toast.LENGTH_SHORT).show();
                cmessRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        CustomTabsIntent customTabsIntent = new CustomTabsIntent.Builder()
                                .setShowTitle(true)
                                .setToolbarColor(ContextCompat.getColor(context, R.color
                                        .colorPrimary))
                                .setCloseButtonIcon(BitmapFactory.decodeResource(context
                                        .getResources(), R.drawable.ic_arrow_back_white_24dp))
                                .setStartAnimations(context, R.anim.slide_in_right, R.anim.fade_out)
                                .setExitAnimations(context, R.anim.fade_in, R.anim.slide_out_right)
                                .addDefaultShareMenuItem()
                                .enableUrlBarHiding()
                                .build();

                        CustomTabActivityHelper.openCustomTab(activity, customTabsIntent,
                                uri,
                                new CustomTabActivityHelper.CustomTabFallback() {
                                    @Override
                                    public void openUri(Activity activity, Uri uri) {
                                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                        context.startActivity(intent);
                                    }
                                });

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "Link is invalid!", Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                Toast.makeText(context, "Offline feature will be added soon. Please keep saving " +
                        "the weekly mess menu for now", Toast
                        .LENGTH_LONG)
                        .show();
            }
        } else {
            Intent intent = new Intent(context, MessMenus.class);
            context.startActivity(intent);
        }
    }


    private boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context
                .CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return (netInfo != null && netInfo.isConnected());
    }
}
