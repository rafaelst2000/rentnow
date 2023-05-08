package com.example.rentnow;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.app_bar_background)));
        }

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