package com.gi3.mesdepensestelecom.data;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.gi3.mesdepensestelecom.data.model.LoggedInUser;
import com.gi3.mesdepensestelecom.database.DatabaseHelper;

import java.io.IOException;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {
    private DatabaseHelper databaseHelper;

    public LoginDataSource(DatabaseHelper databaseHelper) {
        this.databaseHelper = databaseHelper;
    }

    public Result<LoggedInUser> login(String username, String password) {

        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        try {
            // Check if the username and password match in the database
            Cursor cursor = db.rawQuery("SELECT * FROM users WHERE username = ? AND password = ?", new String[]{username, password});

            if (cursor.moveToFirst()) {
                // User authenticated successfully
                int userId = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String displayName = cursor.getString(cursor.getColumnIndexOrThrow("username"));
                LoggedInUser loggedInUser = new LoggedInUser(String.valueOf(userId), displayName);
                cursor.close();
                db.close();
                return new Result.Success<>(loggedInUser);
            } else {
                // Authentication failed
                cursor.close();
                db.close();
                return new Result.Error(new IOException("Invalid credentials"));
            }
        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    public void logout() {
        // TODO: revoke authentication

    }
}