package com.trivento.deventerkroegenapp.tasks;

import android.os.AsyncTask;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Een task voor het updaten van een rating van een gebruiker
 */
public class UpdateRatingTask extends AsyncTask<String, Void, Void> {
    @Override
    protected Void doInBackground(String... params) {
        float rating = Float.valueOf(params[0]);
        int userId = Integer.parseInt(params[1]);
        int kroegId = Integer.parseInt(params[2]);

        //Maak de HttpURLRequest naar de link zodat de rating geüpdate wordt
        try {
            URL url = new URL("http://deventerkroegenappsql.azurewebsites.net/addRating.php?rating=" + rating + "&gebruikersid=" + userId + "&kroegid=" + kroegId);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(15000);
            connection.setRequestMethod("GET");
            connection.setDoInput(true);

            connection.getResponseCode();
            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
