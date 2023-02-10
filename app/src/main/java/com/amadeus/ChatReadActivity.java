package com.amadeus;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Arrays;

public class ChatReadActivity extends AppCompatActivity {

    private LinearLayout chatMessagesView;
    private TextView chatTitle;

    public static final String APP_PREFERENCES = "amadeusSettings";
    public static final String APP_PREFERENCES_FULL_CHAT_HISTORY = "fullChatHistory";

    SharedPreferences amadeusSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_read);
        Bundle arguments = getIntent().getExtras();
        chatMessagesView = findViewById(R.id.chatMessagesView);
        chatTitle = findViewById(R.id.chatTitle);

        String thisChatHistory = arguments.get("thisChatHistory")
                .toString()
                .replace("<START>","")
                .replace("\\n\\n","\\n");

        String chatNumberFromEnd = arguments.get("chatNumberFromEnd")
                .toString();

        chatTitle.setText("CHAT №"+chatNumberFromEnd);

        String[] historySplitted = thisChatHistory.split("\\\\n+");

        for (int messageNumber = 0; messageNumber < historySplitted.length; messageNumber++) {

            String message = historySplitted[messageNumber];

            Boolean fromChar = false;
            if (message.contains("Kurisu:")){
                String buff = message.replace(": (","(")
                        .replace(")","):\n");
                message = buff;
                fromChar = true;
            } else {
                String buff = message.replace("You: ","You:\n");
                message = buff;
                fromChar = false;
            }

            TextView newMessage = new TextView(this);
            newMessage.setText(message);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT);
            params.setMargins(64,0,64,16);
            newMessage.setLayoutParams(params);
            Typeface typeface = ResourcesCompat.getFont(this, R.font.noto_sans);
            newMessage.setTypeface(typeface);
            newMessage.setTextSize(18);
            newMessage.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
            newMessage.setSingleLine(false);
            newMessage.setElegantTextHeight(true);
            if (fromChar == true){
                newMessage.setTextColor(getResources().getColor(R.color.textHighlightColor));

            } else {
                newMessage.setTextColor(getResources().getColor(R.color.textInputColor));
            }

            chatMessagesView.addView(newMessage);

        }

    }

    public void goBack(View v){
        Intent intentBack = new Intent(this, ChatsHistoryActivity.class);
        startActivity(intentBack);
    }

    public void onDel(View v){

        Bundle arguments = getIntent().getExtras();

        new AlertDialog.Builder(this)
                .setTitle("Delete chat")
                .setMessage("Are you sure you want to delete this chat?")

                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        amadeusSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
                        SharedPreferences.Editor amadeusEditor = amadeusSettings.edit();

                        String fullChatHistory = amadeusSettings.getString(APP_PREFERENCES_FULL_CHAT_HISTORY,"");
                        String[] fullChatHistorySplitted = fullChatHistory.split("END_OF_DIALOGUE");

                        int chatNumberFromEnd = (int) arguments.get("chatNumberFromEnd");
                        for (int i = chatNumberFromEnd; i < fullChatHistorySplitted.length; i++) {
                            fullChatHistorySplitted[i - 1] = fullChatHistorySplitted[i];
                        }
                        fullChatHistorySplitted = Arrays.copyOf(fullChatHistorySplitted, fullChatHistorySplitted.length - 1);

                        fullChatHistory = String.join("END_OF_DIALOGUE",fullChatHistorySplitted);

                        amadeusEditor.putString("fullChatHistory",fullChatHistory).commit();

                        Intent intentBack = new Intent(ChatReadActivity.this, ChatsHistoryActivity.class);
                        startActivity(intentBack);
                    }
                })

                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();

    }


}