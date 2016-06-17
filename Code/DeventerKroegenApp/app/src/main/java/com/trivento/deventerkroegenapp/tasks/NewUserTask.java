package com.trivento.deventerkroegenapp.tasks;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.Toast;

import org.apache.commons.io.IOUtils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * Created by Sliomere on 15/06/2016.
 */
public class NewUserTask extends AsyncTask<String, Void, Boolean> {

    private Activity activity;

    public NewUserTask(Activity activity) {
        this.activity = activity;
    }

    @Override
    protected Boolean doInBackground(String... params) {
        String voornaam = params[0];
        String achternaam = params[1];
        String adres = params[2];
        String postcode = params[3];
        String woonplaats = params[4];
        String land = params[5];
        String mail = params[6];
        String wachtwoord = params[7];

        try {
            URL url = new URL("http://deventerkroegenappsql.azurewebsites.net/newUser.php");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(15000);
            connection.setRequestMethod("POST");
            connection.setDoInput(true);

            String urlParams = "voornaam=" + URLEncoder.encode(voornaam, "UTF-8") +
                    "&achternaam=" + URLEncoder.encode(achternaam, "UTF-8") +
                    "&adres=" + URLEncoder.encode(adres, "UTF-8") +
                    "&postcode=" + URLEncoder.encode(postcode, "UTF-8") +
                    "&woonplaats=" + URLEncoder.encode(woonplaats, "UTF-8") +
                    "&land=" + URLEncoder.encode(land, "UTF-8") +
                    "&email=" + URLEncoder.encode(mail, "UTF-8") +
                    "&wachtwoord=" + URLEncoder.encode(wachtwoord, "UTF-8");
            byte[] postData = urlParams.getBytes(StandardCharsets.UTF_8);

            DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
            wr.write(postData);
            wr.flush();
            wr.close();

            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                InputStream is = connection.getInputStream();
                String body = IOUtils.toString(is, "UTF-8");
                IOUtils.closeQuietly(is);

                if(body.contains("success")){
                    connection.disconnect();
                    return true;
                } else {
                }
            }
            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        if(aBoolean){
            Toast.makeText(activity, "Gebruiker geregistreerd. Log nu in", Toast.LENGTH_SHORT).show();
            activity.finish();
        } else {
            Toast.makeText(activity, "Er is iets misgegaan", Toast.LENGTH_SHORT).show();
        }
    }
}
