package com.flam.flyay;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Build;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;


import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.flam.flyay.activities.SignUpActivity;
import com.flam.flyay.util.MockServerUrl;
import com.flam.flyay.util.TouchInterceptor;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.Objects;

public class LogInActivity extends AppCompatActivity {

    private TextInputLayout usernameLayout;
    private TextInputEditText usernameTextField;
    private TextInputLayout passwordLayout;
    private TextInputEditText passwordTextField;
    private CheckBox rememberMeCheckBox;
    private Button signInButton, signUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //initialize the instance variables
        initializeLayout();

    }

    @SuppressLint("ClickableViewAccessibility")
    public void initializeLayout() {

        RelativeLayout touchInterceptor = (RelativeLayout) findViewById(R.id.touchInterceptor);
        touchInterceptor.setOnTouchListener(new TouchInterceptor(this));

        this.usernameLayout = (TextInputLayout) findViewById(R.id.usernameLayout);
        this.usernameTextField = (TextInputEditText) findViewById(R.id.username);
        this.passwordLayout = (TextInputLayout) findViewById(R.id.passwordLayout);
        this.passwordTextField = (TextInputEditText) findViewById(R.id.password);
        this.rememberMeCheckBox = findViewById(R.id.rememberMe);
        this.signInButton = findViewById(R.id.buttonLogin);
        this.signUpButton = findViewById(R.id.buttonSignUp);

        this.signInButton.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                Log.d("LogInActivity", "event: login");

                String username = Objects.requireNonNull(usernameTextField.getText()).toString();
                String password = Objects.requireNonNull(passwordTextField.getText()).toString();

                Log.d("LogInActivity","username: " + username + " password: " + password);

                if(username.length() == 0) {
                    usernameLayout.setErrorEnabled(true);
                    usernameLayout.setError(getString(R.string.username_null_error));
                    passwordLayout.clearFocus();
                    return;
                }
                if(password.length() == 0) {
                    passwordLayout.setErrorEnabled(true);
                    passwordLayout.setError(getString(R.string.password_null_error));
                    usernameLayout.clearFocus();
                    return;
                }

                JSONObject params = new JSONObject();
                try {
                    params.put("username", username);
                    params.put("password", password);
                    params.put("rememberMe", rememberMeCheckBox.isChecked());
                } catch (JSONException e) {
                    Log.getStackTraceString(e);
                }

                signin(MockServerUrl.SIGNIN_OK.url, params);
            }
        });

        this.signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("SignUpActivity", "event: sign_up");
                Intent intent = new Intent(LogInActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        this.usernameTextField.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!usernameTextField.getText().toString().isEmpty())
                    usernameLayout.setErrorEnabled(false);
            }
        });

        this.passwordTextField.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!passwordTextField.getText().toString().isEmpty())
                    passwordLayout.setErrorEnabled(false);
            }
        });
    }

    public void signin(String url, JSONObject params) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, url, params,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        JSONObject containerResponse = new JSONObject();
                        String status = "";
                        try {
                            containerResponse = response.getJSONObject("return");
                            status = containerResponse.getString("status");

                            Log.d("response: ", status);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        if(status.equalsIgnoreCase("OK")) {
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        }
                        else {
                            try {
                                Toast.makeText(getApplicationContext(),
                                        containerResponse.getString("message"),
                                        Toast.LENGTH_SHORT)
                                        .show();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Error.Response", error.toString());
                        Toast.makeText(getApplicationContext(),"Error server connection",Toast.LENGTH_SHORT).show();
                    }
                }
        );

        requestQueue.add(postRequest);
    }
}
