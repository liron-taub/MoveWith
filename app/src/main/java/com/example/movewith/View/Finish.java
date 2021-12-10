package com.example.movewith.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.movewith.Model.Driver;
import com.example.movewith.R;

public class Finish extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish);

        Driver driver = (Driver) getIntent().getSerializableExtra("driver");
        TextView view = findViewById(R.id.msg);
        view.setText("בחרת לנסוע עם " + driver.fullName + ", לחץ על הכפתור כדי ליצור קשר");

        StringBuilder builder = new StringBuilder();
        builder.append("היי ");
        builder.append(driver.fullName);
        builder.append(",\n");
        builder.append("אשמח להצטרף איתך לנסיעה אל ");
        builder.append(driver.destination);

        Button button = findViewById(R.id.contact);
        button.setOnClickListener(v -> {
            SmsManager manager = SmsManager.getDefault();
            manager.sendTextMessage(driver.phoneNumber, null, builder.toString(), null, null);
        });
    }
}