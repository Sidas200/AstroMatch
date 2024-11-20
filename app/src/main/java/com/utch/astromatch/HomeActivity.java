package com.utch.astromatch;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeActivity extends AppCompatActivity {

    private TextView greetingTextView;
    private ImageView zodiacLogoImageView, zodiacNameImageView;
    private Button btnCompatibility;
    private DatabaseReference databaseReference;
    private String zodiacSign; // Almacenar signo zodiacal

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Inicializar vistas
        greetingTextView = findViewById(R.id.greetingTextView);
        zodiacLogoImageView = findViewById(R.id.zodiacLogoImageView);
        zodiacNameImageView = findViewById(R.id.zodiacNameImageView);
        btnCompatibility = findViewById(R.id.btn_compatibility);

        // Obtener el userId pasado desde MainActivity
        String userId = getIntent().getStringExtra("userId");

        // Inicializar referencia a Firebase
        databaseReference = FirebaseDatabase.getInstance().getReference("users").child(userId);

        // Obtener datos desde Firebase
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String name = dataSnapshot.child("name").getValue(String.class);
                    zodiacSign = dataSnapshot.child("signo").getValue(String.class);

                    // Configurar saludo con el nombre
                    greetingTextView.setText("Hola, " + name + "!");

                    // Configurar el logo y el nombre del signo zodiacal
                    int logoResId = getZodiacLogoResource(zodiacSign);
                    int nameResId = getZodiacNameResource(zodiacSign);
                    zodiacLogoImageView.setImageResource(logoResId);
                    zodiacNameImageView.setImageResource(nameResId);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Manejar errores aquí
            }
        });

        // Configurar botón de compatibilidad para abrir CompatibilitiesActivity
        btnCompatibility.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, CompatibilitiesActivity.class);
            intent.putExtra("zodiacSign", zodiacSign); // Pasar signo zodiacal
            startActivity(intent);
        });
    }

    // Método para obtener el recurso de imagen del logo basado en el signo zodiacal
    private int getZodiacLogoResource(String zodiacSign) {
        switch (zodiacSign) {
            case "Aries":
                return R.drawable.arieslogo;
            case "Tauro":
                return R.drawable.tauro;
            case "Géminis":
                return R.drawable.geminislogo;
            case "Cáncer":
                return R.drawable.cancer;
            case "Leo":
                return R.drawable.leologo;
            case "Virgo":
                return R.drawable.virgologo;
            case "Libra":
                return R.drawable.libralogo;
            case "Escorpio":
                return R.drawable.escorpiologo;
            case "Sagitario":
                return R.drawable.sagitariologo;
            case "Capricornio":
                return R.drawable.capricornio;
            case "Acuario":
                return R.drawable.acuario;
            case "Piscis":
                return R.drawable.piscislogo;
            default:
                return R.drawable.arieslogo; // Imagen por defecto
        }
    }

    // Método para obtener el recurso de imagen del nombre basado en el signo zodiacal
    private int getZodiacNameResource(String zodiacSign) {
        switch (zodiacSign) {
            case "Aries":
                return R.drawable.aries_letra;
            case "Tauro":
                return R.drawable.tauroletra;
            case "Géminis":
                return R.drawable.geminisletra;
            case "Cáncer":
                return R.drawable.cancerletra;
            case "Leo":
                return R.drawable.leoletra;
            case "Virgo":
                return R.drawable.virgoletra;
            case "Libra":
                return R.drawable.libralogo;
            case "Escorpio":
                return R.drawable.escorpioletra;
            case "Sagitario":
                return R.drawable.sagitarioletra;
            case "Capricornio":
                return R.drawable.capricornioletra;
            case "Acuario":
                return R.drawable.acuarioletra;
            case "Piscis":
                return R.drawable.pisciletra;
            default:
                return R.drawable.aries_letra; // Imagen por defecto
        }
    }
}
