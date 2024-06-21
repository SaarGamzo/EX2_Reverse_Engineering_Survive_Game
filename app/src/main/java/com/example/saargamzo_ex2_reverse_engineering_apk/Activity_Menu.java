package com.example.saargamzo_ex2_reverse_engineering_apk;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;

public class Activity_Menu extends AppCompatActivity {
    private MaterialButton menu_BTN_start;
    private TextInputEditText menu_EDT_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("Debug", "Entering onCreate method");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Log.d("Debug", "Before findViews call");
        findViews();
        Log.d("Debug", "After findViews call, before initViews call");
        initViews();
        Log.d("Debug", "Exiting onCreate method");
    }

    private void initViews() {
        Log.d("Debug", "Entering initViews method");
        menu_BTN_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Debug", "menu_BTN_start clicked");
                Activity_Menu.this.makeServerCall();
            }
        });
        Log.d("Debug", "Exiting initViews method");
    }

    private void findViews() {
        Log.d("Debug", "Entering findViews method");
        menu_BTN_start = (MaterialButton) findViewById(R.id.menu_BTN_start);
        menu_EDT_id = (TextInputEditText) findViewById(R.id.menu_EDT_id);
        Log.d("Debug", "menu_BTN_start: " + menu_BTN_start);
        Log.d("Debug", "menu_EDT_id: " + menu_EDT_id);
        Log.d("Debug", "Exiting findViews method");
    }

    private void makeServerCall() {
        Log.d("Debug", "Entering makeServerCall method");
        new Thread() {
            public void run() {
                Log.d("Debug", "Thread run started");
                Log.d("Debug", "Value of string url is: " + getString(R.string.url));
                String data = Activity_Menu.getJSON(getString(R.string.url));
                Log.d("Debug", "Data is: " + data);
                if (data != null) {
                    Log.d("Debug", "Data is not null");
                    String id = menu_EDT_id.getText().toString();
                    Log.d("Debug", "ID value is: " + id);
                    startGame(id, data);
                }
                Log.d("Debug", "Thread run finished");
            }
        }.start();
        Log.d("Debug", "Exiting makeServerCall method");
    }

    private void startGame(String id, String data) {
        Log.d("Debug", "Entering startGame method");
        Log.d("Debug", "ID: " + id);
        Log.d("Debug", "Data: " + data);
        String state = data.split(",")[Integer.valueOf(String.valueOf(id.charAt(7)))];
        Log.d("Debug", "State: " + state);
        Log.d("Info", "EXTRA_ID = " + id + ", EXTRA_STATE = " + state);
        Intent intent = new Intent(getBaseContext(), Activity_Game.class);
        intent.putExtra(Activity_Game.EXTRA_ID, id);
        intent.putExtra(Activity_Game.EXTRA_STATE, state);
        startActivity(intent);
        Log.d("Debug", "Exiting startGame method");
    }

    public static String getJSON(String url) {
        Log.d("Debug", "Entering getJSON method");
        Log.d("Debug", "URL: " + url);
        String data = "";
        HttpsURLConnection con = null;
        try {
            HttpsURLConnection con2 = (HttpsURLConnection) new URL(url).openConnection();
            Log.d("Debug", "Connection established");
            con2.connect();
            Log.d("Debug", "Connection connected");
            BufferedReader br = new BufferedReader(new InputStreamReader(con2.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
            br.close();
            data = sb.toString();
            Log.d("Debug", "Data received: " + data);
            if (con2 != null) {
                try {
                    con2.disconnect();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
            Log.d("Debug", "MalformedURLException: " + ex.getMessage());
            if (con != null) {
                con.disconnect();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            Log.d("Debug", "IOException: " + ex.getMessage());
            if (con != null) {
                con.disconnect();
            }
        } catch (Throwable th) {
            Log.d("Debug", "Throwable: " + th.getMessage());
            if (con != null) {
                try {
                    con.disconnect();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            throw th;
        }
        Log.d("Debug", "Exiting getJSON method");
        return data;
    }
}
