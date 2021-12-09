package com.example.movewith.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.movewith.Control.Control;
import com.example.movewith.Model.Address;
import com.example.movewith.Model.Driver;
import com.example.movewith.Model.FirebaseManagement;
import com.example.movewith.Model.Hitchhiker;
import com.example.movewith.Model.Match;
import com.example.movewith.Model.MatchCalculator;
import com.example.movewith.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Button lastDrive;
    public static RequestQueue requestQueue;// התור שאליו נתעדף התאמות

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requestQueue = Volley.newRequestQueue(this);

        Control.activity = this;

        Button driver = findViewById(R.id.driver_button);
        Button hitchhiker = findViewById(R.id.hitchhiker_button);
        lastDrive = findViewById(R.id.lastDrive_button);


        driver.setOnClickListener(v -> {
            Intent myIntent = new Intent(MainActivity.this, DriverActivity.class);
            MainActivity.this.startActivity(myIntent);
        });

        hitchhiker.setOnClickListener(v -> {
            Intent myIntent = new Intent(MainActivity.this, HitchhikerActivity.class);
            MainActivity.this.startActivity(myIntent);
        });

        Activity activity = this;
        lastDrive.setOnClickListener(v -> {
            new AlertDialog.Builder(this)
                    .setTitle("מחיקת הנסיעה המתוכננת")
                    .setMessage("האם ברצונך למחוק את הנסיעה המתוכננת האחרונה שהעלת?")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            FirebaseManagement.deleteDriver(Control.lastDrive(), activity);
                        }
                    })

                    // A null listener allows the button to dismiss the dialog and take no further action.
                    .setNegativeButton(android.R.string.no, null)
                    .show();
        });

        FirebaseManagement.toLate(Control.lastDrive(), this);


        Address driverSrc = new Address("חשמונאים", "הכרמל", 20);
        Address driverDest = new Address("חשמונאים", "המוריה", 2);
        Driver driver1 = new Driver("אבג", "זכר", 30, "123456789", 50, new Date(26, 11, 2021, 13, 30, 0), driverSrc, driverDest);

        Address hitSrc = new Address("רעות", "תומר", 26);
        Address hitDest = new Address("חשמונאים", "המוריה", 1);
        Hitchhiker hitchhiker1 = new Hitchhiker("", "זכר", 20, "09876543", new Date(26, 11, 2021, 13, 30, 0), hitSrc, hitDest);

        Address driverSrc2 = new Address("חשמונאים", "היצהר", 1);
        Address driverDest2 = new Address("חשמונאים", "הכרמל", 16);
        Driver driver2 = new Driver("שדגכע", "זכר", 50, "234567", 11, new Date(26, 11, 2021, 13, 30, 0), driverSrc2, driverDest2);

        Address driverSrc3 = new Address("ירושלים", "הוועד הלאומי", 21);
        Address driverDest3 = new Address("חשמונאים", "המוריה", 26);
        Driver driver3 = new Driver("דגכע", "זכר", 22, "34567", 23, new Date(26, 11, 2021, 15, 30, 0), driverSrc3, driverDest3);


        Match match1 = new Match(driver1, hitchhiker1);
        Match match2 = new Match(driver2, hitchhiker1);
        Match match3 = new Match(driver3, hitchhiker1);
        ArrayList<Match> matches = new ArrayList<>();
        matches.add(match1);
        matches.add(match2);
        matches.add(match3);

        MatchCalculator matchCalculator = new MatchCalculator(matches, this);
        matchCalculator.execute();
    }

    public void refreshView() {
        if (!Control.lastDrive().equals("")) {
            lastDrive.setVisibility(View.VISIBLE);
        } else {
            lastDrive.setVisibility(View.GONE);
        }
    }
}