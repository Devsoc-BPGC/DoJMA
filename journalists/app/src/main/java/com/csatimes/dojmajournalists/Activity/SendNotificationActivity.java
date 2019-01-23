package com.csatimes.dojmajournalists.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.csatimes.dojmajournalists.Model.CampusWatchModel;
import com.csatimes.dojmajournalists.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

import static com.csatimes.dojmajournalists.Utils.FirebaseKeys.SEND_NOTFICATION;
import static com.csatimes.dojmajournalists.Utils.Jhc.getFirebaseRef;


public class SendNotificationActivity extends AppCompatActivity {
    private final DatabaseReference databaseReference = getFirebaseRef().child(SEND_NOTFICATION);
    private EditText campusWatchTitle;
    private EditText cwDescription;
    private ImageView imageView;
    private Uri filePath;
    private String date;
    private final int PICK_IMAGE_REQUEST = 71;
    private StorageReference storageReference;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        final Button addBtn;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_campus_watch);
        mAuth = FirebaseAuth.getInstance();
        FirebaseStorage storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        campusWatchTitle = findViewById(R.id.title);
        campusWatchTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(final Editable editable) {
                if (editable.toString().isEmpty()) {
                    campusWatchTitle.setError(getString(R.string.required));
                } else {
                    campusWatchTitle.setError(null);
                }
            }
        });
        cwDescription = findViewById(R.id.desc);
        addBtn = findViewById(R.id.add);
        Button btnChoose = findViewById(R.id.btnChoose);
        imageView = findViewById(R.id.imgView);
        date = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault()).format(new Date());

        addBtn.setOnClickListener(view -> {
            boolean areRequiredFieldsSet = true;

            if (campusWatchTitle.getText().toString().isEmpty()) {
                campusWatchTitle.setError(getString(R.string.required));
                areRequiredFieldsSet = false;
            }

            if (areRequiredFieldsSet) {
                addData();
            }
        });

        btnChoose.setOnClickListener(v -> chooseImage());
    }

    public void addData() {
        if (filePath != null) {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            StorageReference ref = storageReference.child("campusWatch/" + UUID.randomUUID().toString());

            ref.putFile(filePath)
                    .addOnSuccessListener(taskSnapshot -> {
                        progressDialog.dismiss();
                        Toast.makeText(SendNotificationActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();
                        ref.getDownloadUrl().addOnSuccessListener(uri -> {
                            final String imgUrl = uri.toString();
                            final String id = databaseReference.push().getKey();
                            final CampusWatchModel campusWatchModel = new CampusWatchModel(campusWatchTitle.getText().toString(),
                                    cwDescription.getText().toString(),
                                    imgUrl,
                                    date
                            );
                            databaseReference.child(id).setValue(campusWatchModel).addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    Toast.makeText(SendNotificationActivity.this,
                                            R.string.event_added, Toast.LENGTH_SHORT).show();
                                    final Intent intent = new Intent(SendNotificationActivity.this, HomeActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(SendNotificationActivity.this, "Could not add event", Toast.LENGTH_SHORT)
                                            .show();
                                }
                            });
                        });
                    })
                    .addOnFailureListener(e -> {
                        progressDialog.dismiss();
                        Toast.makeText(SendNotificationActivity.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    })
                    .addOnProgressListener(taskSnapshot -> {
                        double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot
                                .getTotalByteCount());
                        progressDialog.setMessage("Uploaded " + (int) progress + "%");
                    });
        }
    }

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
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
            Intent i = new Intent(SendNotificationActivity.this, LoginActivity.class);
            startActivity(i);
        }
    }
}
