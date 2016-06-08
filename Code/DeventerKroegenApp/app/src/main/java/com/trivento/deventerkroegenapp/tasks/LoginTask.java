package com.trivento.deventerkroegenapp.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.trivento.deventerkroegenapp.activities.LogInActivity;

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
public class LoginTask extends AsyncTask<String, Void, Boolean>{

    private Context context;
    private boolean accepted;

    public LoginTask(Context context) {
        this.context = context;
    }

    @Override
    protected Boolean doInBackground(String... params) {
        String username = params[0];
        String password = params[1];

        try {
            URL url = new URL("http://deventerkroegenappsql.azurewebsites.net/checkLogin.php");            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(15000);
            connection.setRequestMethod("POST");
            connection.setDoInput(true);

            String urlParams = "username=" + URLEncoder.encode(username, "UTF-8") + "&password=" + URLEncoder.encode(md5(password), "UTF-8");
            byte[] postData = urlParams.getBytes(StandardCharsets.UTF_8);

            DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
            wr.write(postData);
            wr.flush();
            wr.close();

            int responseCode = connection.getResponseCode();
            if(responseCode == 200){
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
        if(!accepted){
            Toast.makeText(context, "Username/password incorrect", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, "Login complete", Toast.LENGTH_SHORT).show();
            //TODO implement this when login storage is finished
            //((LogInActivity)context).finish();
        }
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

}
