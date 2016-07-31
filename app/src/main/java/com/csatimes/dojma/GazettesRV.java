package com.csatimes.dojma;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnPausedListener;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.util.Vector;

import static com.csatimes.dojma.DHC.directory;

/**
 * Created by Vikramaditya Kukreja on 31-07-2016.
 */

public class GazettesRV extends RecyclerView.Adapter<GazettesRV.ViewHolder> {
    FirebaseStorage storage = FirebaseStorage.getInstance();
    Context context;
    Vector<GazetteItem> gazetteItems;
    boolean writePermission = true;
    private StorageReference storageRef = storage.getReference().child
            ("documents").child("gazettes");

    public GazettesRV(Context context, Vector<GazetteItem> gazetteItems) {
        this.context = context;
        this.gazetteItems = gazetteItems;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final int pos = position;
        holder.title.setText(gazetteItems.get(position).getTitle());
        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //download


                if (writePermission) {
                    {
                        final File pdfFolder = new File(directory + "/gazettes/");
                        pdfFolder.mkdirs();
                        final String pdfName = gazetteItems.get(pos).getTitle() + ".pdf";
                        File file = new File(pdfFolder, pdfName);


                        if (file.exists()) {
                            Uri path = Uri.fromFile(file);
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.setDataAndType(path, "application/pdf");
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                            try {
                                context.startActivity(intent);
                            } catch (ActivityNotFoundException e) {
                                Toast.makeText(context, "No application to load PDF", Toast
                                        .LENGTH_LONG).show();
                            }
                        } else {
                            //download file using firebase
                            if (isOnline()) {
                                Toast.makeText(context, "Starting Download", Toast.LENGTH_LONG)
                                        .show();
                                StorageReference pdfRef = storage.getReferenceFromUrl
                                        (gazetteItems.get(pos).getUrl());
                                pdfRef.getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                        Toast.makeText(context, "Downloaded!", Toast.LENGTH_SHORT).show();
                                        Uri path = Uri.fromFile(new File(pdfFolder, pdfName));
                                        Intent intent = new Intent(Intent.ACTION_VIEW);
                                        intent.setDataAndType(path, "application/pdf");
                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                                        try {
                                            context.startActivity(intent);
                                        } catch (ActivityNotFoundException e) {
                                            Toast.makeText(context, "No application to load PDF", Toast
                                                    .LENGTH_LONG).show();
                                        }
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(context, "Download Failed!", Toast
                                                .LENGTH_LONG).show();
                                    }
                                }).addOnPausedListener(new OnPausedListener<FileDownloadTask.TaskSnapshot>() {
                                    @Override
                                    public void onPaused(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                        Toast.makeText(context, "Download was paused! why?", Toast
                                                .LENGTH_LONG).show();
                                    }
                                });

                            } else {
                                Toast.makeText(context, R.string.no_internet_msg, Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                } else if (!writePermission) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(gazetteItems.get(pos).getUrl()));
                    context.startActivity(intent);
                }


            }
        });
    }

    @Override
    public int getItemCount() {
        return gazetteItems.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout
                .gazette_item_format, parent, false));
    }

    void setHasWritePermission(boolean hasPermission) {
        this.writePermission = hasPermission;
    }

    private boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context
                .CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return (netInfo != null && netInfo.isConnected());
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;

        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.gazette_item_format_text);
        }
    }

}

