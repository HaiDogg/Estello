package com.example.hdgg.estello;

public class Artikel {
    private String artikel_name;
    private int artikel_preis;


    public Artikel(){}

    public String getArtikel_name() {
        return artikel_name;
    }

    public void setArtikel_name(String artikel_name) {
        this.artikel_name = artikel_name;
    }

    public int getArtikel_preis() {
        return artikel_preis;
    }

    public void setArtikel_preis(int artikel_preis) {
        this.artikel_preis = artikel_preis;
    }

    public Artikel(String artikel_name, int artikel_preis){
        this.artikel_name = artikel_name;
        this.artikel_preis = artikel_preis;
    }


}
