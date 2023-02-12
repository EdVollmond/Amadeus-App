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
import android.text.method.ScrollingMovementMethod;
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
import java.util.Arrays;


public class MainActivity extends Activity implements PopupMenu.OnMenuItemClickListener {

    private EditText textInput;
    private ImageButton buttonSend;
    private TextView textResponse;
    private ImageView charMain;
    private TextView errorTextMain;
    private pl.droidsonroids.gif.GifImageView progressBar;

    public static final String APP_PREFERENCES = "amadeusSettings";
    public static final String APP_PREFERENCES_FULL_CHAT_HISTORY = "fullChatHistory";
    public static final String APP_PREFERENCES_CURRENT_CHAT_HISTORY = "currentChatHistory";

    public static final String APP_PREFERENCES_CHAR_TEMPLATE = "charTemplate";
    public static final String APP_PREFERENCES_CHAR_NAME = "charName";
    public static final String APP_PREFERENCES_CHAR_PERSONA = "charPersona";
    public static final String APP_PREFERENCES_EXAMPLE_DIALOGUE = "exampleDialogue";
    public static final String APP_PREFERENCES_WORLD_SCENARIO = "worldScenario";

    public static final String APP_PREFERENCES_CHAR_GREETING_0 = "charGreeting0";
    public static final String APP_PREFERENCES_CHAR_GREETING_1 = "charGreeting1";
    public static final String APP_PREFERENCES_CHAR_GREETING_2 = "charGreeting2";
    public static final String APP_PREFERENCES_CHAR_GREETING_3 = "charGreeting3";
    public static final String APP_PREFERENCES_CHAR_GREETING_4 = "charGreeting4";

    public static final String APP_PREFERENCES_ENDING_BY_CHAR = "endingByChar";

    public static final String APP_PREFERENCES_LAST_MESSAGE = "lastMessage";
    public static final String APP_PREFERENCES_LAST_RESPONSE = "lastResponse";
    public static final String APP_PREFERENCES_LAST_EMOTION = "lastEmotion";


    public static final String APP_PREFERENCES_REGENERATION = "regeneration";
    public static final String APP_PREFERENCES_END_DIALOG = "endDialog";

    public static final String APP_PREFERENCES_DEBUGGER = "debugger";

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


        errorTextMain = findViewById(R.id.errorTextMain);
        textInput = findViewById(R.id.textInput);
        textResponse = findViewById(R.id.textResponse);
        charMain = findViewById(R.id.charMain);
        buttonSend = findViewById(R.id.buttonSend);

        progressBar = findViewById(R.id.progressBarConnect);

        textResponse.setMovementMethod(new ScrollingMovementMethod());

        try {
            BufferedReader reader = null;

            String charTemplate = amadeusSettings.getString(APP_PREFERENCES_CHAR_TEMPLATE,"");
            if (charTemplate.equals("")) {
                reader = new BufferedReader(new InputStreamReader(getAssets().open("char_template.txt")));
                charTemplate = reader.readLine();
                amadeusEditor.putString("charTemplate", charTemplate).commit();
            }


            String charName = amadeusSettings.getString(APP_PREFERENCES_CHAR_NAME,"");
            if (charName.equals("")){
                reader = new BufferedReader(new InputStreamReader(getAssets().open("kurisu_character/char_name.txt")));
                charName = reader.readLine();
                amadeusEditor.putString("charName", charName).commit();
            }

            String charPersona = amadeusSettings.getString(APP_PREFERENCES_CHAR_PERSONA,"");
            if (charPersona.equals("")){
                reader = new BufferedReader(new InputStreamReader(getAssets().open("kurisu_character/char_persona.txt")));
                charPersona = reader.readLine();
                amadeusEditor.putString("charPersona", charPersona).commit();
            }

            String exampleDialogue = amadeusSettings.getString(APP_PREFERENCES_EXAMPLE_DIALOGUE,"");
            if (exampleDialogue.equals("")){
                reader = new BufferedReader(new InputStreamReader(getAssets().open("kurisu_character/example_dialogue.txt")));
                exampleDialogue = reader.readLine();
                amadeusEditor.putString("exampleDialogue", exampleDialogue).commit();
            }

            String worldScenario = amadeusSettings.getString(APP_PREFERENCES_WORLD_SCENARIO,"");
            if (worldScenario.equals("")){
                reader = new BufferedReader(new InputStreamReader(getAssets().open("kurisu_character/world_scenario.txt")));
                worldScenario = reader.readLine();
                amadeusEditor.putString("worldScenario", worldScenario).commit();
            }

            String charGreeting0 = amadeusSettings.getString(APP_PREFERENCES_CHAR_GREETING_0,"");
            if (charGreeting0.equals("")){
                reader = new BufferedReader(new InputStreamReader(getAssets().open("kurisu_character/greeting0.txt")));
                charGreeting0 = reader.readLine();
                amadeusEditor.putString("charGreeting0", charGreeting0).commit();
            }

            String charGreeting1 = amadeusSettings.getString(APP_PREFERENCES_CHAR_GREETING_1,"");
            if (charGreeting1.equals("")){
                reader = new BufferedReader(new InputStreamReader(getAssets().open("kurisu_character/greeting1.txt")));
                charGreeting1 = reader.readLine();
                amadeusEditor.putString("charGreeting1", charGreeting1).commit();
            }

            String charGreeting2 = amadeusSettings.getString(APP_PREFERENCES_CHAR_GREETING_2,"");
            if (charGreeting2.equals("")){
                reader = new BufferedReader(new InputStreamReader(getAssets().open("kurisu_character/greeting2.txt")));
                charGreeting2 = reader.readLine();
                amadeusEditor.putString("charGreeting2", charGreeting2).commit();
            }

            String charGreeting3 = amadeusSettings.getString(APP_PREFERENCES_CHAR_GREETING_3,"");
            if (charGreeting3.equals("")){
                reader = new BufferedReader(new InputStreamReader(getAssets().open("kurisu_character/greeting3.txt")));
                charGreeting3 = reader.readLine();
                amadeusEditor.putString("charGreeting3", charGreeting3).commit();
            }

            String charGreeting4 = amadeusSettings.getString(APP_PREFERENCES_CHAR_GREETING_4,"");
            if (charGreeting4.equals("")){
                reader = new BufferedReader(new InputStreamReader(getAssets().open("kurisu_character/greeting4.txt")));
                charGreeting4 = reader.readLine();
                amadeusEditor.putString("charGreeting4", charGreeting4).commit();
            }


            reader = new BufferedReader(new InputStreamReader(getAssets().open("emotions/emotions.txt")));
            String emotions = reader.readLine();

            reader = new BufferedReader(new InputStreamReader(getAssets().open("emotions/emotion_tired.txt")));
            String emotion_tired = reader.readLine();

            reader = new BufferedReader(new InputStreamReader(getAssets().open("emotions/emotion_thoughtful.txt")));
            String emotion_thoughtful = reader.readLine();

            reader = new BufferedReader(new InputStreamReader(getAssets().open("emotions/emotion_surprised.txt")));
            String emotion_surprised = reader.readLine();

            reader = new BufferedReader(new InputStreamReader(getAssets().open("emotions/emotion_skeptical.txt")));
            String emotion_skeptical = reader.readLine();

            reader = new BufferedReader(new InputStreamReader(getAssets().open("emotions/emotion_serious.txt")));
            String emotion_serious = reader.readLine();

            reader = new BufferedReader(new InputStreamReader(getAssets().open("emotions/emotion_sad.txt")));
            String emotion_sad = reader.readLine();

            reader = new BufferedReader(new InputStreamReader(getAssets().open("emotions/emotion_playful.txt")));
            String emotion_playful = reader.readLine();

            reader = new BufferedReader(new InputStreamReader(getAssets().open("emotions/emotion_moody.txt")));
            String emotion_moody = reader.readLine();

            reader = new BufferedReader(new InputStreamReader(getAssets().open("emotions/emotion_happy.txt")));
            String emotion_happy = reader.readLine();

            reader = new BufferedReader(new InputStreamReader(getAssets().open("emotions/emotion_glad.txt")));
            String emotion_glad = reader.readLine();

            reader = new BufferedReader(new InputStreamReader(getAssets().open("emotions/emotion_displeased.txt")));
            String emotion_displeased = reader.readLine();

            reader = new BufferedReader(new InputStreamReader(getAssets().open("emotions/emotion_calm.txt")));
            String emotion_calm = reader.readLine();

            reader = new BufferedReader(new InputStreamReader(getAssets().open("emotions/emotion_blushed.txt")));
            String emotion_blushed = reader.readLine();

            reader = new BufferedReader(new InputStreamReader(getAssets().open("emotions/emotion_apathetic.txt")));
            String emotion_apathetic = reader.readLine();

            reader = new BufferedReader(new InputStreamReader(getAssets().open("emotions/emotion_angry.txt")));
            String emotion_angry = reader.readLine();

            amadeusEditor.putString("emotions", emotions);
            amadeusEditor.putString("emotion_tired", emotion_tired);
            amadeusEditor.putString("emotion_thoughtful", emotion_thoughtful);
            amadeusEditor.putString("emotion_surprised", emotion_surprised);
            amadeusEditor.putString("emotion_skeptical", emotion_skeptical);
            amadeusEditor.putString("emotion_serious", emotion_serious);
            amadeusEditor.putString("emotion_sad", emotion_sad);
            amadeusEditor.putString("emotion_playful", emotion_playful);
            amadeusEditor.putString("emotion_moody", emotion_moody);
            amadeusEditor.putString("emotion_happy", emotion_happy);
            amadeusEditor.putString("emotion_glad", emotion_glad);
            amadeusEditor.putString("emotion_displeased", emotion_displeased);
            amadeusEditor.putString("emotion_calm", emotion_calm);
            amadeusEditor.putString("emotion_blushed", emotion_blushed);
            amadeusEditor.putString("emotion_apathetic", emotion_apathetic);
            amadeusEditor.putString("emotion_angry", emotion_angry);

            amadeusEditor.commit();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String first_response = Greetings.randomGeneration(this);

        String lastResponse = amadeusSettings.getString(APP_PREFERENCES_LAST_RESPONSE, first_response);
        String fullChatHistory = amadeusSettings.getString(APP_PREFERENCES_FULL_CHAT_HISTORY,"");
        String currentChatHistory = amadeusSettings.getString(APP_PREFERENCES_CURRENT_CHAT_HISTORY,"");
        String charName = amadeusSettings.getString(APP_PREFERENCES_CHAR_NAME,"");
        String lastEmotion = amadeusSettings.getString(APP_PREFERENCES_LAST_EMOTION, getResources().getString(R.string.first_emotion));

        Boolean endDialog = amadeusSettings.getBoolean(APP_PREFERENCES_END_DIALOG, false);

        if (endDialog == true) {
            String newFullChatHistory = "";
            if (!currentChatHistory.equals("")) {
                String newCurrentChatHistory = currentChatHistory + "\\n" + charName + ": (emotion: " + lastEmotion + ")" + lastResponse + "\\nEND_OF_DIALOGUE\\n<START>\\n";
                if (fullChatHistory.equals("")) {
                    newFullChatHistory = newCurrentChatHistory;
                } else {
                    newFullChatHistory = fullChatHistory + newCurrentChatHistory;
                }
                amadeusEditor.putString("fullChatHistory", newFullChatHistory).commit();
            }

            Log.d("VOLLEY","FULL HISTORY: " + fullChatHistory);

            amadeusEditor.remove("currentChatHistory").commit();
            amadeusEditor.remove("lastMessage").commit();
            amadeusEditor.remove("lastResponse").commit();
            amadeusEditor.remove("lastEmotion").commit();

            String charGreeting = Greetings.randomGeneration(this);

            textResponse.setText(charGreeting);

            String emotionName = Recognizer.getEmotionName(this,getString(R.string.first_emotion));
            int emotionResId = getResources().getIdentifier(emotionName, "drawable", getPackageName());
            charMain.setImageResource(emotionResId);

            amadeusEditor.putBoolean("regeneration", false).commit();
            amadeusEditor.putBoolean("endDialog", false).commit();
            buttonSend.setImageResource(R.drawable.button_send);
            buttonSend.setVisibility(View.VISIBLE);
        }else{
            textResponse.setText(lastResponse);
        }

        amadeusEditor.putBoolean("regeneration", false).commit();

        String emotionName = Recognizer.getEmotionName(MainActivity.this,lastEmotion);
        int emotionResId = getResources().getIdentifier(emotionName, "drawable", getPackageName());
        charMain.setImageResource(emotionResId);

    }


    public void onSendClick(View v) {
        errorTextMain.setVisibility(View.INVISIBLE);

        SharedPreferences.Editor amadeusEditor = amadeusSettings.edit();


        String charTemplate = amadeusSettings.getString(APP_PREFERENCES_CHAR_TEMPLATE, "");
        String charName = amadeusSettings.getString(APP_PREFERENCES_CHAR_NAME, "");
        String charPersona = amadeusSettings.getString(APP_PREFERENCES_CHAR_PERSONA, "");
        String exampleDialogue = amadeusSettings.getString(APP_PREFERENCES_EXAMPLE_DIALOGUE, "");
        String worldScenario = amadeusSettings.getString(APP_PREFERENCES_WORLD_SCENARIO, "");

        Boolean regeneration = amadeusSettings.getBoolean(APP_PREFERENCES_REGENERATION, false);
        Boolean endingByChar = amadeusSettings.getBoolean(APP_PREFERENCES_ENDING_BY_CHAR, true);
        Log.i("MAIN", "ENDING BY CHAR: " + endingByChar);


        String lastMessage = amadeusSettings.getString(APP_PREFERENCES_LAST_MESSAGE, "");
        String lastEmotion = amadeusSettings.getString(APP_PREFERENCES_LAST_EMOTION, getResources().getString(R.string.default_emotion));

        String charGreeting = Greetings.randomGeneration(this);
        String lastResponse = amadeusSettings.getString(APP_PREFERENCES_LAST_RESPONSE, charGreeting);

        String currentChatHistory = amadeusSettings.getString(APP_PREFERENCES_CURRENT_CHAT_HISTORY, "");


        if (textInput.getText().toString().isEmpty() & regeneration == false) {
            Toast.makeText(this, "ERROR: empty input", Toast.LENGTH_SHORT).show();
        } else {
            progressBar.setVisibility(View.VISIBLE);
            String message = "";
            if (regeneration == true) {
                message = lastMessage;
                Log.i("MAIN", "LAST MESSAGE: " + lastMessage);
            } else {
                String rawMessage = textInput.getText().toString();

                String newMessages = "\\n" + charName + ": (emotion: " + lastEmotion + ")" + lastResponse + "\\n" + "You" + ": ";

                String strBuffer = currentChatHistory;
                currentChatHistory = strBuffer + newMessages;

                String buffer = charTemplate
                        .replace("{{char_name}}", charName)
                        .replace("{{char_persona}}",charPersona)
                        .replace("{{char_greeting}}", charGreeting)
                        .replace("{{world_scenario}}", worldScenario)
                        .replace("{{example_dialogue}}", exampleDialogue);
                String charBase = buffer;


                message = charBase + "\\n" + "<START>" + "\\n" + currentChatHistory + rawMessage + "\\n" + charName + ": ";

                buffer = currentChatHistory;
                currentChatHistory = buffer + rawMessage;

                amadeusEditor.putString("currentChatHistory", currentChatHistory).commit();
                amadeusEditor.putString("lastMessage", message).commit();
            }

            Log.i("MAIN", "REGENERATION: " + regeneration);
            Log.i("MAIN", "MESSAGE: " + message);

            amadeusEditor.putBoolean("waiting", true).commit();

            Messenger.onSend(this, message, new VolleyCallback() {
                @Override
                public void onSuccess(String result) {


                    String rawText = result;

                    String defaultCharName = getResources().getString(R.string.default_char_name);
                    Boolean customChar = amadeusSettings.getBoolean(APP_PREFERENCES_CUSTOM_CHAR,false);
                    String charName = amadeusSettings.getString(APP_PREFERENCES_CHAR_NAME,"");


                    String lastResponse = amadeusSettings.getString(APP_PREFERENCES_LAST_RESPONSE, charGreeting);
                    String currentChatHistory = amadeusSettings.getString(APP_PREFERENCES_CURRENT_CHAT_HISTORY, "");

                    String[] TextEmotion = Recognizer.getTextEmotion(rawText);
                    String text = TextEmotion[0];
                    String emotion = TextEmotion[1];

                    Log.i("MAIN", "RAW2 TEXT: " + text);
                    Log.i("MAIN", "EMOTION: " + emotion);

                    String buff = text.replace("END_OF_DIALOG","END_OF_DIALOGUE")
                            .replace("*end of conversation*","END_OF_DIALOGUE")
                            .replace("*end of chat*","END_OF_DIALOGUE")
                            .replace("*end dialogue*","END_OF_DIALOGUE")
                            .replace("*end of dialogue*","END_OF_DIALOGUE");
                    text = buff;


                    if (text.contains("END_OF_DIALOGUE") & endingByChar == true) {

                        Log.i("MAIN", "DIALOGUE WAS ENDED BY CHARACTER");

                        if (customChar == false) {
                            String emotionName = defaultCharName.trim().toLowerCase() + "_back";
                            int emotionResId = getResources().getIdentifier(emotionName, "drawable", getPackageName());
                            charMain.setImageResource(emotionResId);
                        } else {
                            String emotionName = charName.trim().toLowerCase() + "_back";
                            int emotionResId = getResources().getIdentifier(emotionName, "drawable", getPackageName());
                            charMain.setImageResource(emotionResId);
                        }

                        String[] buffer = text.split("END_OF_DIALOG");
                        if (buffer.length > 0) {
                            text = buffer[0];
                        } else {
                            text = "...";
                        }
                        if (text.equals("")) {
                            text = "...";
                        }
                        amadeusEditor.putBoolean("endDialog", true).commit();
                        buttonSend.setVisibility(View.GONE);

                    } else {
                        String emotionName = Recognizer.getEmotionName(MainActivity.this, emotion);
                        int emotionResId = getResources().getIdentifier(emotionName, "drawable", getPackageName());
                        charMain.setImageResource(emotionResId);
                    }

                    buff = text.replace("END_OF_DIALOGUE","");
                    text = buff;

                    amadeusEditor.putString("lastEmotion", emotion).commit();

                    if (text == "") {
                        text = "...";
                    }

                    textInput.setText("");
                    textResponse.setText(text);

                    Log.d("MAIN", "HISTORY: " + currentChatHistory);

                    amadeusEditor.putBoolean("regeneration", false).commit();
                    buttonSend.setImageResource(R.drawable.button_send);
                    Log.d("MAIN", "REGENERATION CHANGED ON: " + "FALSE");


                    lastResponse = text;
                    amadeusEditor.putString("lastResponse", lastResponse).commit();
                    Log.d("MAIN", "LAST RESPONSE: " + "FALSE");

                    progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onError(String result) {
                    Log.e("VOLLEY", result);
                    progressBar.setVisibility(View.GONE);
                    errorTextMain.setText("ERROR: can't get response");
                    errorTextMain.setVisibility(View.VISIBLE);
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
        String charName = amadeusSettings.getString(APP_PREFERENCES_CHAR_NAME,"");
        String currentChatHistory = amadeusSettings.getString(APP_PREFERENCES_CURRENT_CHAT_HISTORY,"");
        String fullChatHistory = amadeusSettings.getString(APP_PREFERENCES_FULL_CHAT_HISTORY,"");

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


            case R.id.start_new_chat:
                errorTextMain.setVisibility(View.INVISIBLE);
                progressBar.setVisibility(View.VISIBLE);

                String newFullChatHistory = "";
                if (!currentChatHistory.equals("")) {
                    String newCurrentChatHistory = currentChatHistory + "\\n" + charName + ": (emotion: " + lastEmotion + ")" + lastResponse + "\\nEND_OF_DIALOGUE\\n<START>\\n";
                    if (fullChatHistory.equals("")) {
                        newFullChatHistory = newCurrentChatHistory;
                    } else {
                        newFullChatHistory = fullChatHistory + newCurrentChatHistory;
                    }
                    amadeusEditor.putString("fullChatHistory", newFullChatHistory).commit();
                }

                fullChatHistory = amadeusSettings.getString(APP_PREFERENCES_FULL_CHAT_HISTORY,"");

                Log.d("VOLLEY","FULL HISTORY: " + fullChatHistory);

                amadeusEditor.remove("currentChatHistory").commit();
                amadeusEditor.remove("lastMessage").commit();
                amadeusEditor.remove("lastResponse").commit();
                amadeusEditor.remove("lastEmotion").commit();

                String charGreeting = Greetings.randomGeneration(this);

                textResponse.setText(charGreeting);

                String emotionName = Recognizer.getEmotionName(this,getString(R.string.first_emotion));
                int emotionResId = getResources().getIdentifier(emotionName, "drawable", getPackageName());
                charMain.setImageResource(emotionResId);

                amadeusEditor.putBoolean("regeneration", false).commit();
                amadeusEditor.putBoolean("endDialog", false).commit();
                buttonSend.setImageResource(R.drawable.button_send);
                buttonSend.setVisibility(View.VISIBLE);

                Log.d("VOLLEY","REGENERATION CHANGED ON: " + "FALSE");
                progressBar.setVisibility(View.GONE);
                Toast.makeText(this, "New chat started", Toast.LENGTH_SHORT).show();
                return true;


            case R.id.remove_message:
                errorTextMain.setVisibility(View.INVISIBLE);

                if(!currentChatHistory.equals("")) {

                    amadeusEditor.putBoolean("endDialog", false).commit();
                    buttonSend.setVisibility(View.VISIBLE);

                    progressBar.setVisibility(View.VISIBLE);

                    Log.d("MAIN","OLD HISTORY: " + currentChatHistory);

                    String[] historySplitted = currentChatHistory.split("\\\\n+");


                    Log.d("MAIN","SPLITTED: " + String.join("|||",historySplitted));

                    String[] newHistory = Arrays.copyOf(historySplitted, historySplitted.length - 2);
                    currentChatHistory = String.join("\\n",newHistory);

                    Log.d("VOLLEY","NEW HISTORY: " + String.join("\\n",newHistory));

                    String text = "";
                    String rawEmotion = "";
                    String emotion = getString(R.string.default_emotion);
                    if (historySplitted[historySplitted.length-2].contains("emotion")) {
                        String[] buff = historySplitted[historySplitted.length-2].split("\\)");
                        String unnormalEmotion = buff[0].replace("(emotion: ", "");
                        rawEmotion = unnormalEmotion.replaceAll("[^A-Za-z]+", "");
                        text = buff[1];
                    }else{
                        text = historySplitted[historySplitted.length-2];
                    }
                    emotion = rawEmotion.replace(charName,"");

                    Log.d("MAIN","LAST RESPONSE: " + text);
                    Log.d("MAIN","LAST EMOTION: " + emotion);


                    String emotionNameRemoving = Recognizer.getEmotionName(MainActivity.this,emotion);
                    int emotionResIdRemoving = getResources().getIdentifier(emotionNameRemoving, "drawable", getPackageName());
                    charMain.setImageResource(emotionResIdRemoving);

                    amadeusEditor.putString("lastResponse", text);
                    amadeusEditor.putString("currentChatHistory", currentChatHistory);
                    amadeusEditor.commit();

                    textResponse.setText(text);

                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(this, "Last message removed", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(this, "Chat history is already empty", Toast.LENGTH_SHORT).show();
                }

                return true;



            case R.id.regenerate:
                errorTextMain.setVisibility(View.INVISIBLE);
                if(amadeusSettings.contains(APP_PREFERENCES_CURRENT_CHAT_HISTORY)){

                    Boolean regeneration = amadeusSettings.getBoolean(APP_PREFERENCES_REGENERATION, false);
                    Boolean endDialog = amadeusSettings.getBoolean(APP_PREFERENCES_END_DIALOG, false);

                    if(regeneration == true){
                        amadeusEditor.putBoolean("regeneration", false);
                        amadeusEditor.commit();
                        buttonSend.setImageResource(R.drawable.button_send);
                        if (endDialog == true) {
                            buttonSend.setVisibility(View.GONE);
                        }
                        Log.d("VOLLEY","REGENERATION CHANGED ON: " + "FALSE");
                        Toast.makeText(this, "Regeneration mode disabled", Toast.LENGTH_SHORT).show();
                    }else {
                        amadeusEditor.putBoolean("regeneration", true);
                        amadeusEditor.commit();
                        buttonSend.setImageResource(R.drawable.button_regenerate);
                        buttonSend.setVisibility(View.VISIBLE);
                        Log.d("VOLLEY","REGENERATION CHANGED ON: " + "TRUE");
                        Toast.makeText(this, "Regeneration mode enabled", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(this, "Nothing to regenerate", Toast.LENGTH_SHORT).show();
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

                                String emotionName = Recognizer.getEmotionName(MainActivity.this,emotion);
                                int emotionResId = getResources().getIdentifier(emotionName, "drawable", getPackageName());
                                charMain.setImageResource(emotionResId);

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

