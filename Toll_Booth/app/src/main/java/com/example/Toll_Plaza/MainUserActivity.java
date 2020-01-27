package com.example.Toll_Plaza;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;

public class MainUserActivity extends AppCompatActivity {
    ListView listView;
    ArrayList<tollinfo> tollinfos = new ArrayList<>();
    ArrayAdapter<tollinfo> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.listView);
        adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,tollinfos);
        listView.setAdapter(adapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        loadtollinfo();
    }

    private  void loadtollinfo()
    {
        Bundle extras;
        String newString;
        extras = getIntent().getExtras();
        newString = extras.getString("UID");
        Log.e("MainUserActivity",newString.toString());

        //create tollinfo
        tollinfos.clear();

        //create url

        //String url = "http://172.18.4.204:5000/tollinfo";
        String url = "http://192.168.43.216:5000/tollinfo?UID="+newString;
        //String url = "http://172.18.4.204:5000/tollinfo";
        //String url = "http://192.168.43.216:5000/tollinfo";

        //call api
        Ion.with(this)
                .load("GET",url)
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {
                        Log.e("MainActivity", result.toString());
                        for (int index = 0; index < result.size();index++){

                            //get json object at every index
                            JsonObject object = result.get(index).getAsJsonObject();
                            tollinfo Tollinfo = new tollinfo();
                            //set the properties

                            Tollinfo.setName(object.get("Name").getAsString());
                            Tollinfo.setAmount(object.get("Amount").getAsFloat());
                            Tollinfo.setTimestamp(object.get("timestamp").getAsString());
                            Tollinfo.setVehical_number(object.get("vehical_number").getAsString());
                            Tollinfo.setVehical_type(object.get("vehical_type").getAsString());

                            //add tollinfo to the tollinfos list
                            tollinfos.add(Tollinfo);
                        }
                        //
                        adapter.notifyDataSetChanged();
                    }
                });
    }
    // options menu


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("Refresh");
        menu.add("Logout");
        menu.add("Wallet");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getTitle().equals("Refresh")){
            loadtollinfo();

        }else if(item.getTitle().equals("Logout")){
            finish();

        }else if(item.getTitle().equals("Wallet")) {
            Intent intent = new Intent(MainUserActivity.this,WalletActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
