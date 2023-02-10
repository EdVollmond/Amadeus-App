package com.amadeus;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;


public class ChatsHistoryActivity extends AppCompatActivity {

    public static final String APP_PREFERENCES = "amadeusSettings";
    public static final String APP_PREFERENCES_FULL_CHAT_HISTORY = "fullChatHistory";
    public static final String APP_PREFERENCES_CURRENT_CHAT_HISTORY = "currentChatHistory";
    public static final String APP_PREFERENCES_LAST_RESPONSE = "lastResponse";


    SharedPreferences amadeusSettings;

    private ImageButton buttonAllDel;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chats_history);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        amadeusSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);

        LinearLayout historyList = findViewById(R.id.historyList);

        SharedPreferences.Editor amadeusEditor = amadeusSettings.edit();
        amadeusEditor.putString("debugger","0").commit();

        String fullChatHistory = amadeusSettings.getString(APP_PREFERENCES_FULL_CHAT_HISTORY, "");
        String currentChatHistory = amadeusSettings.getString(APP_PREFERENCES_CURRENT_CHAT_HISTORY, "");
        String lastResponse = amadeusSettings.getString(APP_PREFERENCES_LAST_RESPONSE, "");

        buttonAllDel = findViewById(R.id.buttonAllDel);

        if (!fullChatHistory.equals("")){

            String chatHistory[] = fullChatHistory.split("END_OF_DIALOGUE");

            for (int chatNumber = 0; chatNumber < chatHistory.length; chatNumber++) {

                int chatNumberFromEnd = chatHistory.length - chatNumber;
                StringBuffer chatNumberStr = new StringBuffer(String.valueOf(chatNumberFromEnd));

                String[] buff = chatHistory[chatNumberFromEnd - 1].split("\\)");

                Boolean thisChat = false;

                String lastText = buff[buff.length - 1]
                        .replaceAll("\\\\n+", "")
                        .replace("<START>", "")
                        .trim();


                if (chatNumberFromEnd == chatHistory.length) {
                    lastText = lastResponse;
                    thisChat = true;
                }

                RelativeLayout newLayout = new RelativeLayout(this);
                newLayout.setLayoutParams(new RelativeLayout.LayoutParams(MATCH_PARENT, 240));
                historyList.addView(newLayout);

                ImageButton newButton = new ImageButton(this);
                newButton.setImageResource(R.drawable.button_big_empty);
                newButton.setBackground(null);
                newButton.setScaleType(ImageView.ScaleType.FIT_XY);

                String chat = "";
                if (thisChat == true){
                    chat = currentChatHistory;
                } else {
                    chat = chatHistory[chatNumberFromEnd - 1];
                }
                String finalChat = chat;

                newButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intentChat = new Intent(ChatsHistoryActivity.this, ChatReadActivity.class);
                        intentChat.putExtra("thisChatHistory", finalChat);
                        intentChat.putExtra("chatNumberFromEnd", chatNumberFromEnd);
                        startActivity(intentChat);
                    }
                });
                TextView newText = new TextView(this);
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT);
                params.setMargins(64, 32, 64, 32);
                newText.setLayoutParams(params);
                Typeface typeface = ResourcesCompat.getFont(this, R.font.noto_sans);
                newText.setTypeface(typeface);
                newText.setTextSize(16);
                newText.setText("Chat №" + chatNumberStr + "   " + lastText);

                newLayout.addView(newButton);
                newLayout.addView(newText);

            }

        } else {

            buttonAllDel.setVisibility(View.INVISIBLE);
            RelativeLayout newLayout = new RelativeLayout(this);
            newLayout.setLayoutParams(new RelativeLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT));
            historyList.addView(newLayout);

            TextView newText = new TextView(this);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT);

            params.setMargins(64, 280, 64, 32);
            newText.setLayoutParams(params);

            newText.setTextSize(20);
            newText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            newText.setText("CHATS HISTORY IS EMPTY NOW...");

            newLayout.addView(newText);
        }

    }

    public void goMain(View v) {
        Intent intentMain = new Intent(this, MainActivity.class);
        startActivity(intentMain);
    }


    public void onClear(View v){

        new AlertDialog.Builder(this)
                .setTitle("Clear all history")
                .setMessage("Are you sure you want to delete all history of chats?")

                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        amadeusSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
                        SharedPreferences.Editor amadeusEditor = amadeusSettings.edit();
                        amadeusEditor.remove("fullChatHistory").commit();
                        Intent intentMain = new Intent(ChatsHistoryActivity.this, MainActivity.class);
                        startActivity(intentMain);
                    }
                })

                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();

    }


}