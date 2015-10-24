package com.example.ha294221.mootster;

import android.app.DatePickerDialog;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Calendar;


public class CreateMootFragment extends Fragment {
    private TextView dateOfMoot;
    private TextView dateOfMootReg;
    private ImageButton calDateOfMoot;
    private ImageButton calDateOfMootReg;
    private ArrayAdapter<String> adapter;

    private Spinner mootTypeDropdown;

    private MultiAutoCompleteTextView bodyOfLawTag;

    Calendar calMootDate = Calendar.getInstance();
    Calendar calMootReg = Calendar.getInstance();

    DatePickerDialog.OnDateSetListener mootDateListener,mootRegListener;
    public CreateMootFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_create_moot, container, false);
        mootTypeDropdown = (Spinner) rootView.findViewById(R.id.mootTypeDropdown);
        dateOfMoot = (TextView) rootView.findViewById(R.id.dateOfMoot);
        dateOfMootReg = (TextView) rootView.findViewById(R.id.dateOfMootReg);
        calDateOfMoot = (ImageButton) rootView.findViewById(R.id.calDateOfMoot);
        calDateOfMootReg = (ImageButton) rootView.findViewById(R.id.calDateOfMootReg);
        bodyOfLawTag = (MultiAutoCompleteTextView) rootView.findViewById(R.id.bodyOfLawTagMultiAutoComplete);

        //Making Dates not editable
        dateOfMoot.setKeyListener(null);
        dateOfMootReg.setKeyListener(null);

        initSpinner();
        initLawTagMultiAutoComplete();

        //Implementing DatePicker Dialog
        mootDateListener = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                calMootDate.set(Calendar.YEAR, year);
                calMootDate.set(Calendar.MONTH, monthOfYear);
                calMootDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateMootDateDisplay();
            }

        };

        mootRegListener = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                calMootReg.set(Calendar.YEAR, year);
                calMootReg.set(Calendar.MONTH, monthOfYear);
                calMootReg.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateMootRegDisplay();
            }

        };

        //Setting click listeners on calendar image buttons
        calDateOfMoot.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                // On button click show datepicker dialog
                DatePickerDialog mootDatePicker = new DatePickerDialog(getActivity(), mootDateListener, calMootDate
                        .get(Calendar.YEAR), calMootDate.get(Calendar.MONTH),
                        calMootDate.get(Calendar.DAY_OF_MONTH));
                mootDatePicker.setTitle("Select Date of Moot");
                mootDatePicker.show();
            }
        });

        calDateOfMootReg.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                // On button click show datepicker dialog
                DatePickerDialog mootRegDatePicker = new DatePickerDialog(getActivity(), mootRegListener, calMootReg
                        .get(Calendar.YEAR), calMootReg.get(Calendar.MONTH),
                        calMootReg.get(Calendar.DAY_OF_MONTH));
                mootRegDatePicker.setTitle("Select Moot Registration Date");
                mootRegDatePicker.show();
            }
        });
        return rootView;
    }


    public void initSpinner(){

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.moot_type_dropdown_list, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mootTypeDropdown.setAdapter(adapter);
    }

    private void initLawTagMultiAutoComplete() {
        String[] lawTags = getResources().getStringArray(R.array.body_tag_list);
        adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,lawTags);
        bodyOfLawTag.setAdapter(adapter);
        bodyOfLawTag.setThreshold(0);
        bodyOfLawTag.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
    }

    private void updateMootDateDisplay() {

        dateOfMoot.setText(new StringBuilder()
                // Month is 0 based so add 1
                .append(calMootDate.get(Calendar.DATE)).append("/").append(calMootDate.get(Calendar.MONTH) + 1).append("/").append(calMootDate.get(Calendar.YEAR)).append(" "));
    }

    private void updateMootRegDisplay() {

        dateOfMootReg.setText(new StringBuilder()
                // Month is 0 based so add 1
                .append(calMootReg.get(Calendar.DATE)).append("/").append(calMootReg.get(Calendar.MONTH) + 1).append("/").append(calMootReg.get(Calendar.YEAR)).append(" "));
    }
}
