package com.trivento.deventerkroegenapp.tasks;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class MapsTask extends AsyncTask<String, Void, String[]> {

    private GoogleMap map;
    private Context context;

    public MapsTask(GoogleMap map, Context context) {
        this.map = map;
        this.context = context;
    }

    @Override
    protected String[] doInBackground(String... params) {
        return new String[]{params[0], params[1]};
    }

    /**
     * Haal de LatLng op van een adres
     * Source: http://stackoverflow.com/questions/3574644/how-can-i-find-the-latitude-and-longitude-from-address
     * @param context de context van de applicatie
     * @param strAddress Het adres
     * @return De LatLng die bij het adres horen
     */
    public LatLng getLocationFromAddress(Context context, String strAddress) {

        Geocoder coder = new Geocoder(context);
        List<Address> address;
        LatLng p1 = null;

        try {
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }
            Address location = address.get(0);
            location.getLatitude();
            location.getLongitude();

            p1 = new LatLng(location.getLatitude(), location.getLongitude());

        } catch (Exception ex) {

            ex.printStackTrace();
        }

        return p1;
    }

    @Override
    protected void onPostExecute(String[] params) {
        final String adres = params[0];
        final String naam = params[1];
        map.setMyLocationEnabled(true);
        /**
         * Zet 1 marker bij de kroeg, een andere (doorzichtige) marker op "mijn locatie" en zoom in binnen de bounds van die 2 markers
         */
        map.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {
                LatLng kroegLoc = getLocationFromAddress(context, adres);
                if (kroegLoc != null) {
                    map.clear();
                    Marker marker = map.addMarker(new MarkerOptions().position(kroegLoc).title(naam));
                    MarkerOptions markerOptions = new MarkerOptions().position(new LatLng(location.getLatitude(), location.getLongitude())).alpha(0f);
                    Marker myMarker = map.addMarker(markerOptions);
                    LatLngBounds.Builder builder = new LatLngBounds.Builder().include(marker.getPosition()).include(myMarker.getPosition());
                    map.moveCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), 50));
                }
            }
        });
    }
}
