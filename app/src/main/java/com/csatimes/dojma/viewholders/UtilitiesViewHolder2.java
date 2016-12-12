package com.csatimes.dojma.viewholders;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.csatimes.dojma.MessMenus;
import com.csatimes.dojma.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;

import static com.csatimes.dojma.utilities.DHC.directory;

/**
 * Created by Vikramaditya Kukreja on 21-07-2016.
 */

public class UtilitiesViewHolder2 extends RecyclerView.ViewHolder implements View.OnClickListener {
    Button a;
    Context context;
    Activity activity;
    DownloadManager downloadManager;
    private Button c;
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference amessRef = storage.getReference().child("images").child("mess").child
            ("amess" +
                    ".jpg");
    private StorageReference cmessRef = storage.getReference().child("images").child("mess").child
            ("cmess" +
                    ".jpg");
    private boolean hasWritePermission = true;

    public UtilitiesViewHolder2(View itemView, Context context, boolean hasWritePermission, Activity activity) {
        super(itemView);
        a = (Button) itemView.findViewById(R.id.viewholder_mess_format_a);
        c = (Button) itemView.findViewById(R.id.viewholder_mess_format_c);

        this.context = context;
        this.hasWritePermission = hasWritePermission;
        this.activity = activity;

        a.setOnClickListener(this);
        c.setOnClickListener(this);
        itemView.setOnClickListener(this);
        downloadManager = (DownloadManager) context
                .getSystemService(Context.DOWNLOAD_SERVICE);

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        if (id == a.getId()) {
            if (isOnline() && hasWritePermission) {
                Toast.makeText(context, "Downloading will start soon", Toast.LENGTH_LONG).show();
                amessRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        File file = new File(directory + "/mess/", "amess.jpg");
                        if (file.exists()) {
                            boolean delete = file.delete();
                            if (!delete) {
                                Log.e("TAG", "Delete old image manually");
                            }
                        }
                        DownloadManager.Request request = new DownloadManager.Request(uri)
                                .setTitle("A mess menu").setNotificationVisibility
                                        (DownloadManager.Request
                                                .VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                                .setMimeType
                                        ("image/*").setDestinationUri(Uri.fromFile(file));
                        downloadManager.enqueue(request);


                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "Link is invalid!", Toast.LENGTH_SHORT).show();
                    }
                });
            } else if (!hasWritePermission) {
                Toast.makeText(context, "Consider allowing write permission", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.fromFile(new File(directory +
                        "/mess/", "amess.jpg")), "image/*");
                try {
                    context.startActivity(intent);
                } catch (Exception e) {
                    Toast.makeText(context, "Menu not downlaoded yet. Try again later", Toast
                            .LENGTH_LONG).show();
                }
            }
        } else if (id == c.getId()) {
            if (isOnline()) {
                Toast.makeText(context, "Downloading will start soon", Toast.LENGTH_LONG).show();
                cmessRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        File file = new File(directory + "/mess/", "cmess.jpg");
                        if (file.exists()) {
                            boolean delete = file.delete();
                            if (!delete) {
                                Log.e("TAG", "Delete old image manually");
                            }
                        }
                        DownloadManager.Request request = new DownloadManager.Request(uri)
                                .setTitle("C mess menu").setNotificationVisibility
                                        (DownloadManager.Request
                                                .VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                                .setMimeType
                                        ("image/*").setDestinationUri(Uri.fromFile(file));
                        downloadManager.enqueue(request);


                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "Link is invalid!", Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.fromFile(new File(directory +
                        "/mess/", "cmess.jpg")), "image/*");
                try {
                    context.startActivity(intent);
                } catch (Exception e) {
                    Toast.makeText(context, "Menu not downlaoded yet. Try again later", Toast
                            .LENGTH_LONG).show();
                }
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
