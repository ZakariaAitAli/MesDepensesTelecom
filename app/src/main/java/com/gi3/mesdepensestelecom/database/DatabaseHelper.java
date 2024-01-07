package com.gi3.mesdepensestelecom.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.gi3.mesdepensestelecom.Models.OperateurEnum;
import com.gi3.mesdepensestelecom.Models.TypeAbonnement;
import com.gi3.mesdepensestelecom.Models.User;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String databaseName = "Depense.db";

    public DatabaseHelper(Context context) {
        super(context, databaseName, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase MyDatabase) {
        if (!isTableExists(MyDatabase, "users")) {
            MyDatabase.execSQL("create Table users(id INTEGER primary key, username TEXT, password TEXT)");
            insertUser(MyDatabase, "simofatt", "63416341");
            insertUser(MyDatabase, "nada", "63416341");
        }

        if (!isTableExists(MyDatabase, "abonnements")) {

            MyDatabase.execSQL("CREATE TABLE abonnements (" + "id INTEGER PRIMARY KEY," + "dateDebut String," + "dateFin String," + "prix FLOAT," + "typeAbonnement INTEGER," + "operateur INTEGER," + "userId INTEGER," + "FOREIGN KEY(userId) REFERENCES users(id))");
            insertAbonnement(MyDatabase, "01/01/2023", "31/12/2023", 100.0f, TypeAbonnement.Enum.fibreOptique.ordinal(), OperateurEnum.Enum.IAM.ordinal(), 1);
            insertAbonnement(MyDatabase,"01/01/2024", "31/12/2024", 150.0f, TypeAbonnement.Enum.WIFI.ordinal(), OperateurEnum.Enum.INWI.ordinal(), 2);


        }

        if (!isTableExists(MyDatabase, "supplements")) {
            MyDatabase.execSQL("create Table supplements(" + " Id INTEGER primary key, " + "date String ," + "prix float, " + "abonnementId INTEGER," + " FOREIGN KEY(abonnementId) REFERENCES abonnements(id) )");
            MyDatabase.execSQL("INSERT INTO supplements (date, prix, abonnementId) VALUES ('01/03/2023', 100.0, 1)");
            MyDatabase.execSQL("INSERT INTO supplements (date, prix, abonnementId) VALUES ('01/04/2023', 150.0, 2)");
        }

        if (!isTableExists(MyDatabase, "recharges")) {

            MyDatabase.execSQL("create Table recharges(" + " id INTEGER primary key, " + "date String ," + "somme float, " + "userId INTEGER," + "operateur Integer," + "FOREIGN KEY(userId) REFERENCES users(id) )" );
            // Insert data into "recharges" table
            MyDatabase.execSQL("INSERT INTO recharges (date, somme, userId,operateur) VALUES ('01/05/2023', 20.0, 1,1)");
            MyDatabase.execSQL("INSERT INTO recharges (date, somme, userId,operateur) VALUES ('01/06/2023', 40.0, 2,1)");

        }
    }





    private static void insertAbonnement(SQLiteDatabase db, String dateDebut, String dateFin, float somme, int typeAbonnement, int operateur, int userId) {
        ContentValues values = new ContentValues();
        values.put("dateDebut", dateDebut);
        values.put("dateFin", dateFin);
        values.put("prix", somme);
        values.put("typeAbonnement", typeAbonnement);
        values.put("operateur", operateur);
        values.put("userId", userId);

        db.insert("abonnements", null, values);
    }
    private static void insertUser(SQLiteDatabase db, String username, String password) {
        ContentValues values = new ContentValues();
        values.put("username", username);
        values.put("password", password);

        db.insert("users", null, values);
    }

    private boolean isTableExists(SQLiteDatabase db, String tableName) {
        Cursor cursor = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table' AND name=?", new String[]{tableName});
        boolean tableExists = cursor.getCount() > 0;
        cursor.close();
        return tableExists;
    }


    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int i, int i1) {
        MyDB.execSQL("drop Table if exists users");
        MyDB.execSQL("drop Table if exists abonnements");
        MyDB.execSQL("drop Table if exists supplements");
        MyDB.execSQL("drop Table if exists recharges");
    }

    public long insertUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", user.username);
        contentValues.put("password", user.password);

        long result = db.insert("users", null, contentValues);
        db.close();

        return result;
    }


    public boolean checkUsername(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE username = ?", new String[]{username});
        boolean usernameExists = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return usernameExists;
    }

    public boolean checkUsernamePassword(User user) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE username = ? AND password = ?", new String[]{user.username, user.password});
        boolean usernamePasswordMatch = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return usernamePasswordMatch;
    }

}