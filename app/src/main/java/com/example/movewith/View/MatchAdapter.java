package com.example.movewith.View;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.movewith.R;

import java.util.HashMap;
import java.util.List;

public class MatchAdapter extends RecyclerView.Adapter<MatchAdapter.ViewHolder> {
    private List<HashMap<String, String>> mData;
    private LayoutInflater mInflater;
    private Context context;

    // data is passed into the constructor
    MatchAdapter(Context context, List<HashMap<String, String>> data) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.match_row, parent, false);
        view.setOnClickListener(v -> {
            Intent myIntent = new Intent(context, Finish.class);
            context.startActivity(myIntent);
        });
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        HashMap<String, String> current = mData.get(position);
        holder.setContent(current);
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView name, phone, location;

        ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            phone = itemView.findViewById(R.id.phone_number);
            location = itemView.findViewById(R.id.location);
        }

        public void setContent(HashMap<String, String> map) {
            name.setText(map.get("name"));
            phone.setText(map.get("phone"));
            location.setText(map.get("location"));
        }
    }
}