package com.example.rentnow;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;


public interface RetrofitApi {

   // @GET("users/{user}/repos")
    //Call <List<Repository>> getRepositories(@Path("user") String user);

    @POST("users")
    Call<User> createAccount(@Body User user);

    @POST("sessions")
    Call<User> login(@Body User user);
}
