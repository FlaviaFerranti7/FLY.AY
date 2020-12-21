package com.flam.flyay;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.flam.flyay.activities.SignUpActivity;
import com.flam.flyay.util.MockServerUrl;

import org.json.JSONException;
import org.json.JSONObject;

public class LogInActivity extends AppCompatActivity {

    private EditText usernameTextField, passwordTextField;
    private CheckBox rememberMeCheckBox;
    private Button signInButton, signUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //initialize the instance variables
        initializeLayout();

    }

    public void initializeLayout() {
        this.usernameTextField = findViewById(R.id.username);
        this.passwordTextField = findViewById(R.id.password);
        this.rememberMeCheckBox = findViewById(R.id.rememberMe);
        this.signInButton = findViewById(R.id.buttonLogin);
        this.signUpButton = findViewById(R.id.buttonSignUp);

        this.signInButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.d("LogInActivity", "event: login");

                String username = usernameTextField.getText().toString();
                String password = passwordTextField.getText().toString();

                Log.d("LogInActivity","username: " + username + " password: " + password);

                JSONObject params = new JSONObject();
                try {
                    params.put("username", username);
                    params.put("password", password);
                    params.put("rememberMe", rememberMeCheckBox.isChecked());
                } catch (JSONException e) {
                    Log.getStackTraceString(e);
                }

                executeRequestPOST(MockServerUrl.LOGIN_OK.url, params);
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
    }

    public void executeRequestPOST(String url, JSONObject params) {
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
