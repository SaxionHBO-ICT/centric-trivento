package com.trivento.deventerkroegenapp.tasks;

import android.os.AsyncTask;
import android.view.Menu;
import android.view.View;

import com.trivento.deventerkroegenapp.R;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Sliomere on 07/06/2016.
 */
public class CategoryTask extends AsyncTask<View, Void, JSONArray> {

    private Menu menu;

    public CategoryTask(Menu menu) {
        this.menu = menu;
    }

    @Override
    protected JSONArray doInBackground(View... params) {
        JSONArray jsonArray = null;
        try {
            URL url = new URL("http://deventerkroegenappsql.azurewebsites.net/categories.php");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(15000);
            connection.setRequestMethod("GET");
            connection.setDoInput(true);

            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                InputStream is = connection.getInputStream();
                String jsonString = IOUtils.toString(is, "UTF-8");
                IOUtils.closeQuietly(is);

                jsonArray = new JSONArray(jsonString);
            }
            connection.disconnect();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return jsonArray;
    }

    @Override
    protected void onPostExecute(JSONArray jsonArray) {
        if (jsonArray != null) {
            menu.add(R.id.categories, 1, Menu.FIRST, "Alles");
            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    JSONObject object = jsonArray.getJSONObject(i);
                    menu.add(R.id.categories, i+2, Menu.FLAG_APPEND_TO_GROUP, object.getString("categorie"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
