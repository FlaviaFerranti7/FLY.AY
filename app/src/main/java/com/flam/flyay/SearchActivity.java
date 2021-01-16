package com.flam.flyay;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.ToggleButton;

import com.flam.flyay.fragments.EventDetailsFragment;
import com.flam.flyay.fragments.SearchFormFragment;
import com.flam.flyay.model.Event;
import com.flam.flyay.util.CategoryEnum;
import com.flam.flyay.util.TouchInterceptor;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

import static com.flam.flyay.util.CategoryEnum.FESTIVITY;
import static com.flam.flyay.util.CategoryEnum.FINANCES;
import static com.flam.flyay.util.CategoryEnum.FREE_TIME;
import static com.flam.flyay.util.CategoryEnum.STUDY;
import static com.flam.flyay.util.CategoryEnum.WELLNESS;

public class SearchActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ActionBar ab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        initializeLayout();
        addFragment(new SearchFormFragment(), null);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ab = getSupportActionBar();
        ab.setTitle("Search");
        ab.setIcon(R.drawable.ic_search);

        BottomNavigationView navView = findViewById(R.id.nav_view);
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

    }

    public void initializeLayout() {

        RelativeLayout touchInterceptor = (RelativeLayout) findViewById(R.id.touchInterceptor);
        touchInterceptor.setOnTouchListener(new TouchInterceptor(this));

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

    /*private Bundle createParamsEventsFragment() {
        Bundle bundle = new Bundle();
        bundle.putString("searchParamsName", searchName);
        bundle.putString("searchParamsPlace", searchPlace);
        bundle.putString("checkedCategory", String.valueOf(checkedCategoryList));
        return bundle;
    }*/

    private Bundle createParamsFragment(Event event) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("event", event);

        return bundle;
    }


    /*@RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onEventSelected(Event e) {
        Log.d(".MainActivity", e.toString());

        addFragment(new EventDetailsFragment(), createParamsFragment(e));
    }*/

    public void addFragment(Fragment fragment, Bundle params) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        fragment.setArguments(params);
        transaction.replace(R.id.fragment_container, fragment, fragment.getClass().getName());
        transaction.addToBackStack(null);
        transaction.commit();
    }
}