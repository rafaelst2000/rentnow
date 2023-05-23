package com.example.rentnow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.palette.graphics.Palette;

import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MotorcycleDetailsActivity extends AppCompatActivity {
    ImageButton goBackBtn;
    ImageView image, ellipsis;
    TextView name, rate, displacement, details;
    EditText address;
    MaterialButton rentMotorcycleBtn;
    LinearLayout cardContainer;
    LayoutInflater inflater;

    String token;
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
        getPreferences();

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

        rentMotorcycleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rentMotorcycle(motorcycle);
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

    private void getPreferences() {
        String PREFS = Constants.PREFS;
        String KEY_TOKEN = Constants.KEY_TOKEN;
        SharedPreferences preferences = getSharedPreferences(PREFS, MODE_PRIVATE);

        token = preferences.getString(KEY_TOKEN, "");
    }

    private void rentMotorcycle(Motorcycle motorcycle) {
        RetrofitApi api = RetrofitClient.getRetrofitInstance().create(RetrofitApi.class);
        Call<Void> call = api.rentMotorcycle(motorcycle.getBrand(), motorcycle.getId(), token);
        rentMotorcycleBtn.setEnabled(false);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(MotorcycleDetailsActivity.this, motorcycle.getName()+" reservada com sucesso!", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(MotorcycleDetailsActivity.this, MyRentsActivity.class);
                    startActivity(i);
                }
                rentMotorcycleBtn.setEnabled(true);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(MotorcycleDetailsActivity.this, "Houve um erro ao reservar a motocicleta, tente novamente mais tarde", Toast.LENGTH_LONG).show();
                rentMotorcycleBtn.setEnabled(true);
            }
        });
    }
}