package com.utch.astromatch;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class HomeActivity extends AppCompatActivity {

    private TextView greetingTextView;
    private ImageView zodiacLogoImageView, zodiacNameImageView;
    private Button btnCompatibility;
    private DatabaseReference databaseReference;
    private String zodiacSign; // Almacenar signo zodiacal

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Obtener el userId pasado desde MainActivity
        userId = getIntent().getStringExtra("userId");

        // Inicializar vistas
        greetingTextView = findViewById(R.id.greetingTextView);
        zodiacLogoImageView = findViewById(R.id.zodiacLogoImageView);
        zodiacNameImageView = findViewById(R.id.zodiacNameImageView);
        btnCompatibility = findViewById(R.id.btn_compatibility);

        // Cargar datos del usuario desde Firebase
        loadUserData();
    }

    private void loadUserData() {
        db.collection("users").document(userId).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            String name = documentSnapshot.getString("name");
                            String signo = documentSnapshot.getString("signo");

                            // Configurar saludo con el nombre
                            greetingTextView.setText("Hola, " + name + "!");

        // Configurar botón de compatibilidad para abrir CompatibilitiesActivity
        btnCompatibility.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, CompatibilitiesActivity.class);
            intent.putExtra("zodiacSign", zodiacSign); // Pasar signo zodiacal
            startActivity(intent);
        });
                            // Configurar el logo y el nombre del signo zodiacal
                            int logoResId = getZodiacLogoResource(signo);
                            int nameResId = getZodiacNameResource(signo);
                            zodiacLogoImageView.setImageResource(logoResId);
                            zodiacNameImageView.setImageResource(nameResId);
                        } else {
                            Toast.makeText(HomeActivity.this, "No se encontraron datos del usuario", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("HomeActivity", "Error al cargar datos del usuario", e);
                        Toast.makeText(HomeActivity.this, "Error al cargar datos", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    // Método para obtener el recurso de imagen del logo basado en el signo zodiacal
    private int getZodiacLogoResource(String signo) {
        switch (signo) {
            case "Aries": return R.drawable.arieslogo;
            case "Tauro": return R.drawable.tauro;
            case "Géminis": return R.drawable.geminislogo;
            case "Cáncer": return R.drawable.cancer;
            case "Leo": return R.drawable.leologo;
            case "Virgo": return R.drawable.virgologo;
            case "Libra": return R.drawable.libralogo;
            case "Escorpio": return R.drawable.escorpiologo;
            case "Sagitario": return R.drawable.sagitariologo;
            case "Capricornio": return R.drawable.capricornio;
            case "Acuario": return R.drawable.acuario;
            case "Piscis": return R.drawable.piscislogo;
            default: return R.drawable.logo;
        }
    }

    // Método para obtener el recurso de imagen del nombre basado en el signo zodiacal
    private int getZodiacNameResource(String signo) {
        switch (signo) {
            case "Aries": return R.drawable.aries_letra;
            case "Tauro": return R.drawable.tauroletra;
            case "Géminis": return R.drawable.geminisletra;
            case "Cáncer": return R.drawable.cancerletra;
            case "Leo": return R.drawable.leoletra;
            case "Virgo": return R.drawable.virgoletra;
            case "Libra": return R.drawable.libraletra;
            case "Escorpio": return R.drawable.escorpioletra;
            case "Sagitario": return R.drawable.sagitarioletra;
            case "Capricornio": return R.drawable.capricornioletra;
            case "Acuario": return R.drawable.acuarioletra;
            case "Piscis": return R.drawable.pisciletra;
            default: return R.drawable.logo;
        }
    }
}


