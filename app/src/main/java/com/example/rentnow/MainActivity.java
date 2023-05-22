package com.example.rentnow;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

    private void login() {
        loginBtn.setEnabled(false);
        User user = new User("", email.getText().toString(), password.getText().toString());
        RetrofitApi api = RetrofitClient.getRetrofitInstance().create(RetrofitApi.class);
        Call<User> call = api.login(user);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "Usuário autenticado com sucesso!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "E-mail ou senha inválidos.", Toast.LENGTH_SHORT).show();
                }
                loginBtn.setEnabled(true);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Houve um erro ao autenticar o usuário, tente novamente mais tarde", Toast.LENGTH_LONG).show();
                loginBtn.setEnabled(true);
            }
        });
    }
}

       /* LinearLayout chipContainer = findViewById(R.id.chipContainer);
        List<String> chipLabels = Arrays.asList("Triumph", "Kawasaki", "BMW", "Honda", "Yamaha");
        createChips(chipContainer, chipLabels);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
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
        });
    }

    private void createChips(LinearLayout chipContainer, List<String> chipTexts) {
        for (String text : chipTexts) {
            TextView chip = new TextView(this);
            chip.setText(text);
            chip.setTextSize(16);
            chip.setTypeface(chip.getTypeface(), Typeface.BOLD);
            chip.setTextColor(Color.BLACK);
            chip.setBackgroundResource(R.drawable.shape_chip_unselected);
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

            chip.setOnClickListener(v -> toggleChipSelection(chip));

            chipContainer.addView(chip);
        }
    }

    private void toggleChipSelection(TextView chip) {
        if (chip.getTag() == null || !(boolean) chip.getTag()) {
            chip.setBackgroundResource(R.drawable.shape_chip);
            chip.setTextColor(Color.WHITE);
            chip.setTag(true);
        } else {
            chip.setBackgroundResource(R.drawable.shape_chip_unselected);
            chip.setTextColor(Color.BLACK);
            chip.setTag(false);
        }
    }
}*/