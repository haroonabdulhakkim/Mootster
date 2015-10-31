package com.example.ha294221.mootster;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.ha294221.mootster.helper.SQLiteHandler;

import java.util.HashMap;


public class EditProfileAdminFragment extends Fragment {
    private EditText editFname;
    private EditText editLname;
    private EditText editDob;
    private EditText editContact;
    private EditText editCollege;
    private EditText editEmail;

    private SQLiteHandler db;
    private Toolbar toolbar;
    public EditProfileAdminFragment(){}


    @Override
    public void onStop() {
        super.onStop();
        toolbar.getMenu().clear();
        Log.e("Inside onStop", "onStop");
    }

    @Override
    public void onStart() {
        super.onStart();
        toolbar.inflateMenu(R.menu.menu_edit_profile);
        Log.e("Inside onStart", "onStart");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_edit_profile_admin, container, false);
        db = new SQLiteHandler(getActivity().getApplicationContext());
        editFname= (EditText) rootView.findViewById(R.id.edit_fname);
        editLname= (EditText) rootView.findViewById(R.id.edit_lname);
        editDob= (EditText) rootView.findViewById(R.id.edit_dob);
        editContact= (EditText) rootView.findViewById(R.id.edit_contact);
        editCollege= (EditText) rootView.findViewById(R.id.edit_college);
        editEmail= (EditText) rootView.findViewById(R.id.edit_email);
        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar1);
       // toolbar.inflateMenu(R.menu.menu_edit_profile);
        // Fetching user details from sqlite
        HashMap<String, Object> user = db.getUserDetails();
        Log.e("User Details", db.getUserDetails().toString());
        if(user!=null) {
            String fname = (String) user.get("fname");
            String lname = (String) user.get("lname");
            String contact = (String) user.get("contact");
            String college = (String) user.get("college");
            String email = (String) user.get("email");

            editFname.setText(fname);
            editLname.setText(lname);

            editContact.setText(contact);
            editCollege.setText(college);
            editEmail.setText(email);
        }
        return rootView;
    }


}
