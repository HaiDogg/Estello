package com.example.hdgg.estello;

public class Benutzer {
    private static int benutzer_id = 0;
    private String benutzer_name;
    private String benutzer_email;
    private String benutzer_pw;



public Benutzer(){

    }

    public Benutzer(String benutzer_name, String benutzer_email, String benutzer_pw){

        this.benutzer_name = benutzer_name;
        this.benutzer_email = benutzer_email;
        this.benutzer_pw = benutzer_pw;
        benutzer_id ++;

    }

    public static int getBenutzer_id() {
        return benutzer_id;
    }

    public void setBenutzer(String benutzer_name){
    this.benutzer_name = benutzer_name;

    }

    public String getBenutzer_name(){
        return benutzer_name;
    }

    public String getBenutzer_email(){
        return benutzer_email;
    }
}
