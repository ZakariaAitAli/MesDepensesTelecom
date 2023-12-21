package com.gi3.mesdepensestelecom.database.mysql;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface MySQLApi {

    @POST("performQuery")
    Call<String> performQuery(@Body String sql);
}
