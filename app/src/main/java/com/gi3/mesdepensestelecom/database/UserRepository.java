package com.gi3.mesdepensestelecom.database;
// UserRepository.java

import android.content.Context;
import android.content.SharedPreferences;

import com.gi3.mesdepensestelecom.Models.User;

public class UserRepository {
    private final DatabaseHelper databaseHelper;
    private final SharedPreferences sharedPreferences;

    // SharedPreferences keys
    private static final String USER_SESSION_PREF = "user_session";
    private static final String USER_ID_KEY = "user_id";
    private static final String DISPLAY_NAME_KEY = "display_name";

    public UserRepository(Context context) {
        this.databaseHelper = new DatabaseHelper(context);
        this.sharedPreferences = context.getSharedPreferences(USER_SESSION_PREF, Context.MODE_PRIVATE);
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

    public void saveUserSession(String userId, String displayName) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_ID_KEY, userId);
        editor.putString(DISPLAY_NAME_KEY, displayName);
        editor.apply();
    }

    public boolean isUserLoggedIn() {
        String userId = sharedPreferences.getString(USER_ID_KEY, null);
        String displayName = sharedPreferences.getString(DISPLAY_NAME_KEY, null);
        return userId != null && displayName != null;
    }

    public void clearUserSession() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(USER_ID_KEY);
        editor.remove(DISPLAY_NAME_KEY);
        editor.apply();
    }
}
