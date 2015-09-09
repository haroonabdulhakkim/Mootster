package com.example.ha294221.mootster;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.ha294221.mootster.helper.SessionManager;

import java.util.Calendar;


public class RegisterAdminActivity extends Activity {

    private static final String TAG = RegisterActivity.class.getSimpleName();
    private String role;
    private Button btnRegisterAdminToLoginScreen;
    private ImageButton calendarDob;

    private TextView dobAdmin;

    Calendar myCalendar = Calendar.getInstance();


    private SessionManager session;

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
        btnRegisterAdminToLoginScreen = (Button) findViewById(R.id.btnRegisterAdminToLoginScreen);
        dobAdmin = (EditText) findViewById(R.id.dobAdmin);
        calendarDob = (ImageButton) findViewById(R.id.calendarDob);
        // Session manager
        session = new SessionManager(getApplicationContext());

        // Check if user is already logged in or not
        if (session.isLoggedIn()) {
            // User is already logged in. Take him to main activity
            Intent intent = new Intent(this,
                    MainActivity.class);
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

        dobAdmin.setText(new StringBuilder()
                // Month is 0 based so add 1
                .append(myCalendar.get(Calendar.DATE)).append("/").append(myCalendar.get(Calendar.MONTH) + 1).append("/").append(myCalendar.get(Calendar.YEAR)).append(" "));
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
