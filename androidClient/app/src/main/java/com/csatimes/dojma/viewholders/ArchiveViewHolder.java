package com.csatimes.dojma.viewholders;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.csatimes.dojma.R;
import com.csatimes.dojma.activities.UtilitiesArchivesActivity;
import com.csatimes.dojma.models.Archive;
import com.facebook.drawee.view.SimpleDraweeView;

import java.io.File;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import static androidx.core.content.FileProvider.getUriForFile;

public class ArchiveViewHolder extends RecyclerView.ViewHolder {

    private TextView title;
    private SimpleDraweeView image;
    private ProgressBar progressBar;
    private Context context;
    private ImageView circle, download;
    private FirebaseStorage storage = FirebaseStorage.getInstance();

    public ArchiveViewHolder(View itemView) {
        super(itemView);
        circle = itemView.findViewById(R.id.item_format_archive_circle);
        download = itemView.findViewById(R.id.item_format_archive_download);
        context = itemView.getContext();
        progressBar = itemView.findViewById(R.id.item_format_archive_progressbar);
        title = itemView.findViewById(R.id.item_format_archive_title);
        image = itemView.findViewById(R.id.item_format_archive_image);
    }

    public void populate(@NonNull final Archive archive) {
        title.setText(archive.title);
        image.setImageURI(archive.imageUrl);
        File file = new File(context.getFilesDir(), "archives/" + archive.title + ".pdf");
        if (file.exists()) {
            progressBar.setVisibility(View.GONE);
            circle.setVisibility(View.GONE);
            download.setVisibility(View.GONE);
        }

        itemView.setOnClickListener(view -> {
            if (file.exists()) {
                readPdf(file);
            }
            else {
                circle.setVisibility(View.GONE);
                download.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);

                downloadPdf(archive);
            }
        });
    }

    public void downloadPdf(final Archive archive) {

        Toast.makeText(context, "Download has started", Toast.LENGTH_SHORT).show();
        File archives = new File(context.getFilesDir(), "archives");
        archives.mkdirs();

        StorageReference storageRef = storage.getReference();
        StorageReference pathReference = storageRef.child("archives/" + archive.title +".pdf");
        File ifile = new File(context.getFilesDir(), "archives/"+archive.title + ".pdf");

        pathReference
                .getFile(ifile)
                .addOnSuccessListener(taskSnapshot -> {
                    Toast.makeText(context, "Download completed", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                    readPdf(ifile);

                })
                .addOnFailureListener(e -> Log.e(UtilitiesArchivesActivity.TAG, e.getMessage()))
                .addOnProgressListener(taskSnapshot -> {
                    double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                    progressBar.setProgress((int) progress);
                });
    }

    public void readPdf(File file){
        Uri path = getUriForFile(context, "com.csatimes.dojma.fileprovider", file);
        Intent target = new Intent(Intent.ACTION_VIEW);
        target.setDataAndType(path, "application/pdf");
        target.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        Intent intent = Intent.createChooser(target, "Open File");
        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(context, "Please install a pdf reader app", Toast.LENGTH_SHORT).show();
        }
    }

}
