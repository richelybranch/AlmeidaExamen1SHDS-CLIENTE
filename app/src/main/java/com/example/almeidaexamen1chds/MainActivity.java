package com.example.almeidaexamen1chds;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private TextView descriptionTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        descriptionTextView = findViewById(R.id.descriptionTextView);
        Button profileButton = findViewById(R.id.profileButton);

        profileButton.setOnClickListener(v -> fetchProfile());
    }

    private void fetchProfile() {
        new Thread(() -> {
            try {
                URL url = new URL("http://10.10.35.211:3000/almeida");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder result = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
                reader.close();

                runOnUiThread(() -> descriptionTextView.setText(result.toString()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}
