package com.flam.flyay;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private EditText username, password;
    private CheckBox rememberMe;
    private Button loginButton;

    private final String loginUrl = "http://localhost:3000/signin";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialize the instance variables
        initializationLayout();

    }

    public void initializationLayout() {
        this.username = findViewById(R.id.username);
        this.password = findViewById(R.id.password);
        this.rememberMe = findViewById(R.id.rememberMe);
        this.loginButton = findViewById(R.id.buttonLogin);

        this.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("MainActivity", "event: login");

                String nickname = username.getText().toString();
                String pass = password.getText().toString();

                Log.d("MainActivity","username: " + nickname + " password: " + pass);

                if(rememberMe.isChecked()) {
                    Log.d("MainActivity", "remember username and password");
                }

                doLogin(loginUrl, nickname, pass);
            }
        });
    }

    public void doLogin(String url, final String usernameInput, final String passwordInput) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Response", response);

                        //TODO: redirect home page
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Error.Response", error.getMessage());

                        //TODO: manage error (popup or alert text?)
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<>();
                params.put("username", usernameInput);
                params.put("password", passwordInput);

                return params;
            }
        };
        requestQueue.add(postRequest);
    }
}
