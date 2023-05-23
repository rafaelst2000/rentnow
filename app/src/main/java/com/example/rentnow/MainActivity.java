package com.example.rentnow;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import org.w3c.dom.Text;

import java.util.Arrays;
import java.util.List;
import com.example.rentnow.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    EditText email;
    EditText password;
    Button loginBtn;
    TextView createAccountBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.app_bar_background)));
        }

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        loginBtn = findViewById(R.id.loginBtn);
        createAccountBtn = findViewById(R.id.createAccountBtn);

        createAccountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCreateAccountActivity();
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    public void openCreateAccountActivity() {
        Intent i = new Intent(this, CreateAccountActivity.class);
        startActivity(i);
    }

    public void openHomeActivity() {
        Intent i = new Intent(this, HomeActivity.class);
        startActivity(i);
    }

    private void login() {
        loginBtn.setEnabled(false);
        User user = new User("", email.getText().toString(), password.getText().toString());
        RetrofitApi api = RetrofitClient.getRetrofitInstance().create(RetrofitApi.class);
        Call<LoginResponse> call = api.login(user);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    String name = response.body().getName();
                    String token = response.body().getToken();
                    saveSharedPreferences(name, token);
                    Toast.makeText(MainActivity.this, "Usuário autenticado com sucesso!", Toast.LENGTH_SHORT).show();
                    openHomeActivity();
                } else {
                    Toast.makeText(MainActivity.this, "E-mail ou senha inválidos.", Toast.LENGTH_SHORT).show();
                }
                loginBtn.setEnabled(true);
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Houve um erro ao autenticar o usuário, tente novamente mais tarde", Toast.LENGTH_LONG).show();
                loginBtn.setEnabled(true);
            }
        });
    }

    private void saveSharedPreferences(String name, String token) {
        String PREFS = Constants.PREFS;
        String KEY_NAME = Constants.KEY_NAME;
        String KEY_TOKEN = Constants.KEY_TOKEN;

        SharedPreferences preferences = getSharedPreferences(PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putString(KEY_NAME, name);
        editor.putString(KEY_TOKEN, token);
        editor.commit();
    }
}
