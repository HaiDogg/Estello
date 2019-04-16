package com.example.hdgg.estello;

public class Listing {

    private String listing_name;
    private double preis;



    public Listing(String listing_name, double preis){

        this.listing_name = listing_name;
        this.preis = preis;

    }

    public void setListing_name(String listing_name){
        this.listing_name= listing_name;

    }

    public String getListing_name() {
        return listing_name;
    }

    public void setPreis(double preis){
        this.preis= preis;

    }

    public double getPreis() {
        return preis;
    }
}
