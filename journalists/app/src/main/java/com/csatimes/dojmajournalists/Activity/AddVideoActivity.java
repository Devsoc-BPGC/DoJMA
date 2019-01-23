package com.csatimes.dojmajournalists.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.csatimes.dojmajournalists.Model.AddVideoModel;
import com.csatimes.dojmajournalists.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import java.util.Calendar;
import java.util.Locale;

import static com.csatimes.dojmajournalists.Utils.FirebaseKeys.VIDEOS;
import static com.csatimes.dojmajournalists.Utils.Jhc.getFirebaseRef;

public class AddVideoActivity extends AppCompatActivity {

    private final DatabaseReference databaseReference = getFirebaseRef().child(VIDEOS);
    private String creator_final = "DoJMA";
    private String chosen_type = "youtube";
    private Spinner creator;
    private Spinner type;
    private EditText videoName;
    private EditText videoUrl;
    private EditText description;
    private Button video_upload_date;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        final Button addBtn;
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_add_video);

        creator = findViewById(R.id.creator);

        String[] departments = new String[]{"DoJMA", "DoPY"};
        ArrayAdapter<String> creator_adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, departments);
        creator.setAdapter(creator_adapter);
        creator.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        creator_final = "DoJMA";
                        break;
                    case 1:
                        creator_final = "DoPY";
                        break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        type = findViewById(R.id.source);

        String[] types = new String[]{"youtube", "facebook"};
        ArrayAdapter<String> type_adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, types);
        type.setAdapter(type_adapter);
        type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        chosen_type = "youtube";
                        break;
                    case 1:
                        chosen_type = "facebook";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        videoName = findViewById(R.id.video_title);
        description = findViewById(R.id.video_desc);
        videoUrl = findViewById(R.id.url);

        video_upload_date = findViewById(R.id.date);
        addBtn = findViewById(R.id.add);
        progressBar = findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.GONE);
        video_upload_date.setOnClickListener(v -> {
            video_upload_date.setError(null);
            final DatePickerDialog dpd;
            final Calendar c = Calendar.getInstance();
            final int currentYear = c.get(Calendar.YEAR);
            final int currentMonth = c.get(Calendar.MONTH);
            final int currentDate = c.get(Calendar.DAY_OF_MONTH);
            dpd = new DatePickerDialog(AddVideoActivity.this,
                    (view, year, month, dayOfMonth) ->
                            video_upload_date.setText(String.format(Locale.ENGLISH, "%02d-%02d-%04d",
                                    dayOfMonth, month+1, year)),
                    currentYear,
                    currentMonth,
                    currentDate);
            dpd.setTitle("Select Date");
            dpd.show();
        });

        addBtn.setOnClickListener(view -> {
            boolean areRequiredFieldsSet = true;

            if (videoName.getText().toString().isEmpty()) {
                videoName.setError(getString(R.string.required));
                areRequiredFieldsSet = false;
            }

            if (videoUrl.getText().toString().isEmpty()) {
                videoUrl.setError(getString(R.string.required));
                areRequiredFieldsSet = false;
            }

            if (areRequiredFieldsSet) {
                addData();
            }
        });
    }

    public void addData() {
        final String id = databaseReference.push().getKey();
        progressBar.setVisibility(View.VISIBLE);

        final AddVideoModel addVideoModel = new AddVideoModel(
                videoName.getText().toString(),
                description.getText().toString(),
                video_upload_date.getText().toString(),
                chosen_type,
                creator_final,
                videoUrl.getText().toString());

        databaseReference.child(id).setValue(addVideoModel).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(AddVideoActivity.this,
                        R.string.event_added, Toast.LENGTH_SHORT).show();
                final Intent intent = new Intent(AddVideoActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
                progressBar.setVisibility(View.GONE);
            } else {
                Toast.makeText(AddVideoActivity.this, "Could not add event", Toast.LENGTH_SHORT)
                        .show();
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
            Intent i = new Intent(AddVideoActivity.this, LoginActivity.class);
            startActivity(i);
        }
    }
}
