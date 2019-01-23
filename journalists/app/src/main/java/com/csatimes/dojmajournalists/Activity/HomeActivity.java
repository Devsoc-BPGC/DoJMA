package com.csatimes.dojmajournalists.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.csatimes.dojmajournalists.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mAuth = FirebaseAuth.getInstance();
        findViewById(R.id.btn_add_event).setOnClickListener(v ->
                startActivity(new Intent(HomeActivity.this,
                        AddEventActivity.class)));
        findViewById(R.id.btn_add_cw).setOnClickListener(v ->
                startActivity(new Intent(HomeActivity.this,
                        AddCampusWatchActivity.class)));
        findViewById(R.id.btn_del_event).setOnClickListener(v ->
                startActivity(new Intent(HomeActivity.this,
                        DeleteEventActivity.class)));
        findViewById(R.id.add_video).setOnClickListener(v ->
                startActivity(new Intent(HomeActivity.this,
                        AddVideoActivity.class)));
        findViewById(R.id.send_notif).setOnClickListener(v ->
                startActivity(new Intent(HomeActivity.this,
                        SendNotificationActivity.class)));
        findViewById(R.id.btn_update_menu).setOnClickListener(v ->
                startActivity(new Intent(HomeActivity.this,
                        MessMenuActivity.class)));
    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null)
        {
            Intent i = new Intent(HomeActivity.this, LoginActivity.class);
            startActivity(i);
            Log.i("user",currentUser.getEmail());
        }
    }
}
