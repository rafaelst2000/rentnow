package com.example.rentnow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
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

public class HomeActivity extends AppCompatActivity implements MotorcycleAdapter.OnMotorcycleClickListener {
    BottomNavigationView bottomNavigationView;
    RecyclerView recyclerView;
    MotorcycleAdapter motorcycleAdapter;
    private static List<Motorcycle> motorcycles = new ArrayList<>();
    private static List<Motorcycle> filteredList = new ArrayList<>();

    EditText searchbar;
    TextView userName, emptyState;
    ImageView logout;

    String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        searchbar = findViewById(R.id.searchbar);
        userName = findViewById(R.id.userName);
        logout = findViewById(R.id.logout);
        emptyState = findViewById(R.id.emptyState);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        motorcycleAdapter = new MotorcycleAdapter(this, motorcycles, this);
        recyclerView.setAdapter(motorcycleAdapter);

        LinearLayout chipContainer = findViewById(R.id.chipContainer);
        List<String> chipLabels = Arrays.asList("Triumph", "Kawasaki", "BMW", "Honda", "Yamaha", "Suzuki");
        createChips(chipContainer, chipLabels);
        getPreferences();
        loadMotorcycles();

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
                        return false;
                    case R.id.menu_item_2:
                        goToMyMotorcycles();
                        return true;
                }
                return false;
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutUser();
            }
        });
    }

    private TextView selectedChip = null;

    private void createChips(LinearLayout chipContainer, List<String> chipTexts) {
        for (int i = 0; i < chipTexts.size(); i++) {
            String text = chipTexts.get(i);
            TextView chip = new TextView(this);
            chip.setText(text);
            chip.setTextSize(16);
            chip.setTypeface(chip.getTypeface(), Typeface.BOLD);
            chip.setPadding(64, 32, 64, 32);
            chip.setGravity(Gravity.CENTER);

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            layoutParams.setMargins(10, 10, 10, 10);
            chip.setLayoutParams(layoutParams);

            chip.setElevation(4);
            chip.setTranslationZ(4);

            if (i == 0) {
                chip.setBackgroundResource(R.drawable.shape_chip);
                chip.setTextColor(Color.WHITE);
                chip.setTag(true);
                selectedChip = chip;
            } else {
                chip.setBackgroundResource(R.drawable.shape_chip_unselected);
                chip.setTextColor(Color.BLACK);
                chip.setTag(false);
            }

            chip.setOnClickListener(v -> toggleChipSelection(chip));

            chipContainer.addView(chip);
        }
    }

    private void toggleChipSelection(TextView chip) {
        if (chip == selectedChip) return;

        if (selectedChip != null && selectedChip != chip) {
            selectedChip.setBackgroundResource(R.drawable.shape_chip_unselected);
            selectedChip.setTextColor(Color.BLACK);
            selectedChip.setTag(false);
        }

        if (chip.getTag() == null || !(boolean) chip.getTag()) {
            chip.setBackgroundResource(R.drawable.shape_chip);
            chip.setTextColor(Color.WHITE);
            chip.setTag(true);
            selectedChip = chip;
        } else {
            chip.setBackgroundResource(R.drawable.shape_chip_unselected);
            chip.setTextColor(Color.BLACK);
            chip.setTag(false);
            selectedChip = null;
        }
        loadMotorcycles();
    }

    @Override
    public void onMotorcycleClick(int position) {
        Motorcycle selectedMotorcycle = filteredList.get(position);
        Intent i = new Intent(HomeActivity.this, MotorcycleDetailsActivity.class);
        i.putExtra("selected_motorcycle", selectedMotorcycle);
        startActivity(i);
    }

    public void goToMyMotorcycles() {
        Intent i = new Intent(HomeActivity.this, MyRentsActivity.class);
        startActivity(i);
    }

    private void loadMotorcycles() {
        searchbar.setText("");
        RetrofitApi api = RetrofitClient.getRetrofitInstance().create(RetrofitApi.class);
        Call<List<Motorcycle>> call = api.getMotorcyclesByBrand(selectedChip.getText().toString());
        call.enqueue(new Callback<List<Motorcycle>>() {
            @Override
            public void onResponse(Call<List<Motorcycle>> call, Response<List<Motorcycle>> response) {
                if (response.isSuccessful()) {
                    motorcycles = response.body();
                    motorcycleAdapter.updateMotorcycles(motorcycles);

                    if(motorcycles.isEmpty()) {
                        emptyState.setText("Nenhuma motocicleta disponível");
                        emptyState.setVisibility(View.VISIBLE);
                    } else {
                        emptyState.setVisibility(View.GONE);
                    }
                } else {
                    Toast.makeText(HomeActivity.this, "Erro ao buscar motocicletas", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Motorcycle>> call, Throwable t) {
                Toast.makeText(HomeActivity.this, "Erro ao buscar motocicletas", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void filterMotorcycles(String text) {
        filteredList = new ArrayList<>();
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
        String KEY_NAME = Constants.KEY_NAME;
        String KEY_TOKEN = Constants.KEY_TOKEN;
        SharedPreferences preferences = getSharedPreferences(PREFS, MODE_PRIVATE);

        String nome = preferences.getString(KEY_NAME, "");
        token = preferences.getString(KEY_TOKEN, "");

        userName.setText("Olá, " + nome);
    }

    private void logoutUser() {
        saveSharedPreferences();
        Intent i = new Intent(HomeActivity.this, MainActivity.class);
        startActivity(i);
    }

    private void saveSharedPreferences() {
        String PREFS = Constants.PREFS;
        String KEY_NAME = Constants.KEY_NAME;
        String KEY_TOKEN = Constants.KEY_TOKEN;

        SharedPreferences preferences = getSharedPreferences(PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putString(KEY_NAME, "");
        editor.putString(KEY_TOKEN, "");
        editor.commit();
    }
}
