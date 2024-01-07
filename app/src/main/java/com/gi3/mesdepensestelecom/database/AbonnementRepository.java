package com.gi3.mesdepensestelecom.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.gi3.mesdepensestelecom.Models.Abonnement;
import androidx.annotation.Nullable;
import android.content.ContentValues;

import java.util.ArrayList;
import java.util.List;

import com.gi3.mesdepensestelecom.Models.Abonnement;
import com.gi3.mesdepensestelecom.Models.TypeAbonnement;

import java.util.ArrayList;
import java.util.List;

public class AbonnementRepository extends SQLiteOpenHelper {
    private DatabaseHelper databaseHelper;
    public static final String databaseName = "Depense.db";

    SQLiteDatabase db;


    public AbonnementRepository(@Nullable Context context) {
        super(context, databaseName, null, 1);
        databaseHelper = new DatabaseHelper(context);
        db= databaseHelper.getWritableDatabase();
    }



    public long insertAbonnement(Abonnement abonnement) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("dateDebut", abonnement.dateDebut);
        values.put("dateFin", abonnement.dateFin);
        values.put("prix", abonnement.prix);
        values.put("operateur", abonnement.operateur);
        values.put("typeAbonnement", abonnement.typeAbonnement);
        values.put("userId", abonnement.userId);

        long result = db.insert("abonnements", null, values);
        db.close();
        return result;


    }





//REMEMBER TO ADD DATE
    private void GetAbonnements(String typeAbo) {

        List<Abonnement> abonnementsList = new ArrayList<>();

        int typeAboEnum = TypeAbonnement.Enum.valueOf(typeAbo).ordinal() ;
        Cursor cursor = db.rawQuery("SELECT * FROM abonnements WHERE typeAbonnement=? ", new String[]{typeAbo});

        try {
            int idIndex = cursor.getColumnIndex("id");
            int dateDebutIndex = cursor.getColumnIndex("dateDebut");
            int dateFinIndex = cursor.getColumnIndex("dateFin");
            int sommeIndex = cursor.getColumnIndex("somme");
            int operateurIndex = cursor.getColumnIndex("operateur");
            int userIdIndex = cursor.getColumnIndex("userId");
            int typeAbonnementIndex = cursor.getColumnIndex("typeAbonnement");

            while (cursor.moveToNext()) {
                if (idIndex != -1) {
                    Abonnement abonnement = new Abonnement ();

                    abonnement.Id = cursor.getInt(idIndex);
                    abonnement.dateDebut = cursor.getString(dateDebutIndex);
                    abonnement.dateFin = cursor.getString(dateFinIndex);
                    abonnement.prix = cursor.getFloat(sommeIndex);
                    abonnement.operateur = cursor.getInt(operateurIndex);
                    abonnement.userId = cursor.getInt(userIdIndex);
                    abonnement.typeAbonnement = cursor.getInt(typeAbonnementIndex);

                    abonnementsList.add(abonnement);
                }
            }
        } finally {
            cursor.close();
        }

    }




    private boolean isTableExists(SQLiteDatabase db, String tableName) {
        Cursor cursor = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table' AND name=?", new String[]{tableName});
        boolean tableExists = cursor.getCount() > 0;
        cursor.close();
        return tableExists;
    }


















    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }


    public List<String> getAllOperators() {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        List<String> operators = new ArrayList<>();

        // Query to get distinct operator names from "operateurs" table
        String query = "SELECT DISTINCT op_name FROM operateurs";
        Cursor cursor = db.rawQuery(query, null);

        // Check if the cursor has data
        if (cursor != null && cursor.moveToFirst()) {
            // Get the column index for "op_name"
            int columnIndex = cursor.getColumnIndex("op_name");

            // Iterate through the cursor to retrieve operator names
            do {
                // Check if the column index is valid
                if (columnIndex >= 0) {
                    String operatorName = cursor.getString(columnIndex);
                    operators.add(operatorName);
                }
            } while (cursor.moveToNext());
        }

        // Close cursor and database
        if (cursor != null) {
            cursor.close();
        }
        db.close();

        return operators;
    }
}


