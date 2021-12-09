package com.example.movewith.Model;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.example.movewith.View.Prefer;

import java.util.List;

public class MatchCalculator extends AsyncTask<Void, Void, Void> {
    public static List<Match> matches;
    private Context context;

    public MatchCalculator(List<Match> matches, Context context) {
        this.matches = matches;
        this.context = context;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        double maxSrc = 0, maxDest = 0;
        double maxTime = 0, maxAge = 0;
        for (Match match : matches) {
            match.calculate();
            if (match.getSrcDist() > maxSrc)
                maxSrc = match.getSrcDist();
            if (match.getDestDist() > maxDest)
                maxDest = match.getDestDist();
            if (match.getAgeDist() > maxAge)
                maxAge = match.getAgeDist();
            if (match.getTimeDist() > maxTime)
                maxTime = match.getTimeDist();

        }

        for (Match match : matches) {
            match.setDestDist(maxDest == 0 ? 0 : match.getDestDist() / maxDest);
            match.setSrcDist(maxSrc == 0 ? 0 : match.getSrcDist() / maxSrc);
            match.setTimeDist(maxTime == 0 ? 0 : match.getTimeDist() / maxTime);
            match.setAgeDist(maxAge == 0 ? 0 : match.getAgeDist() / maxAge);
        }

        matches.sort((o1, o2) -> Double.compare(o1.getGrade(), o2.getGrade()));

        Intent intent = new Intent(context, Prefer.class);
        context.startActivity(intent);

        return null;
    }
}
