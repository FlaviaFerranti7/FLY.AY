package com.flam.flyay;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.LinearLayout;

import com.flam.flyay.model.Event;
import com.flam.flyay.model.EventFinances;
import com.flam.flyay.model.EventWellness;
import com.flam.flyay.services.EventService;
import com.flam.flyay.services.ServerCallback;
import com.flam.flyay.util.CategoryEnum;
import com.flam.flyay.util.MockServerUrl;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import static com.flam.flyay.util.CategoryEnum.*;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private ActionBar ab;
    private Calendar c;
    private SimpleDateFormat df;
    private String currentDate;

    private LinearLayout eventsList;

    private EventService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            initializeMainActivity();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ab = getSupportActionBar();
        // To display the arrow that goes back
        // ab.setDisplayHomeAsUpEnabled(true);

        c = Calendar.getInstance(TimeZone.getTimeZone("GMT"),Locale.getDefault());
        df = new SimpleDateFormat("dd/MM/yyyy");
        currentDate = df.format(c.getTime());
        ab.setTitle(currentDate);
        ab.setIcon(R.drawable.ic_home_page);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setSelectedItemId(R.id.home);
        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem){
                switch (menuItem.getItemId()){
                    case R.id.home:
                        ab.setTitle(currentDate);
                        ab.setIcon(R.drawable.ic_home_page);
                        return true;

                    case R.id.lens:
                        startActivity(new Intent(getApplicationContext(), SearchActivity.class));
                        overridePendingTransition(0,0);
                        ab.setTitle("Search");
                        ab.setIcon(R.drawable.ic_search);
                        return true;

                    case R.id.plus:
                        startActivity(new Intent(getApplicationContext(), AddEventActivity.class));
                        overridePendingTransition(0,0);
                        ab.setTitle("Add event");
                        ab.setIcon(R.drawable.ic_add_event);
                        return true;

                    case R.id.list:
                        startActivity(new Intent(getApplicationContext(), ToDoActivity.class));
                        overridePendingTransition(0,0);
                        ab.setTitle("To do");
                        ab.setIcon(R.drawable.ic_to_do);
                        return true;

                    case R.id.profile:
                        startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                        overridePendingTransition(0,0);
                        ab.setTitle("Profile");
                        ab.setIcon(R.drawable.ic_profile);
                        return true;
                }
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.actions_menu, menu);
        for (int i = 0; i < menu.size(); i++) {
            menu.getItem(i).setVisible(false);
            if(menu.getItem(i).getItemId() == R.id.home_calendar)
                menu.getItem(i).setVisible(true);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.home_calendar:
                //goToCalendar(); // TO DO
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initializeMainActivity() throws  JSONException{
        service = new EventService(this);
        eventsList = findViewById(R.id.eventsList);

        JSONObject params = new JSONObject();
        params.put("currentDay", currentDate);

        service.getEventsByDay(MockServerUrl.EVENT_DAY.url, params, new ServerCallback() {
            @Override
            public void onSuccess(JSONObject result) {
                JSONArray containerResponse = new JSONArray();
                try {
                    containerResponse = result.getJSONArray("return");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                for(int i = 0; i < containerResponse.length(); i ++) {
                    JSONObject currentJSONObject = new JSONObject();
                    Button btn = new Button(getApplicationContext());
                    Gson gson = new GsonBuilder()
                            .setDateFormat("dd/MM/yyyy").create();
                    btn.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 300));
                    try {
                        currentJSONObject = containerResponse.getJSONObject(i);
                        Event event = new Event();
                        switch (currentJSONObject.getString("category")) {
                            case "FESTIVITY":
                                break;
                            case "WELLNESS":
                                event = gson.fromJson(currentJSONObject.toString(), EventWellness.class);
                                break;
                            case "STUDY":
                                break;
                            case "FINANCES":
                                event = gson.fromJson(currentJSONObject.toString(), EventFinances.class);
                                break;
                            case "FREE_TIME":
                                break;

                        }

                        Log.d(".MainActivity", event.toString());
                        btn.setText(currentJSONObject.getString("title"));
                        eventsList.addView(btn);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

}