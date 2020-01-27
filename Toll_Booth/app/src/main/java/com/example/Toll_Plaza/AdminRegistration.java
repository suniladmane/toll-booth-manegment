package com.example.Toll_Plaza;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

public class AdminRegistration extends AppCompatActivity {
    EditText editEmail, editPassword, editName, editVehicalNumber, editVehicalType, editPhoneNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_registration);

        editName = findViewById(R.id.editName);
        editEmail = findViewById(R.id.editEmail);
        editPhoneNo = findViewById(R.id.editPhoneNo);
        editPassword = findViewById(R.id.editPassword);


    }

    public void onRegister(View view) {
        String name = editName.getText().toString();
        String email = editEmail.getText().toString();
        String phoneno = editPhoneNo.getText().toString();
        String password = editPassword.getText().toString();

        if (name.length() == 0) {
            editName.setError("Name is mandatory");
        } else if (email.length() == 0) {
            editEmail.setError("Email is mandatory");
        } else if (phoneno.length() == 0) {
            editPhoneNo.setError("phoneNo  is mandatory");
        } else if (password.length() == 0) {
            editPassword.setError("password is mandatory");
        } else {
            //create url
            //String url = "http://172.18.4.204:5000/user/register";  //college
            String url = "http://192.168.43.216:5000/admin/register"; //my mobile
            //create body
            JsonObject body = new JsonObject();
            body.addProperty("name", name);
            body.addProperty("email", email);
            body.addProperty("phone", phoneno);
            body.addProperty("password", password);


            //call the api
            Ion.with(this)
                    .load("post", url)
                    .setJsonObjectBody(body)
                    .asJsonObject()
                    .setCallback(new FutureCallback<JsonObject>() {
                        @Override
                        public void onCompleted(Exception e, JsonObject result) {
                            //Log.e("login activity",result.toString());
                            String status = result.get("status").getAsString();

                            if (status.equals("success")) {

                                Toast.makeText(AdminRegistration.this, "Successfully register new account please login", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(AdminRegistration.this, "Invalid email or password", Toast.LENGTH_SHORT).show();
                            }

                        }


                    });
        }
    }


    public void oncancel(View view) {
        finish();

    }


}