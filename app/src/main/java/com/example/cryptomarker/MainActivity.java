package com.example.cryptomarker;

// Migrating from OkHttp to Volley.
// Going to try and handle all API calls using their objects and methods, clean up the code here. (NA)
// Add RecyclerView to the MainActivity (DONE).
// Switching back to OkHttp. Volley does not work without passing application context.

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.Objects;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity {

    final int limit = 4;
    final String url = "https://api.coinranking.com/v1/public/coins?limit=" + limit;
    String[] prices = new String[limit], symbols = new String[limit];
    String[] imageUrls = new String[]{"https://pngimg.com/uploads/bitcoin/bitcoin_PNG48.png",
            "https://cdn.freebiesupply.com/logos/thumbs/2x/ethereum-1-logo.png",
            "https://cdn.freebiesupply.com/logos/large/2x/ripple-2-logo-png-transparent.png",
            "https://cryptologos.cc/logos/tether-usdt-logo.png"};
    boolean[] changes = new boolean[limit];

    OkHttpClient client = new OkHttpClient();

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the default night mode.
        AppCompatDelegate.setDefaultNightMode(
                (AppCompatDelegate.MODE_NIGHT_YES));
        setContentView(R.layout.activity_main);

        // Lock orientation of this activity to PORTRAIT.
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        // Toolbar with custom properties.
        final Toolbar toolbar = this.findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        final ImageButton imageButton = this.findViewById(R.id.ref_btn);
        final RecyclerView recyclerView = this.findViewById(R.id.crypto_list);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        imageButton.setOnClickListener(v -> {
            // TODO Show and refresh prices on button click.
            try{
                Thread.sleep(1000);
                // Make the API call.
                final Request request = new Request.Builder().url(url).build();
                new PrepData().execute(request);
                recyclerView.setAdapter(new RecyclerAdapter(prices, imageUrls, symbols, changes));
            } catch (Exception e){
                Toast toast = Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, Gravity.CENTER_HORIZONTAL, 40);
                toast.show();
            }

            Toast toast = Toast.makeText(MainActivity.this, "Updated", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, Gravity.CENTER_HORIZONTAL, 400);
            toast.show();
        });
    }

    // Asynchronous task runs in background to make API call and sets the data resources to be set as RecyclerView adapter.
    @SuppressLint("StaticFieldLeak")
    class PrepData extends AsyncTask<Request, Void, Response> {

        @Override
        protected Response doInBackground(Request... requests) {
            Response response = null;
            try {
                response = client.newCall(requests[0]).execute();
            } catch (IOException e) {
                Toast toast = Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, Gravity.CENTER_HORIZONTAL, 40);
                toast.show();
            }
            return response;
        }

        @Override
        protected void onPostExecute(Response response) {
            super.onPostExecute(response);
            try {
                // Static image sources.
                // Parse JSON here, and images are put into string.
                String jData = Objects.requireNonNull(response.body()).string();
                JSONArray coins = new JSONObject(jData)
                        .getJSONObject("data")
                        .getJSONArray("coins");
                for (int i = 0; i < coins.length(); i++) {
                    prices[i] = "$ " + coins.getJSONObject(i).getString("price").substring(0, 8);
                    symbols[i] = coins.getJSONObject(i).getString("symbol");
                    if(coins.getJSONObject(i).getInt("change") > 0)
                        changes[i] = true;
                    else changes[i] = false;
                }
                // Toast.makeText(MainActivity.this, imageUrls[0], Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                Toast toast = Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, Gravity.CENTER_HORIZONTAL, 40);
                toast.show();

            }
        }
    }

    public void buttonBounce(View view){

    }
}
