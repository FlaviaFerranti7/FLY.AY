package com.flam.flyay.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.flam.flyay.R;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {
    private EditText name, username, email, password, confirmPassword;
    private Button signUpButton;

    private final String signUpUrl = "http://localhost:3000/user/signup1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //initialize the instance variables
        initializationLayout();

    }

    public void initializationLayout() {
        this.name = findViewById(R.id.name);
        this.username = findViewById(R.id.username);
        this.email = findViewById(R.id.email);
        this.password = findViewById(R.id.password);
        this.confirmPassword = findViewById(R.id.confirm_password);
        this.signUpButton = findViewById(R.id.buttonSignUp);

        this.signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("SignUpActivity", "event: sign_up");

                String firstname = name.getText().toString();
                String nickname = username.getText().toString();
                String mail = email.getText().toString();
                String pass = password.getText().toString();
                String confpass = confirmPassword.getText().toString();

                Log.d("SignUpActivity", "name: " + firstname + " username: " + nickname + " email: " + mail + " password: " + pass + " confirm password: " + confpass);

                doSignUp(signUpUrl, firstname, nickname, mail, pass, confpass);
            }
        });
    }

    public void doSignUp(String url, final String firstnameInput, final String usernameInput, final String emailInput, final String passwordInput, final String confPasswordInput) {
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
                params.put("name", firstnameInput);
                params.put("username", usernameInput);
                params.put("email", emailInput);
                params.put("password", passwordInput);
                params.put("confirm password", confPasswordInput);

                return params;
            }
        };
        requestQueue.add(postRequest);
    }
}

