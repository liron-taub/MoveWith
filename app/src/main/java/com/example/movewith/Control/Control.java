package com.example.movewith.Control;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.example.movewith.Model.Driver;
import com.example.movewith.Model.FirebaseManagement;
import com.example.movewith.View.MainActivity;
import com.example.movewith.View.SuccessDriver;

public class Control {
    public static MainActivity activity;

    public static void saveDriver(Driver driver) {
        FirebaseManagement.saveDriver(driver, activity);
    }

    public static void driverUploaded(boolean success) {
        if (success) {
            Intent myIntent = new Intent(activity, SuccessDriver.class);
            myIntent.addFlags(FLAG_ACTIVITY_NEW_TASK);
            activity.startActivity(myIntent);
        } else {
            Toast.makeText(activity, "לא הצלחנו להעלות את הנתונים, נסה שוב", Toast.LENGTH_SHORT).show();
        }
    }

    public static String lastDrive() {
        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        return sharedPref.getString("lastDrive", "");
    }

    public static void refreshView() {
        activity.refreshView();
    }
}
