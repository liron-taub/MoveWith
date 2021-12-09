package com.example.movewith.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.movewith.Model.Driver;
import com.example.movewith.Model.Match;
import com.example.movewith.Model.MatchCalculator;
import com.example.movewith.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Prefer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prefer);
        getSupportActionBar().setTitle("ההתאמות שנמצאו עבורך...");

        List<Driver> drivers = new ArrayList<>();
        for (Match match:  MatchCalculator.matches) {
            drivers.add(match.getDriver());
        }

        RecyclerView recyclerView = findViewById(R.id.all_match);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        MatchAdapter adapter = new MatchAdapter(this, drivers);
        recyclerView.setAdapter(adapter);
    }
}