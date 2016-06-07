package com.trivento.deventerkroegenapp.tasks;

import android.os.AsyncTask;

import com.trivento.deventerkroegenapp.fragments.KroegListFragment;
import com.trivento.deventerkroegenapp.model.Kroeg;
import com.trivento.deventerkroegenapp.model.KroegData;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Created by Sliomere on 30/05/2016.
 */
public class ConnectionTask extends AsyncTask<String, Void, Void> {

    private List<Kroeg> kroegen;

    public ConnectionTask(List<Kroeg> kroegen){
        this.kroegen = kroegen;
    }

    @Override
    protected void onPreExecute() {
        KroegData.kroegen.clear();
    }

    @Override
    protected Void doInBackground(String... params) {
        try {
            URL url = new URL("http://deventerkroegenappsql.azurewebsites.net/kroegen.php");
            if(params.length > 0){
                url = new URL("http://deventerkroegenappsql.azurewebsites.net/kroegen.php?category=" + params[0]);
            }
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(15000);
            connection.setRequestMethod("GET");
            connection.setDoInput(true);

            int responseCode = connection.getResponseCode();
            if(responseCode == 200){
                InputStream is = connection.getInputStream();
                JSONArray jsonArray = new JSONArray(IOUtils.toString(is, "UTF-8"));
                IOUtils.closeQuietly(is);

                for(int i = 0; i < jsonArray.length(); i++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    int kroegID = jsonObject.getInt("kroeg_id");
                    String naam = jsonObject.getString("naam");
                    String adres = jsonObject.getString("adres");
                    String openingstijden = jsonObject.getString("openingstijden");
                    String beschrijving = jsonObject.getString("beschrijving");
                    String avatar = jsonObject.getString("avatar");
                    String categorie = jsonObject.getString("categorie");
                    String urlString = jsonObject.getString("url");

                    Kroeg kroeg = new Kroeg(kroegID, naam, adres, openingstijden, beschrijving, urlString, avatar, categorie);
                    kroegen.add(kroeg);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        KroegListFragment.notifyDataSetChanged();
    }
}
