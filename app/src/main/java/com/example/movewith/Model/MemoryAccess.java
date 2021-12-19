package com.example.movewith.Model;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class MemoryAccess {
    static SharedPreferences.Editor editor;
    static Activity activity;

    static private void openEditor() {
        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        editor = sharedPref.edit();
    }
}
