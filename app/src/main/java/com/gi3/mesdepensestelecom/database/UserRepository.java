package com.gi3.mesdepensestelecom.database;

import android.content.Context;

import com.gi3.mesdepensestelecom.Models.User;

public class UserRepository {
    private final DatabaseHelper databaseHelper;

    public UserRepository(Context context) {
        databaseHelper = new DatabaseHelper(context);
    }

    public long addUser(User user) {
        return databaseHelper.insertUser(user);
    }

    public boolean checkUsername(String username) {
        return databaseHelper.checkUsername(username);
    }

    public boolean checkUsernamePassword(User user) {
        return databaseHelper.checkUsernamePassword(user);
    }
}
