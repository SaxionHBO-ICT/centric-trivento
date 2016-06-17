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

/**
 * Created by Sliomere on 07/06/2016.
 */
public class LoginTask extends AsyncTask<String, Void, Boolean> {

    private boolean showToast;
    private Activity activity;
    private Context context;
    private boolean accepted;
    private String username;
    private String password;
    private boolean isMD5Already = false;

    public LoginTask(Context context, boolean isMD5Already, boolean showToast) {
        this.context = context;
        this.isMD5Already = isMD5Already;
        this.showToast = showToast;
    }

    public LoginTask(Activity activity, boolean showToast) {
        this.activity = activity;
        this.context = activity.getApplicationContext();
        this.showToast = showToast;
    }

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
        if (accepted) {
            SharedPreferences sharedPreferences = context.getSharedPreferences(Reference.LOCATION, Context.MODE_PRIVATE);
            sharedPreferences.edit().putString(Reference.USERNAME, username).apply();
            sharedPreferences.edit().putString(Reference.PASSWORD, password).apply();
            if (activity != null) {
                if(showToast) {
                    Toast.makeText(activity, "Login geslaagd", Toast.LENGTH_SHORT).show();
                }
                activity.finish();
            } else if(showToast){
                Toast.makeText(context, "Login geslaagd", Toast.LENGTH_SHORT).show();
            }
        } else if(showToast){
            Toast.makeText(context, "Email/wachtwoord incorrect", Toast.LENGTH_SHORT).show();
        }
    }

}
