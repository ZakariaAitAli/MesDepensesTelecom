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
            insertUser(MyDatabase, "simofatt", "6341");
            insertUser(MyDatabase, "nada", "6341");
        }

        if (!isTableExists(MyDatabase, "abonnements")) {

            MyDatabase.execSQL("CREATE TABLE abonnements (" + "id INTEGER PRIMARY KEY," + "dateDebut String," + "dateFin String," + "prix FLOAT," + "typeAbonnement INTEGER," + "operateur INTEGER," + "userId INTEGER," + "FOREIGN KEY(userId) REFERENCES users(id))");
            insertAbonnement(MyDatabase, "12/03/2023", "12/04/2024", 100.0f, TypeAbonnement.Enum.fibreOptique.ordinal(), OperateurEnum.Enum.IAM.ordinal(), 1);
            insertAbonnement(MyDatabase,"12/03/2023", "12/04/2024", 150.0f, TypeAbonnement.Enum.WIFI.ordinal(), OperateurEnum.Enum.INWI.ordinal(), 2);

        }

        if (!isTableExists(MyDatabase, "supplements")) {
            MyDatabase.execSQL("create Table supplements(" + " Id INTEGER primary key, " + "date String ," + "prix float, " + "abonnementId INTEGER," + " FOREIGN KEY(abonnementId) REFERENCES abonnements(id) )");
        }

        if (!isTableExists(MyDatabase, "recharges")) {
            MyDatabase.execSQL("create Table recharges(" + " id INTEGER primary key, " + "date String ," + "somme float, " + "userId INTEGER," + "FOREIGN KEY(userId) REFERENCES users(id) )");
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