package com.gi3.mesdepensestelecom.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.gi3.mesdepensestelecom.Models.Recharge;
import com.gi3.mesdepensestelecom.Models.Supplement;

import java.util.HashMap;

public class SupplementRepository {
    private final DatabaseHelper databaseHelper;

    public SupplementRepository(Context context) {
        this.databaseHelper = new DatabaseHelper(context);

    }

    public HashMap<Integer, String> getAbonnementsMapByUserId(int userId){
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        String[] projection = {"id", "typeAbonnement" /* Add other columns as needed */};
        String selection = "userId = ?";
        String[] selectionArgs = {String.valueOf(userId)};
        Cursor cursor = db.query("abonnements", projection, selection, selectionArgs, null, null, null);

        HashMap<Integer, String> abonnementsMap = new HashMap<>();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id_abonnement = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                int typeAbonnement = cursor.getInt(cursor.getColumnIndexOrThrow("typeAbonnement"));
                String transformedType = transformTypeAbonnement(typeAbonnement);

                // Concatenate transformedType with id
                String value = transformedType + "-" + id_abonnement;

                // Add to the HashMap
                abonnementsMap.put(id_abonnement, value);
            } while (cursor.moveToNext());

            // Close the cursor after use
            cursor.close();
        }

        Log.d("abonnementsMapSize", String.valueOf(abonnementsMap.size()));


        return abonnementsMap;
    }

    static String transformTypeAbonnement(int typeAbonnement) {
        // You can implement your transformation logic here
        switch (typeAbonnement) {
            case 0:
                return "fibreOptique";
            case 1:
                return "WIFI";
            case 2:
                return "MobileAppel";
            case 3:
                return "Fixe";
            case 4:
                return "MobileInternet";
            default:
                return "Unknown";
        }
    }


    public long insertRechargeSupplement(Supplement supplement) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("prix", supplement.prix);
        values.put("abonnementId", supplement.idAbonnement);
        values.put("date", supplement.date);
        long result = db.insert("supplements", null, values);
        db.close();
        return result;
    }
}
