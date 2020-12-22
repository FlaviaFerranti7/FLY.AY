package com.flam.flyay;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.flam.flyay.fragments.EventsListFragment;
import com.flam.flyay.model.Event;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;


public class MainActivity extends AppCompatActivity implements EventsListFragment.OnEventsListListener{
    private Toolbar toolbar;
    private ActionBar ab;
    private Calendar c;
    private SimpleDateFormat df;
    private String currentDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        initializeFragments();

        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.getMenu().getItem(2).setEnabled(false);
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

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }

    private void initializeFragments() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        EventsListFragment eventsListFragment = new EventsListFragment();
        eventsListFragment.setArguments(createParamsEventsFragment());
        fragmentTransaction.add(R.id.fragment_events, eventsListFragment);
        fragmentTransaction.commit();
    }

    private Bundle createParamsEventsFragment() {
        Bundle bundle = new Bundle();
        bundle.putString("currentDate", this.currentDate);

        return bundle;
    }

    @Override
    public void onEventSelected(Event e) {
        Log.d(".MainActivity", e.toString());
        Toast.makeText(getApplicationContext(), e.getTitle() + " event", Toast.LENGTH_SHORT).show();
    }
}