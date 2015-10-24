package com.example.ha294221.mootster;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.ha294221.mootster.app.AppConfig;
import com.example.ha294221.mootster.app.AppController;
import com.example.ha294221.mootster.helper.SQLiteHandler;
import com.example.ha294221.mootster.helper.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;


public class RegisterAdminActivity extends AppCompatActivity {

    private static final String TAG = RegisterActivity.class.getSimpleName();
    private String role;
    private Button btnRegisterAdmin;
    private Button btnRegisterAdminToLoginScreen;

    private EditText inputFnameAdmin;
    private EditText inputLnameAdmin;
    private EditText inputContactAdmin;
    private EditText inputDobAdmin;
    private EditText inputCollegeAdmin;
    private ImageButton calendarDob;
    private EditText inputFacultyIncharge;
    private EditText inputFacultyContact;
    private EditText inputEmailAdmin;
    private EditText inputPasswordAdmin;

    Calendar myCalendar = Calendar.getInstance();

    private ProgressDialog pDialog;
    private SessionManager session;
    private SQLiteHandler db;

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_admin);

        //Setting status bar color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.color_secondary));
        }

        inputFnameAdmin = (EditText) findViewById(R.id.fnameAdmin);
        inputLnameAdmin = (EditText) findViewById(R.id.lnameAdmin);
        inputContactAdmin = (EditText) findViewById(R.id.contactAdmin);
        inputDobAdmin = (EditText) findViewById(R.id.dobAdmin);
        calendarDob = (ImageButton) findViewById(R.id.calendarDob);
        inputCollegeAdmin = (EditText) findViewById(R.id.collegeAdmin);
        inputFacultyIncharge = (EditText) findViewById(R.id.facultyIncharge);
        inputFacultyContact = (EditText) findViewById(R.id.facultyContact);
        inputEmailAdmin = (EditText) findViewById(R.id.emailAdmin);
        inputPasswordAdmin = (EditText) findViewById(R.id.passwordAdmin);

        btnRegisterAdmin = (Button) findViewById(R.id.btnRegisterAdmin);
        btnRegisterAdminToLoginScreen = (Button) findViewById(R.id.btnRegisterAdminToLoginScreen);

        //Making Date of Birth not editable
        inputDobAdmin.setKeyListener(null);

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
            Intent intent = new Intent(this,
                    UserMainActivity.class);
            startActivity(intent);
            finish();
        }




        calendarDob.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                // On button click show datepicker dialog
                new DatePickerDialog(RegisterAdminActivity.this , date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        // Register Button Click event
        btnRegisterAdmin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String fnameAdmin = inputFnameAdmin.getText().toString();
                String lnameAdmin = inputLnameAdmin.getText().toString();
                String contactAdmin = inputContactAdmin.getText().toString();
                String dobAdmin = inputDobAdmin.getText().toString();
                String collegeAdmin = inputCollegeAdmin.getText().toString();
                String facultyIncharge = inputFacultyIncharge.getText().toString();
                String facultyContact = inputFacultyContact.getText().toString();
                String emailAdmin = inputEmailAdmin.getText().toString();
                String passwordAdmin = inputPasswordAdmin.getText().toString();

                if (!fnameAdmin.isEmpty() && !emailAdmin.isEmpty() && !passwordAdmin.isEmpty() && !lnameAdmin.isEmpty() && !contactAdmin.isEmpty() && !dobAdmin.isEmpty() && !collegeAdmin.isEmpty() && !facultyIncharge.isEmpty() && !facultyContact.isEmpty()) {
                    registerAdmin(fnameAdmin, lnameAdmin, contactAdmin, dobAdmin, collegeAdmin, emailAdmin, passwordAdmin);
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Please enter your details!", Toast.LENGTH_LONG)
                            .show();
                }
            }
        });

        // Link to Login Screen
        btnRegisterAdminToLoginScreen.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),
                        LoginActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    private void updateDisplay() {

        inputDobAdmin.setText(new StringBuilder()
                // Month is 0 based so add 1
                .append(myCalendar.get(Calendar.DATE)).append("/").append(myCalendar.get(Calendar.MONTH) + 1).append("/").append(myCalendar.get(Calendar.YEAR)).append(" "));
    }

    /**
     * Function to store user in MySQL database will post params(tag, name,
     * email, password) to register url
     * */
    private void registerAdmin(final String fnameAdmin, final String lnameAdmin, final String contactAdmin, final String dobAdmin, final String collegeAdmin, final String emailAdmin,
                              final String passwordAdmin)  {
        // Tag used to cancel the request
        String tag_string_req = "req_register_admin";

        pDialog.setMessage("Registering ...");
        showDialog();

        JSONObject reqJson = new JSONObject();
        try {
            reqJson.put("tag", "register");
            reqJson.put("fname", fnameAdmin);
            reqJson.put("lname", lnameAdmin);
            reqJson.put("contact", contactAdmin);
            reqJson.put("college", collegeAdmin);
            reqJson.put("email", emailAdmin);
            reqJson.put("password", passwordAdmin);
            reqJson.put("dob", dobAdmin);
            reqJson.put("isadmin", true);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST,
                AppConfig.URL_REGISTER, reqJson, new Response.Listener<JSONObject>() {


                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, "Register Admin Response: " + response);
                        hideDialog();

                        try {


                            boolean error = response.getBoolean("error");
                            if (!error) {
                                // Admin successfully stored in MySQL
                                // Now store the admin in sqlite
                                Toast.makeText(getApplicationContext(),
                                        "Admin Registered Successfully!", Toast.LENGTH_SHORT)
                                        .show();

                                String uid = response.getString("uid");
                                JSONObject user = response.getJSONObject("user");
                                String fnameAdm = user.getString("fname");
                                String lnameAdm = user.getString("lname");
                                String contactAdm = user.getString("contact");
                                String collegeAdm = user.getString("college");
                                String emailAdm = user.getString("email");
                                String created_at = user.getString("created_at");
                                // Inserting row in users table
                                //db.addUser(fnameAdm, lnameAdm, contactAdm, collegeAdm, emailAdm, uid, created_at);

                                // Launch login activity
                                Intent intent = new Intent(
                                        RegisterAdminActivity.this,
                                        LoginActivity.class);
                                startActivity(intent);
                                finish();
                            } else {

                                // Error occurred in registration. Get the error
                                // message
                                String errorMsg = response.getString("error_msg");
                                Toast.makeText(getApplicationContext(),
                                        "Registration not unsuccessful", Toast.LENGTH_SHORT).show();
                                Toast.makeText(getApplicationContext(),
                                        errorMsg, Toast.LENGTH_SHORT).show();
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
                         Log.e(TAG, "Admin Registration Error: " + error.getMessage());
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register_admin, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
