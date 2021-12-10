package com.example.movewith.View;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.movewith.Model.Driver;
import com.example.movewith.R;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

public class MatchAdapter extends RecyclerView.Adapter<MatchAdapter.ViewHolder> {
    private List<Driver> mData;
    private LayoutInflater mInflater;
    private Context context;

    // data is passed into the constructor
    MatchAdapter(Context context, List<Driver> data) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.match_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        view.setOnClickListener(viewHolder);
        return viewHolder;
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Driver current = mData.get(position);
        holder.setContent(current);
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name, time, src, dest, age, price;
        Driver driver;

        ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            src = itemView.findViewById(R.id.src);
            dest = itemView.findViewById(R.id.dest);
            age = itemView.findViewById(R.id.age);
            time = itemView.findViewById(R.id.time);
            price = itemView.findViewById(R.id.price);
        }

        public void setContent(Driver driver) {
            this.driver = driver;
            name.setText(driver.fullName + ",");
            src.setText(driver.source.toString());
            dest.setText(driver.destination.toString());
            age.setText((driver.gender.equals("זכר") ? " בן " : " בת ") + driver.age);
            time.setText(new SimpleDateFormat("kk:mm").format(driver.time));
            price.setText(driver.price + "₪");
        }

        @Override
        public void onClick(View v) {
            Intent myIntent = new Intent(context, Finish.class);
            myIntent.putExtra("driver", this.driver);
            context.startActivity(myIntent);
        }
    }
}