package com.trivento.deventerkroegenapp.model;

import java.io.Serializable;

/**
 * Created by Sliomere on 12/05/2016.
 */
public class Kroeg implements Serializable{

    private int kroeg_id;
    private String naam;
    private String adres;
    private String openingstijden;
    private String beschrijving;
    private String avatar;
    private String categorie;

    public Kroeg(int kroeg_id, String naam, String adres, String openingstijden, String beschrijving, String avatar, String categorie) {
        this.kroeg_id = kroeg_id;
        this.naam = naam;
        this.adres = adres;
        this.openingstijden = openingstijden;
        this.beschrijving = beschrijving;
        this.avatar = avatar;
        this.categorie = categorie;
    }

    public int getKroeg_id() {
        return kroeg_id;
    }

    public String getNaam() {
        return naam;
    }

    public String getAdres() {
        return adres;
    }

    public String getOpeningstijden() {
        return openingstijden;
    }

    public String getBeschrijving() {
        return beschrijving;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getCategorie() {
        return categorie;
    }

    @Override
    public String toString() {
        return "Kroeg{" +
                "kroeg_id=" + kroeg_id +
                ", naam='" + naam + '\'' +
                ", adres='" + adres + '\'' +
                ", openingstijden='" + openingstijden + '\'' +
                ", beschrijving='" + beschrijving + '\'' +
                ", categorie='" + categorie + '\'' +
                '}';
    }
}
