package com.amadeus;

public interface VolleyCallback{
    void onSuccess(String responseText, String responseEmotion);
    void onError(String responseText);
}