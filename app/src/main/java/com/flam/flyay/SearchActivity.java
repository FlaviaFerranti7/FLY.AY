package com.flam.flyay;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.ToggleButton;

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

    private TextInputLayout eventNameLayout;
    private TextInputEditText eventNameTextField;
    private TextInputLayout eventPlaceLayout;
    private TextInputEditText eventPlaceTextField;
    private Button searchButton;

    private MaterialButtonToggleGroup toggleCategories;

    private String searchName;
    private String searchPlace;

    private String checkedCategory;
    private List<String> checkedCategoryList;

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

        this.checkedCategoryList = new ArrayList<>();

        RelativeLayout touchInterceptor = (RelativeLayout) findViewById(R.id.touchInterceptor);
        touchInterceptor.setOnTouchListener(new TouchInterceptor(this));

        this.eventNameLayout = (TextInputLayout) findViewById(R.id.whichEventLayout);
        this.eventNameTextField = (TextInputEditText) findViewById(R.id.whichEvent);

        this.eventPlaceLayout = (TextInputLayout) findViewById(R.id.whereEventLayout);
        this.eventPlaceTextField = (TextInputEditText) findViewById(R.id.whereEvent);

        this.searchButton = findViewById(R.id.buttonSearch);
        this.toggleCategories = findViewById(R.id.toggleButtonCategories);

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

        this.toggleCategories.addOnButtonCheckedListener(new MaterialButtonToggleGroup.OnButtonCheckedListener() {
            @Override
            public void onButtonChecked(MaterialButtonToggleGroup group, int checkedId, boolean isChecked) {
                if (isChecked) {
                    if(checkedId == R.id.festivityCategoryButton) {
                        checkedCategory = FESTIVITY.name;
                        checkedCategoryList.add(checkedCategory);
                        Log.d(".SearchActivity", "selected category: " + checkedCategory);
                        Log.d(".SearchActivity", "selected category: " + checkedCategoryList);
                    } else if(checkedId == R.id.financesCategoryButton) {
                        checkedCategory = FINANCES.name;
                        checkedCategoryList.add(checkedCategory);
                        Log.d(".SearchActivity", "selected category: " + checkedCategory);
                        Log.d(".SearchActivity", "selected category: " + checkedCategoryList);
                    } else if(checkedId == R.id.freeTimeCategoryButton) {
                        checkedCategory = FREE_TIME.name;
                        checkedCategoryList.add(checkedCategory);
                        Log.d(".SearchActivity", "selected category: " + checkedCategory);
                        Log.d(".SearchActivity", "selected category: " + checkedCategoryList);
                    } else if(checkedId == R.id.studyCategoryButton) {
                        checkedCategory = STUDY.name;
                        checkedCategoryList.add(checkedCategory);
                        Log.d(".SearchActivity", "selected category: " + checkedCategory);
                        Log.d(".SearchActivity", "selected category: " + checkedCategoryList);
                    } else if(checkedId == R.id.wellnessCategoryButton) {
                        checkedCategory = WELLNESS.name;
                        checkedCategoryList.add(checkedCategory);
                        Log.d(".SearchActivity", "selected category: " + checkedCategory);
                        Log.d(".SearchActivity", "selected category: " + checkedCategoryList);
                    }
                }
                Intent intent = new Intent(SearchActivity.this, SearchResults.class);
                intent.putExtras(createParamsEventsFragment());
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
        bundle.putString("checkedCategory", String.valueOf(checkedCategoryList));
        return bundle;
    }
}