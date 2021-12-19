package com.example.movewith.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import com.example.movewith.Control.Control;
import com.example.movewith.Model.Address;
import com.example.movewith.Model.Driver;
import com.example.movewith.Model.GPSLocation;
import com.example.movewith.Model.Hitchhiker;
import com.example.movewith.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Date;

public class HitchhikerActivity extends AppCompatActivity {
    EditText city, street, number;
    GPSLocation gpsLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hitchhiker);

        String[] gender = {"זכר", "נקבה"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, gender);
        AutoCompleteTextView textView = (AutoCompleteTextView) findViewById(R.id.gender_list);
        textView.setAdapter(adapter);

        city = findViewById(R.id.city);
        street = findViewById(R.id.steet_src);
        number = findViewById(R.id.number_src);

        gpsLocation = new GPSLocation();
        gpsLocation.setUp(city, street, number, this, null, null);

        FloatingActionButton next= findViewById(R.id.next_page);
        next.setOnClickListener(v -> {
            createHitchhiker();
        });
    }

    public void createHitchhiker() {
        String fullName = ((EditText) findViewById(R.id.full_name)).getText().toString();
        if (fullName.length() == 0) {
            Toast.makeText(this, "שדה השם ריק", Toast.LENGTH_SHORT).show();
            return;
        }

        String gender = ((EditText) findViewById(R.id.gender_list)).getText().toString();
        if (gender.length() == 0) {
            Toast.makeText(this, "שדה המין ריק", Toast.LENGTH_SHORT).show();
            return;
        }

        String ageString = ((EditText) findViewById(R.id.age)).getText().toString();
        if (ageString.length() == 0) {
            Toast.makeText(this, "שדה הגיל ריק", Toast.LENGTH_SHORT).show();
            return;
        }
        int age = Integer.parseInt(ageString);

        String phoneNumber = ((EditText) findViewById(R.id.phone_number)).getText().toString();
        if (!phoneNumber.matches("^05\\d([-]?)\\d{7}$")) {
            Toast.makeText(this, "מספר טלפון לא תקין", Toast.LENGTH_SHORT).show();
            return;
        }

        String timeString = ((EditText) findViewById(R.id.time)).getText().toString();
        if (!timeString.matches("^([0-9]|0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$")) {
            Toast.makeText(this, "השעה אינה תקינה", Toast.LENGTH_SHORT).show();
            return;
        }
        String[] timeArray = timeString.split(":");
        int hour = Integer.parseInt(timeArray[0]);
        int minute = Integer.parseInt(timeArray[1]);
        int second = 0;
        if (timeArray.length > 2)
            second = Integer.parseInt(timeArray[2]);
        Date time = new Date();
        time.setHours(hour);
        time.setMinutes(minute);
        time.setSeconds(second);

        String street = ((EditText) findViewById(R.id.steet_src)).getText().toString();
        if (street.length() == 0) {
            Toast.makeText(this, "שדה הרחוב ריק", Toast.LENGTH_SHORT).show();
            return;
        }

        String city = ((EditText) findViewById(R.id.city)).getText().toString();
        if (city.length() == 0) {
            Toast.makeText(this, "שדה העיר ריק", Toast.LENGTH_SHORT).show();
            return;
        }

        String numberString = ((EditText) findViewById(R.id.number_src)).getText().toString();
        if (numberString.length() == 0) {
            Toast.makeText(this, "שדה מספר ריק", Toast.LENGTH_SHORT).show();
            return;
        }
        int number = Integer.parseInt(numberString);
        Address source = new Address(city, street, number);

        street = ((EditText) findViewById(R.id.street_dest)).getText().toString();
        if (street.length() == 0) {
            Toast.makeText(this, "שדה הרחוב ריק", Toast.LENGTH_SHORT).show();
            return;
        }

        city = ((EditText) findViewById(R.id.city_dest)).getText().toString();
        if (city.length() == 0) {
            Toast.makeText(this, "שדה העיר ריק", Toast.LENGTH_SHORT).show();
            return;
        }

        numberString = ((EditText) findViewById(R.id.number_dest)).getText().toString();
        if (numberString.length() == 0) {
            Toast.makeText(this, "שדה מספר ריק", Toast.LENGTH_SHORT).show();
            return;
        }
        number = Integer.parseInt(numberString);

        Address destination = new Address(city, street, number);

        Hitchhiker hitchhiker = new Hitchhiker(fullName, gender, age, phoneNumber, time, source, destination);

        Intent intent = new Intent(this, Prefer.class);
        intent.putExtra("hitchhiker", hitchhiker);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        gpsLocation.stopListeners();
    }
}