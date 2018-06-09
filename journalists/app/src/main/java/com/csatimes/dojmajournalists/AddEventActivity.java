package com.csatimes.dojmajournalists;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DecimalFormat;
import java.util.Calendar;

public class AddEventActivity extends AppCompatActivity  {


    private EditText eventTitle, eventDescription, eventTime, eventDate, eventLocation;
    private DatabaseReference databaseReference;
    private Calendar c = Calendar.getInstance();
    private int Year = c.get(Calendar.YEAR);
    private int Month = c.get(Calendar.MONTH);
    private int Day = c.get(Calendar.DAY_OF_MONTH);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Button addBtn;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addevent_activity);
        eventTitle = findViewById(R.id.title);
        eventDescription = findViewById(R.id.desc);
        eventTime = findViewById(R.id.time);
        eventDate = findViewById(R.id.date);
        eventLocation = findViewById(R.id.location);
        addBtn = findViewById(R.id.add);

        //getting time and date
        setDatePickerDialog();
        setTimePickerDialog();

        databaseReference = FirebaseDatabase.getInstance().getReference().child("events2");
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(eventTitle.getText().toString().isEmpty()  || eventDescription.getText().toString().isEmpty() || eventTime.getText().toString().isEmpty() || eventDate.getText().toString().isEmpty() || eventLocation.getText().toString().isEmpty())
                {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(AddEventActivity.this);
                    dialog.setCancelable(false);
                    dialog.setTitle("WAIT!");
                    dialog.setMessage("Please ensure you fill all the entries." );
                    dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            Log.i("AlertDialog","Message Received");
                        }
                    });
                    final AlertDialog alert = dialog.create();
                    alert.show();
                }

                else {
                    addData();
                    Intent intent = new Intent(AddEventActivity.this,HomeActivity.class);
                    startActivity(intent);
                    finish();
                }
            }

        };
        addBtn.setOnClickListener(listener);
    }
    public void addData() {

        String id = databaseReference.push().getKey();
        databaseReference.child(id).child("title").setValue(eventTitle.getText().toString());
        databaseReference.child(id).child("desc").setValue(eventDescription.getText().toString());
        databaseReference.child(id).child("startTime").setValue(eventTime.getText().toString());
        databaseReference.child(id).child("startDate").setValue(eventDate.getText().toString());
        databaseReference.child(id).child("location").setValue(eventLocation.getText().toString());
        Toast.makeText(AddEventActivity.this, "Added to Events!", Toast.LENGTH_SHORT).show();

    }

    public void setDatePickerDialog(){

        eventDate.setKeyListener(null);
        eventDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dpd;
                dpd = new DatePickerDialog(AddEventActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        eventDate.setText(String.format(new DecimalFormat("00").format(dayOfMonth) + new DecimalFormat("00").format(month) + new DecimalFormat("00").format(year)));
                    }
                },Year,Month,Day);
                dpd.setTitle("Select Date");
                dpd.show();
            }
        });
    }


    public void setTimePickerDialog(){

        eventTime.setKeyListener(null);
        eventTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int Hour = c.get(Calendar.HOUR_OF_DAY);
                int Minute = c.get(Calendar.MINUTE);
                final TimePickerDialog tpd = new TimePickerDialog(AddEventActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        eventTime.setText(new DecimalFormat("00").format(hourOfDay) + new DecimalFormat("00").format(minute));
                    }

                },Hour,Minute,true);
                tpd.setTitle("Select Time");
                tpd.show();
            }
        });
    }

}



