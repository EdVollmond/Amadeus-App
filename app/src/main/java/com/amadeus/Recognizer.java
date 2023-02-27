package com.amadeus;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.util.Random;

public class Recognizer {
    public static final String APP_PREFERENCES = "amadeusSettings";
    public static final String APP_PREFERENCES_EMOTIONS = "emotions";
    public static final String APP_PREFERENCES_EMOTION_TIRED = "emotion_tired";
    public static final String APP_PREFERENCES_EMOTION_THOUGHTFUL = "emotion_thoughtful";
    public static final String APP_PREFERENCES_EMOTIONS_SURPRISED = "emotion_surprised";
    public static final String APP_PREFERENCES_EMOTIONS_SKEPTICAL = "emotion_skeptical";
    public static final String APP_PREFERENCES_EMOTIONS_SERIOUS = "emotion_serious";
    public static final String APP_PREFERENCES_EMOTIONS_SAD = "emotion_sad";
    public static final String APP_PREFERENCES_EMOTIONS_PLAYFUL = "emotion_playful";
    public static final String APP_PREFERENCES_EMOTIONS_MOODY = "emotion_moody";
    public static final String APP_PREFERENCES_EMOTIONS_HAPPY = "emotion_happy";
    public static final String APP_PREFERENCES_EMOTIONS_GLAD = "emotion_glad";
    public static final String APP_PREFERENCES_EMOTIONS_DISPLEASED = "emotion_displeased";
    public static final String APP_PREFERENCES_EMOTIONS_CALM = "emotion_calm";
    public static final String APP_PREFERENCES_EMOTIONS_BLUSHED = "emotion_blushed";
    public static final String APP_PREFERENCES_EMOTIONS_APATHETIC = "emotion_apathetic";
    public static final String APP_PREFERENCES_EMOTIONS_ANGRY = "emotion_angry";
    public static final String APP_PREFERENCES_CUSTOM_CHAR = "customChar";
    public static final String APP_PREFERENCES_CHAR_NAME = "charName";
    static SharedPreferences amadeusSettings;

    public static String[] getTextEmotion(String rawText){
        String emotions = amadeusSettings.getString(APP_PREFERENCES_EMOTIONS, "");

        String buff = "";
        String potentialEmotion = "not_found";

        if (rawText.contains("You:")) {
            String[] buffer = rawText.split("You:");
            rawText = buffer[0];
        }

        buff  = rawText
                .replace("Amadeus Kurisu:", "")
                .replace("Kurisu:","")
                .replace("\\n","")
                .replace("EMOTIONS:","emotion:")
                .replace("EMOTION:","emotion:")
                .replace("Emotions:","emotion:")
                .replace("Emotion:","emotion:")
                .replace("emotions:","emotion:")
                .replace("{","(")
                .replace("}",")")
                .replace("]",")")
                .replace("[","(")
                .replace("end of message","")
                .replace("End of message","")
                .replace("END OF MESSAGE", "")
                .replace("end_of_message","")
                .replace("End_of_message","")
                .replace("END_OF_MESSAGE", "");


        rawText = buff.trim();

        Log.i("RECOGNIZER", "RAW1 TEXT: " + rawText);

        if (rawText.contains("(emotion:")) {
            String[] buffer = rawText.split("\\)");
            if (buffer.length > 2) {
                String[] bufferTheSecond = buffer[0].split("emotion:");

                if (bufferTheSecond.length > 1) {

                    if (!bufferTheSecond[0].trim().equals("(")) {
                        potentialEmotion = bufferTheSecond[1].replace("emotion:", "").trim();
                        rawText = bufferTheSecond[0].replace("(", "").replace("\\", "").trim();
                    } else {
                        String[] bufferTheThird = buffer[1].split("\\(");
                        if (bufferTheThird.length > 1) {
                            potentialEmotion = bufferTheSecond[1].replace("emotion:", "").trim();
                            rawText = bufferTheThird[0].replace("(", "").replace("\\", "").trim();
                        }
                    }

                } else {
                    potentialEmotion = "neutral";
                    rawText = "...";
                }

            } else if (buffer.length == 2) {
                String[] bufferTheSecond = buffer[0].split("emotion:");
                if (bufferTheSecond.length > 1) {
                    if (!bufferTheSecond[0].trim().equals("(")) {
                        potentialEmotion = bufferTheSecond[1].trim();
                        rawText = bufferTheSecond[0].replace("(", "").replace("\\", "").trim();
                    } else {
                        potentialEmotion = bufferTheSecond[1].trim();
                        rawText = buffer[1].trim().replace("(", "").replace("\\", "").trim();
                    }
                } else {
                    potentialEmotion = "neutral";
                rawText = "...";
            }
            } else {
                potentialEmotion = "neutral";
                rawText = "...";
            }
        } else {

            buff = rawText.replace("(", "*")
                    .replace(")", "*")
                    .replace(".*", "*");
            rawText = buff;

            Log.i("RECOGNIZER", "RAW1-1 TEXT: " + rawText);

            String[] buffer = buff.split("\\*");
            String[] allEmotions = emotions.split(",");

            if (buffer.length > 0) {
                for (int partNum = 0; partNum < buffer.length; partNum++) {
                    if (!buffer[partNum].equals("")) {
                        String[] bufferTheSecond = buffer[partNum].replaceAll("[^\\da-zA-Zа ]", "")
                                .toLowerCase()
                                .trim()
                                .split(" ");
                        for (int wordNum = 0; wordNum < bufferTheSecond.length; wordNum++) {
                            String word = bufferTheSecond[wordNum];
                            for (int emoNum = 0; emoNum < allEmotions.length; emoNum++) {
                                if (word.equals(allEmotions[emoNum])) ;
                                potentialEmotion = allEmotions[emoNum];

                            }
                        }
                    }
                }
            }
        }

        if (rawText.equals("")|rawText.equals("**")|rawText.equals("\"")){
            rawText = "...";
        }

        String emotion = "";

        if (potentialEmotion.equals("not_found")){
            emotion = "neutral";
            Log.i("RECOGNIZER", "EMOTION NOT FOUND");
        } else {
            emotion = potentialEmotion.toLowerCase();
        }

        String[] TextEmotion = {rawText,emotion};

        return TextEmotion;
    }

    public static String getEmotionName(Context context, String emotion){

        amadeusSettings = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor amadeusEditor = amadeusSettings.edit();

        String drawableName = "emotion_neutral";
        String defaultCharName = context.getResources().getString(R.string.default_char_name);
        Boolean customChar = amadeusSettings.getBoolean(APP_PREFERENCES_CUSTOM_CHAR,false);

        if (!emotion.equals("neutral")) {
            final Random random = new Random();

            String emotions = amadeusSettings.getString(APP_PREFERENCES_EMOTIONS, "");
            String emotion_tired = amadeusSettings.getString(APP_PREFERENCES_EMOTION_TIRED, "");
            String emotion_thoughtful = amadeusSettings.getString(APP_PREFERENCES_EMOTION_THOUGHTFUL, "");
            String emotion_surprised = amadeusSettings.getString(APP_PREFERENCES_EMOTIONS_SURPRISED, "");
            String emotion_skeptical = amadeusSettings.getString(APP_PREFERENCES_EMOTIONS_SKEPTICAL, "");
            String emotion_serious = amadeusSettings.getString(APP_PREFERENCES_EMOTIONS_SERIOUS, "");
            String emotion_sad = amadeusSettings.getString(APP_PREFERENCES_EMOTIONS_SAD, "");
            String emotion_playful = amadeusSettings.getString(APP_PREFERENCES_EMOTIONS_PLAYFUL, "");
            String emotion_moody = amadeusSettings.getString(APP_PREFERENCES_EMOTIONS_MOODY, "");
            String emotion_happy = amadeusSettings.getString(APP_PREFERENCES_EMOTIONS_HAPPY, "");
            String emotion_glad = amadeusSettings.getString(APP_PREFERENCES_EMOTIONS_GLAD, "");
            String emotion_displeased = amadeusSettings.getString(APP_PREFERENCES_EMOTIONS_DISPLEASED, "");
            String emotion_calm = amadeusSettings.getString(APP_PREFERENCES_EMOTIONS_CALM, "");
            String emotion_blushed = amadeusSettings.getString(APP_PREFERENCES_EMOTIONS_BLUSHED, "");
            String emotion_apathetic = amadeusSettings.getString(APP_PREFERENCES_EMOTIONS_APATHETIC, "");
            String emotion_angry = amadeusSettings.getString(APP_PREFERENCES_EMOTIONS_ANGRY, "");

            Log.i("RECOG","Emotion:" + emotion);

            if (emotions.contains(emotion)) {

                Log.i("RECOG","Emotion in emotions list");

                if (customChar == false) {
                    if (emotion_angry.contains(emotion)) {
                        if (random.nextBoolean() == true) {
                            drawableName = "emotion_angry";
                        } else {
                            drawableName = "emotion_hands_angry";
                        }
                    }
                    if (emotion_apathetic.contains(emotion)) {
                        drawableName = "emotion_apathetic";
                    }
                    if (emotion_blushed.contains(emotion)) {
                        if (random.nextBoolean() == true) {
                            drawableName = "emotion_blushed";
                        } else {
                            drawableName = "emotion_hands_blushed";
                        }
                    }
                    if (emotion_calm.contains(emotion)) {
                        drawableName = "emotion_hands_calm";

                    }
                    if (emotion_displeased.contains(emotion)) {
                        drawableName = "emotion_displeased";

                    }
                    if (emotion_glad.contains(emotion)) {
                        drawableName = "emotion_hands_glad";

                    }
                    if (emotion_happy.contains(emotion)) {
                        drawableName = "emotion_happy";

                    }
                    if (emotion_moody.contains(emotion)) {
                        drawableName = "emotion_moody";

                    }
                    if (emotion_playful.contains(emotion)) {
                        drawableName = "emotion_playful";

                    }
                    if (emotion_sad.contains(emotion)) {
                        drawableName = "emotion_sad";

                    }
                    if (emotion_serious.contains(emotion)) {
                        drawableName = "emotion_serious";

                    }
                    if (emotion_skeptical.contains(emotion)) {
                        drawableName = "emotion_hands_skeptical";

                    }
                    if (emotion_surprised.contains(emotion)) {
                        drawableName = "emotion_hands_surprised";

                    }
                    if (emotion_thoughtful.contains(emotion)) {
                        drawableName = "emotion_hands_thoughtful";

                    }
                    if (emotion_tired.contains(emotion)) {
                        drawableName = "emotion_tired";
                    }
                } else {
                    if (emotion_angry.contains(emotion)) {
                        drawableName = "emotion_angry";
                    }
                    if (emotion_apathetic.contains(emotion)) {
                        drawableName = "emotion_apathetic";
                    }
                    if (emotion_blushed.contains(emotion)) {
                        drawableName = "emotion_blushed";
                        }
                    if (emotion_calm.contains(emotion)) {
                        drawableName = "emotion_calm";
                    }
                    if (emotion_displeased.contains(emotion)) {
                        drawableName = "emotion_displeased";
                    }
                    if (emotion_glad.contains(emotion)) {
                        drawableName = "emotion_glad";
                    }
                    if (emotion_happy.contains(emotion)) {
                        drawableName = "emotion_happy";
                    }
                    if (emotion_moody.contains(emotion)) {
                        drawableName = "emotion_moody";
                    }
                    if (emotion_playful.contains(emotion)) {
                        drawableName = "emotion_playful";
                    }
                    if (emotion_sad.contains(emotion)) {
                        drawableName = "emotion_sad";
                    }
                    if (emotion_serious.contains(emotion)) {
                        drawableName = "emotion_serious";
                    }
                    if (emotion_skeptical.contains(emotion)) {
                        drawableName = "emotion_skeptical";
                    }
                    if (emotion_surprised.contains(emotion)) {
                        drawableName = "emotion_surprised";
                    }
                    if (emotion_thoughtful.contains(emotion)) {
                        drawableName = "emotion_thoughtful";
                    }
                    if (emotion_tired.contains(emotion)) {
                        drawableName = "emotion_tired";
                    }
                }
                amadeusEditor.putString("lastEmotion", emotion).commit();
            } else {
                amadeusEditor.putString("lastEmotion", "neutral").commit();
            }
        } else {
            amadeusEditor.putString("lastEmotion", "neutral").commit();
        }

        String emotionName = "";

        if (customChar == false) {
            emotionName = defaultCharName.trim().toLowerCase() + "_" + drawableName;

        } else {
            emotionName = drawableName;
        }

        Log.i("RECOG","custom char " + customChar.toString());
        Log.i("RECOG","emotionname " + emotionName);

        return emotionName;
    }

    public static Bitmap getCustomEmotion(Context context, String emotion){
        amadeusSettings = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        String charName = amadeusSettings.getString(APP_PREFERENCES_CHAR_NAME, "");
        String emotionName = Recognizer.getEmotionName(context, emotion);
        File data = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        String folder = charName.trim().toLowerCase().replace(" ","_");
        File file = new File(data + "/" + folder + "/" + emotionName + ".png");
        if (file.exists()) {
            Log.i("RECOGNIZER", "EXISTS");
            if (file.canRead()){
                Log.i("RECOGNIZER", "CAN READ");
            } else {
                Toast.makeText(context, "ERROR: can't read emotion file", Toast.LENGTH_SHORT).show();
                Log.i("RECOGNIZER", "CAN'T READ");
            }
        } else {
            Toast.makeText(context, "ERROR: can't find emotion file", Toast.LENGTH_SHORT).show();
            Log.i("RECOGNIZER", "NOT EXISTS");
        }
        Log.i("RECOGNIZER", data.toString());
        Log.i("RECOGNIZER", file.toString());
        Bitmap bitmap = BitmapFactory.decodeFile(file.toString());
        return bitmap;
    }

    public static File getCharJson(Context context, String jsonName){
        File data = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        String folder = jsonName.trim();
        File file = new File(data + "/" + folder);
        if (file.exists()) {
            Log.i("RECOGNIZER", "EXISTS");
            if (file.canRead()){
                Log.i("RECOGNIZER", "CAN READ");
            } else {
                Toast.makeText(context, "ERROR: can't read .json file", Toast.LENGTH_SHORT).show();
                Log.i("RECOGNIZER", "CAN'T READ");
            }
        } else {
            Toast.makeText(context, "ERROR: can't find .json file", Toast.LENGTH_SHORT).show();
            Log.i("RECOGNIZER", "NOT EXISTS");
        }
        Log.i("RECOGNIZER", data.toString());
        Log.i("RECOGNIZER", file.toString());
        return file;
    }






}
