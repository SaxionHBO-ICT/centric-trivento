package com.trivento.deventerkroegenapp.model;

/**
 * Created by Sliomere on 25/05/2016.
 */
public class Gebruiker {

    private String gebruiker_id;
    private String voornaam;
    private String achternaam;
    private String adres;
    private String postcode;
    private String woonplaats;
    private String land;
    private String mail;
    private String wachtwoord;

    public Gebruiker(String gebruiker_id, String voornaam, String achternaam, String adres, String postcode, String woonplaats, String land, String mail, String wachtwoord) {
        this.gebruiker_id = gebruiker_id;
        this.voornaam = voornaam;
        this.achternaam = achternaam;
        this.adres = adres;
        this.postcode = postcode;
        this.woonplaats = woonplaats;
        this.land = land;
        this.mail = mail;
        this.wachtwoord = wachtwoord;
    }

    public String getGebruiker_id() {
        return gebruiker_id;
    }

    public String getVoornaam() {
        return voornaam;
    }

    public String getAchternaam() {
        return achternaam;
    }

    public String getAdres() {
        return adres;
    }

    public String getPostcode() {
        return postcode;
    }

    public String getWoonplaats() {
        return woonplaats;
    }

    public String getLand() {
        return land;
    }

    public String getMail() {
        return mail;
    }

    public String getWachtwoord() {
        return wachtwoord;
    }
}
