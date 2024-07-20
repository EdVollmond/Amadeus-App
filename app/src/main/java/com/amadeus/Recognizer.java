package com.amadeus;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.util.Random;

public class Recognizer {
    public static final String APP_PREFERENCES = "amadeusSettings";
    static SharedPreferences amadeusSettings;


    public static String getEmotionName(Context context, String emotion){

        amadeusSettings = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor amadeusEditor = amadeusSettings.edit();

        Log.i("RECOG", emotion);

        String drawableName = "";


        if (emotion.equals("neutral")) {

            drawableName = "emotion_neutral";

        } else {
            final Random random = new Random();

            if (emotion.equals("angry")) {
                if (random.nextBoolean() == true) {
                    drawableName = "emotion_angry";
                } else {
                    drawableName = "emotion_hands_angry";
                }
            }
            if (emotion.equals("apathetic")) {
                drawableName = "emotion_apathetic";
            }
            if (emotion.equals("blushed")) {
                if (random.nextBoolean() == true) {
                    drawableName = "emotion_blushed";
                } else {
                    drawableName = "emotion_hands_blushed";
                }
            }
            if (emotion.equals("calm")) {
                drawableName = "emotion_hands_calm";

            }
            if (emotion.equals("displeased")) {
                drawableName = "emotion_displeased";

            }
            if (emotion.equals("glad")) {
                drawableName = "emotion_hands_glad";

            }
            if (emotion.equals("happy")) {
                drawableName = "emotion_happy";

            }
            if (emotion.equals("moody")) {
                drawableName = "emotion_moody";

            }
            if (emotion.equals("playful")) {
                drawableName = "emotion_playful";

            }
            if (emotion.equals("sad")) {
                drawableName = "emotion_sad";

            }
            if (emotion.equals("serious")) {
                drawableName = "emotion_serious";

            }
            if (emotion.equals("skeptical")) {
                drawableName = "emotion_hands_skeptical";

            }
            if (emotion.equals("surprised")) {
                drawableName = "emotion_hands_surprised";

            }
            if (emotion.equals("thoughtful")) {
                drawableName = "emotion_hands_thoughtful";

            }
            if (emotion.equals("tired")) {
                drawableName = "emotion_tired";
            }
        }

        String emotionName = "kurisu_" + drawableName;

        Log.i("RECOG","emotionname " + emotionName);

        return emotionName;
    }







}
