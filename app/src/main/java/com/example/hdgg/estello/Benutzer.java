package com.example.hdgg.estello;

public class Benutzer {
    private String benutzer_name;
    private String benutzer_pw;
    private double guthaben;



public Benutzer(){

    }

    public Benutzer(String benutzer_name, String benutzer_pw, double guthaben){

        this.benutzer_name = benutzer_name;
        this.benutzer_pw = benutzer_pw;
        this.guthaben = guthaben;

    }

    //Benutzer Name

    public void setBenutzer_name(String benutzer_name){
    this.benutzer_name = benutzer_name;

    }

    public String getBenutzer_name(){
        return benutzer_name;
    }


    //Benutzer Passwort

    public void setBenutzer_pw(String benutzer_pw){
        this.benutzer_pw = benutzer_pw;

    }

    public String getBenutzer_pass(){

    return benutzer_pw;
    }

    //Guthaben
    public void setGuthaben(double guthaben){
        this.guthaben = guthaben;
    }

    public double getGuthaben(){
    return guthaben;
    }
}
