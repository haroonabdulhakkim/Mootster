package com.example.ha294221.mootster;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.ha294221.mootster.app.AppConfig;
import com.example.ha294221.mootster.app.AppController;
import com.example.ha294221.mootster.helper.JSONParser;
import com.example.ha294221.mootster.helper.SQLiteHandler;
import com.example.ha294221.mootster.helper.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by HA294221 on 8/8/2015.
 */
public class LoginActivity extends AppCompatActivity{
    // LogCat tag
    private static final String TAG = LoginActivity.class.getSimpleName();
    private static final String MY_PREFS_NAME = "LoginCredentials";
    private Button btnLogin;
    private Button btnLinkToRegister;
    private EditText inputEmail;
    private EditText inputPassword;
    private ProgressDialog pDialog;
    private SQLiteHandler db;
    private SessionManager session;
    JSONParser jsonParser = new JSONParser();

    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Setting status bar color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.color_secondary));
        }

        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLinkToRegister = (Button) findViewById(R.id.btnLinkToRegisterScreen);

        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String prefUsername = prefs.getString("username", null);
        String prefPassword = prefs.getString("password",null);
        if (prefUsername != null && prefPassword != null )
        {
            inputEmail.setText(prefUsername);
            inputPassword.setText(prefPassword);
        }

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        // Session manager
        session = new SessionManager(getApplicationContext());
        db = new SQLiteHandler(getApplicationContext());
        // Check if user is already logged in or not
        session.setLogin(true);

        db.addUser("Haroon", "H", "8089574308", "IIT M", "har1@gmail.com", 1, "001", "27/09/2015");
        if (session.isLoggedIn()) {
            // User is already logged in. Take him to main activity
            HashMap<String, Object> user = db.getUserDetails();
            Boolean isadmin = (user.get("isadmin")==1);
            if(isadmin){
                Intent intent = new Intent(LoginActivity.this,
                        AdminMainActivity.class);
                startActivity(intent);
                finish();
            } else {
                Intent intent = new Intent(LoginActivity.this, UserMainActivity.class);
                startActivity(intent);
                finish();
            }
        }

        // Login button Click Event
        btnLogin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                        String email = inputEmail.getText().toString();
                        String password = inputPassword.getText().toString();
                        // Check for empty data in the form
                        if (email.trim().length() > 0 && password.trim().length() > 0) {

                            SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                            editor.putString("username", email);
                            editor.putString("password", password);
                            editor.commit();

                            // login user
                            checkLogin(email, password);

                        } else {
                            // Prompt user to enter credentials
                            Toast.makeText(getApplicationContext(),
                                    "Please enter the credentials!", Toast.LENGTH_LONG)
                                    .show();
                        }



            }

        });

        // Link to Register Screen
        btnLinkToRegister.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),
                        UserRoleActivity.class);
                startActivity(i);
            }
        });

    }

    /**
     * function to verify login details in mysql db
     * */
    private void checkLogin(final String email, final String password) {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        pDialog.setMessage("Logging in ...");
        showDialog();


        JSONObject reqJson = new JSONObject();
        try {
            reqJson.put("tag", "login");
            reqJson.put("email", email);
            reqJson.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "Request Json: " + reqJson.toString());
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST,
                AppConfig.URL_LOGIN, reqJson, new Response.Listener<JSONObject>() {


            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, "Login Response: " + response);
                hideDialog();

                try {


                    boolean error = response.getBoolean("error");
                    // Check for error node in json
                    if (!error) {
                        // user successfully logged in
                        // Create login session
                        session.setLogin(true);
                        String uid = response.getString("uid");
                        JSONObject user = response.getJSONObject("user");
                        String fname = user.getString("fname");
                        String lname = user.getString("lname");
                        String contact = user.getString("contact");
                        String college = user.getString("college");
                        String email = user.getString("email");
                        Boolean isadmin = "1".equals(user.getString("isadmin"));
                        String created_at = user.getString("created_at");

                        int role= (isadmin)?1:0; // 1 - Admin , 0 - user
                        db.addUser(fname, lname, contact, college, email, role, uid, created_at);
                        // Launch main activity
                        if(isadmin){
                            Intent intent = new Intent(LoginActivity.this,
                                    AdminMainActivity.class);
                            startActivity(intent);
                            finish();
                        }else {
                            Intent intent = new Intent(LoginActivity.this,
                                    UserMainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    } else {
                        // Error in login. Get the error message
                        String errorMsg = response.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    Log.e(TAG,Log.getStackTraceString(e));
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                }

            }

        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }

        });

        Log.d(TAG, "JSON request: " + req.toString());
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(req, tag_string_req);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

}