package com.example.finalproject;
import com.example.finalproject.Database;
import android.content.Intent;
import android.view.View;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EditUsersDatabase extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_users_database);

        LinearLayout userListLayout = findViewById(R.id.userListLayout);

        Database db = new Database(this);
        Cursor cursor = db.getAllUsers();

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
            String email = cursor.getString(cursor.getColumnIndexOrThrow("email"));

            LinearLayout row = new LinearLayout(this);
            row.setOrientation(LinearLayout.HORIZONTAL);

            TextView userInfo = new TextView(this);
            userInfo.setText("ID: " + id + " | Name: " + name + " | Email: " + email);
            userInfo.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));

            Button editBtn = new Button(this);
            editBtn.setText("Edit");
            editBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   Intent intent = new Intent(EditUsersDatabase.this, EditSingleUser.class);
                   intent.putExtra("USER_ID", id);
                    startActivity(intent);
                }
            });

            Button deleteBtn = new Button(this);
            deleteBtn.setText("Delete");
            deleteBtn.setOnClickListener(v -> {
                Database dbx = new Database(this);
                dbx.deleteUser(id);
                recreate(); // Refresh the activity
            });

            row.addView(userInfo);
            row.addView(editBtn);
            row.addView(deleteBtn);

            userListLayout.addView(row);
        }
        cursor.close();
    }
}