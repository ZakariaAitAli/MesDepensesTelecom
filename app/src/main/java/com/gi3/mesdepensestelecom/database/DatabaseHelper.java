package com.gi3.mesdepensestelecom.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.gi3.mesdepensestelecom.Models.User;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String databaseName = "Depense.db";

    public DatabaseHelper(Context context) {
        super(context, databaseName, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create Table users(id INTEGER primary key, username TEXT, password TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop Table if exists users");
        onCreate(db);
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

