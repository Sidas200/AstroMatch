package com.utch.astromatch;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private EditText etName, etDay, etMonth, etYear;
    private Button btnStart;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializar los campos de texto y el botón
        etName = findViewById(R.id.et_name);
        etDay = findViewById(R.id.et_day);
        etMonth = findViewById(R.id.et_month);
        etYear = findViewById(R.id.et_year);
        btnStart = findViewById(R.id.btn_start);

        // Configurar el botón para responder al hacer clic
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener los valores de los campos de texto
                String name = etName.getText().toString().trim();
                String dayStr = etDay.getText().toString().trim();
                String monthStr = etMonth.getText().toString().trim();
                String yearStr = etYear.getText().toString().trim();

                // Validar que todos los campos están llenos
                if (name.isEmpty() || dayStr.isEmpty() || monthStr.isEmpty() || yearStr.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Por favor, completa todos los campos.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Convertir día, mes y año a enteros
                int day = Integer.parseInt(dayStr);
                int month = Integer.parseInt(monthStr);
                int year = Integer.parseInt(yearStr);

                // Verificar que los valores de fecha son válidos
                if (!isValidDate(day, month, year)) {
                    Toast.makeText(MainActivity.this, "Fecha no válida. Por favor, revisa los valores.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Guardar los datos en Firebase
                saveUserData(name, day, month, year);
            }
        });
    }

    private void saveUserData(String name, int day, int month, int year) {
        Map<String, Object> userData = new HashMap<>();
        userData.put("name", name);
        userData.put("day", day);
        userData.put("month", month);
        userData.put("year", year);

        db.collection("users")
                .add(userData)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("MainActivity", "Usuario guardado con ID: " + documentReference.getId());
                        Toast.makeText(MainActivity.this, "Datos guardados correctamente", Toast.LENGTH_SHORT).show();

                        // Continuar a la siguiente pantalla
                        //Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                        // intent.putExtra("name", name);
                        //intent.putExtra("day", day);
                        //intent.putExtra("month", month);
                        //intent.putExtra("year", year);
                        //startActivity(intent);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("MainActivity", "Error al guardar los datos", e);
                        Toast.makeText(MainActivity.this, "Error al guardar los datos", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    // Método para validar la fecha de nacimiento
    private boolean isValidDate(int day, int month, int year) {
        if (month < 1 || month > 12) return false;
        if (day < 1 || day > 31) return false;
        if (month == 2) return isLeapYear(year) ? day <= 29 : day <= 28;
        if (month == 4 || month == 6 || month == 9 || month == 11) return day <= 30;
        return true;
    }

    private boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }
}


