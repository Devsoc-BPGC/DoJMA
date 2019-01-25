package com.csatimes.dojmajournalists.notification;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.csatimes.dojmajournalists.R;
import com.csatimes.dojmajournalists.home.HomeActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

import androidx.appcompat.app.AppCompatActivity;

import static com.csatimes.dojmajournalists.login.LoginActivity.checkLogin;
import static com.csatimes.dojmajournalists.utils.FirebaseKeys.SEND_NOTFICATION;
import static com.csatimes.dojmajournalists.utils.Jhc.getFirebaseRef;


public class SendNotificationActivity extends AppCompatActivity {
    private final DatabaseReference databaseReference = getFirebaseRef().child(SEND_NOTFICATION);
    private final int PICK_IMAGE_RC = 71;
    private EditText titleEt;
    private EditText subtitleEt;
    private EditText clickUrlEt;
    private ImageView thumbIv;
    private Uri filePath;
    private String date;
    private StorageReference storageReference;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_notification);
        FirebaseStorage storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        titleEt = findViewById(R.id.tv_title);
        titleEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(final Editable editable) {
                if (editable.toString().isEmpty()) {
                    titleEt.setError(getString(R.string.required));
                } else {
                    titleEt.setError(null);
                }
            }
        });
        subtitleEt = findViewById(R.id.et_desc);
        clickUrlEt = findViewById(R.id.et_url);
        Button chooseImgBtn = findViewById(R.id.btn_choose_img);
        thumbIv = findViewById(R.id.iv_thumb);
        date = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault()).format(new Date());
        chooseImgBtn.setOnClickListener(v -> chooseImage());
    }

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_RC);
    }

    @Override
    public void onStart() {
        super.onStart();
        checkLogin(this);
    }

    public void addData(View v) {
        if (titleEt.getText().toString().isEmpty()) {
            titleEt.setError(getString(R.string.required));
            return;
        }

        if (filePath != null) {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            StorageReference ref = storageReference.child("Notification/" + UUID.randomUUID().toString());

            ref.putFile(filePath)
                    .addOnSuccessListener(taskSnapshot -> {
                        progressDialog.dismiss();
                        Toast.makeText(SendNotificationActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();
                        ref.getDownloadUrl().addOnSuccessListener(uri -> {
                            final String imgUrl = uri.toString();
                            final String id = databaseReference.push().getKey();
                            final SendNotificationModel sendNotificationModel = new SendNotificationModel();
                            sendNotificationModel.title = titleEt.getText().toString();
                            sendNotificationModel.subtitle = subtitleEt.getText().toString();
                            sendNotificationModel.timestamp = date;
                            sendNotificationModel.imageUrl = imgUrl;
                            sendNotificationModel.clickUrl = clickUrlEt.getText().toString();
                            assert id != null;
                            databaseReference.child(id).setValue(sendNotificationModel).addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    Toast.makeText(SendNotificationActivity.this,
                                            R.string.event_added, Toast.LENGTH_SHORT).show();
                                    final Intent intent = new Intent(SendNotificationActivity.this, HomeActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(SendNotificationActivity.this, "Could not Send Notification", Toast.LENGTH_SHORT)
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_RC && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                thumbIv.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
