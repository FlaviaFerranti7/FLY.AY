package com.flam.flyay;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.flam.flyay.fragments.CalendarFragment;
import com.flam.flyay.fragments.EventDetailsFragment;
import com.flam.flyay.fragments.HomeFragment;
import com.flam.flyay.model.Event;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;
import java.util.TimeZone;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.WHITE;


public class MainActivity extends AppCompatActivity implements HomeFragment.OnEventsListListener{
    private Toolbar toolbar;
    private ActionBar ab;
    private Calendar c;
    private SimpleDateFormat df;
    private String currentDate;
    private BottomNavigationView navView;

    private Event eventSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            setTheme(R.style.DarkAppTheme);
        } else {
            setTheme(R.style.AppTheme);
        }
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ab = getSupportActionBar();
        // To display the arrow that goes back
        // ab.setDisplayHomeAsUpEnabled(true);

        c = Calendar.getInstance(TimeZone.getTimeZone("GMT"),Locale.getDefault());
        df = new SimpleDateFormat("dd/MM/yyyy");
        currentDate = df.format(c.getTime());

        navView = findViewById(R.id.nav_view);
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            navView.setBackgroundColor(BLACK);
            toolbar.setBackgroundColor(BLACK);
        }
        else{
            navView.setBackgroundColor(WHITE);
            toolbar.setBackgroundColor(WHITE);
        }
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
                        return true;

                    case R.id.plus:
                        startActivity(new Intent(getApplicationContext(), AddEventActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.list:
                        startActivity(new Intent(getApplicationContext(), ToDoActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.profile:
                        startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
        setFragmentMarginBottom(R.id.fragment_container);
        addFragment(new HomeFragment(), createParamsEventsFragment());
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        Log.d(".ActivityMain", "onCreateOptionsMenu");

        MenuInflater inflater = getMenuInflater();

        Fragment calendarFragment = (Fragment) getSupportFragmentManager()
                .findFragmentByTag(CalendarFragment.class.getName());
        Fragment fragmentInFrame = (Fragment) getSupportFragmentManager()
                .findFragmentByTag(EventDetailsFragment.class.getName());


        if(fragmentInFrame == null && calendarFragment == null) {
            Log.d(".ActivityMain", "setting home calendar option on toolbar");
            inflater.inflate(R.menu.actions_menu, menu);

            for (int i = 0; i < menu.size(); i++) {
                if(menu.getItem(i).getItemId() != R.id.home_calendar)
                    menu.getItem(i).setVisible(false);
            }
        } else if (fragmentInFrame != null) {
            Log.d(".ActivityMain", "setting event details actions on toolbar");
            inflater.inflate(R.menu.event_details_actions, menu);
        }

        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.home_calendar:
                addFragment(new CalendarFragment(), createParamsEventsFragment());
                Toast.makeText(this.getApplicationContext(),"Go to Calendar",Toast.LENGTH_SHORT).show();
                break;
            case R.id.edit_event:
                //TODO: go to form add event
                Toast.makeText(this.getApplicationContext(),"Go to Form Add Event",Toast.LENGTH_SHORT).show();
                break;
            case R.id.delete_event:
                //TODO: integrate pop-up to confirm
                Toast.makeText(this.getApplicationContext(),"Pop-up to Confirm",Toast.LENGTH_SHORT).show();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }

    private Bundle createParamsEventsFragment() {
        Bundle bundle = new Bundle();
        bundle.putString("currentDate", this.currentDate);

        return bundle;
    }

    private int getNavigationBarHeight() {
        int resourceId = getResources().getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            return getResources().getDimensionPixelSize(resourceId);
        }
        return 0;
    }

    private Bundle createParamsFragment(Event event) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("event", event);

        getSupportActionBar().setTitle(event.getTitle());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setIcon(null);

        return bundle;
    }


    private void setFragmentMarginBottom(int id) {
        final FrameLayout frameLayout = findViewById(id);

        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) frameLayout.getLayoutParams();
        params.setMargins(0, 0, 0, getNavigationBarHeight());
        frameLayout.setLayoutParams(params);

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onEventSelected(Event e) {
        Log.d(".MainActivity", e.toString());
        Objects.requireNonNull(getSupportActionBar()).setTitle(e.getTitle());
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Objects.requireNonNull(getSupportActionBar()).setIcon(null);
        invalidateOptionsMenu();
        addFragment(new EventDetailsFragment(), createParamsFragment(e));
    }

    public void addFragment(Fragment fragment, Bundle params) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        fragment.setArguments(params);
        transaction.replace(R.id.fragment_container, fragment, fragment.getClass().getName());
        Log.d("String fragment: ", fragment.getClass().getName());
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {

        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > 1) {
            fragmentManager.popBackStackImmediate();
        } else {
            super.onBackPressed();
        }
    }

}