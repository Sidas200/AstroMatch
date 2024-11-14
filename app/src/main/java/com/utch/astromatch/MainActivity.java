package com.utch.astromatch;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText etName, etDay, etMonth, etYear;
    private Button btnStart;

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

                // Continuar con la lógica, por ejemplo, ir a la siguiente pantalla
                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                intent.putExtra("name", name);
                intent.putExtra("day", day);
                intent.putExtra("month", month);
                intent.putExtra("year", year);
                startActivity(intent);
            }
        });
    }

    // Método para validar la fecha de nacimiento
    private boolean isValidDate(int day, int month, int year) {
        // Verificar que el mes esté en el rango correcto
        if (month < 1 || month > 12) {
            return false;
        }

        // Verificar que el día esté en el rango correcto según el mes
        if (day < 1 || day > 31) {
            return false;
        }
        if (month == 2) { // Febrero
            if (isLeapYear(year)) {
                return day <= 29;
            } else {
                return day <= 28;
            }
        }
        if (month == 4 || month == 6 || month == 9 || month == 11) { // Meses con 30 días
            return day <= 30;
        }

        return true;
    }

    // Método para verificar si es año bisiesto
    private boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }
}