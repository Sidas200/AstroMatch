package com.utch.astromatch;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class CompatibilitiesActivity extends AppCompatActivity {

    private TextView titleTextView;
    private Button btnVeryCompatible, btnCompatible, btnNeutral, btnIncompatible;
    private TextView tvVeryCompatible, tvCompatible, tvNeutral, tvIncompatible;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compatibilities);

        // Inicializar vistas
        titleTextView = findViewById(R.id.titleTextView);
        btnVeryCompatible = findViewById(R.id.btnVeryCompatible);
        btnCompatible = findViewById(R.id.btnCompatible);
        btnNeutral = findViewById(R.id.btnNeutral);
        btnIncompatible = findViewById(R.id.btnIncompatible);

        tvVeryCompatible = findViewById(R.id.tvVeryCompatible);
        tvCompatible = findViewById(R.id.tvCompatible);
        tvNeutral = findViewById(R.id.tvNeutral);
        tvIncompatible = findViewById(R.id.tvIncompatible);

        // Configurar título dinámico
        String zodiacSign = getIntent().getStringExtra("zodiacSign");
        if (zodiacSign != null) {
            titleTextView.setText("Compatibilidades de " + zodiacSign);
        }

        // Configurar listeners para los botones
        btnVeryCompatible.setOnClickListener(v -> toggleVisibility(tvVeryCompatible));
        btnCompatible.setOnClickListener(v -> toggleVisibility(tvCompatible));
        btnNeutral.setOnClickListener(v -> toggleVisibility(tvNeutral));
        btnIncompatible.setOnClickListener(v -> toggleVisibility(tvIncompatible));
    }

    // Método para alternar la visibilidad de un TextView
    private void toggleVisibility(TextView textView) {
        if (textView != null) {
            if (textView.getVisibility() == View.GONE) {
                textView.setVisibility(View.VISIBLE); // Mostrar
            } else {
                textView.setVisibility(View.GONE); // Ocultar
            }
        }
    }
}
