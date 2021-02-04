package com.example.pogodex;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class LaunchActivity extends AppCompatActivity {

    //Wait time for loading MainActivity after Splash Screen
    private static final int launchMainIn = 2000;

    Animation toRight, toLeft;
    ImageView gLogo, oLogo;
    TextView goFetchTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        gLogo = findViewById(R.id.firstHalf);
        oLogo = findViewById(R.id.logo_secHalf);
        goFetchTxt = findViewById(R.id.GO_fetch_text);
        toRight = AnimationUtils.loadAnimation(this,R.anim.o_to_right);
        toLeft = AnimationUtils.loadAnimation(this,R.anim.g_to_left);

        gLogo.setAnimation(toLeft);
        oLogo.setAnimation(toRight);

        //Load MainActivity after animation finishes
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(LaunchActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        },launchMainIn);
    }
}