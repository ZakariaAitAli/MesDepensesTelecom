package com.gi3.mesdepensestelecom.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.gi3.mesdepensestelecom.Models.Recharge;

public class RechargeRepository {
    private final DatabaseHelper databaseHelper;

    public RechargeRepository(Context context) {
        this.databaseHelper = new DatabaseHelper(context);

    }
    public long insertRechargeSimple(Recharge recharge) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("somme", recharge.prix);
        values.put("operateur", recharge.operateur);
        values.put("userId", recharge.idUser);
        values.put("date", recharge.date);
        long result = db.insert("recharges", null, values);
        db.close();
        return result;
    }
}
