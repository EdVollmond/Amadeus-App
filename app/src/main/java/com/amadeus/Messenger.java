package com.amadeus;

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
    public static final String APP_PREFERENCES_OUTPUT_MAX = "outputMax";
    public static final String APP_PREFERENCES_CONTEXT_MEMORY_MAX = "contextMemoryMax";
    public static final String APP_PREFERENCES_temperature = "temperature";
    public static final String APP_PREFERENCES_REPETITION_PENALTY = "repetitionPenalty";
    public static final String APP_PREFERENCES_REPETITION_PENALTY_RANGE = "repetitionPenaltyRange";
    public static final String APP_PREFERENCES_END_DIALOG = "endDialog";
    public static final String APP_PREFERENCES_CURRENT_CHAT_HISTORY = "currentChatHistory";
    public static final String APP_PREFERENCES_URL = "inputURL";

    static SharedPreferences amadeusSettings;


    public static void onSend(Context context,String message,final VolleyCallback callback){

        amadeusSettings = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor amadeusEditor = amadeusSettings.edit();
        String inputURL = amadeusSettings.getString(APP_PREFERENCES_URL, "");
        Log.i("LOG", "URL: " + inputURL);

        try {
            RequestQueue requestQueue = Volley.newRequestQueue(context);
            String URL = inputURL + "/api/v1/generate/";
            JSONObject jsonBody = new JSONObject();

/*            List<String> sampler_order = new ArrayList<>();

            sampler_order.add("6");
            sampler_order.add("0");
            sampler_order.add("1");
            sampler_order.add("2");
            sampler_order.add("3");
            sampler_order.add("4");
            sampler_order.add("5");*/

            //int[] sampler_order = {6, 0, 1, 2, 3, 4, 5};

            int max_context_length = amadeusSettings.getInt(APP_PREFERENCES_CONTEXT_MEMORY_MAX, 2048);
            int max_length = amadeusSettings.getInt(APP_PREFERENCES_OUTPUT_MAX, 50);
            double rep_pen = amadeusSettings.getInt(APP_PREFERENCES_REPETITION_PENALTY, 108) * 0.01;
            int rep_pen_range = amadeusSettings.getInt(APP_PREFERENCES_REPETITION_PENALTY_RANGE, 1024);
            double temperature = amadeusSettings.getInt(APP_PREFERENCES_temperature, 50) * 0.01;

            //Log.i("MESSENGER", sampler_order.toString());

            jsonBody.put("prompt", message);
            jsonBody.put("use_story", false);
            jsonBody.put("use_memory", false);
            jsonBody.put("use_authors_note", false);
            jsonBody.put("use_world_info", false);
            jsonBody.put("max_context_length", max_context_length);
            jsonBody.put("max_length", max_length);
            jsonBody.put("rep_pen", rep_pen);
            jsonBody.put("rep_pen_range", rep_pen_range);
            jsonBody.put("temperature", temperature);
            jsonBody.put("tfs", 0.9);
            jsonBody.put("top_a", 0);
            jsonBody.put("top_k", 0);
            jsonBody.put("top_p", 0.9);
            jsonBody.put("typical", 1);
            //jsonBody.put("sampler_order", sampler_order);



            final String requestBody = jsonBody.toString();

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.i("VOLLEY", response.toString());

                    Boolean endDialog = amadeusSettings.getBoolean(APP_PREFERENCES_END_DIALOG, false);

                    if (endDialog == true) {
                        amadeusEditor.putBoolean("endDialog", false).commit();
                    }

                    String rawText = "";
                    try {
                        JSONArray resultsArray = response.getJSONArray("results");
                        JSONObject resultsObject = resultsArray.getJSONObject(0);
                        rawText = resultsObject.getString("text");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    callback.onSuccess(rawText);

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
