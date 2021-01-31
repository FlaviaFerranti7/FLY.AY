package com.flam.flyay;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.flam.flyay.fragments.AddEventFormFragment;
import com.flam.flyay.fragments.EventDetailsFragment;
import com.flam.flyay.fragments.SearchFormFragment;
import com.flam.flyay.fragments.SearchResultsFragment;
import com.flam.flyay.model.Event;
import com.flam.flyay.util.TouchInterceptor;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.WHITE;

public class AddEventActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ActionBar ab;

    private Event eventEditable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            setTheme(R.style.DarkAppTheme);
        } else {
            setTheme(R.style.AppTheme);
        }
        setContentView(R.layout.activity_add_event);

        RelativeLayout touchInterceptor = (RelativeLayout) findViewById(R.id.touchInterceptorAddEvent);
        touchInterceptor.setOnTouchListener(new TouchInterceptor(this));

        Bundle arguments = getIntent().getExtras();
        if(arguments != null)
            eventEditable = (Event) arguments.getSerializable("eventEditable");
        else
            Log.d(".AddEventActivity", "no parameters");

        Log.d(".AddEventActivity", "event editable: " + eventEditable);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ab = getSupportActionBar();

        BottomNavigationView navView = findViewById(R.id.nav_view);
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            navView.setBackgroundColor(BLACK);
            toolbar.setBackgroundColor(BLACK);
        }
        else{
            navView.setBackgroundColor(WHITE);
            toolbar.setBackgroundColor(WHITE);
        }
        navView.setSelectedItemId(R.id.plus);
        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem){
                switch (menuItem.getItemId()){
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.lens:
                        startActivity(new Intent(getApplicationContext(), SearchActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.plus:
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

        addFragment(new AddEventFormFragment(), null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.actions_menu, menu);
        for (int i = 0; i < menu.size(); i++) {
            menu.getItem(i).setVisible(false);
        }
        return true;
    }


    public void addFragment(Fragment fragment, Bundle params) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        fragment.setArguments(params);
        transaction.replace(R.id.fragment_container, fragment, fragment.getClass().getName());
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