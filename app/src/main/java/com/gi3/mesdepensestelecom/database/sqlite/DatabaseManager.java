package com.gi3.mesdepensestelecom.database.sqlite;

import android.content.Context;
import android.database.SQLException;

import com.gi3.mesdepensestelecom.database.mysql.MySQLApi;
import com.gi3.mesdepensestelecom.database.mysql.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DatabaseManager {

    private final DatabaseHelper dbHelper;
    private final MySQLApi mySQLApi;

    public DatabaseManager(Context context) {
        dbHelper = new DatabaseHelper(context);
        mySQLApi = RetrofitClient.createService(MySQLApi.class);
    }

    public void open() throws SQLException {
        dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public void performMySQLQuery(String sql) {
        Call<String> call = mySQLApi.performQuery(sql);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    String result = response.body();
                    // Handle the result as needed
                } else {
                    // Handle unsuccessful response
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                // Handle failure
            }
        });
    }

    // Add methods for database operations (insert, update, query) based on your requirements
}
