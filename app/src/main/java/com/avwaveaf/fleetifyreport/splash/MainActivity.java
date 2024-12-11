package com.avwaveaf.fleetifyreport.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.avwaveaf.fleetifyreport.databinding.ActivityMainBinding;
import com.avwaveaf.fleetifyreport.home.HomeActivity;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setupWindowInsets();

        // Delay navigation by 1500ms
        setupDelayAndNavigation();
    }

    private void setupDelayAndNavigation() {
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            // Navigate to HomeActivity
            startActivity(new Intent(MainActivity.this, HomeActivity.class));
            finish();
        }, 1500); // 1500ms delay
    }

    private void setupWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.main, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
