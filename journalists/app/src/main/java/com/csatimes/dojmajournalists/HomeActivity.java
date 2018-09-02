package com.csatimes.dojmajournalists;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        findViewById(R.id.btn_add_event).setOnClickListener(v ->
                startActivity(new Intent(HomeActivity.this,
                        AddEventActivity.class)));
        findViewById(R.id.btn_add_cw).setOnClickListener(v ->
                startActivity(new Intent(HomeActivity.this,
                        AddCampusWatch.class)));
    }
}
