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
import android.widget.Toast;

import com.csatimes.dojmajournalists.Model.CampusWatchModel;
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

public class MessMenuActivity extends AppCompatActivity {
    private final DatabaseReference databaseReference = getFirebaseRef().child(CAMPUS_WATCH);
    private Button a_mess, c_mess, d_mess;
    private final int PICK_IMAGE_REQUEST = 71;
    private Uri filePath;
    private StorageReference storageReference;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mess_menu);
        mAuth = FirebaseAuth.getInstance();
        FirebaseStorage storage = FirebaseStorage.getInstance();
        a_mess = findViewById(R.id.a_mess);
        c_mess = findViewById(R.id.c_mess);
        d_mess = findViewById(R.id.d_mess);

        a_mess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MessMenuActivity.this,UpdateMessMenuActivity.class);
                intent.putExtra("mess","A Mess");
                startActivity(intent);
            }
        });

        c_mess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MessMenuActivity.this,UpdateMessMenuActivity.class);
                intent.putExtra("mess","C Mess");
                startActivity(intent);
            }
        });
        d_mess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MessMenuActivity.this,UpdateMessMenuActivity.class);
                intent.putExtra("mess","D Mess");
                startActivity(intent);
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null)
        {
            Intent i = new Intent(MessMenuActivity.this, LoginActivity.class);
            startActivity(i);
        }
    }
}
