package com.amadeus;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;


public class MainActivity extends Activity implements PopupMenu.OnMenuItemClickListener {

    private EditText textInput;
    private ImageButton buttonSend;
    private EditText textResponse;
    private ImageView charMain;
    private TextView errorTextMain;
    private pl.droidsonroids.gif.GifImageView progressBar;
    private pl.droidsonroids.gif.GifImageView glitch;

    public static final String APP_PREFERENCES = "amadeusSettings";
    public static final String APP_PREFERENCES_BUG_LOG = "bugLog";
    public static final String APP_PREFERENCES_CURRENT_CHAT_HISTORY = "currentChatHistory";


    public static final String APP_PREFERENCES_LAST_RESPONSE = "lastResponse";
    public static final String APP_PREFERENCES_LAST_EMOTION = "lastEmotion";


    public static final String APP_PREFERENCES_REGENERATION = "regeneration";
    public static final String APP_PREFERENCES_END_DIALOG = "endDialog";


    public static final String APP_PREFERENCES_DIALOG_ID = "dialogId";
    public static final String APP_PREFERENCES_CUSTOM_CHAR = "customChar";
    static SharedPreferences amadeusSettings;


    @SuppressLint("MissingInflatedId")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        amadeusSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor amadeusEditor = amadeusSettings.edit();
        String lastEmotion = amadeusSettings.getString(APP_PREFERENCES_LAST_EMOTION, "neutral");
        String lastMessage = amadeusSettings.getString(APP_PREFERENCES_LAST_RESPONSE, "");


        errorTextMain = findViewById(R.id.errorTextMain);
        textInput = findViewById(R.id.textInput);
        textResponse = findViewById(R.id.textResponse);
        charMain = findViewById(R.id.charMain);
        buttonSend = findViewById(R.id.buttonSend);
        glitch = findViewById(R.id.glitch);
        progressBar = findViewById(R.id.progressBarConnect);


        String emotionName = Recognizer.getEmotionName(MainActivity.this, lastEmotion);
        int emotionResId = getResources().getIdentifier(emotionName, "drawable", getPackageName());
        charMain.setImageResource(emotionResId);

        textResponse.setText(lastMessage);

        amadeusEditor.putBoolean("regeneration", false).commit();


    }


    public void onSendClick(View v) {
        errorTextMain.setVisibility(View.INVISIBLE);

        SharedPreferences.Editor amadeusEditor = amadeusSettings.edit();

        Boolean regeneration = amadeusSettings.getBoolean(APP_PREFERENCES_REGENERATION, false);

        String bugLog = amadeusSettings.getString(APP_PREFERENCES_BUG_LOG, "");


        String lastResponse = textResponse.getText().toString();
        amadeusEditor.putString("lastResponse", lastResponse).commit();

        if (textInput.getText().toString().isEmpty() & regeneration == false) {
            Toast.makeText(this, "ERROR: empty input", Toast.LENGTH_SHORT).show();
        } else {
            progressBar.setVisibility(View.VISIBLE);
            String message = textInput.getText().toString();

            Log.i("MAIN", "REGENERATION: " + regeneration);
            amadeusEditor.putString("bugLog",bugLog + "\n" + "[MAIN]" + "REGENERATION: " + regeneration);
            Log.i("MAIN", "MESSAGE: " + message);
            amadeusEditor.putString("bugLog",bugLog + "\n" + "[MAIN]" + "MESSAGE: " + message);


            amadeusEditor.putBoolean("waiting", true).commit();

            Messenger.onSend(this, message, new VolleyCallback() {
                @Override
                public void onSuccess(String responseText, String responseEmotion) {
                    Log.i("MAIN", "TEXT:" + responseText + "   EMOTION:" + responseEmotion);

                    String emotionName = Recognizer.getEmotionName(MainActivity.this, responseEmotion);
                    int emotionResId = getResources().getIdentifier(emotionName, "drawable", getPackageName());
                    charMain.setImageResource(emotionResId);

                    textInput.setText("");
                    textResponse.setText(responseText);

                    amadeusEditor.putString("lastResponse", responseText).commit();
                    amadeusEditor.putString("lastEmotion", responseEmotion).commit();

                    progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onError(String responseText) {

                    amadeusEditor.putString("bugLog",bugLog + "\n" + "[VOLLEY]");

                    progressBar.setVisibility(View.GONE);
                    errorTextMain.setText("ERROR: can't get response");
                    errorTextMain.setVisibility(View.VISIBLE);
                    Boolean customChar = amadeusSettings.getBoolean(APP_PREFERENCES_CUSTOM_CHAR, false);
                    if (customChar == false) {
                        glitch.setVisibility(View.VISIBLE);
                        View view = v;
                        view.postDelayed(new Runnable() {
                            public void run() {
                                glitch.setVisibility(View.GONE);
                            }
                        }, 500);
                    }
                }

            });
        }
    }





    public void onCharacterClick(View view){
        PopupMenu character_popup_menu = new PopupMenu(MainActivity.this, view);
        character_popup_menu.setOnMenuItemClickListener(MainActivity.this);
        character_popup_menu.inflate(R.menu.charpopup);
        character_popup_menu.show();
    }
    public void onOtherClick(View view){

        PopupMenu popup_menu = new PopupMenu(MainActivity.this, view);
        popup_menu.setOnMenuItemClickListener(MainActivity.this);
        popup_menu.inflate(R.menu.popup);
        popup_menu.show();
    }

    public boolean onMenuItemClick(MenuItem item) {

        SharedPreferences.Editor amadeusEditor = amadeusSettings.edit();
        String lastResponse = amadeusSettings.getString(APP_PREFERENCES_LAST_RESPONSE,"");
        String lastEmotion = amadeusSettings.getString(APP_PREFERENCES_LAST_EMOTION,getString(R.string.default_emotion));
        String bugLog = amadeusSettings.getString(APP_PREFERENCES_BUG_LOG, "");

        switch (item.getItemId()) {


            case R.id.chats_history:
                Intent intentMain = new Intent(this, ChatsHistoryActivity.class);
                startActivity(intentMain);
                return true;



            case R.id.settings:
                errorTextMain.setVisibility(View.INVISIBLE);
                Intent intentSettings = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intentSettings);
                return true;


            case R.id.start_new:
                int dialogId = amadeusSettings.getInt(APP_PREFERENCES_DIALOG_ID, 0);
                errorTextMain.setVisibility(View.INVISIBLE);
                progressBar.setVisibility(View.VISIBLE);

                textInput.setText("");
                textResponse.setText("");

                amadeusEditor.remove("lastResponse").commit();
                amadeusEditor.remove("lastEmotion").commit();

                amadeusEditor.putBoolean("regeneration", false).commit();

                buttonSend.setImageResource(R.drawable.button_send);
                buttonSend.setVisibility(View.VISIBLE);

                Log.d("MAIN","REGENERATION CHANGED ON: " + "FALSE");
                amadeusEditor.putString("bugLog",bugLog + "\n" + "[MAIN]" + "REGENERATION CHANGED ON: " + "FALSE");

                amadeusEditor.putInt("dialogId", dialogId + 1 ).commit();

                progressBar.setVisibility(View.GONE);
                Toast.makeText(this, "New chat started", Toast.LENGTH_SHORT).show();
                return true;


            case R.id.remove_message:
                errorTextMain.setVisibility(View.INVISIBLE);



                return true;



            case R.id.regenerate:
                errorTextMain.setVisibility(View.INVISIBLE);

                Boolean regeneration = amadeusSettings.getBoolean(APP_PREFERENCES_REGENERATION, false);
                if(regeneration == true){
                    amadeusEditor.putBoolean("regeneration", false);
                    amadeusEditor.commit();
                    buttonSend.setImageResource(R.drawable.button_send);
                    Log.d("MAIN","REGENERATION CHANGED ON: " + "FALSE");
                    amadeusEditor.putString("bugLog",bugLog + "\n" + "[MAIN]" + "REGENERATION CHANGED ON: " + "FALSE");
                    Toast.makeText(this, "Regeneration mode disabled", Toast.LENGTH_SHORT).show();
                } else {
                    amadeusEditor.putBoolean("regeneration", true);
                    amadeusEditor.commit();
                    buttonSend.setImageResource(R.drawable.button_regenerate);
                    buttonSend.setVisibility(View.VISIBLE);
                    Log.d("MAIN","REGENERATION CHANGED ON: " + "TRUE");
                    amadeusEditor.putString("bugLog",bugLog + "\n" + "[MAIN]" + "REGENERATION CHANGED ON: " + "TRUE");
                    Toast.makeText(this, "Regeneration mode enabled", Toast.LENGTH_SHORT).show();
                }

                return true;

            case R.id.change_emotion:
                Toast.makeText(this, "Changing emotion", Toast.LENGTH_SHORT).show();

                final String[] emotionsArray = {
                        "neutral",
                        "angry",
                        "apathetic",
                        "blushed",
                        "displeased",
                        "glad",
                        "happy",
                        "moody",
                        "playful",
                        "sad",
                        "serious",
                        "skeptical",
                        "surprised",
                        "thoughtful",
                        "tired",
                        "calm"};

                        new AlertDialog.Builder(this)
                        .setTitle("Choose emotion")
                        .setItems(emotionsArray, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                String emotion = emotionsArray[which];

                                String emotionName = Recognizer.getEmotionName(MainActivity.this, emotion);
                                int emotionResId = getResources().getIdentifier(emotionName, "drawable", getPackageName());
                                charMain.setImageResource(emotionResId);

                                amadeusEditor.putString("lastEmotion", emotion).commit();

                            }
                        })
                        .show();

                return true;

            case R.id.edit_character:
                Intent intentEditCharacter = new Intent(MainActivity.this, CharacterEditActivity.class);
                startActivity(intentEditCharacter);

                return true;
        }
        return true;
    }


}

