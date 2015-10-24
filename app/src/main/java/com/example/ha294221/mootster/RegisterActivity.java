package com.example.ha294221.mootster;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.ha294221.mootster.app.AppConfig;
import com.example.ha294221.mootster.app.AppController;
import com.example.ha294221.mootster.helper.SQLiteHandler;
import com.example.ha294221.mootster.helper.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

/**
 * Created by HA294221 on 8/8/2015.
 */
public class RegisterActivity extends Activity{
    private static final String TAG = RegisterActivity.class.getSimpleName();
    private Button btnRegister;
    private Button btnLinkToLogin;
    private EditText inputFirstName;
    private EditText inputLastName;
    private EditText inputContact;
    private EditText inputCollege;
    private EditText inputEmail;
    private EditText inputPassword;
    private ImageButton calendarDobReg;
    private TextView dobReg;

    private ProgressDialog pDialog;
    private SessionManager session;
    private SQLiteHandler db;
    private Calendar myCalendar = Calendar.getInstance();

    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateDisplay();
        }

    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Setting status bar color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.color_secondary));
        }

        inputFirstName = (EditText) findViewById(R.id.fname);
        inputLastName = (EditText) findViewById(R.id.lname);
        inputContact = (EditText) findViewById(R.id.contact);
        dobReg = (EditText) findViewById(R.id.dobReg);
        calendarDobReg = (ImageButton) findViewById(R.id.calendarDobReg);
        inputCollege = (EditText) findViewById(R.id.college);
        inputPassword = (EditText) findViewById(R.id.password);
        inputEmail = (EditText) findViewById(R.id.email);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnLinkToLogin = (Button) findViewById(R.id.btnLinkToLoginScreen);


        //Making Date of Birth not editable
        dobReg.setKeyListener(null);

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(true);

        // Session manager
        session = new SessionManager(getApplicationContext());

        // SQLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // Check if user is already logged in or not
        if (session.isLoggedIn()) {
            // User is already logged in. Take him to main activity
            Intent intent = new Intent(RegisterActivity.this,
                    UserMainActivity.class);
            startActivity(intent);
            finish();
        }

        // Calendar Button Click Event
        calendarDobReg.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                // On button click show datepicker dialog
                new DatePickerDialog(RegisterActivity.this , date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        // Register Button Click event
        btnRegister.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String fname = inputFirstName.getText().toString();
                String lname = inputLastName.getText().toString();
                String contact = inputContact.getText().toString();
                String dob = dobReg.getText().toString();
                String college = inputCollege.getText().toString();
                String email = inputEmail.getText().toString();
                String password = inputPassword.getText().toString();

                if (!fname.isEmpty() && !email.isEmpty() && !password.isEmpty() && !lname.isEmpty() && !contact.isEmpty() && !dob.isEmpty() && !college.isEmpty()) {
                    registerUser(fname, lname, contact, dob, college, email, password);
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Please enter your details!", Toast.LENGTH_LONG)
                            .show();
                }
            }
        });

        // Link to Login Screen
        btnLinkToLogin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),
                        LoginActivity.class);
                startActivity(i);
                finish();
            }
        });

    }

    /**
     * Function to store user in MySQL database will post params(tag, name,
     * email, password) to register url
     * */
    private void registerUser(final String fname, final String lname, final String contact, final String dob, final String college, final String email,
                              final String password) {
        // Tag used to cancel the request
        String tag_string_req = "req_register_user";

        pDialog.setMessage("Registering ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_REGISTER, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Register Response: " + response);
                hideDialog();

                try {

                    JSONObject jObj = new JSONObject(response);

                    boolean error = jObj.getBoolean("error");
                    if (!error) {
                        // User successfully stored in MySQL
                        // Now store the user in sqlite
                        Toast.makeText(getApplicationContext(),
                                "User Registered Successfully!", Toast.LENGTH_SHORT)
                                .show();

                        String uid = jObj.getString("uid");
                        JSONObject user = jObj.getJSONObject("user");
                        String fname = user.getString("fname");
                        String lname = user.getString("lname");
                        String contact = user.getString("contact");
                        String college = user.getString("college");
                        String email = user.getString("email");
                        String created_at = user.getString("created_at");
                        // Inserting row in users table
                        //db.addUser(fname, lname, contact, college, email, uid, created_at);

                        // Launch login activity
                        Intent intent = new Intent(
                                RegisterActivity.this,
                                LoginActivity.class);
                        startActivity(intent);
                        finish();
                    } else {

                        // Error occurred in registration. Get the error
                        // message
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    Log.e(TAG,Log.getStackTraceString(e));
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "User Registration Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {



        };
        JSONObject reqJson = new JSONObject();
        try {
            reqJson.put("tag", "register");
            reqJson.put("fname", fname);
            reqJson.put("lname", lname);
            reqJson.put("contact", contact);
            reqJson.put("college", college);
            reqJson.put("email", email);
            reqJson.put("password", password);
            reqJson.put("dob", dob);
            reqJson.put("isadmin", false);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST,
                AppConfig.URL_REGISTER, reqJson, new Response.Listener<JSONObject>() {


            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, "Register User Response: " + response);
                hideDialog();

                try {
                    boolean error = response.getBoolean("error");
                    if (!error) {
                        // User successfully stored in MySQL
                        // Now store the user in sqlite
                        Toast.makeText(getApplicationContext(),
                                "User Registered Successfully!", Toast.LENGTH_SHORT)
                                .show();

                        String uid = response.getString("uid");
                        JSONObject user = response.getJSONObject("user");
                        String fname = user.getString("fname");
                        String lname = user.getString("lname");
                        String contact = user.getString("contact");
                        String college = user.getString("college");
                        String email = user.getString("email");
                        String created_at = user.getString("created_at");
                        // Inserting row in users table
                        //db.addUser(fname, lname, contact, college, email, uid, created_at);

                        // Launch login activity
                        Intent intent = new Intent(
                                RegisterActivity.this,
                                LoginActivity.class);
                        startActivity(intent);
                        finish();
                    } else {

                        // Error occurred in registration. Get the error
                        // message
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
                Log.e(TAG, "User Registration Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }

        });


        Log.v(TAG, "JSON request: " + req.toString());
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    private void updateDisplay() {

        dobReg.setText(new StringBuilder()
                // Month is 0 based so add 1
                .append(myCalendar.get(Calendar.DATE)).append("/").append(myCalendar.get(Calendar.MONTH) + 1).append("/").append(myCalendar.get(Calendar.YEAR)).append(" "));
    }
}
