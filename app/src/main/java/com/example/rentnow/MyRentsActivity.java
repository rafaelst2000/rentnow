package com.example.rentnow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyRentsActivity extends AppCompatActivity implements MotorcycleAdapter.OnMotorcycleClickListener{

    RecyclerView recyclerView;
    MotorcycleAdapter motorcycleAdapter;
    private static List<Motorcycle> motorcycles = new ArrayList<>();

    EditText searchbar;
    TextView emptyState;
    String token;

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_rents);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.menu_item_2);
        Menu menu = bottomNavigationView.getMenu();

        MenuItem menuItem = menu.findItem(R.id.menu_item_1);
        menuItem.setIcon(R.drawable.house_outlined);
        MenuItem menuItem2 = menu.findItem(R.id.menu_item_2);
        menuItem2.setIcon(R.drawable.bag_fill);

        searchbar = findViewById(R.id.searchbar);
        emptyState = findViewById(R.id.emptyState);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        motorcycleAdapter = new MotorcycleAdapter(this, motorcycles, this);
        recyclerView.setAdapter(motorcycleAdapter);

        getPreferences();
        loadMyMotorcycles();

        searchbar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filterMotorcycles(s.toString());
            }
        });

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_item_1:
                        goToHome();
                        return true;
                    case R.id.menu_item_2:
                        return false;
                }
                return false;
            }
        });
    }

    @Override
    public void onMotorcycleClick(int position) {

    }

    private void loadMyMotorcycles() {
        searchbar.setText("");
        RetrofitApi api = RetrofitClient.getRetrofitInstance().create(RetrofitApi.class);
        Call<List<Motorcycle>> call = api.getMyMotorcycles(token);
        call.enqueue(new Callback<List<Motorcycle>>() {
            @Override
            public void onResponse(Call<List<Motorcycle>> call, Response<List<Motorcycle>> response) {
                if (response.isSuccessful()) {
                    motorcycles = response.body();
                    motorcycleAdapter.updateMotorcycles(motorcycles);
                } else {
                    Toast.makeText(MyRentsActivity.this, "Erro ao buscar motocicletas", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Motorcycle>> call, Throwable t) {
                Toast.makeText(MyRentsActivity.this, "Erro ao buscar motocicletas", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void filterMotorcycles(String text) {
        List<Motorcycle> filteredList = new ArrayList<>();
        for (Motorcycle item : motorcycles) {
            if (item.getName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        motorcycleAdapter.updateMotorcycles(filteredList);
        if(filteredList.isEmpty()) {
            emptyState.setText("Nenhum resultado encontrado");
            emptyState.setVisibility(View.VISIBLE);
        } else {
            emptyState.setVisibility(View.GONE);
        }
    }

    private void getPreferences() {
        String PREFS = Constants.PREFS;
        String KEY_TOKEN = Constants.KEY_TOKEN;
        SharedPreferences preferences = getSharedPreferences(PREFS, MODE_PRIVATE);

        token = preferences.getString(KEY_TOKEN, "");
    }

    private void goToHome() {
        Intent i = new Intent(MyRentsActivity.this, HomeActivity.class);
        startActivity(i);
    }
}