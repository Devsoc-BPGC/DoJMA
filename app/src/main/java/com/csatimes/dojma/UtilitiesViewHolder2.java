package com.csatimes.dojma;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;

import java.io.File;
import java.io.FileOutputStream;

import static com.csatimes.dojma.DHC.directory;

/**
 * Created by Vikramaditya Kukreja on 21-07-2016.
 */

public class UtilitiesViewHolder2 extends RecyclerView.ViewHolder implements View.OnClickListener {
    Button a;
    Button c;

    String amessLink = "https://cdn.rawgit.com/MobileApplicationsClub/DoJMA-Assets-Repo/master/Images/Mess%20Menu/amess.jpg";
    String cmessLink = "https://cdn.rawgit.com/MobileApplicationsClub/DoJMA-Assets-Repo/master/Images/Mess%20Menu/cmess.jpg";

    Context context;

    boolean hasWritePermission = false;
    File messFolder, file;

    public UtilitiesViewHolder2(View itemView, Context context, boolean hasWritePermission) {
        super(itemView);
        a = (Button) itemView.findViewById(R.id.viewholder_mess_format_a);
        c = (Button) itemView.findViewById(R.id.viewholder_mess_format_c);

        this.context = context;
        this.hasWritePermission = hasWritePermission;

        a.setOnClickListener(this);
        c.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        if (id == a.getId()) {
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
                    RequestQueue queue = Volley.newRequestQueue(context);
                    ImageRequest request = new ImageRequest(amessLink, new Response.Listener<Bitmap>() {
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
                                context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(amessLink)));
                            }

                        }
                    }, 0, 0, Bitmap.Config.ARGB_8888, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(amessLink)));
                        }
                    });
                    queue.add(request);
                } else {
                    Toast.makeText(context, "Write denied!", Toast.LENGTH_SHORT).show();
                    context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(cmessLink)));
                }
            }
        } else if (id == c.getId()) {
            messFolder = new File(directory + "/mess/");
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
                    RequestQueue queue = Volley.newRequestQueue(context);
                    ImageRequest request = new ImageRequest(cmessLink, new Response.Listener<Bitmap>() {
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
                                context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(cmessLink)));
                            }

                        }
                    }, 0, 0, Bitmap.Config.ARGB_8888, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(cmessLink)));
                        }
                    });
                    queue.add(request);
                } else {
                    Toast.makeText(context, "Write denied!", Toast.LENGTH_SHORT).show();
                    context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(cmessLink)));
                }
            }


        }
    }

    private boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context
                .CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return (netInfo != null && netInfo.isConnected());
    }
}
