package com.csatimes.dojmajournalists.video;

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

import com.csatimes.dojmajournalists.R;
import com.csatimes.dojmajournalists.home.HomeActivity;
import com.google.firebase.database.DatabaseReference;

import java.util.Calendar;
import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;

import static com.csatimes.dojmajournalists.login.LoginActivity.checkLogin;
import static com.csatimes.dojmajournalists.utils.FirebaseKeys.VIDEOS;
import static com.csatimes.dojmajournalists.utils.Jhc.getFirebaseRef;

public class AddVideoActivity extends AppCompatActivity {

    private final DatabaseReference databaseReference = getFirebaseRef().child(VIDEOS);
    private final String[] departments = new String[]{"DoJMA", "DoPY", "FMaC", "Waves", "Quark","Spree","TedX","Dance Club","MuSoc", "Drama Club", "MIME Club","Synchronoise","Nirmaan", "Abhigyaan"};
    private final String[] sourceTypes = new String[]{"youtube", "facebook"};
    private String creator = "DoJMA";
    private String chosenType = "youtube";
    private EditText videoNameEt;
    private EditText videoUrlEt;
    private EditText descriptionEt;
    private Button uploadDateBtn;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_video);

        final Spinner creatorSpinner = findViewById(R.id.spinner_creator);

        ArrayAdapter<String> creator_adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, departments);
        creatorSpinner.setAdapter(creator_adapter);
        creatorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                creator = departments[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        final Spinner sourceTypeSpinner = findViewById(R.id.spinner_source_type);

        ArrayAdapter<String> sourceTypeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, sourceTypes);
        sourceTypeSpinner.setAdapter(sourceTypeAdapter);
        sourceTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                chosenType = sourceTypes[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        videoNameEt = findViewById(R.id.video_title);
        descriptionEt = findViewById(R.id.video_desc);
        videoUrlEt = findViewById(R.id.url);

        uploadDateBtn = findViewById(R.id.btn_date);
        progressBar = findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onStart() {
        super.onStart();
        checkLogin(this);
    }

    /**
     * {@link android.view.View.OnClickListener} for {@link R.id#btn_add}
     *
     * @param view ignored.
     */
    public void addData(View view) {
        boolean areRequiredFieldsSet = true;
        if (videoNameEt.getText().toString().isEmpty()) {
            videoNameEt.setError(getString(R.string.required));
            areRequiredFieldsSet = false;
        }

        if (videoUrlEt.getText().toString().isEmpty()) {
            videoUrlEt.setError(getString(R.string.required));
            areRequiredFieldsSet = false;
        }

        if (!areRequiredFieldsSet) {
            return;
        }
        final String id = databaseReference.push().getKey();
        progressBar.setVisibility(View.VISIBLE);

        final AddVideoModel addVideoModel = new AddVideoModel();
        addVideoModel.videoName = videoNameEt.getText().toString();
        addVideoModel.description = descriptionEt.getText().toString();
        addVideoModel.dateStamp = uploadDateBtn.getText().toString();
        addVideoModel.type = chosenType;
        addVideoModel.creator = creator;
        addVideoModel.videoURL = videoUrlEt.getText().toString();
        assert id != null;
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

    /**
     * {@link android.view.View.OnClickListener} for {@link R.id#btn_date}
     *
     * @param v ignored
     */
    public void chooseUploadDate(View v) {
        uploadDateBtn.setError(null);
        final DatePickerDialog datePickerDialog;
        final Calendar calendar = Calendar.getInstance();
        final int currentYear = calendar.get(Calendar.YEAR);
        final int currentMonth = calendar.get(Calendar.MONTH);
        final int currentDate = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog.OnDateSetListener listener = (view, year, month, dayOfMonth) -> {
            String date = String.format(Locale.ENGLISH, "%02d-%02d-%04d", dayOfMonth, month + 1, year);
            uploadDateBtn.setText(date);
        };
        datePickerDialog = new DatePickerDialog(AddVideoActivity.this,
                listener,
                currentYear,
                currentMonth,
                currentDate);
        datePickerDialog.setTitle("Select Date");
        datePickerDialog.show();
    }
}
