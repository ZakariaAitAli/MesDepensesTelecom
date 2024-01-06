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



import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.text.ParseException;
import java.util.Calendar;

import java.util.Locale;




public class AbonnementRepository extends SQLiteOpenHelper {
    public static final String databaseName = "Depense.db";
    DatabaseHelper databaseHelper;
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

    //REMEMBER TO ADD DATE
    public void GetAbonnements(String typeAbo) {


        List<Abonnement> abonnementsList = new ArrayList<>();
        HashMap<String, String> AbonnementsDic = new HashMap<>();

        String typeAboEnum = Integer.toString(TypeAbonnement.Enum.valueOf(typeAbo).ordinal());

        Cursor cursor = db.rawQuery("SELECT * FROM abonnements WHERE typeAbonnement=? ", new String[]{typeAboEnum});

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

            for (Abonnement item : abonnementsList) {


            }
        } finally {
            cursor.close();
        }

    }

    public int getMonthDifference(String dateDebut, String dateFin) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());

        try {
            Date startDate = dateFormat.parse(dateDebut);
            Date endDate = dateFormat.parse(dateFin);

            Calendar startCalendar = Calendar.getInstance();
            startCalendar.setTime(startDate);

            Calendar endCalendar = Calendar.getInstance();
            endCalendar.setTime(endDate);

            int diffYear = endCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR);
            int diffMonth = endCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH);

            if (endCalendar.get(Calendar.DAY_OF_MONTH) < startCalendar.get(Calendar.DAY_OF_MONTH)) {
                diffMonth--;
            }

            return Math.abs(diffYear * 12 + diffMonth);
        } catch (ParseException e) {
            e.printStackTrace(); // Handle the parsing exception according to your requirements
            return -1; // Return an error code or handle the error as needed
        }

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }




}


