package com.csatimes.dojmajournalists;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class home extends AppCompatActivity {
    TextView tv;
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        tv = findViewById(R.id.tv);
        btn = findViewById(R.id.btn);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(home.this,addEvent.class);
                intent.putExtra("homeToAdd", "hello");
                startActivity(intent);
                finish();
            }
        };
        btn.setOnClickListener(listener);
        String p;
        if (getIntent().getExtras() != null) {
            p = getIntent().getExtras().getString("addToHome");

        }
    }
}

