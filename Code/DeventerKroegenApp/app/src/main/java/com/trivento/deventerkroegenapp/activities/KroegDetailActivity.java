package com.trivento.deventerkroegenapp.activities;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.trivento.deventerkroegenapp.R;
import com.trivento.deventerkroegenapp.model.Kroeg;
import com.trivento.deventerkroegenapp.util.Reference;

import net.opacapp.multilinecollapsingtoolbar.CollapsingToolbarLayout;

import java.util.List;

public class KroegDetailActivity extends AppCompatActivity implements OnMapReadyCallback {

    private Kroeg kroeg;
    private GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kroeg_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        kroeg = (Kroeg) getIntent().getSerializableExtra("KROEG");

        CollapsingToolbarLayout ctl = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        ctl.setTitle(kroeg.getNaam());

        String drawable = "kroeg";
        ctl.setBackground(ContextCompat.getDrawable(this, getResources().getIdentifier(drawable, "drawable", getPackageName())));

        TextView tvDesc = (TextView) findViewById(R.id.tv_desc);
        tvDesc.setText(kroeg.getBeschrijving());

        TextView tvOpeningHours = (TextView) findViewById(R.id.tv_opening_hours);
        tvOpeningHours.setText(Html.fromHtml(kroeg.getOpeningstijden()));

        TextView tvURL = (TextView) findViewById(R.id.tv_url);
        tvURL.setText(kroeg.getURL());

        RatingBar ratingBar = (RatingBar) findViewById(R.id.rating_detail);
        Drawable ratingBarDrawable = ratingBar.getProgressDrawable();
        ratingBarDrawable.setColorFilter(Color.parseColor("#F99D2C"), PorterDuff.Mode.SRC_ATOP);

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.fragment_maps);
        mapFragment.getMapAsync(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.map = googleMap;
        map.setMyLocationEnabled(true);
        map.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {
                map.clear();
                Marker marker = map.addMarker(new MarkerOptions().position(getLocationFromAddress(getApplicationContext(), kroeg.getAdres())).title(kroeg.getNaam()));
                MarkerOptions markerOptions =  new MarkerOptions().position(new LatLng(location.getLatitude(), location.getLongitude())).alpha(0f);
                Marker myMarker = map.addMarker(markerOptions);
                LatLngBounds.Builder builder = new LatLngBounds.Builder().include(marker.getPosition()).include(myMarker.getPosition());
                map.animateCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), 15));
            }
        });
        /*Marker marker = map.addMarker(new MarkerOptions().position(getLocationFromAddress(this, kroeg.getAdres())).title(kroeg.getNaam()));

        LatLngBounds.Builder builder = new LatLngBounds.Builder().include(marker.getPosition()).include();
        int padding = 5;
        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
        map.animateCamera(cu);*/
    }

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
}
