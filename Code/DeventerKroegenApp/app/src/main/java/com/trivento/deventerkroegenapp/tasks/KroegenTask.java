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

public class KroegenTask extends AsyncTask<String, Void, Void> {

    private List<Kroeg> kroegen;

    /**
     * Task die óf alle kroegen ophaalt, óf alle kroegen uit 1 categorie ophaalt
     * @param kroegen De lijst waar de kroegen in moeten komen te staan
     */
    public KroegenTask(List<Kroeg> kroegen){
        this.kroegen = kroegen;
    }

    //Leeg de lijst, zodat er geen dubbele kroegen in komen te staan
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

            //Haal alle informatie uit JSON en maak daar kroeg objecten van, die in de list worden gestopt
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
                    String categorie = jsonObject.getString("categorie");
                    String urlString = jsonObject.getString("url");

                    float rating = 0;

                    URL urlRating = new URL("http://deventerkroegenappsql.azurewebsites.net/rating.php?kroeg_id=" + kroegID);
                    HttpURLConnection connectionRating = (HttpURLConnection) urlRating.openConnection();
                    connectionRating.setReadTimeout(10000);
                    connectionRating.setConnectTimeout(15000);
                    connectionRating.setRequestMethod("GET");
                    connectionRating.setDoInput(true);

                    int responseCodeRating = connectionRating.getResponseCode();
                    if (responseCodeRating == 200) {
                        InputStream isRating = connectionRating.getInputStream();
                        String jsonStringRating = IOUtils.toString(isRating, "UTF-8");
                        IOUtils.closeQuietly(isRating);

                        JSONObject jsonObjectRating = new JSONObject(jsonStringRating);
                        rating = (float) jsonObjectRating.getDouble("rating");
                    }
                    connectionRating.disconnect();

                    Kroeg kroeg = new Kroeg(kroegID, naam, adres, openingstijden, beschrijving, urlString, categorie, rating);
                    kroegen.add(kroeg);
                }
            }
            connection.disconnect();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        //Notify de adapter dat de inhoud is veranderd
        KroegListFragment.notifyDataSetChanged();
    }
}
