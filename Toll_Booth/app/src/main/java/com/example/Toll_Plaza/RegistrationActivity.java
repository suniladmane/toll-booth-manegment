package com.example.Toll_Plaza;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

public class RegistrationActivity extends AppCompatActivity {
    EditText editEmail, editPassword, editName, editVehicalNumber, editVehicalType, editPhoneNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        editName = findViewById(R.id.editName);
        editEmail = findViewById(R.id.editEmail);
        editPhoneNo = findViewById(R.id.editPhoneNo);
        editVehicalType = findViewById(R.id.editVehicalType);
        editVehicalNumber = findViewById(R.id.editVehicalNumber);
        editPassword = findViewById(R.id.editPassword);


    }

    public void onRegister(View view) {
        String name = editName.getText().toString();
        String email = editEmail.getText().toString();
        String phoneno = editPhoneNo.getText().toString();
        String vehicaltype = editVehicalType.getText().toString();
        String vehicalnumber = editVehicalNumber.getText().toString();
        String password = editPassword.getText().toString();

        if (name.length() == 0) {
            editName.setError("Name is mandatory");
        } else if (email.length() == 0) {
            editEmail.setError("Email is mandatory");
        } else if (phoneno.length() == 0) {
            editPhoneNo.setError("phoneNo  is mandatory");
        } else if (name.length() == 0) {
            editVehicalType.setError("vehical is mandatory");
        } else if (vehicalnumber.length() == 0) {
            editVehicalNumber.setError("vehical is mandatory");
        } else if (password.length() == 0) {
            editPassword.setError("password is mandatory");
        } else {
            //create url
            //String url = "http://172.18.4.204:5000/user/register";  //college
            String url = "http://192.168.43.216:5000/user/register"; //my mobile
            //create body
            JsonObject body = new JsonObject();
            body.addProperty("name", name);
            body.addProperty("email", email);
            body.addProperty("phone", phoneno);
            body.addProperty("vehicaltype", vehicaltype);
            body.addProperty("vehicalno", vehicalnumber);
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

                                Toast.makeText(RegistrationActivity.this, "Successfully register new account please login", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(RegistrationActivity.this, "Invalid email or password", Toast.LENGTH_SHORT).show();
                            }

                        }


                    });
        }
    }


    public void oncancel(View view) {
        finish();

    }


}