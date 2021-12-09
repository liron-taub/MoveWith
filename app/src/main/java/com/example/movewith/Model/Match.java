package com.example.movewith.Model;

import android.os.AsyncTask;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.movewith.View.MainActivity;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.net.ssl.HttpsURLConnection;

public class Match extends AsyncTask<Void, Void, Void> {
    Driver driver;
    Hitchhiker hitchhiker;

    double srcDist;
    double destDist;
    double ageDist;
    double timeDist;
    double gender;

    double grade;

    public Match(Driver driver, Hitchhiker hitchhiker) {
        this.driver = driver;
        this.hitchhiker = hitchhiker;

        this.execute();
    }

    private void distance(Address address1, Address address2, String name) {
        String add1 = address1.street + " " + address1.number + ", " + address1.city;// מחזיק את המלל בשורה אחת
        String add2 = address2.street + " " + address2.number + ", " + address2.city;
        try {
            add1 = URLEncoder.encode(add1, StandardCharsets.UTF_8.toString());
            add2 = URLEncoder.encode(add2, StandardCharsets.UTF_8.toString());
            String url = "https://www.mapquestapi.com/directions/v2/route?key=V9hdGG5eQEAtZJT963CiWKDYwmDYdLrb&from=" + add1 + "&to=" + add2;

            // התחברות לאתר
            URL url1 = new URL(url);
            HttpsURLConnection connection = (HttpsURLConnection) url1.openConnection();
            connection.connect();
            InputStream stream = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null)
                builder.append(line).append("\n");
            JSONObject response = new JSONObject(builder.toString());

            if (name.equals("srcDist"))
                srcDist = response.getJSONObject("route").getDouble("distance");
            else
                destDist = response.getJSONObject("route").getDouble("distance");
        } catch (Exception ex) {
            System.out.println(ex);// צריך להוסיף תהליכון מעפן שיסדר הכל!!!
        }
    }

    @Override
    protected Void doInBackground(Void... voids) {
        distance(driver.source, hitchhiker.source, "srcDist");
        distance(driver.destination, hitchhiker.destination, "destDist");
        return null;
    }

    @Override
    protected void onPostExecute(Void unused) {
        super.onPostExecute(unused);

        ageDist = Math.abs(driver.age - hitchhiker.age);
        timeDist = Math.abs(driver.time.getTime() - hitchhiker.time.getTime());
        gender = driver.gender.equals(hitchhiker.gender) ? 0 : 100; // if מקוצר עבור הוספת המין בשקלול הסופי
    }
}