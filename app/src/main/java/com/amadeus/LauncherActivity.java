package com.amadeus;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class LauncherActivity extends AppCompatActivity {

    private ImageButton button_connect;
    private EditText urlInput;
    private EditText loginInput;
    private pl.droidsonroids.gif.GifImageView progressBar;
    private TextView errorText;

    public static final String APP_PREFERENCES = "amadeusSettings";
    public static final String APP_PREFERENCES_URL = "inputURL";
    public static final String APP_PREFERENCES_LOGIN = "inputLogin";

    SharedPreferences amadeusSettings;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        amadeusSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        progressBar = findViewById(R.id.progressBarConnect);

        button_connect = findViewById(R.id.button_connect);
        urlInput = findViewById(R.id.urlInput);
        loginInput = findViewById(R.id.loginInput);
        errorText = findViewById(R.id.errorText);

        amadeusSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        if(amadeusSettings.contains(APP_PREFERENCES_URL)) {
            urlInput.setText(amadeusSettings.getString(APP_PREFERENCES_URL, ""));
        }
        if(amadeusSettings.contains(APP_PREFERENCES_LOGIN)) {
            loginInput.setText(amadeusSettings.getString(APP_PREFERENCES_LOGIN, ""));
        }

    }


    public void onConnect(View view) {


        String inputUrl = urlInput.getText().toString().replace("api","");

        Log.i("LAUNCHER",inputUrl);

        if (urlInput.getText().toString().isEmpty()) {
            progressBar.setVisibility(View.GONE);
            errorText.setText("ERROR: server address is not inputted");
            errorText.setVisibility(View.VISIBLE);


        } else {
            errorText.setText("");
            errorText.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
            RequestQueue queue = Volley.newRequestQueue(LauncherActivity.this);
            String url = inputUrl + "/login/";
            Log.i("LAUNCHER",url);
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("My logging", inputUrl + response);

                            String Login = loginInput.getText().toString();

                            SharedPreferences.Editor amadeusEditor = amadeusSettings.edit();
                            amadeusEditor.putString("inputURL", inputUrl);
                            amadeusEditor.putString("inputLogin", Login);
                            amadeusEditor.commit();

                            progressBar.setVisibility(View.GONE);
                            Intent intentMain = new Intent(LauncherActivity.this, MainActivity.class);
                            LauncherActivity.this.startActivity(intentMain);

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("My logging", "Error = " + error);
                    Toast.makeText(LauncherActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                    errorText.setText("ERROR: connection failed");
                    errorText.setVisibility(View.VISIBLE);
                }


            });

            stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                    1200000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            queue.add(stringRequest);
        }
    }
    public void onCancel(View v) {
        Intent intentMain = new Intent(this, MainActivity.class);
        startActivity(intentMain);
    }


}