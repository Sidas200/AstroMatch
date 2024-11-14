package com.utch.astromatch;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class UserInfoActivity extends AppCompatActivity {
    private FirebaseFirestore db;
    private static final String TAG = "UserInfoActivity";
    private EditText nameEditText;
    private TextView resultTextView;
    private Button searchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.horoscopo);

        db = FirebaseFirestore.getInstance();

        nameEditText = findViewById(R.id.nameEditText);
        resultTextView = findViewById(R.id.resultTextView);
        searchButton = findViewById(R.id.searchButton);

        searchButton.setOnClickListener(v -> {
            String name = nameEditText.getText().toString().trim();

            if (!name.isEmpty()) {
                fetchUserInfo(name);
            } else {
                Toast.makeText(UserInfoActivity.this, "Ingrese un nombre para buscar", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchUserInfo(String name) {
        db.collection("users")
                .whereEqualTo("nombre", name)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        QuerySnapshot querySnapshot = task.getResult();
                        if (!querySnapshot.isEmpty()) {
                            StringBuilder userInfo = new StringBuilder();
                            for (QueryDocumentSnapshot document : querySnapshot) {
                                String userName = document.getString("nombre");
                                String birthDate = document.getString("fecha_nac");



                                userInfo.append("Nombre: ").append(userName)
                                        .append("\nFecha de nacimiento: ").append(birthDate)
                                        .append("\n\n");
                            }
                            resultTextView.setText(userInfo.toString());
                        } else {
                            resultTextView.setText("No se encontraron resultados para el nombre ingresado.");
                        }
                    } else {
                        Log.w(TAG, "Error en la búsqueda", task.getException());
                        Toast.makeText(UserInfoActivity.this, "Error al buscar datos", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
