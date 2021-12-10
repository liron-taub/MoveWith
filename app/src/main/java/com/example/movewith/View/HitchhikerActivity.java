package com.example.movewith.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import com.example.movewith.Control.Control;
import com.example.movewith.Model.Address;
import com.example.movewith.Model.Driver;
import com.example.movewith.Model.Hitchhiker;
import com.example.movewith.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Date;

public class HitchhikerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hitchhiker);

        String[] gender = {"זכר", "נקבה"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, gender);
        AutoCompleteTextView textView = (AutoCompleteTextView) findViewById(R.id.gender_list);
        textView.setAdapter(adapter);

        FloatingActionButton next= findViewById(R.id.next_page);
        next.setOnClickListener(v -> {
            createHitchhiker();
        });
    }
    public void createHitchhiker() {
        String fullName = ((EditText) findViewById(R.id.full_name)).getText().toString();
        String gender = ((EditText) findViewById(R.id.gender_list)).getText().toString();
        int age = Integer.parseInt(((EditText) findViewById(R.id.age)).getText().toString());
        String phoneNumber = ((EditText) findViewById(R.id.phone_number)).getText().toString();


        String timeString = ((EditText) findViewById(R.id.time)).getText().toString();
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
        String city = ((EditText) findViewById(R.id.city)).getText().toString();
        int number = Integer.parseInt(((EditText) findViewById(R.id.number_src)).getText().toString());
        Address source = new Address(city, street, number);

        street = ((EditText) findViewById(R.id.street_dest)).getText().toString();
        city = ((EditText) findViewById(R.id.city_dest)).getText().toString();
        number = Integer.parseInt(((EditText) findViewById(R.id.number_dest)).getText().toString());
        Address destination = new Address(city, street, number);

        Hitchhiker hitchhiker = new Hitchhiker(fullName, gender, age, phoneNumber, time, source, destination);

        Intent intent = new Intent(this, Prefer.class);
        intent.putExtra("hitchhiker", hitchhiker);
        startActivity(intent);
    }
}