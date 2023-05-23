package com.example.rentnow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.palette.graphics.Palette;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

public class MotorcycleDetailsActivity extends AppCompatActivity {
    ImageButton goBackBtn;
    ImageView image, ellipsis;
    TextView name, rate, displacement, details;
    EditText address;
    MaterialButton rentMotorcycleBtn;
    LinearLayout cardContainer;
    LayoutInflater inflater;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_motorcycle_details);

        Intent i = getIntent();
        Motorcycle motorcycle = (Motorcycle) i.getSerializableExtra("selected_motorcycle");
        int imageResId = getResources().getIdentifier(motorcycle.getImage(), "drawable", getPackageName());

        inflater = LayoutInflater.from(this);

        cardContainer = findViewById(R.id.cardContainer);
        goBackBtn = findViewById(R.id.goBack);
        image = findViewById(R.id.image);
        name = findViewById(R.id.name);
        rate = findViewById(R.id.rate);
        displacement = findViewById(R.id.displacement);
        details = findViewById(R.id.details);
        address = findViewById(R.id.address);
        rentMotorcycleBtn = findViewById(R.id.rentMotorcycleBtn);
        ellipsis = findViewById(R.id.ellipsis_image);

        name.setText(motorcycle.getName());
        rate.setText(motorcycle.getRate().toString());
        displacement.setText(motorcycle.getDisplacement() + " cilindradas");
        details.setText(motorcycle.getDescription());
        address.setText(motorcycle.getLocation());
        image.setImageResource(imageResId);
        rentMotorcycleBtn.setText("Alugar por R$" + motorcycle.getPrice() + "/dia");

        address.setEnabled(false);
        goBackBtn.setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_IN);
        goBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MotorcycleDetailsActivity.this, HomeActivity.class);
                startActivity(i);
            }
        });

        createCards(motorcycle);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), imageResId);
        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            public void onGenerated(Palette p) {
                Palette.Swatch vibrant = p.getDarkVibrantSwatch();
                if(vibrant != null) {
                    int vibrantColor = vibrant.getRgb();
                    ellipsis.setColorFilter(vibrantColor, PorterDuff.Mode.SRC_IN);
                    rentMotorcycleBtn.setBackgroundColor(vibrantColor);
                }
            }
        });
    }

    private void createCards(Motorcycle motorcycle) {
        View cardView1 = inflater.inflate(R.layout.card_item_details, cardContainer, false);
        TextView topText1 = cardView1.findViewById(R.id.top_text);
        topText1.setText("Potência");
        TextView centerText1 = cardView1.findViewById(R.id.center_text);
        centerText1.setText(motorcycle.getCv());
        cardContainer.addView(cardView1);

        View cardView2 = inflater.inflate(R.layout.card_item_details, cardContainer, false);
        TextView topText2 = cardView2.findViewById(R.id.top_text);
        topText2.setText("Tanque");
        TextView centerText2 = cardView2.findViewById(R.id.center_text);
        centerText2.setText(motorcycle.getFuel() + " litros");
        cardContainer.addView(cardView2);

        View cardView3 = inflater.inflate(R.layout.card_item_details, cardContainer, false);
        TextView topText3 = cardView3.findViewById(R.id.top_text);
        topText3.setText("Peso");
        TextView centerText3 = cardView3.findViewById(R.id.center_text);
        centerText3.setText(motorcycle.getWeigth());
        cardContainer.addView(cardView3);

        View cardView4 = inflater.inflate(R.layout.card_item_details, cardContainer, false);
        TextView topText4 = cardView4.findViewById(R.id.top_text);
        topText4.setText("Fabricante");
        TextView centerText4 = cardView4.findViewById(R.id.center_text);
        centerText4.setText(motorcycle.getBrand());
        cardContainer.addView(cardView4);

    }
}