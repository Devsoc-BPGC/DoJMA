package com.csatimes.dojma;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by Vikramaditya Kukreja on 21-07-2016.
 */

public class UtilitiesViewHolder2 extends RecyclerView.ViewHolder implements View.OnClickListener {
    Button a;
    Button c;

    String amessLink = "https://raw.githubusercontent.com/MobileApplicationsClub/DoJMA-Assets-Repo/master/Images/Mess%20Menu/amess.jpg";
    String cmessLink = "https://raw.githubusercontent.com/MobileApplicationsClub/DoJMA-Assets-Repo/master/Images/Mess%20Menu/cmess.jpg";

    Context context;

    boolean hasWritePermission = true;
    File messFolder, file;

    Activity activity;

    public UtilitiesViewHolder2(View itemView, Context context, boolean hasWritePermission, Activity activity) {
        super(itemView);
        a = (Button) itemView.findViewById(R.id.viewholder_mess_format_a);
        c = (Button) itemView.findViewById(R.id.viewholder_mess_format_c);

        this.context = context;
        this.hasWritePermission = hasWritePermission;
        this.activity = activity;

        a.setOnClickListener(this);
        c.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        if (id == a.getId()) {
            /*
            messFolder = new File(directory + "/mess/");
            messFolder.mkdirs();
            final String imageName = "amess.jpg";
            file = new File(messFolder, imageName);
            if (file.exists() && !isOnline()) {
                Toast.makeText(context, "No Internet", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.fromFile(file), "image/*");
                context.startActivity(intent);
            } else if (!isOnline() && !file.exists()) {
                Toast.makeText(context, "No Internet! Can't download", Toast.LENGTH_SHORT).show();
            } else {
                if (hasWritePermission) {
                    startDownloadAndDisplay(file, amessLink, "amess.jpg");
                } else {
                    Toast.makeText(context, "Write denied!", Toast.LENGTH_SHORT).show();
                    context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(cmessLink)));
                }
            }*/
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
                    Uri.parse(amessLink),
                    new CustomTabActivityHelper.CustomTabFallback() {
                        @Override
                        public void openUri(Activity activity, Uri uri) {
                            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                            context.startActivity(intent);
                        }
                    });
        } else if (id == c.getId()) {
            /*messFolder = new File(directory + "/mess/");
            messFolder.mkdirs();
            final String imageName = "cmess.jpg";
            file = new File(messFolder, imageName);
            if (file.exists() && !isOnline()) {
                Toast.makeText(context, "No Internet", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.fromFile(file), "image/*");
                context.startActivity(intent);
            } else if (!isOnline() && !file.exists()) {
                Toast.makeText(context, "No Internet! Can't download", Toast.LENGTH_SHORT).show();
            } else {
                if (hasWritePermission) {
                    startDownloadAndDisplay(file, cmessLink, "cmess.jpg");
                } else {
                    Toast.makeText(context, "Write denied!", Toast.LENGTH_SHORT).show();
                    context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(cmessLink)));
                }
            }

*/
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
                    Uri.parse(cmessLink),
                    new CustomTabActivityHelper.CustomTabFallback() {
                        @Override
                        public void openUri(Activity activity, Uri uri) {
                            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                            context.startActivity(intent);
                        }
                    });
        }
    }

    private void startDownloadAndDisplay(final File file, final String link, String name) {
        RequestQueue queue = Volley.newRequestQueue(context);
        ImageRequest request = new ImageRequest(link, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {

                try {
                    FileOutputStream out = new FileOutputStream(file);
                    response.compress(Bitmap.CompressFormat.JPEG, 100, out);
                    out.flush();
                    out.close();
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setDataAndType(Uri.fromFile(file), "image/*");
                    context.startActivity(intent);
                } catch (Exception e) {
                    context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(link)));
                }

            }
        }, 0, 0, Bitmap.Config.ARGB_8888, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(link)));
            }
        });
        queue.add(request);
    }

    private boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context
                .CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return (netInfo != null && netInfo.isConnected());
    }
}
