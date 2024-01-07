package com.gi3.mesdepensestelecom.Models;

import java.util.Date;

public class Recharge {

    public int Id;
    public int idUser;
    public float prix;
    public String date;
    public int operateur;


    public Recharge(float v, int operator, int userId, String date) {
        this.prix= v;
        this.operateur= operator;
        this.idUser= userId;
        this.date = date;
    }
    public Recharge() {

    }
}