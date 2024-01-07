package com.gi3.mesdepensestelecom.Models;

import java.util.Date;

public class Supplement {
    public int id ;
    public int idAbonnement ;
    public float prix ;

    public String date ;

    public Supplement( int idAbonnement,  float prix, String date ){
        this.idAbonnement =idAbonnement;
        this.prix=prix;
        this.date=date;
    }
}