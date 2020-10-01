package com.example.spike_exercise;

import android.app.Application;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;

public class ApplicationClass extends Application {
    public static final String APPLICATION_ID = "1C5AC4C0-8D08-E7A4-FF8B-BD7B2CFDC800";
    public static final String API_KEY = "6E71C444-5C22-41E4-933C-3380AA3E64A2";
    public static final String SERVER_URL = "https://api.backendless.com";

    public static BackendlessUser user;
    @Override
    public void onCreate() {
        super.onCreate();

        Backendless.setUrl(SERVER_URL );
        Backendless.initApp( getApplicationContext(),
                APPLICATION_ID,
                API_KEY );
    }
}
