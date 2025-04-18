package net.devcircuit.lafiyacare;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SplashActivity extends AppCompatActivity {

    private static int SPLASH_TIMEOUT = 3000; //3 secs
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_splash);

       new Handler().postDelayed(()->{
           Intent i = new Intent(SplashActivity.this,MainActivity.class);
            startActivity(i);
            finish();
       },SPLASH_TIMEOUT);
    }
}