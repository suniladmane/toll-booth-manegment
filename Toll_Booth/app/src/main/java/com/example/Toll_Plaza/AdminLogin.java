package com.example.Toll_Plaza;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

public class AdminLogin extends AppCompatActivity {

    EditText editEmail,editPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        editEmail = findViewById(R.id.editEmail);
        editPassword = findViewById(R.id.editPassword);
    }

    public void onLogin(View view) {
        String email = editEmail.getText().toString();
        String passworld = editPassword.getText().toString();
        if(email.length()==0)
        {
            editEmail.setError("email is mandetory");
        }
        else if(editPassword.length()==0)
        {
            editPassword.setError("passworld is mandetory");
        }
        else
        {
            //create url
            //String url = "http://172.18.4.204:5000/user/login";
            String url = "http://192.168.43.216:5000/admin/login";
            //create body
            JsonObject body = new JsonObject();
            body.addProperty("email",email);
            body.addProperty("password",passworld);
            //call the api
            Ion.with(this)
                    .load("post",url)
                    .setJsonObjectBody(body)
                    .asJsonObject()
                    .setCallback(new FutureCallback<JsonObject>() {
                        @Override
                        public void onCompleted(Exception e, JsonObject result) {
                            Log.e("login activity",result.toString());
                            String status = result.get("UID").getAsString();
                            if(status.equals("error"))
                            {
                                Toast.makeText(AdminLogin.this,"Invalid email or password",Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                Toast.makeText(AdminLogin.this,"Welcome to my application",Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(AdminLogin.this,MainActivity.class);
                                         //String key = status;
                                         //intent.putExtra("KEY",key);
                                                startActivity(intent);
                                finish();

                            }

                        }
                    });
        }
    }

    public void onRegister(View view) {
        Intent intent= new Intent(this,AdminRegistration.class);
        startActivity(intent);
    }


}
