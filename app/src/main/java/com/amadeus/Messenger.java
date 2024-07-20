package com.amadeus;

import static com.amadeus.MainActivity.APP_PREFERENCES_LAST_RESPONSE;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;


import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class Messenger {

    public static final String APP_PREFERENCES = "amadeusSettings";
    public static final String APP_PREFERENCES_URL = "inputURL";
    public static final String APP_PREFERENCES_USER_ID = "userId";
    public static final String APP_PREFERENCES_USER_NAME = "userName";
    public static final String APP_PREFERENCES_CHAR_ID = "charId";
    public static final String APP_PREFERENCES_LAST_EMOTION = "lastEmotion";
    public static final String APP_PREFERENCES_LAST_RESPONSE = "lastResponse";
    public static final String APP_PREFERENCES_DIALOG_ID = "dialogId";


    static SharedPreferences amadeusSettings;


    public static void onSend(Context context,String message,final VolleyCallback callback){

        amadeusSettings = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        String inputURL = amadeusSettings.getString(APP_PREFERENCES_URL, "");

        Log.i("LOG", "URL: " + inputURL);

        try {
            RequestQueue requestQueue = Volley.newRequestQueue(context);
            String URL = "http://5.166.207.206:8000" + "/generate/";
            JSONObject jsonBody = new JSONObject();


            String userId = amadeusSettings.getString(APP_PREFERENCES_USER_ID, "User");
            String userName = amadeusSettings.getString(APP_PREFERENCES_USER_NAME, "User");
            String charId = amadeusSettings.getString(APP_PREFERENCES_CHAR_ID, "Kurisu");
            String lastEmotion = amadeusSettings.getString(APP_PREFERENCES_LAST_EMOTION, "neutral");
            String lastMessage = amadeusSettings.getString(APP_PREFERENCES_LAST_RESPONSE, "");
            int dialogId = amadeusSettings.getInt(APP_PREFERENCES_DIALOG_ID, 0);


            jsonBody.put("userId", "EdVollmond");
            jsonBody.put("charId", charId);
            jsonBody.put("dialogId", dialogId);
            jsonBody.put("lastEmotion", lastEmotion);
            jsonBody.put("lastMessage", lastMessage);
            jsonBody.put("text", message);
            jsonBody.put("regeneration", false);
            jsonBody.put("userName", "Max");

            final String requestBody = jsonBody.toString();
            Log.i("REQUEST: ", requestBody);

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.i("RESPONSE: ", response.toString());

                    String responseText = "";
                    String responseEmotion = "";


                    try {
                        responseText = response.getString("text");
                        responseEmotion = response.getString("emotion");
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }


                    callback.onSuccess(responseText, responseEmotion);

                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    callback.onError(error + "");
                }
            }) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public byte[] getBody() {
                    try {
                        return requestBody == null ? null : requestBody.getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                        return null;
                    }
                }
            };

            jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(1200000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            requestQueue.add(jsonObjectRequest);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
