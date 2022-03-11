package com.example.meetingschedhuler;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.widget.TextView;

public class SplashScreen extends AppCompatActivity {

    TextView loadingText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        loadingText = findViewById(R.id.loading_text);
        final String inputText = "Loading";
        Handler handler = new Handler();
        for( Integer i =0; i<6; i++){
            if(i ==3){
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadingText.setText("Loading .");
                    }
                },i *500);
            }
            else {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadingText.setText(loadingText.getText()+" .");
                    }
                },i*500);
            }

        }

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashScreen.this, MainActivity.class));
                finish();
            }
        }, 3000);


    }
}