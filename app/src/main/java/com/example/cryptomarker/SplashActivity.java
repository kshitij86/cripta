package com.example.cryptomarker;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class SplashActivity extends AppCompatActivity {
    String[] facts = {"As of January 2018, Bitcoin amounted 34% of the total cryptocurrency market value",
            "107 out of 251 countries do not have any regulation regarding the purchase or use of Bitcoins",
            "As the creator of Bitcoin is still unknown, some people even say that the coin was created by four companies – Samsung, Toshiba, Nakamichi, Motorola – in collaboration",
            "Unlike paper money, Bitcoins are totally virtual, which means you cannot touch or feel these coins but can still use them for payments",
            "There are more than 1,300 cryptocurrencies (and growing)",
            "Blockchain technology is being tested by a number of brand-name businesses"};

    private TextView fact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Cover the full screen.
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        // Show a fact while user is waiting.
        Random random = new Random();
        fact = findViewById(R.id.facts);
        fact.setText(facts[random.nextInt(facts.length - 1)]);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 2000);

    }
}
