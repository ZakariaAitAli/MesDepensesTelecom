package com.gi3.mesdepensestelecom.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.gi3.mesdepensestelecom.Models.Abonnement;
import com.gi3.mesdepensestelecom.Models.TypeAbonnement;

import java.util.ArrayList;
import java.util.List;

public class AbonnementRepository extends SQLiteOpenHelper  {
    public static final String databaseName = "Depense.db";
    DatabaseHelper databaseHelper ;
    SQLiteDatabase db  ;




    public AbonnementRepository(@Nullable Context context) {
        super(context, databaseName, null, 1);
        databaseHelper = new DatabaseHelper(context);
        db= databaseHelper.getWritableDatabase();
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
                    abonnement.DateDebut = cursor.getString(dateDebutIndex);
                    abonnement.dateFin = cursor.getString(dateFinIndex);
                    abonnement.prix = cursor.getFloat(sommeIndex);
                    abonnement.operateur = cursor.getInt(operateurIndex);
                    abonnement.idUser = cursor.getInt(userIdIndex);
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
