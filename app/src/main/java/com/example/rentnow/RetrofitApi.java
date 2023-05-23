package com.example.rentnow;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;


public interface RetrofitApi {

    @GET("/{brand}/motorcycles")
    Call <List<Motorcycle>> getMotorcyclesByBrand(@Path("brand") String brand);

    @GET("/motorcycles")
    Call <List<Motorcycle>> getMyMotorcycles(@Header("Authorization") String token);

    @POST("users")
    Call<User> createAccount(@Body User user);

    @POST("sessions")
    Call<LoginResponse> login(@Body User user);

    @PUT("/{brand}/motorcycles/{id}")
    Call<Void> rentMotorcycle(@Path("brand") String brand, @Path("id") String id, @Header("Authorization") String token);
}
