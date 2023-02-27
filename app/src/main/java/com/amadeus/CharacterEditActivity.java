package com.amadeus;


import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Switch;
import android.widget.Toast;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class CharacterEditActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    private static final int STORAGE_PERMISSION_CODE = 101;

    public static final String APP_PREFERENCES = "amadeusSettings";

    public static final String APP_PREFERENCES_CHAR_NAME = "charName";
    public static final String APP_PREFERENCES_CHAR_PERSONA = "charPersona";
    public static final String APP_PREFERENCES_EXAMPLE_DIALOGUE = "exampleDialogue";
    public static final String APP_PREFERENCES_WORLD_SCENARIO = "worldScenario";
    public static final String APP_PREFERENCES_CHAR_GREETING_0 = "charGreeting0";
    public static final String APP_PREFERENCES_CHAR_GREETING_1 = "charGreeting1";
    public static final String APP_PREFERENCES_CHAR_GREETING_2 = "charGreeting2";
    public static final String APP_PREFERENCES_CHAR_GREETING_3 = "charGreeting3";
    public static final String APP_PREFERENCES_CHAR_GREETING_4 = "charGreeting4";

    public static final String APP_PREFERENCES_CUSTOM_CHAR = "customChar";

    SharedPreferences amadeusSettings;

    private EditText charNameEdit;
    private EditText charPersonaEdit;
    private EditText charGreeting0Edit;
    private EditText worldScenarioEdit;
    private EditText exampleDialogueEdit;
    private EditText charGreeting1Edit;
    private EditText charGreeting2Edit;
    private EditText charGreeting3Edit;
    private EditText charGreeting4Edit;

    private Switch switchCustomChar;


    String path;
    Uri uri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_edit);
        amadeusSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);

        charNameEdit = findViewById(R.id.charName);
        charPersonaEdit = findViewById(R.id.charPersona);
        charGreeting0Edit = findViewById(R.id.charGreetings0);
        worldScenarioEdit = findViewById(R.id.worldScenario);
        exampleDialogueEdit = findViewById(R.id.exampleDialogue);
        charGreeting1Edit = findViewById(R.id.charGreetings1);
        charGreeting2Edit = findViewById(R.id.charGreetings2);
        charGreeting3Edit = findViewById(R.id.charGreetings3);
        charGreeting4Edit = findViewById(R.id.charGreetings4);
        switchCustomChar = findViewById(R.id.switchCustomChar);

        String charName = amadeusSettings.getString(APP_PREFERENCES_CHAR_NAME,"");
        String charPersona = amadeusSettings.getString(APP_PREFERENCES_CHAR_PERSONA,"");
        String charGreeting0 = amadeusSettings.getString(APP_PREFERENCES_CHAR_GREETING_0,"");
        String worldScenario = amadeusSettings.getString(APP_PREFERENCES_WORLD_SCENARIO,"");
        String exampleDialogue = amadeusSettings.getString(APP_PREFERENCES_EXAMPLE_DIALOGUE,"");
        String charGreeting1 = amadeusSettings.getString(APP_PREFERENCES_CHAR_GREETING_1,"");
        String charGreeting2 = amadeusSettings.getString(APP_PREFERENCES_CHAR_GREETING_2,"");
        String charGreeting3 = amadeusSettings.getString(APP_PREFERENCES_CHAR_GREETING_3,"");
        String charGreeting4 = amadeusSettings.getString(APP_PREFERENCES_CHAR_GREETING_4,"");

        String buffer = exampleDialogue.replace("\\n","\n");
        exampleDialogue = buffer;
        buffer = charPersona.replace("\\n","\n");
        charPersona = buffer;

        charNameEdit.setText(charName);
        charPersonaEdit.setText(charPersona);
        charGreeting0Edit.setText(charGreeting0);
        worldScenarioEdit.setText(worldScenario);
        exampleDialogueEdit.setText(exampleDialogue);
        charGreeting1Edit.setText(charGreeting1);
        charGreeting2Edit.setText(charGreeting2);
        charGreeting3Edit.setText(charGreeting3);
        charGreeting4Edit.setText(charGreeting4);

        Boolean customChar = amadeusSettings.getBoolean(APP_PREFERENCES_CUSTOM_CHAR,false);
        switchCustomChar.setChecked(customChar);

        switchCustomChar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, STORAGE_PERMISSION_CODE);
            }
        });

    }


    public void onSave(View v){
        SharedPreferences.Editor amadeusEditor = amadeusSettings.edit();

        String charName = charNameEdit.getText().toString().trim();
        String charPersona = charPersonaEdit.getText().toString().trim();
        String charGreeting0 = charGreeting0Edit.getText().toString().trim();
        String worldScenario = worldScenarioEdit.getText().toString().trim();
        String exampleDialogue = exampleDialogueEdit.getText().toString().trim();
        String charGreeting1 = charGreeting1Edit.getText().toString().trim();
        String charGreeting2 = charGreeting2Edit.getText().toString().trim();
        String charGreeting3 = charGreeting3Edit.getText().toString().trim();
        String charGreeting4 = charGreeting4Edit.getText().toString().trim();

        Boolean customChar = switchCustomChar.isChecked();

        String buffer = exampleDialogue.replace("\n","\\n");
        exampleDialogue = buffer;
        buffer = charPersona.replace("\n","\\n");
        charPersona = buffer;

        amadeusEditor.putString("charName",charName).commit();
        amadeusEditor.putString("charPersona",charPersona).commit();
        amadeusEditor.putString("charGreeting0",charGreeting0).commit();
        amadeusEditor.putString("worldScenario",worldScenario).commit();
        amadeusEditor.putString("exampleDialogue",exampleDialogue).commit();
        amadeusEditor.putString("charGreeting1",charGreeting1).commit();
        amadeusEditor.putString("charGreeting2",charGreeting2).commit();
        amadeusEditor.putString("charGreeting3",charGreeting3).commit();
        amadeusEditor.putString("charGreeting4",charGreeting4).commit();

        amadeusEditor.putBoolean("customChar",customChar).commit();
    }
    public void goMain(View v) {
        Intent intentMain = new Intent(this, MainActivity.class);
        startActivity(intentMain);
    }

    public void checkPermission(String permission, int requestCode)
    {
        // Checking if permission is not granted
        if (ContextCompat.checkSelfPermission(CharacterEditActivity.this, permission) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(CharacterEditActivity.this, new String[] { permission }, requestCode);
        }
        else {
            Toast.makeText(CharacterEditActivity.this, "Permission already granted", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(CharacterEditActivity.this, "Storage Permission Granted", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(CharacterEditActivity.this, "Storage Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void onOtherClick(View view){
        PopupMenu edit_menu = new PopupMenu(CharacterEditActivity.this, view);
        edit_menu.setOnMenuItemClickListener(CharacterEditActivity.this);
        edit_menu.inflate(R.menu.chareditpopupmenu);
        edit_menu.show();
    }

    public boolean onMenuItemClick(MenuItem item) {

        switch (item.getItemId()) {


            case R.id.json_import:

                AlertDialog.Builder importDialog = new AlertDialog.Builder(this);
                final EditText edittext = new EditText(this);
                importDialog.setTitle("Import from JSON");
                importDialog.setMessage("Put name of JSON file in your Download directory");
                importDialog.setView(edittext);
                importDialog.setNegativeButton("Cancel", null);
                importDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String jsonName = edittext.getText().toString();

                        File file = Recognizer.getCharJson(CharacterEditActivity.this,jsonName);
                        


                    }
                });
                importDialog.show();












                return true;


            case R.id.json_export:















                return true;


            case R.id.set_default:

                try {
                    BufferedReader reader = null;

                    reader = new BufferedReader(new InputStreamReader(getAssets().open("kurisu_character/char_name.txt")));
                    String charName = reader.readLine();

                    reader = new BufferedReader(new InputStreamReader(getAssets().open("kurisu_character/char_persona.txt")));
                    String charPersona = reader.readLine();

                    reader = new BufferedReader(new InputStreamReader(getAssets().open("kurisu_character/example_dialogue.txt")));
                    String exampleDialogue = reader.readLine();

                    reader = new BufferedReader(new InputStreamReader(getAssets().open("kurisu_character/world_scenario.txt")));
                    String worldScenario = reader.readLine();

                    reader = new BufferedReader(new InputStreamReader(getAssets().open("kurisu_character/greeting0.txt")));
                    String charGreeting0 = reader.readLine();

                    reader = new BufferedReader(new InputStreamReader(getAssets().open("kurisu_character/greeting1.txt")));
                    String charGreeting1 = reader.readLine();

                    reader = new BufferedReader(new InputStreamReader(getAssets().open("kurisu_character/greeting2.txt")));
                    String charGreeting2 = reader.readLine();

                    reader = new BufferedReader(new InputStreamReader(getAssets().open("kurisu_character/greeting3.txt")));
                    String charGreeting3 = reader.readLine();

                    reader = new BufferedReader(new InputStreamReader(getAssets().open("kurisu_character/greeting4.txt")));
                    String charGreeting4 = reader.readLine();

                    charNameEdit.setText(charName);
                    charPersonaEdit.setText(charPersona);
                    charGreeting0Edit.setText(charGreeting0);
                    worldScenarioEdit.setText(worldScenario);
                    exampleDialogueEdit.setText(exampleDialogue);
                    charGreeting1Edit.setText(charGreeting1);
                    charGreeting2Edit.setText(charGreeting2);
                    charGreeting3Edit.setText(charGreeting3);
                    charGreeting4Edit.setText(charGreeting4);

                    switchCustomChar.setChecked(false);

                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }


                return true;

        }
    return true;
    }


}