package com.example.hdgg.estello;

public class Benutzer {
    private String benutzer_name;
    private String benutzer_pw;



public Benutzer(){

    }

    public Benutzer(String benutzer_name,
                    //String benutzer_email,
                    String benutzer_pw){

        this.benutzer_name = benutzer_name;
        //this.benutzer_email = benutzer_email;
        this.benutzer_pw = benutzer_pw;

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


    @Override
    public String toString(){
    String out = benutzer_name;
    return out;
    }
}
