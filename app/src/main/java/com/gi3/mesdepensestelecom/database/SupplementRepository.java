package com.gi3.mesdepensestelecom.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.gi3.mesdepensestelecom.Models.Recharge;
import com.gi3.mesdepensestelecom.Models.Supplement;

import java.util.HashMap;

public class SupplementRepository {
    private final DatabaseHelper databaseHelper;

    public SupplementRepository(Context context) {
        this.databaseHelper = new DatabaseHelper(context);

    }

    public HashMap<Integer, String> getAbonnementsMapByUserId(Integer userId){
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        String[] projection = {"id", "typeAbonnement" /* Add other columns as needed */};
        String selection = userId    + " = ?";
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

        return abonnementsMap;
    }

    private String transformTypeAbonnement(int typeAbonnement) {
        // You can implement your transformation logic here
        switch (typeAbonnement) {
            case 1:
                return "fibreOptique";
            case 2:
                return "WIFI";
            case 3:
                return "MobileAppel";
            case 4:
                return "Fixe";
            case 5:
                return "MobileInternet";
            default:
                return "Unknown";
        }
    }


    public long insertRechargeSupplement(Supplement supplement) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("somme", supplement.prix);
        values.put("operateur", supplement.idAbonnement);
        values.put("date", supplement.date);
        long result = db.insert("supplements", null, values);
        db.close();
        return result;
    }
}
