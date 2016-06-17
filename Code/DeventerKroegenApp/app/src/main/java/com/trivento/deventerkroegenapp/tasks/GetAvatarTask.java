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

/**
 * Created by Sliomere on 17/06/2016.
 */
public class GetAvatarTask extends AsyncTask<Integer, Void, Drawable>{

    private CollapsingToolbarLayout ctl;
    private Context context;

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
        Log.d(Reference.TAG, "onPostExecute: Ding");
        ctl.setBackground(avatar);
    }
}
