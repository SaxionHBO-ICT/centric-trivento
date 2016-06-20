package com.trivento.deventerkroegenapp.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.trivento.deventerkroegenapp.R;

import java.util.List;

public class KroegListArrayAdapter extends ArrayAdapter<Kroeg>{

    public List<Kroeg> kroegen;

    /**
     * De ArrayAdapter voor kroegen
     * @param context De context van de applicatie
     * @param resource Het resource id van het list_item
     * @param objects De lijst met objecten (kroegen) die de inhoud van de listview vormen
     */
    public KroegListArrayAdapter(Context context, int resource, List<Kroeg> objects) {
        super(context, resource, objects);
        this.kroegen = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.list_item_kroeg, null);

        //Haal de kroeg op en vul alle informatie in het list_item
        Kroeg kroeg = kroegen.get(position);

        TextView tvName = (TextView) view.findViewById(R.id.tv_li_name);
        tvName.setText(kroeg.getNaam());
        TextView tvDescShort = (TextView) view.findViewById(R.id.tv_desc_short);
        tvDescShort.setText(kroeg.getBeschrijving());
        ImageView ivAvatar = (ImageView) view.findViewById(R.id.iv_avatar);

        //Kies een avatar voor de kroeg op basis van de categorie
        String categorie = kroeg.getCategorie();
        switch (categorie.toLowerCase()){
            case "eetcafé": ivAvatar.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_eetcafe));
                break;
            case "bier": ivAvatar.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_bier));
                break;
            case "bruin café": ivAvatar.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_bruin_cafe));
                break;
            case "muziek": ivAvatar.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_muziek));
                break;
            default: ivAvatar.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_grand_cafe));
        }

        RatingBar rbRating = (RatingBar) view.findViewById(R.id.rb_li_rating);
        rbRating.setRating(kroeg.getRating());

        return view;
    }
}
