package com.csatimes.dojmajournalists;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;

import static com.csatimes.dojmajournalists.FirebaseKeys.NODE_EVENTS;
import static com.csatimes.dojmajournalists.Jhc.getFirebaseRef;

public class AddEventActivity extends AppCompatActivity {
    private final DatabaseReference databaseReference = getFirebaseRef().child(NODE_EVENTS);
    private EditText eventTitle;
    private EditText eventDescription;
    private Button eventTime;
    private Button eventDate;
    private EditText eventLocation;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        final Button addBtn;
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_add_event);
        eventTitle = findViewById(R.id.title);
        eventTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(final CharSequence c, final int i,
                                          final int i1, final int i2) {
            }

            @Override
            public void onTextChanged(final CharSequence c, final int i,
                                      final int i1, final int i2) {
            }

            @Override
            public void afterTextChanged(final Editable editable) {
                if (editable.toString().isEmpty()) {
                    eventTitle.setError(getString(R.string.required));
                } else {
                    eventTitle.setError(null);
                }
            }
        });
        eventDescription = findViewById(R.id.desc);
        eventTime = findViewById(R.id.time);
        eventDate = findViewById(R.id.date);
        eventLocation = findViewById(R.id.location);
        addBtn = findViewById(R.id.add);

        eventDate.setOnClickListener(v -> {
            eventDate.setError(null);
            final DatePickerDialog dpd;
            final Calendar c = Calendar.getInstance();
            final int currentYear = c.get(Calendar.YEAR);
            final int currentMonth = c.get(Calendar.MONTH);
            final int currentDate = c.get(Calendar.DAY_OF_MONTH);
            dpd = new DatePickerDialog(AddEventActivity.this,
                    (view, year, month, dayOfMonth) ->
                            eventDate.setText(String.format(Locale.ENGLISH, "%02d%02d%04d",
                                    dayOfMonth, month, year)),
                    currentYear,
                    currentMonth,
                    currentDate);
            dpd.setTitle("Select Date");
            dpd.show();
        });
        eventTime.setOnClickListener(v -> {
            eventTime.setError(null);
            final Calendar c = Calendar.getInstance();
            final int hour = c.get(Calendar.HOUR_OF_DAY);
            final int minute = c.get(Calendar.MINUTE);
            final TimePickerDialog tpd = new TimePickerDialog(AddEventActivity.this,
                    (view, hourOfDay, min) ->
                            eventTime.setText(new DecimalFormat("00").format(hourOfDay) +
                                    new DecimalFormat("00").format(min)), hour, minute, true);
            tpd.setTitle(getString(R.string.select_time));
            tpd.show();
        });

        addBtn.setOnClickListener(view -> {
            boolean areRequiredFieldsSet = true;

            if (eventTitle.getText().toString().isEmpty()) {
                eventTitle.setError(getString(R.string.required));
                areRequiredFieldsSet = false;
            }

            if (!eventDate.getText().toString().matches("[0-9]{8}")) {
                eventDate.setError(getString(R.string.required));
                areRequiredFieldsSet = false;
            }

            if (!eventTime.getText().toString().matches("[0-9]{4}")) {
                eventTime.setError(getString(R.string.required));
                areRequiredFieldsSet = false;
            }

            if (areRequiredFieldsSet) {
                addData();
            }
        });
    }

    public void addData() {
        final String id = databaseReference.push().getKey();
        final Event event = new Event(eventTitle.getText().toString(),
                eventDescription.getText().toString(),
                eventTime.getText().toString(),
                eventDate.getText().toString(),
                eventLocation.getText().toString());
        databaseReference.child(id).setValue(event).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(AddEventActivity.this,
                        R.string.event_added, Toast.LENGTH_SHORT).show();
                final Intent intent = new Intent(AddEventActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(AddEventActivity.this, "Could not add event", Toast.LENGTH_SHORT)
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
            Intent i = new Intent(AddEventActivity.this, LoginActivity.class);
            startActivity(i);
        }
    }
}
