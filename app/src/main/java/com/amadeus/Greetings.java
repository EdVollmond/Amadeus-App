package com.amadeus;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Random;

public class Greetings {

    public static final String APP_PREFERENCES = "amadeusSettings";

    public static final String APP_PREFERENCES_CHAR_GREETING_0 = "charGreeting0";
    public static final String APP_PREFERENCES_CHAR_GREETING_1 = "charGreeting1";
    public static final String APP_PREFERENCES_CHAR_GREETING_2 = "charGreeting2";
    public static final String APP_PREFERENCES_CHAR_GREETING_3 = "charGreeting3";
    public static final String APP_PREFERENCES_CHAR_GREETING_4 = "charGreeting4";

    static SharedPreferences amadeusSettings;



    public static String randomGeneration(Context context){
        amadeusSettings = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);

        String charGreeting0 = amadeusSettings.getString(APP_PREFERENCES_CHAR_GREETING_0,"");
        String charGreeting1 = amadeusSettings.getString(APP_PREFERENCES_CHAR_GREETING_1,"");
        String charGreeting2 = amadeusSettings.getString(APP_PREFERENCES_CHAR_GREETING_2,"");
        String charGreeting3 = amadeusSettings.getString(APP_PREFERENCES_CHAR_GREETING_3,"");
        String charGreeting4 = amadeusSettings.getString(APP_PREFERENCES_CHAR_GREETING_4,"");

        final Random random = new Random();
        String charGreetings[] = {charGreeting0,charGreeting1,charGreeting2,charGreeting3,charGreeting4};
        int randomValue = random.nextInt(5);
        while (charGreetings[randomValue].equals("")){
            randomValue = random.nextInt(5);
        }
        String charGreeting = charGreetings[randomValue];
        return charGreeting;
    }

}
