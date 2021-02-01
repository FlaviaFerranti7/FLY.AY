package com.flam.flyay;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.flam.flyay.fragments.CalendarFragment;
import com.flam.flyay.fragments.EventDetailsFragment;
import com.flam.flyay.fragments.SearchFormFragment;
import com.flam.flyay.fragments.SearchResultsFragment;
import com.flam.flyay.model.Event;
import com.flam.flyay.util.TouchInterceptor;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Objects;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.WHITE;


public class SearchActivity extends AppCompatActivity implements SearchResultsFragment.OnEventsListListener {

    private Toolbar toolbar;
    private ActionBar ab;

    private Event eventSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            setTheme(R.style.DarkAppTheme);
        } else {
            setTheme(R.style.AppTheme);
        }
        setContentView(R.layout.activity_search);

        RelativeLayout touchInterceptor = (RelativeLayout) findViewById(R.id.touchInterceptor);
        touchInterceptor.setOnTouchListener(new TouchInterceptor(this));

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
        navView.setSelectedItemId(R.id.lens);
        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.lens:
                        return true;

                    case R.id.plus:
                        startActivity(new Intent(getApplicationContext(), AddEventActivity.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.list:
                        startActivity(new Intent(getApplicationContext(), ToDoActivity.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.profile:
                        startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });

        addFragment(new SearchFormFragment(), null);
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

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        Log.d(".ActivitySearch", "onCreateOptionsMenu");

        MenuInflater inflater = getMenuInflater();

        Fragment fragmentInFrame = (Fragment) getSupportFragmentManager()
                .findFragmentByTag(EventDetailsFragment.class.getName());


        if(fragmentInFrame != null) {
            Log.d(".ActivitySearch", "setting event details actions on toolbar");
            inflater.inflate(R.menu.event_details_actions, menu);
        }

        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final Fragment fragmentInFrame = (Fragment) getSupportFragmentManager()
                .findFragmentByTag(EventDetailsFragment.class.getName());


        switch (item.getItemId()) {
            case R.id.edit_event:
                //TODO: go to form add event
                if(fragmentInFrame != null) {
                    Intent intent = new Intent(fragmentInFrame.getContext(), AddEventActivity.class);
                    Bundle b = new Bundle();
                    Log.d(".MainActivity", "event selected: " + eventSelected.toString());
                    b.putSerializable("eventEditable", eventSelected);
                    intent.putExtras(b);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(this.getApplicationContext(),"No selectable event",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.delete_event:
                AlertDialog.Builder alertbox = new AlertDialog.Builder(this);
                alertbox.setTitle("Delete event");
                alertbox.setMessage("Do you want to delete the new event?");

                alertbox.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(fragmentInFrame.getContext(), MainActivity.class);
                        startActivity(intent);
                    }
                });
                alertbox.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

                alertbox.setIcon(R.drawable.ic_delete);
                alertbox.show();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }

    private Bundle createParamsFragment(Event event) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("event", event);

        getSupportActionBar().setTitle(event.getTitle());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setIcon(null);

        return bundle;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onEventSelected(Event e) {
        Log.d(".SearchActivity", e.toString());
        Objects.requireNonNull(getSupportActionBar()).setTitle(e.getTitle());
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Objects.requireNonNull(getSupportActionBar()).setIcon(null);
        invalidateOptionsMenu();
        eventSelected = e;
        addFragment(new EventDetailsFragment(), createParamsFragment(e));
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