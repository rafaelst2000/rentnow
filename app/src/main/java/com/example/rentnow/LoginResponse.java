package com.example.rentnow;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class LoginResponse implements Serializable {
    @SerializedName("name")
    private String name;
    @SerializedName("token")
    private String token;

    public LoginResponse(String name, String token) {
        this.name = name;
        this.token = token;
    }

    public String getName() { return name; }
    public String getToken() { return token; }
}
