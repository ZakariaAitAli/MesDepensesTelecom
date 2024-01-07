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
        db = databaseHelper.getWritableDatabase();
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

    public List<Abonnement> getAllAbonnementsByUserId(int userId) {
        List<Abonnement> abonnements = new ArrayList<>();
        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        String[] columns = {"id", "dateDebut", "dateFin", "prix", "typeAbonnement", "operateur"};
        String selection = "userId = ?";
        String[] selectionArgs = {String.valueOf(userId)};

        Cursor cursor = db.query("abonnements", columns, selection, selectionArgs, null, null, null);

        while (cursor.moveToNext()) {
            Abonnement abonnement = new Abonnement();


            abonnement.Id =cursor.getInt(0);
            abonnement.dateDebut =cursor.getString(1);
            abonnement.dateFin =cursor.getString(2);
            abonnement.prix=cursor.getFloat(3);
            abonnement.typeAbonnement=cursor.getInt(4);
            abonnement.operateur=cursor.getInt(5);

            abonnements.add(abonnement);
        }

        cursor.close();
        db.close();
        return abonnements;
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
                    Abonnement abonnement = new Abonnement();

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




}


