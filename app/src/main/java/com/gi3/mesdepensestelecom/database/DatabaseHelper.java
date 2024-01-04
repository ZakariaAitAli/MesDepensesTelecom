package com.gi3.mesdepensestelecom.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String databaseName = "Depense.db";

    public DatabaseHelper(@Nullable Context context) {
        super(context, databaseName, null, 1);
    }

    @Override

    public void onCreate(SQLiteDatabase MyDatabase) {
        if (!isTableExists(MyDatabase, "users")) {
            MyDatabase.execSQL("create Table users(id INTEGER primary key, username TEXT, password TEXT)");
        }

        if (!isTableExists(MyDatabase, "operateurs")) {
            MyDatabase.execSQL("create Table operateurs(op_id INTEGER primary key, op_name TEXT)");
        }

        if (!isTableExists(MyDatabase, "abonnements")) {
            MyDatabase.execSQL("CREATE TABLE abonnements (" + "abonnement_id INTEGER PRIMARY KEY," + "date_debut DATE," + "date_fin DATE," + "prix FLOAT," +  "type TEXT CHECK (type IN (1, 2, 3, 4, 5))," +
                    "op_id INTEGER," + "user_id INTEGER," +  "FOREIGN KEY(op_id) REFERENCES operations(op_id)," + "FOREIGN KEY(user_id) REFERENCES users(user_id))");
        }

        if (!isTableExists(MyDatabase, "supplements")) {
            MyDatabase.execSQL("create Table supplements(" + " supplement_id INTEGER primary key, " + "date DATE ," + "prix float, "+ "abonnement_id INTEGER," + " FOREIGN KEY(abonnement_id) REFERENCES abonnements(abonnement_id) )");
        }

        if (!isTableExists(MyDatabase, "recharges")) {
            MyDatabase.execSQL("create Table recharges(" + " recharge_id INTEGER primary key, " + "date DATE ," + "prix float, "+ "user_id INTEGER," + "FOREIGN KEY(user_id) REFERENCES users(user_id) )");
        }
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
    }

    public Boolean insertData(String username, String password) {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("password", password);
        long result = MyDatabase.insert("users", null, contentValues);
        return result != -1;
    }

    public Boolean checkUsername(String username) {
        SQLiteDatabase myDatabase = this.getWritableDatabase();
        Cursor cursor = myDatabase.rawQuery("SELECT * FROM users WHERE username = ?", new String[]{username});
        boolean usernameExists = cursor.getCount() > 0;
        cursor.close();

        return usernameExists;
    }

    public Boolean checkUsernamePassword(String username, String password) {
        SQLiteDatabase myDatabase = this.getWritableDatabase();
        Cursor cursor = myDatabase.rawQuery("SELECT * FROM users WHERE username = ? AND password = ?", new String[]{username, password});
        boolean usernamePasswordMatch = cursor.getCount() > 0;
        cursor.close();
        return usernamePasswordMatch;
    }

}
