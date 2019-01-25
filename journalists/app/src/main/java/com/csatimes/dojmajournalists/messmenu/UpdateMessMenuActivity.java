package com.csatimes.dojmajournalists.messmenu;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.csatimes.dojmajournalists.R;
import com.csatimes.dojmajournalists.home.HomeActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;
import java.io.Serializable;
import java.util.UUID;

import androidx.appcompat.app.AppCompatActivity;

import static com.csatimes.dojmajournalists.login.LoginActivity.checkLogin;
import static com.csatimes.dojmajournalists.utils.Jhc.getFirebaseRef;

public class UpdateMessMenuActivity extends AppCompatActivity {
    public static final String FIELD_MESS = "mess";
    private final DatabaseReference databaseReference = getFirebaseRef().child("mess");
    private final int PICK_IMAGE_REQUEST = 71;
    private Uri filePath;
    private StorageReference storageReference;
    private Mess mess;
    private ImageView menuIv;

    public static void launchMessUpdate(Context parent, Mess mess) {
        Intent messUpdateIntent = new Intent(parent, UpdateMessMenuActivity.class);
        messUpdateIntent.putExtra(FIELD_MESS, mess);
        parent.startActivity(messUpdateIntent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_mess_menu);
        Intent intent = getIntent();
        mess = (Mess) intent.getSerializableExtra("mess");
        TextView titleTv = findViewById(R.id.tv_title);
        titleTv.setText(mess.name);
        FirebaseStorage storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        Button uploadBtn = findViewById(R.id.btn_upload);
        menuIv = findViewById(R.id.image_menu);
        uploadBtn.setOnClickListener(v -> chooseImage());
    }

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    public void onStart() {
        super.onStart();
        checkLogin(this);
    }

    public void addData(View view) {
        if (filePath != null) {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            StorageReference ref = storageReference.child("mess/" + UUID.randomUUID().toString());

            ref.putFile(filePath)
                    .addOnSuccessListener(taskSnapshot -> {
                        progressDialog.dismiss();
                        Toast.makeText(UpdateMessMenuActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();
                        ref.getDownloadUrl().addOnSuccessListener(uri -> {
                            final String imgUrl = uri.toString();
                            String id;


                            final MessMenuModel messMenuModel = new MessMenuModel();
                            messMenuModel.imageUrl = imgUrl;
                            messMenuModel.title = mess.name;

                            switch (mess) {
                                case A: {
                                    id = "1";
                                    break;
                                }
                                case C: {
                                    id = "-2";
                                    break;
                                }
                                case D: {
                                    id = "-3";
                                    break;
                                }
                                default: {
                                    id = "1";
                                    break;
                                }
                            }
                            databaseReference.child(id).setValue(messMenuModel).addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    Toast.makeText(UpdateMessMenuActivity.this,
                                            R.string.event_added, Toast.LENGTH_SHORT).show();
                                    final Intent intent = new Intent(UpdateMessMenuActivity.this, HomeActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(UpdateMessMenuActivity.this, "Could not add event", Toast.LENGTH_SHORT)
                                            .show();
                                }
                            });
                        });
                    })
                    .addOnFailureListener(e -> {
                        progressDialog.dismiss();
                        Toast.makeText(UpdateMessMenuActivity.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    })
                    .addOnProgressListener(taskSnapshot -> {
                        double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot
                                .getTotalByteCount());
                        progressDialog.setMessage("Uploaded " + (int) progress + "%");
                    });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                menuIv.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public enum Mess implements Serializable {
        A("A Mess"),
        C("C Mess"),
        D("D Mess");
        public String name;

        Mess(final String name) {
            this.name = name;
        }
    }
}
