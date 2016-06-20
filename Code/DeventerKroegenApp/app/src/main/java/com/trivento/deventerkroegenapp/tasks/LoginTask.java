package com.trivento.deventerkroegenapp.tasks;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.trivento.deventerkroegenapp.util.Reference;

import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginTask extends AsyncTask<String, Void, Boolean> {

    private boolean showToast;
    private Activity activity;
    private Context context;
    private boolean accepted;
    private String gebruikerId;
    private String username;
    private String password;
    private boolean isMD5Already = false;

    /**
     * Taks voor het inloggen van een gebruiker
     * @param context de context van de applicatie
     * @param isMD5Already Voor het geval dat er alleen gechecked moet worden of de inloggegevens kloppen en het wachtwoord dus al md5 is
     * @param showToast Of er een toast moet worden gemaakt of het inloggen succesvol is of niet
     */
    public LoginTask(Context context, boolean isMD5Already, boolean showToast) {
        this.context = context;
        this.isMD5Already = isMD5Already;
        this.showToast = showToast;
    }

    /**
     * @param activity De Activity waarvandaan de task wordt gerunt, zodat die kan worden afgesloten
     * @param showToast Of er een toast moet worden gemaakt of het inloggen succesvol is of niet
     */
    public LoginTask(Activity activity, boolean showToast) {
        this.activity = activity;
        this.context = activity.getApplicationContext();
        this.showToast = showToast;
    }

    /**
     * MD5 algoritme voor de input string
     * Source: http://stackoverflow.com/questions/4846484/md5-hashing-in-android
     * @param s De input string
     * @return MD5 versie van de input string
     */
    public static String md5(final String s) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest
                    .getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    protected Boolean doInBackground(String... params) {
        username = params[0];
        if (!isMD5Already) {
            password = md5(params[1]);
        } else {
            password = params[1];
        }

        Log.d(Reference.TAG, "doInBackground: running task");

        //Maak de POST request om te kijken of de inloggegevens kloppen
        try {
            URL url = new URL("http://deventerkroegenappsql.azurewebsites.net/checkLogin.php");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(15000);
            connection.setRequestMethod("POST");
            connection.setDoInput(true);

            String urlParams = "username=" + URLEncoder.encode(username, "UTF-8") + "&password=" + URLEncoder.encode(password, "UTF-8");
            byte[] postData = urlParams.getBytes(StandardCharsets.UTF_8);

            DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
            wr.write(postData);
            wr.flush();
            wr.close();

            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                InputStream is = connection.getInputStream();
                String jsonString = IOUtils.toString(is, "UTF-8");
                IOUtils.closeQuietly(is);

                JSONObject jsonObject = new JSONObject(jsonString);
                accepted = jsonObject.getBoolean("accepted");
                gebruikerId = jsonObject.getString("gebruiker_id");
            }
            connection.disconnect();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return accepted;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        Reference.loginAccepted = accepted;
        //Als het inloggen succesvol is, sla username, password en gebruikersid op in de SharedPreferences en maak eventueel een toast
        if (accepted) {
            SharedPreferences sharedPreferences = context.getSharedPreferences(Reference.LOCATION, Context.MODE_PRIVATE);
            sharedPreferences.edit().putString(Reference.USERNAME, username).apply();
            sharedPreferences.edit().putString(Reference.PASSWORD, password).apply();
            sharedPreferences.edit().putString(Reference.GEBRUIKERID, gebruikerId).apply();
            if (activity != null) {
                if(showToast) {
                    Toast.makeText(activity, "Login geslaagd", Toast.LENGTH_SHORT).show();
                }
                //Sluit de activity af als dat nodig is
                activity.finish();
            } else if(showToast){
                Toast.makeText(context, "Login geslaagd", Toast.LENGTH_SHORT).show();
            }
        } else if(showToast){
            Toast.makeText(context, "Email/wachtwoord incorrect", Toast.LENGTH_SHORT).show();
        }
    }

}
