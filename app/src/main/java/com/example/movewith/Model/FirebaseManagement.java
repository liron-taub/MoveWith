package com.example.movewith.Model;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.movewith.Control.Control;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;

public class FirebaseManagement {
    static FirebaseFirestore db = FirebaseFirestore.getInstance();

    public static void saveDriver(Driver driver, Activity activity) {
        db.collection("drivers")
                .add(driver)
                .addOnSuccessListener(documentReference -> {
                    SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString("lastDrive", documentReference.getId());
                    editor.apply();
                    Control.driverUploaded(true);
                })
                .addOnFailureListener(e -> {
                    Control.driverUploaded(false);
                });
    }

    public static void deleteDriver(String id, Activity activity) {
        db.collection("drivers").document(id).delete().addOnSuccessListener(command -> {
            SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.clear();
            editor.apply();

            Control.refreshView();
        });
    }

    public static void toLate(String id, Activity activity) {
        if (id.equals("")) return;

        db.collection("drivers").document(id).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document != null) {
                    Driver driver = task.getResult().toObject(Driver.class);
                    if (driver != null && driver.time.before(new Date())) {  //TODO: להוסיף זמן השהייה
                        deleteDriver(id, activity);
                    } else {
                        Control.refreshView();
                    }
                }
            }
        });
    }
}
