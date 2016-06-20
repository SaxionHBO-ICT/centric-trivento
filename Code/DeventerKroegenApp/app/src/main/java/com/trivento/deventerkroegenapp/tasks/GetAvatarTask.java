package com.trivento.deventerkroegenapp.tasks;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;

import com.trivento.deventerkroegenapp.util.Reference;

import net.opacapp.multilinecollapsingtoolbar.CollapsingToolbarLayout;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

public class GetAvatarTask extends AsyncTask<Integer, Void, Drawable>{

    private CollapsingToolbarLayout ctl;
    private Context context;

    /**
     * Class die de avatar van een kroeg uit een HttpURLRequest haalt (te zien in de detailActivity)
     * @param ctl De CollapsingToolbarLayout waarin de avatar als background functioneerd
     * @param context De context van de app
     */
    public GetAvatarTask(CollapsingToolbarLayout ctl, Context context) {
        this.ctl = ctl;
        this.context = context;
    }

    @Override
    protected Drawable doInBackground(Integer... params) {
        try {
            java.net.URL url = new java.net.URL("http://deventerkroegenappsql.azurewebsites.net/getAvatar.php?id=" + params[0]);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();

            //De avatar wordt in Base64 doorgegeven door de server, dus wordt hier omgezet in een byteArray en daarna in een Drawable, die als achtergrond wordt gebruikt
            String body = IOUtils.toString(input, "UTF-8");
            byte[] decodedString = Base64.decode(body.getBytes(), Base64.DEFAULT);
            Drawable drawable = new BitmapDrawable(context.getResources(), BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length));

            IOUtils.closeQuietly(input);
            return drawable;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(Drawable avatar) {
        ctl.setBackground(avatar);
    }
}
