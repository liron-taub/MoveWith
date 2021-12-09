package com.example.movewith.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

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

        HashMap<String, String> map = new HashMap<>();
        map.put("name", "נופר");
        map.put("phone", "0527089552");
        map.put("location", "חשמונאים");

        List<HashMap<String, String>> list = new ArrayList<>();
        list.add(map);
        list.add(map);
        list.add(map);
        list.add(map);
        list.add(map);

        RecyclerView recyclerView = findViewById(R.id.all_match);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        MatchAdapter adapter = new MatchAdapter(this, list);
        recyclerView.setAdapter(adapter);
    }
}