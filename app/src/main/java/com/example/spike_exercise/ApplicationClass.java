package com.example.spike_exercise;

import android.app.Application;

import com.backendless.Backendless;

public class ApplicationClass extends Application {
    public static final String APPLICATION_ID = "BB9EFDFD-4B7D-FACD-FF54-627E43FCA700";
    public static final String API_KEY = "70655957-7222-400B-8DB2-4AF76FD8A8ED";
    public static final String SERVER_URL = "https://api.backendless.com";

    @Override
    public void onCreate() {
        super.onCreate();

        Backendless.setUrl(SERVER_URL );
        Backendless.initApp( getApplicationContext(),
                APPLICATION_ID,
                API_KEY );
    }
}
