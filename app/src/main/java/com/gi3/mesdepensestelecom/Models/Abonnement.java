package com.gi3.mesdepensestelecom.Models;

import java.util.Date;

public class Abonnement {

    public int Id;
    public String dateDebut;
    public String dateFin;
    public float prix;
    public int operateur;
    public int userId ;
    public int typeAbonnement;
    public Abonnement(String dateDebut, String dateFin, float prix, int operateur, int userId, int typeAbonnement) {
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.prix = prix;
        this.operateur = operateur;
        this.userId = userId;
        this.typeAbonnement = typeAbonnement;
    }
public Abonnement(){}

}


