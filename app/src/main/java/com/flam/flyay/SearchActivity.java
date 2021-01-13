package com.flam.flyay;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.Toast;

import com.flam.flyay.activities.SignUpActivity;
import com.flam.flyay.adapter.EventAdapter;
import com.flam.flyay.fragments.EventsListFragment;
import com.flam.flyay.fragments.SearchResultsFragment;
import com.flam.flyay.model.Event;
import com.flam.flyay.model.StatusResponse;
import com.flam.flyay.services.EventService;
import com.flam.flyay.services.ServerCallback;
import com.flam.flyay.services.UserService;
import com.flam.flyay.util.TouchInterceptor;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SearchActivity extends AppCompatActivity {

    private TextInputLayout eventNameLayout;
    private TextInputEditText eventNameTextField;
    private TextInputLayout eventPlaceLayout;
    private TextInputEditText eventPlaceTextField;
    private Button searchButton;

    private String searchName;
    private String searchPlace;

    private Toolbar toolbar;
    private ActionBar ab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        initializeLayout();

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

        this.eventNameLayout = (TextInputLayout) findViewById(R.id.whichEventLayout);
        this.eventNameTextField = (TextInputEditText) findViewById(R.id.whichEvent);

        this.eventPlaceLayout = (TextInputLayout) findViewById(R.id.whereEventLayout);
        this.eventPlaceTextField = (TextInputEditText) findViewById(R.id.whereEvent);

        this.searchButton = findViewById(R.id.buttonSearch);

        this.searchButton.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {

                searchName = eventNameTextField.getText().toString();
                searchPlace = eventPlaceTextField.getText().toString();

                Intent intent = new Intent(SearchActivity.this, SearchResults.class);
                intent.putExtras(createParamsEventsFragment());
                startActivity(intent);
            }
        });
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



    private Bundle createParamsEventsFragment() {
        Bundle bundle = new Bundle();
        bundle.putString("searchParamsName", searchName);
        bundle.putString("searchParamsPlace", searchPlace);
        return bundle;
    }

}