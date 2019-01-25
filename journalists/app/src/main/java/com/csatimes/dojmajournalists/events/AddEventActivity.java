package com.csatimes.dojmajournalists.events;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.csatimes.dojmajournalists.R;
import com.csatimes.dojmajournalists.home.HomeActivity;
import com.google.firebase.database.DatabaseReference;

import java.util.Calendar;
import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;

import static com.csatimes.dojmajournalists.login.LoginActivity.checkLogin;
import static com.csatimes.dojmajournalists.utils.FirebaseKeys.NODE_EVENTS;
import static com.csatimes.dojmajournalists.utils.Jhc.getFirebaseRef;

public class AddEventActivity extends AppCompatActivity {
    private final DatabaseReference databaseReference = getFirebaseRef().child(NODE_EVENTS);
    private EditText titleEt;
    private EditText descEt;
    private Button timeBtn;
    private Button dateBtn;
    private EditText locationEt;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        titleEt = findViewById(R.id.tv_title);
        titleEt.addTextChangedListener(new TextWatcher() {
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
                    titleEt.setError(getString(R.string.required));
                } else {
                    titleEt.setError(null);
                }
            }
        });
        descEt = findViewById(R.id.et_desc);
        timeBtn = findViewById(R.id.btn_time);
        dateBtn = findViewById(R.id.btn_date);
        locationEt = findViewById(R.id.et_location);
        progressBar = findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onStart() {
        super.onStart();
        checkLogin(this);
    }

    /**
     * {@link android.view.View.OnClickListener} for {@link #timeBtn}
     *
     * @param v ignored
     */
    public void chooseTime(View v) {
        timeBtn.setError(null);
        final Calendar calendar = Calendar.getInstance();
        final int hour = calendar.get(Calendar.HOUR_OF_DAY);
        final int minute = calendar.get(Calendar.MINUTE);
        final TimePickerDialog.OnTimeSetListener listener = (view, hourOfDay, min) -> {
            String time = String.format(Locale.ENGLISH, "%02d%02d", hourOfDay, min);
            timeBtn.setText(time);
        };
        final TimePickerDialog tpd = new TimePickerDialog(AddEventActivity.this, listener, hour, minute, true);
        tpd.setTitle(getString(R.string.select_time));
        tpd.show();
    }

    /**
     * {@link android.view.View.OnClickListener} for {@link #dateBtn}
     *
     * @param v ignored
     */
    public void chooseDate(View v) {
        dateBtn.setError(null);
        final DatePickerDialog dialog;
        final Calendar calendar = Calendar.getInstance();
        final int currentYear = calendar.get(Calendar.YEAR);
        final int currentMonth = calendar.get(Calendar.MONTH);
        final int currentDate = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog.OnDateSetListener listener = (view, year, month, dayOfMonth) -> {
            String date = String.format(Locale.ENGLISH, "%02d%02d%04d", dayOfMonth, month + 1, year);
            dateBtn.setText(date);
        };
        dialog = new DatePickerDialog(AddEventActivity.this,
                listener,
                currentYear,
                currentMonth,
                currentDate);
        dialog.setTitle("Select Date");
        dialog.show();
    }

    /**
     * {@link android.view.View.OnClickListener} for {@link R.id#btn_add_event} button.
     *
     * @param view ignored
     */
    public void addEvent(View view) {
        boolean areRequiredFieldsSet = true;

        if (titleEt.getText().toString().isEmpty()) {
            titleEt.setError(getString(R.string.required));
            areRequiredFieldsSet = false;
        }

        if (!dateBtn.getText().toString().matches("[0-9]{8}")) {
            dateBtn.setError(getString(R.string.required));
            areRequiredFieldsSet = false;
        }

        if (!timeBtn.getText().toString().matches("[0-9]{4}")) {
            timeBtn.setError(getString(R.string.required));
            areRequiredFieldsSet = false;
        }

        if (areRequiredFieldsSet) {
            addData();
        }
    }

    private void addData() {
        final String id = databaseReference.push().getKey();
        progressBar.setVisibility(View.VISIBLE);
        final EventModel event = new EventModel();
        event.title = titleEt.getText().toString();
        event.desc = descEt.getText().toString();
        event.startTime = timeBtn.getText().toString();
        event.startDate = dateBtn.getText().toString();
        event.location = locationEt.getText().toString();
        assert id != null;
        databaseReference.child(id).setValue(event).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(AddEventActivity.this,
                        R.string.event_added, Toast.LENGTH_SHORT).show();
                final Intent intent = new Intent(AddEventActivity.this, HomeActivity.class);
                startActivity(intent);
                progressBar.setVisibility(View.GONE);
                finish();
            } else {
                Toast.makeText(AddEventActivity.this, "Could not add event", Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }
}
