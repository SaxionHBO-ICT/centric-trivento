package com.trivento.deventerkroegenapp.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import com.trivento.deventerkroegenapp.R;

import java.util.List;

/**
 * Created by Sliomere on 12/05/2016.
 */
public class KroegListArrayAdapter extends ArrayAdapter<Kroeg>{

    public List<Kroeg> kroegen;

    public KroegListArrayAdapter(Context context, int resource, List<Kroeg> objects) {
        super(context, resource, objects);
        this.kroegen = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.list_item_kroeg, null);

        Kroeg kroeg = kroegen.get(position);

        TextView tvName = (TextView) view.findViewById(R.id.tv_li_name);
        tvName.setText(kroeg.getNaam());
        TextView tvDescShort = (TextView) view.findViewById(R.id.tv_desc_short);
        tvDescShort.setText(kroeg.getBeschrijving());

        RatingBar rbRating = (RatingBar) view.findViewById(R.id.rb_li_rating);
        rbRating.setRating(kroeg.getKroeg_id()/2);

        return view;
    }
}
