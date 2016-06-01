package com.trivento.deventerkroegenapp.model;

/**
 * Created by Sliomere on 25/05/2016.
 */
public class Rating {

    private int kroeg_id;
    private int gebruiker_id;
    private int rating;

    public Rating() {
    }

    public Rating(int kroeg_id, int gebruiker_id, int rating) {
        this.kroeg_id = kroeg_id;
        this.gebruiker_id = gebruiker_id;
        this.rating = rating;
    }

    public int getKroeg_id() {
        return kroeg_id;
    }

    public int getGebruiker_id() {
        return gebruiker_id;
    }

    public int getRating() {
        return rating;
    }
}
