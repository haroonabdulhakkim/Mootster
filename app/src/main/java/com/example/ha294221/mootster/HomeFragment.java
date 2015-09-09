package com.example.ha294221.mootster;

/**
 * Created by HA294221 on 8/29/2015.
 */
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ha294221.mootster.helper.SQLiteHandler;
import com.example.ha294221.mootster.helper.SessionManager;

import java.util.HashMap;

public class HomeFragment extends Fragment {
    private TextView txtFirstName;
    private TextView txtEmail;
    private Button btnLogout;

    private SQLiteHandler db;
    private SessionManager session;


    public HomeFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        txtFirstName = (TextView) rootView.findViewById(R.id.fname);
        txtEmail = (TextView) rootView.findViewById(R.id.email);
        btnLogout = (Button) rootView.findViewById(R.id.btnLogout);

        // SqLite database handler
        db = new SQLiteHandler(getActivity().getApplicationContext());

        // session manager
        session = new SessionManager(getActivity().getApplicationContext());

        if (!session.isLoggedIn()) {
            logoutUser();
        }

        // Fetching user details from sqlite
        HashMap<String, String> user = db.getUserDetails();
        Toast.makeText(getActivity().getApplicationContext(), user.toString() + "!!!", Toast.LENGTH_LONG).show();
        String fname = user.get("fname");
        String email = user.get("email");
        Toast.makeText(getActivity().getApplicationContext(), fname + "!!!", Toast.LENGTH_LONG).show();
        // Displaying the user details on the screen
        txtFirstName.setText(fname);
        txtEmail.setText(email);

        // Logout button click event
        btnLogout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                logoutUser();
            }
        });
        return rootView;
    }

    /**
     * Logging out the user. Will set isLoggedIn flag to false in shared
     * preferences Clears the user data from sqlite users table
     * */
    private void logoutUser() {
        session.setLogin(false);

        db.deleteUsers();

        // Launching the login activity
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
        getActivity().finish();
    }
}
