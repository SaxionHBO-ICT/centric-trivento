package com.trivento.deventerkroegenapp.activities;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.RatingBar;

import com.trivento.deventerkroegenapp.R;
import com.trivento.deventerkroegenapp.model.Kroeg;

import net.opacapp.multilinecollapsingtoolbar.CollapsingToolbarLayout;

public class KroegDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kroeg_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Kroeg kroeg = (Kroeg) getIntent().getSerializableExtra("KROEG");

        CollapsingToolbarLayout ctl = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        ctl.setTitle(kroeg.getNaam());
        //WARNING replace this with real code (placeholder)
        String drawable = "kroeg";
        ctl.setBackground(ContextCompat.getDrawable(this, getResources().getIdentifier(drawable, "drawable", getPackageName())));

        RatingBar ratingBar = (RatingBar) findViewById(R.id.rating_detail);
        Drawable ratingBarDrawable = ratingBar.getProgressDrawable();
        ratingBarDrawable.setColorFilter(Color.parseColor("#F99D2C"), PorterDuff.Mode.SRC_ATOP);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
