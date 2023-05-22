package com.example.rentnow;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;


public interface RetrofitApi {

    @GET("/{brand}/motorcycles")
    Call <List<Motorcycle>> getMotorcyclesByBrand(@Path("brand") String brand);

    @POST("users")
    Call<User> createAccount(@Body User user);

    @POST("sessions")
    Call<User> login(@Body User user);
}
