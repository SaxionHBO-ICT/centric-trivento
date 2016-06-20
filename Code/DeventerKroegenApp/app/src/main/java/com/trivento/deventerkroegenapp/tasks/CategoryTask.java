package com.trivento.deventerkroegenapp.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;

import com.trivento.deventerkroegenapp.R;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class CategoryTask extends AsyncTask<Void, Void, JSONArray> {

    private Menu menu;
    private Context context;

    /**
     * Task voor het ophalen van alle categorieën
     * @param menu Het menu waar de items in moeten worden geplaatst
     * @param context De context van de applicatie
     */
    public CategoryTask(Menu menu, Context context) {
        this.menu = menu;
        this.context = context;
    }

    @Override
    protected JSONArray doInBackground(Void... params) {
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
            //Aangezien het login-item er altijd moet zijn met hetzelfde id etc, haalen we deze eerst op voordat we het menu legen
            MenuItem login = menu.getItem(0);
            menu.clear();
            //Maak een nieuw loginItem aan
            MenuItem loginNew = menu.add(login.getGroupId(), login.getItemId(), login.getOrder(), login.getTitle());
            loginNew.setIcon(context.getResources().getDrawable(R.drawable.ic_action_login));
            //Maak een Submenu met de verschillende categorieën uit de jsonArray aan met als eerste item "Alles"
            SubMenu subMenu = menu.addSubMenu(1, 0, Menu.FLAG_APPEND_TO_GROUP, "Categorieën");
            subMenu.add("Alles");
            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    JSONObject object = jsonArray.getJSONObject(i);
                    subMenu.add(1, i+1, Menu.FLAG_APPEND_TO_GROUP, object.getString("categorie"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
