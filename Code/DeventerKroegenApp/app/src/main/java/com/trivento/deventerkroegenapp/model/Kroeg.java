package com.trivento.deventerkroegenapp.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Sliomere on 12/05/2016.
 */
public class Kroeg implements Serializable{

    private String name;
    private float rating;

    public Kroeg(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }
}
