package com.trivento.deventerkroegenapp.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import com.trivento.deventerkroegenapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sliomere on 12/05/2016.
 */
public class KroegListArrayAdapter extends ArrayAdapter<Kroeg>{

    private List<Kroeg> kroegen;

    public KroegListArrayAdapter(Context context, int resource, List<Kroeg> objects) {
        super(context, resource, objects);
        this.kroegen = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.list_item_kroeg, null);

        TextView tvName = (TextView) view.findViewById(R.id.tv_li_name);
        tvName.setText(kroegen.get(position).getName());

        RatingBar rbRating = (RatingBar) view.findViewById(R.id.rb_li_rating);
        rbRating.setRating(kroegen.get(position).getRating());

        return view;
    }
}
