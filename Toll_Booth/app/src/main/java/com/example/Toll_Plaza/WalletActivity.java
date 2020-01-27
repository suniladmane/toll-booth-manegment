package com.example.Toll_Plaza;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class WalletActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);
    }

    public void onAdd(View view) {
        Intent intent = new Intent(WalletActivity.this,UpiActivity.class);
        startActivity(intent);
    }

    public void onBalance(View view) {
    }
}
