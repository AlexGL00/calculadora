package com.AlexGL00.calculadora;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        launchMain();

        Animation gradientColor = AnimationUtils.loadAnimation(this, R.anim.graddient_color);
        ImageView mainActivityLogo = findViewById(R.id.logoInicio);
        TextView mainActivityName = findViewById(R.id.InicioAppName);
        mainActivityLogo.startAnimation(gradientColor);
        mainActivityName.startAnimation(gradientColor);

        ImageView lideBackground = findViewById(R.id.InicioBlackground);
        if (lideBackground != null) {
            Glide.with(this)
                    .load("https://media.istockphoto.com/id/165795927/es/vector/ciencia-y-garabatos.jpg?s=612x612&w=0&k=20&c=M5HKWgcMvnMnxrUaGrg58snAx6jv8qzo5bKec5HfEYE=")
                    .transition(DrawableTransitionOptions.withCrossFade(1000))
                    .centerCrop()
                    .into(lideBackground);
        }
    }

    public void launchMain() {
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            Intent intent = new Intent(MainActivity.this, SplashScreen.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }, 3000);
    }
}
