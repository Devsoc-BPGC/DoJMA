package com.csatimes.dojmajournalists;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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

public class addEventActivity extends AppCompatActivity  {

    Button add_btn;
    EditText event_title, event_description, event_time, event_date, event_location;
    DatabaseReference databaseReference;
    Calendar c = Calendar.getInstance();
    int Year = c.get(Calendar.YEAR);
    int Month = c.get(Calendar.MONTH);
    int Day = c.get(Calendar.DAY_OF_MONTH);
    DatePickerDialog dpd;
 //   SaveData saveData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addevent_activity);

        event_title = findViewById(R.id.title);
        event_description = findViewById(R.id.desc);
        event_time = findViewById(R.id.time);
        event_date = findViewById(R.id.date);
        event_location = findViewById(R.id.location);
        add_btn = findViewById(R.id.add);

        //getting time and date
        setDatePickerDialog();
        setTimePickerDialog();


        String p;
        if (getIntent().getExtras() != null) {
            p = getIntent().getExtras().getString("homeToAdd");

        }


        databaseReference = FirebaseDatabase.getInstance().getReference().child("events2");
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              if(event_title.getText().toString().isEmpty()  || event_description.getText().toString().isEmpty() || event_time.getText().toString().isEmpty() || event_date.getText().toString().isEmpty() || event_location.getText().toString().isEmpty())
               {
                   AlertDialog.Builder dialog = new AlertDialog.Builder(addEventActivity.this);
                   dialog.setCancelable(false);
                   dialog.setTitle("WAIT!");
                   dialog.setMessage("Please ensure you fill all the entries." );
                   dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface dialog, int id) {
                       }
                   });
                   final AlertDialog alert = dialog.create();
                   alert.show();
               }

               else {
                  AddData();
                  Intent intent = new Intent(addEventActivity.this,homeActivity.class);
                  intent.putExtra("addToHome", "bye");
                  startActivity(intent);
                  finish();
               }
            }

        };
        add_btn.setOnClickListener(listener);
    }
        public void AddData() {

            String id = databaseReference.push().getKey();
            databaseReference.child(id).child("title").setValue(event_title.getText().toString());
            databaseReference.child(id).child("desc").setValue(event_description.getText().toString());
            databaseReference.child(id).child("startTime").setValue(event_time.getText().toString());
            databaseReference.child(id).child("startDate").setValue(event_date.getText().toString());
            databaseReference.child(id).child("location").setValue(event_location.getText().toString());
            Toast.makeText(addEventActivity.this, "Added to Events!", Toast.LENGTH_SHORT).show();

        }

    public void setDatePickerDialog(){

        event_date.setKeyListener(null);
        event_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dpd = new DatePickerDialog(addEventActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        event_date.setText(String.format(new DecimalFormat("00").format(dayOfMonth) + new DecimalFormat("00").format(month) + new DecimalFormat("00").format(year)));
                    }
                },Year,Month,Day);
                dpd.setTitle("Select Date");
                dpd.show();
            }
        });
    }


    public void setTimePickerDialog(){

        event_time.setKeyListener(null);
        event_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int Hour = c.get(Calendar.HOUR_OF_DAY);
                int Minute = c.get(Calendar.MINUTE);
                final TimePickerDialog tpd = new TimePickerDialog(addEventActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                       event_time.setText(new DecimalFormat("00").format(hourOfDay) + new DecimalFormat("00").format(minute));
                    }

                },Hour,Minute,true);
                tpd.setTitle("Select Time");
                tpd.show();
            }
        });
    }

}


