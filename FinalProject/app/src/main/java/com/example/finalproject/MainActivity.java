package com.example.finalproject;

import android.os.Bundle;
import android.content.Intent;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.finalproject.Database;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Initialize the database helper
        Database db = new Database(this);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        EditText username = findViewById(R.id.username);
        EditText password = findViewById(R.id.password);
        EditText email = findViewById(R.id.email);
        EditText confirmPassword = findViewById(R.id.confirmPassword);
        Button loginBtn = findViewById(R.id.loginButton);
        Button signupBtn = findViewById(R.id.signupButton);
        Button editPageButton = findViewById(R.id.editPageButton);

        loginBtn.setOnClickListener(v -> {
            String user = username.getText().toString();
            String pass = password.getText().toString();
            Toast.makeText(this, "Logging in: " + user, Toast.LENGTH_SHORT).show();
            // Add actual login logic here
        });

        signupBtn.setOnClickListener(v -> {
            String user = username.getText().toString();
            String pass = password.getText().toString();
            String confirmPass = confirmPassword.getText().toString();
            String userEmail = email.getText().toString();

            if (!pass.equals(confirmPass)) {
                Toast.makeText(this, "Passwords do not match!", Toast.LENGTH_SHORT).show();
            } else {
                boolean inserted = db.insertUser(user, userEmail);
                if(inserted){
                    Toast.makeText(this, "User registered successfully!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Registration failed. Please try again.", Toast.LENGTH_SHORT).show();
                }
                // Here you could add further actions, e.g., transition to another activity
            }
        });

        editPageButton.setOnClickListener(v -> {
            String user = username.getText().toString();
            String pass = password.getText().toString();

            if (user.equals("admin") && pass.equals("admin123")) {
                Intent intent = new Intent(MainActivity.this, EditUsersDatabase.class);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Access Denied: Admin credentials required", Toast.LENGTH_SHORT).show();
            }
        });
    }
}