package com.example.Toll_Plaza;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class ChoiceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice);
    }

    public void onAdminLogin(View view)
    {
        Intent intent = new Intent(ChoiceActivity.this,AdminLogin.class);
        startActivity(intent);
    }

    public void onUserLogin(View view) {
        Intent intent= new Intent(ChoiceActivity.this,Login_Activity.class);
        startActivity(intent);
    }
}
