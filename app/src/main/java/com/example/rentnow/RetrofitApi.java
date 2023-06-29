package com.example.rentnow;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface RetrofitApi {

    @GET("/motorcycles")
    Call <List<Motorcycle>> getMotorcyclesByBrand(@Query("brand") String brand, @Header("Authorization") String token);

    @GET("/motorcycles")
    Call <List<Motorcycle>> getMyMotorcycles(@Query("myMotorcycles") String myMotorcycles, @Header("Authorization") String token);

    @POST("users")
    Call<User> createAccount(@Body User user);

    @POST("sessions")
    Call<LoginResponse> login(@Body User user);

    @PUT("/motorcycles/{id}")
    Call<Void> rentMotorcycle(@Path("id") String id, @Header("Authorization") String token);
}
