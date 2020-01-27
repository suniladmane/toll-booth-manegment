package com.example.Toll_Plaza;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


public class LoaderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loader);


        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);

                    SharedPreferences preferences =
                            PreferenceManager.getDefaultSharedPreferences(LoaderActivity.this);

                   //  check if user has already logged in
                    boolean isLoggedIn = preferences.getBoolean("isLoggedIn", false);

                    if (isLoggedIn) {
                        // if yes, let the user go to main activity
                        Intent intent = new Intent(LoaderActivity.this, MainActivity.class);
                       startActivity(intent);
                     } else {
                    //     if not, ask user to login first
                        Intent intent = new Intent(LoaderActivity.this, ChoiceActivity.class);
                        startActivity(intent);
                    }

                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }
}
