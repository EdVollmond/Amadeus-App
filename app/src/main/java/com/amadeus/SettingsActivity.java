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
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;



public class SettingsActivity extends AppCompatActivity {

    private TextView outputMaxCounter;
    private TextView contextMemoryMaxCounter;
    private TextView temperatureCounter;
    private TextView repetitionPenaltyCounter;
    private TextView repetitionPenaltyRangeCounter;

    private EditText debugLogText;

    private SeekBar outputMaxSB;
    private SeekBar contextMemoryMaxSB;
    private SeekBar temperatureSB;
    private SeekBar repetitionPenaltySB;
    private SeekBar repetitionPenaltyRangeSB;

    private Switch switchEndingByChar;



    public static final String APP_PREFERENCES = "amadeusSettings";
    public static final String APP_PREFERENCES_BUG_LOG = "bugLog";
    public static final String APP_PREFERENCES_OUTPUT_MAX = "outputMax";
    public static final String APP_PREFERENCES_CONTEXT_MEMORY_MAX = "contextMemoryMax";
    public static final String APP_PREFERENCES_temperature = "temperature";
    public static final String APP_PREFERENCES_REPETITION_PENALTY = "repetitionPenalty";
    public static final String APP_PREFERENCES_REPETITION_PENALTY_RANGE = "repetitionPenaltyRange";

    public static final String APP_PREFERENCES_ENDING_BY_CHAR = "endingByChar";

    SharedPreferences amadeusSettings;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        amadeusSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);

        Boolean endingByChar = amadeusSettings.getBoolean(APP_PREFERENCES_ENDING_BY_CHAR, true);
        String bugLog = amadeusSettings.getString(APP_PREFERENCES_BUG_LOG,"Debug log is empty");

        int outputMax = amadeusSettings.getInt(APP_PREFERENCES_OUTPUT_MAX, 50);
        int contextMemoryMax = amadeusSettings.getInt(APP_PREFERENCES_CONTEXT_MEMORY_MAX, 2048);
        int temperature = amadeusSettings.getInt(APP_PREFERENCES_temperature, 95);
        int repetitionPenalty = amadeusSettings.getInt(APP_PREFERENCES_REPETITION_PENALTY, 108);
        int repetitionPenaltyRange = amadeusSettings.getInt(APP_PREFERENCES_REPETITION_PENALTY_RANGE, 1024);

        debugLogText = findViewById(R.id.debugLogText);

        outputMaxSB = findViewById(R.id.outputMaxSB);
        contextMemoryMaxSB = findViewById(R.id.contextMemoryMaxSB);
        temperatureSB = findViewById(R.id.temperatureSB);
        repetitionPenaltySB = findViewById(R.id.repetitionPenaltySB);
        repetitionPenaltyRangeSB = findViewById(R.id.repetitionPenaltyRangeSB);

        debugLogText.setText(bugLog);
        Log.i("SETTINGS", bugLog);


        outputMaxSB.setProgress(outputMax);
        contextMemoryMaxSB.setProgress(contextMemoryMax);
        temperatureSB.setProgress(temperature);
        repetitionPenaltySB.setProgress(repetitionPenalty);
        repetitionPenaltyRangeSB.setProgress(repetitionPenaltyRange);

        outputMaxCounter = findViewById(R.id.outputMaxCounter);
        contextMemoryMaxCounter = findViewById(R.id.contextMemoryMaxCounter);
        temperatureCounter = findViewById(R.id.temperatureCounter);
        repetitionPenaltyCounter = findViewById(R.id.repetitionPenaltyCounter);
        repetitionPenaltyRangeCounter = findViewById(R.id.repetitionPenaltyRangeCounter);

        switchEndingByChar = findViewById(R.id.switchEndingByChar);

        outputMaxSB.setProgress(outputMax);
        String outputMaxStr = String.valueOf(outputMax);
        outputMaxCounter.setText(outputMaxStr);

        contextMemoryMaxSB.setProgress(contextMemoryMax);
        String contextMemoryMaxStr = String.valueOf(contextMemoryMax);
        contextMemoryMaxCounter.setText(contextMemoryMaxStr);

        temperatureSB.setProgress(temperature);
        if (temperature > 100) {
            StringBuffer temperatureStr = new StringBuffer(String.valueOf(temperature));
            temperatureStr.insert(1, ".");
            temperatureCounter.setText(temperatureStr.toString());
        }else {
            String temperatureStr = "0." + String.valueOf(temperature);
            temperatureCounter.setText(temperatureStr);
        }

        repetitionPenaltySB.setProgress(repetitionPenalty);
        StringBuffer repetitionPenaltyStr = new StringBuffer(String.valueOf(repetitionPenalty));
        repetitionPenaltyStr.insert(1,".");
        repetitionPenaltyCounter.setText(repetitionPenaltyStr.toString());

        repetitionPenaltyRangeSB.setProgress(repetitionPenaltyRange);
        String repetitionPenaltyRangeStr = String.valueOf(repetitionPenaltyRange);
        repetitionPenaltyRangeCounter.setText(repetitionPenaltyRangeStr);

        switchEndingByChar.setChecked(endingByChar);

        outputMaxSB.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                String outputMaxStr = String.valueOf(progress);
                outputMaxCounter.setText(outputMaxStr);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        contextMemoryMaxSB.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                String contextMemoryMaxStr = String.valueOf(progress);
                contextMemoryMaxCounter.setText(contextMemoryMaxStr);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        temperatureSB.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {


                if (progress > 100) {
                    StringBuffer temperatureStr = new StringBuffer(String.valueOf(progress));
                    temperatureStr.insert(1, ".");
                    temperatureCounter.setText(temperatureStr.toString());
                }else {
                    String temperatureStr = "0." + String.valueOf(progress);
                    temperatureCounter.setText(temperatureStr);
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        repetitionPenaltySB.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                StringBuffer repetitionPenaltyStr = new StringBuffer(String.valueOf(progress));
                repetitionPenaltyStr.insert(1,".");
                repetitionPenaltyCounter.setText(repetitionPenaltyStr.toString());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        repetitionPenaltyRangeSB.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                String repetitionPenaltyRangeStr = String.valueOf(progress);
                repetitionPenaltyRangeCounter.setText(repetitionPenaltyRangeStr);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }


    public void goMain(View v) {
        Intent intentMain = new Intent(this, MainActivity.class);
        startActivity(intentMain);
    }


    public void saveChanges(View v) {
        amadeusSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor amadeusEditor = amadeusSettings.edit();
        amadeusEditor.putInt("outputMax", outputMaxSB.getProgress()).commit();
        amadeusEditor.putInt("contextMemoryMax", contextMemoryMaxSB.getProgress()).commit();
        amadeusEditor.putInt("temperature", temperatureSB.getProgress()).commit();
        amadeusEditor.putInt("repetitionPenalty", repetitionPenaltySB.getProgress()).commit();
        amadeusEditor.putInt("repetitionPenaltyRange", repetitionPenaltyRangeSB.getProgress()).commit();

        amadeusEditor.putBoolean("endingByChar", switchEndingByChar.isChecked()).commit();

    }

    public void dropSettings(View v) {

        amadeusSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);

        int outputMax = 50;
        int contextMemoryMax = 2048;
        int temperature = 50;
        int repetitionPenalty = 108;
        int repetitionPenaltyRange = 1024;

        Boolean endingByChar =  true;


        outputMaxSB.setProgress(outputMax);
        contextMemoryMaxSB.setProgress(contextMemoryMax);
        temperatureSB.setProgress(temperature);
        repetitionPenaltySB.setProgress(repetitionPenalty);
        repetitionPenaltyRangeSB.setProgress(repetitionPenaltyRange);

        switchEndingByChar.setChecked(endingByChar);
    }







}