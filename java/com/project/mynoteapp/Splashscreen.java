package com.project.mynoteapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.content.Intent;
import android.os.Handler;

public class Splashscreen extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle states) {
        super.onCreate(states);
        setContentView(R.layout.splashscreen);
        int screentime = 2000;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
                    Boolean isUserRegistered = prefs.getBoolean("userRegistered", false); //False is a default value
                    if (isUserRegistered) {
                        startActivity(new Intent(Splashscreen.this,Login.class));
                    }
                    else{
                        startActivity(new Intent(Splashscreen.this,SignUp.class));
                    }
                }
            }, screentime);

    }
}
