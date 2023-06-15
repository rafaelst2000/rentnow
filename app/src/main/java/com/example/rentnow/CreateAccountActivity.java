package com.example.rentnow;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

public class CreateAccountActivity extends AppCompatActivity {

    EditText name;
    EditText email;
    EditText password;
    Button registryBtn;
    TextView loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        registryBtn = findViewById(R.id.registryBtn);
        loginBtn = findViewById(R.id.loginBtn);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCreateLoginActivity();
            }
        });

        registryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAccount();
            }
        });
    }

    public void openCreateLoginActivity() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    private void createAccount() {
        registryBtn.setEnabled(false);
        registryBtn.setText("Criando sua conta...");
        User user = new User(name.getText().toString(), email.getText().toString(), password.getText().toString());
        RetrofitApi api = RetrofitClient.getRetrofitInstance().create(RetrofitApi.class);
        Call<User> call = api.createAccount(user);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                registryBtn.setText("Cadastrar");
                if (response.isSuccessful()) {
                    Toast.makeText(CreateAccountActivity.this, "Usuário criado com sucesso!", Toast.LENGTH_SHORT).show();
                    openCreateLoginActivity();
                } else {
                    Toast.makeText(CreateAccountActivity.this, "E-mail já está em uso.", Toast.LENGTH_SHORT).show();
                }
                registryBtn.setEnabled(true);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                registryBtn.setText("Cadastrar");
                Toast.makeText(CreateAccountActivity.this, "Houve um erro ao criar usuário, tente novamente mais tarde", Toast.LENGTH_LONG).show();
                registryBtn.setEnabled(true);
            }
        });
    }
}