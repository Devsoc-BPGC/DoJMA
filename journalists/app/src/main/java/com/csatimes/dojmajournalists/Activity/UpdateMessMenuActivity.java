package com.csatimes.dojmajournalists.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
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

import com.csatimes.dojmajournalists.Model.MessMenuModel;
import com.csatimes.dojmajournalists.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;
import java.util.UUID;

import static com.csatimes.dojmajournalists.Utils.FirebaseKeys.CAMPUS_WATCH;
import static com.csatimes.dojmajournalists.Utils.Jhc.getFirebaseRef;

public class UpdateMessMenuActivity extends AppCompatActivity {
    private final DatabaseReference databaseReference = getFirebaseRef().child("mess");
    private Button a_mess, c_mess, d_mess;
    private final int PICK_IMAGE_REQUEST = 71;
    private Uri filePath;
    private StorageReference storageReference;
    private FirebaseAuth mAuth;
    private String mess;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_mess_menu);
        Intent intent = getIntent();
        mess = intent.getStringExtra("mess");
        TextView title = findViewById(R.id.title);
        title.setText(mess);
        mAuth = FirebaseAuth.getInstance();
        FirebaseStorage storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        Button upload = findViewById(R.id.upload);
        imageView = findViewById(R.id.image_menu);
        upload.setOnClickListener(v -> chooseImage());

        Button update = findViewById(R.id.update);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addData();
            }
        });
    }
    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }
    public void addData() {
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
                            String id = "1";


                            final MessMenuModel messMenuModel = new MessMenuModel(imgUrl, mess);

                            if (mess.equals("A Mess")){
                                id = "1";
                            }
                            if (mess.equals("C Mess")){
                                id = "-2";
                            }
                            if (mess.equals("D Mess")){
                                id = "-3";
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
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null)
        {
            Intent i = new Intent(UpdateMessMenuActivity.this, LoginActivity.class);
            startActivity(i);
        }
    }
}
