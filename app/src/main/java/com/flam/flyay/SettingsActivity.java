package com.flam.flyay;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NavUtils;
import androidx.preference.PreferenceFragmentCompat;

import com.flam.flyay.util.TouchInterceptor;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class SettingsActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ActionBar ab;
    private CheckBox plan_day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            setTheme(R.style.DarkAppTheme);
        } else {
            setTheme(R.style.AppTheme);
        }
        setContentView(R.layout.activity_settings);

        //RelativeLayout touchInterceptor = (RelativeLayout) findViewById(R.id.touchInterceptor);
        //touchInterceptor.setOnTouchListener(new TouchInterceptor(this));

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ab = getSupportActionBar();
        ab.setTitle("Settings");
        ab.setDisplayHomeAsUpEnabled(true);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setSelectedItemId(R.id.profile);
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
                        startActivity(new Intent(getApplicationContext(), AddEventActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.list:
                        startActivity(new Intent(getApplicationContext(), ToDoActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.profile:
                        return true;
                }
                return false;
            }
        });

        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            setTheme(R.style.DarkAppTheme);
        }

        Button changeThemeBtn = findViewById(R.id.changeThemeBtn);

        changeThemeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                }

                finish();
                startActivity(new Intent(SettingsActivity.this, SettingsActivity.this.getClass()));
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}