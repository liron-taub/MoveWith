package com.example.movewith.Model;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.movewith.Control.Control;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FirebaseManagement {
    //קישור לפרייבייס
    static FirebaseFirestore db = FirebaseFirestore.getInstance();

    public static void saveDriver(Driver driver, Activity activity) {
        db.collection("drivers")
                // דוחף לתוך הקולקשיין את הנהג החדש אחרת אם לא הצליח אז כותב שלא הצליח
                .add(driver)
                .addOnSuccessListener(documentReference -> {
                    SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);//קישור לזיכרון של הפאלפון
                    SharedPreferences.Editor editor = sharedPref.edit();// פותח את הקישור לעריכה
                    editor.putString("lastDrive", documentReference.getId());// שומר את הנתונים על הזיכרון של הפאלפון ונותן id
                    editor.apply();//שמור את השינויים שעשינו על הזיכרון
                    Control.driverUploaded(true);
                })
                .addOnFailureListener(e -> {
                    Control.driverUploaded(false);
                });
    }

    //פונקציה שמקבלת id של נהג והיא מוחקת אותו(הכפתור האדום)
    public static void cancelDriver(String id, Activity activity) {
        db.collection("drivers").document(id).update("canceled", true).addOnSuccessListener(command -> {
            SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.remove("lastDrive");
            editor.apply();

            Control.refreshView();
        });
    }

    // מחיקת נסיעה שעבר זמנה
    public static void toLate(String id, Activity activity) {
        if (id.equals("")) return;

        db.collection("drivers").document(id).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document != null) {
                    Driver driver = task.getResult().toObject(Driver.class);
                    if (driver != null && driver.time.before(new Date())) {  //TODO: להוסיף זמן השהייה
                        cancelDriver(id, activity);
                    } else {
                        Control.refreshView();
                    }
                }
            }
        });
    }

    // להוריד את כל הנתונים של הנהגים מהפיירבייס בשביל למצוא התאמה
    public static void downloadDrivers() {
        db.collection("drivers")
                .whereEqualTo("canceled", false)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<Driver> drivers = task.getResult().toObjects(Driver.class);

                        Control.findMatch(drivers);
                    } else {
                        Control.findMatch(new ArrayList<>());
                    }
                });
    }
}
