package com.example.rentnow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity implements MotorcycleAdapter.OnMotorcycleClickListener {
    RecyclerView recyclerView;
    MotorcycleAdapter motorcycleAdapter;
    private static List<Motorcycle> motorcycles = new ArrayList<>();

    EditText searchbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        searchbar = findViewById(R.id.searchbar);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        motorcycleAdapter = new MotorcycleAdapter(this, motorcycles, this);
        recyclerView.setAdapter(motorcycleAdapter);

        LinearLayout chipContainer = findViewById(R.id.chipContainer);
        List<String> chipLabels = Arrays.asList("Triumph", "Kawasaki", "BMW", "Honda", "Yamaha", "Suzuki");
        createChips(chipContainer, chipLabels);
        loadMotorcycles();
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

    }

    private void loadMotorcycles() {
        RetrofitApi api = RetrofitClient.getRetrofitInstance().create(RetrofitApi.class);
        Call<List<Motorcycle>> call = api.getMotorcyclesByBrand(selectedChip.getText().toString());
        call.enqueue(new Callback<List<Motorcycle>>() {
            @Override
            public void onResponse(Call<List<Motorcycle>> call, Response<List<Motorcycle>> response) {
                if (response.isSuccessful()) {
                    motorcycles = response.body();
                    motorcycleAdapter.updateMotorcycles(motorcycles);
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

           /* BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.menu_item_1:
                        // Ação para o item 1
                        return true;
                    case R.id.menu_item_2:
                        // Ação para o item 2
                        return true;
                    default:
                        return false;
                }
            }
        }); */
}
