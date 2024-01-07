package com.gi3.mesdepensestelecom.database;

import static com.gi3.mesdepensestelecom.database.SupplementRepository.transformTypeAbonnement;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.gi3.mesdepensestelecom.Models.Abonnement;
import androidx.annotation.Nullable;
import android.content.ContentValues;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.gi3.mesdepensestelecom.Models.Abonnement;
import com.gi3.mesdepensestelecom.Models.Recharge;
import com.gi3.mesdepensestelecom.Models.Supplement;
import com.gi3.mesdepensestelecom.Models.TypeAbonnement;



import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.text.ParseException;
import java.util.Calendar;

import java.util.Locale;
import java.util.concurrent.TimeUnit;


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

    public List<String> getAllAbonnementsByUserId(int userId) {
        List<String> abonnements = new ArrayList<>();

        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        String[] columns = {"id", "dateDebut", "dateFin", "prix", "typeAbonnement", "operateur"};
        String selection = "userId = ?";
        String[] selectionArgs = {String.valueOf(userId)};

        Cursor cursor = db.query("abonnements", columns, selection, selectionArgs, null, null, null);

        while (cursor.moveToNext()) {
            //Abonnement abonnement = new Abonnement();
           /* abonnement.Id =cursor.getInt(0);
            abonnement.dateDebut =cursor.getString(1);
            abonnement.dateFin =cursor.getString(2);
            abonnement.prix=cursor.getFloat(3);
            abonnement.typeAbonnement=cursor.getInt(4);
            abonnement.operateur=cursor.getInt(5);
                        abonnements.add(abonnement);

            */
            int id = cursor.getInt(0);
            String dateDebut =cursor.getString(1);
            String dateFin =cursor.getString(2);
            String prix =cursor.getString(3);
            String typeAbonnement= transformTypeAbonnement(cursor.getInt(4));
            String abonnementData = typeAbonnement + "- Montant: " + prix + "Dhs - Date fin: "+ dateFin ;
            abonnements.add(abonnementData);




        }

        cursor.close();
        db.close();
        return abonnements;
    }

//REMEMBER TO ADD DATE

    //REMEMBER TO ADD DATE
    public  HashMap<String, Float>  GetAbonnements(String year,int userId) {
        List<Abonnement> abonnementsList = new ArrayList<>();
        List<Supplement> supplements = new ArrayList<>();
        List<Recharge> recharges = new ArrayList<>();

        HashMap<String, Float> abonnementsDic = new HashMap<>();

        Cursor cursor = db.rawQuery("SELECT * FROM abonnements  Where userId= ?", new String[]{String.valueOf(userId)});

        try {
            int idIndex = cursor.getColumnIndex("id");
            int dateDebutIndex = cursor.getColumnIndex("dateDebut");
            int dateFinIndex = cursor.getColumnIndex("dateFin");
            int sommeIndex = cursor.getColumnIndex("prix");
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
                int diff = getMonthDifference(item.dateDebut, item.dateFin);

                // Parse the start date to extract the year and month
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                Date startDate;
                try {
                    startDate = dateFormat.parse(item.dateDebut);
                } catch (ParseException e) {
                    e.printStackTrace();
                    continue;
                }

                Calendar calendar = Calendar.getInstance();
                calendar.setTime(startDate);

                for (int i = 0; i < diff; i++) {
                    // Check if the year matches the specified year
                    if (calendar.get(Calendar.YEAR) == Integer.parseInt(year)) {
                        String key = String.format("%tY-%tm", calendar, calendar);
                        if (abonnementsDic.containsKey(key)) {
                            float somme = abonnementsDic.get(key) + item.prix;
                            abonnementsDic.replace(key, somme);
                        } else {
                            abonnementsDic.put(key, item.prix);
                        }
                    }
                    calendar.add(Calendar.MONTH, 1);
                }
            }
            supplements =getSupplementsByYear(year) ;
            recharges = getRechargesByYear(year) ;
            for (Supplement supp : supplements) {
                try {

                    SimpleDateFormat inputFormat = new SimpleDateFormat("dd/MM/yyyy");
                    Date date = inputFormat.parse(supp.date);

                    SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM");
                    String formattedDate = outputFormat.format(date);


                    if (abonnementsDic.containsKey(formattedDate)) {
                        float somme = abonnementsDic.get(formattedDate) + supp.prix;
                        abonnementsDic.replace(formattedDate, somme);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            for(Recharge rech : recharges) {

          try{
                SimpleDateFormat inputFormat = new SimpleDateFormat("dd/MM/yyyy");
                Date date = inputFormat.parse(rech.date);

                SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy/MM");
                String formattedDate = outputFormat.format(date);
                if (abonnementsDic.containsKey(formattedDate)) {
                    float somme = abonnementsDic.get(formattedDate) + rech.prix;
                    abonnementsDic.replace(formattedDate, somme);
                }else {
                    abonnementsDic.put(formattedDate,rech.prix);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
            }


        } finally {
            cursor.close();

        }
        return abonnementsDic ;
    }

    public List<Recharge> getRechargesByYear(String year) {
        List<Recharge> rechargesList = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT * FROM recharges WHERE SUBSTR(date, -4) = ? AND date LIKE '__/__/____'", new String[]{year});

        try {
            int idIndex = cursor.getColumnIndex("id");
            int dateIndex = cursor.getColumnIndex("date");
            int sommeIndex = cursor.getColumnIndex("somme");
            int userIdIndex = cursor.getColumnIndex("userId");

            while (cursor.moveToNext()) {
                if (idIndex != -1) {
                    Recharge recharge = new Recharge();

                    recharge.Id = cursor.getInt(idIndex);
                    recharge.date = cursor.getString(dateIndex);
                    recharge.prix = cursor.getFloat(sommeIndex);

                    rechargesList.add(recharge);
                }
            }
        } finally {
            cursor.close();
        }

        return rechargesList;
    }


    public List<Supplement> getSupplementsByYear(String year) {
        List<Supplement> supplementsList = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT * FROM supplements WHERE SUBSTR(date, -4) = ? AND date LIKE '__/__/____'", new String[]{year});

        try {
            int idIndex = cursor.getColumnIndex("Id");
            int dateIndex = cursor.getColumnIndex("date");
            int prixIndex = cursor.getColumnIndex("prix");
            int abonnementIdIndex = cursor.getColumnIndex("abonnementId");

            while (cursor.moveToNext()) {
                if (idIndex != -1) {
                    Supplement supplement = new Supplement();

                    supplement.id = cursor.getInt(idIndex);
                    supplement.date = cursor.getString(dateIndex);
                    supplement.prix = cursor.getFloat(prixIndex);
                    supplement.idAbonnement = cursor.getInt(abonnementIdIndex);

                    supplementsList.add(supplement);
                }
            }
        } finally {
            cursor.close();
        }

        return supplementsList;
    }




    public int getMonthDifference(String dateString1, String dateString2) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date date1 = dateFormat.parse(dateString1);
            Date date2 = dateFormat.parse(dateString2);

            // Calculate the difference in months
            long diffInMillies = Math.abs(date2.getTime() - date1.getTime());
            long diffInDays = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);

            int monthsDifference = (int) (diffInDays / 30);
             return monthsDifference ;

        } catch (ParseException e) {
            e.printStackTrace();
            return -1 ;
        }
    }



    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }




}


