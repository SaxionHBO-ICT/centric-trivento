package com.trivento.deventerkroegenapp.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.trivento.deventerkroegenapp.R;
import com.trivento.deventerkroegenapp.model.Kroeg;
import com.trivento.deventerkroegenapp.tasks.GetAvatarTask;
import com.trivento.deventerkroegenapp.tasks.MapsTask;
import com.trivento.deventerkroegenapp.tasks.UpdateRatingTask;
import com.trivento.deventerkroegenapp.util.Reference;

import net.opacapp.multilinecollapsingtoolbar.CollapsingToolbarLayout;

/**
 * De detailView van een kroeg
 */
public class KroegDetailActivity extends AppCompatActivity implements OnMapReadyCallback {

    private Kroeg kroeg;
    private GoogleMap map;
    private RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kroeg_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        kroeg = (Kroeg) getIntent().getSerializableExtra("KROEG");

        //De CollapsingToolbarlayout van de DetailActivity is te zien bovenin het scherm. Deze layout maakt het mogelijk dat de toolbar kleiner en groter kan worden
        CollapsingToolbarLayout ctl = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbar);
        ctl.setTitle(kroeg.getNaam());

        GetAvatarTask getAvatarTask = new GetAvatarTask(ctl, this);
        getAvatarTask.execute(kroeg.getKroeg_id());

        setTitle(kroeg.getNaam());

        TextView tvDesc = (TextView) findViewById(R.id.tv_desc);
        tvDesc.setText(kroeg.getBeschrijving());

        TextView tvOpeningHours = (TextView) findViewById(R.id.tv_opening_hours);
        tvOpeningHours.setText(Html.fromHtml(kroeg.getOpeningstijden()));

        TextView tvURL = (TextView) findViewById(R.id.tv_url);
        tvURL.setText(kroeg.getURL());

        ratingBar = (RatingBar) findViewById(R.id.rating_detail);
        ratingBar.setRating(kroeg.getRating());

        //Als er ingelogd is, mag er een rating gegeven worden
        if(LogInActivity.checkLogin(this, false)){
            ratingBar.setEnabled(true);
        } else {
            ratingBar.setEnabled(false);
        }

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.fragment_maps);
        mapFragment.getMapAsync(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Als er op de save knop wordt geklikt, sla de rating op
        if(item.getItemId() == R.id.save){
            float rating = ratingBar.getRating();
            UpdateRatingTask updateRatingTask = new UpdateRatingTask();
            //Haal het gebruikersId uit de SharedPreferences
            SharedPreferences sharedPreferences = getSharedPreferences(Reference.LOCATION, MODE_PRIVATE);
            String gebruikerId = sharedPreferences.getString(Reference.GEBRUIKERID, null);
            updateRatingTask.execute(String.valueOf(rating), gebruikerId, String.valueOf(kroeg.getKroeg_id()));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.detail_menu, menu);
        return true;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.map = googleMap;
        //Instantieer de googleMap
        MapsTask mapsTask = new MapsTask(map, this);
        mapsTask.execute(kroeg.getAdres(), kroeg.getNaam());
    }
}
