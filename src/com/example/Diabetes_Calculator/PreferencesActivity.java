package com.example.Diabetes_Calculator;

/**
 * Created by benajminw5409 on 4/5/2016.
 */


import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class PreferencesActivity extends BaseActivity  {

    EditText editTextDoctorEmail;
    RadioButton radioButtonPrefADAG;
    RadioButton radioButtonPrefDCCT;
    RadioGroup radioGroupPrefFormulas;
    EditText editTextPrefBeforeDate;
    EditText editTextPrefAfterDate;
    EditText editTextSubject;
    EditText editTextDrNotes;
    String formula;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preferences);
        editTextDoctorEmail = (EditText)findViewById( R.id.editTextDoctorEmail);
        radioButtonPrefADAG = (RadioButton) findViewById(R.id.radioButtonPrefADAG);
        radioButtonPrefDCCT = (RadioButton) findViewById(R.id.radioButtonPrefDCCT);
        radioGroupPrefFormulas = (RadioGroup) findViewById(R.id.radioGroupPrefFormulas);
        editTextPrefBeforeDate = (EditText)findViewById( R.id.editTextPrefBeforeDate);
        editTextPrefAfterDate = (EditText)findViewById( R.id.editTextPrefAfterDate);
        editTextSubject = (EditText)findViewById( R.id.editTextSubject);
        editTextDrNotes = (EditText)findViewById( R.id.editTextDrNotes);

        SharedPreferences sp = getSharedPreferences("calcPref", Context.MODE_PRIVATE);
        String radioFormula = sp.getString("formula", "");
        if(radioFormula.equals("ADAG")){
            radioButtonPrefADAG.setChecked(true);
            radioButtonPrefDCCT.setChecked(false);
            formula = "ADAG";

        }else if(radioFormula.equals("DCCT")){
            radioButtonPrefADAG.setChecked(false);
            radioButtonPrefDCCT.setChecked(true);
            formula = "DCCT";
        }

        editTextDoctorEmail.setText(sp.getString("drEmail", ""));

        String before = sp.getString("beforeDate", "");
        String after = sp.getString("afterDate", "");

        if(before != ""){
            editTextPrefBeforeDate.setText(before);
        }else{
            setPrefBeforeDateOnView();
        }

        if(after != ""){
            editTextPrefAfterDate.setText(after);

        }else{
            setPrefAfterDateOnView();
        }

        editTextSubject.setText(sp.getString("subject", ""));
        editTextDrNotes.setText(sp.getString("drNotes", ""));
    }


    public void onclickSavePreferences( View v ){
        savePreferences();
        toastIt("Preferences saved" );
    }

    public void savePreferences(){
        SharedPreferences sp = getSharedPreferences("calcPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("drEmail", editTextDoctorEmail.getText().toString());
        editor.putString("formula", formula);
        editor.putString("beforeDate", editTextPrefBeforeDate.getText().toString());
        editor.putString("afterDate", editTextPrefAfterDate.getText().toString());
        editor.putString("subject", editTextSubject.getText().toString());
        editor.putString("drNotes", editTextDrNotes.getText().toString());

        editor.apply();
    }

    public void radioButtonClicked( View v ) {
        int selectedId = radioGroupPrefFormulas.getCheckedRadioButtonId();
        RadioButton selected = (RadioButton) findViewById(selectedId);

        if (selected.getId() == radioButtonPrefADAG.getId()) {
            formula = "ADAG";

        } else if (selected.getId() == radioButtonPrefDCCT.getId()) {
            formula = "DCCT";
        }

    }

    private Calendar c1 = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener dateBefore = new DatePickerDialog.OnDateSetListener(){
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth ){
            c1.set(Calendar.YEAR, year);
            c1.set(Calendar.MONTH, month);
            c1.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            setPrefBeforeDateOnView();
        }
    };

    private Calendar c2 = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener dateAfter = new DatePickerDialog.OnDateSetListener(){
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth ){
            c2.set(Calendar.YEAR, year);
            c2.set(Calendar.MONTH, month);
            c2.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            setPrefAfterDateOnView();
        }
    };

    private void setPrefBeforeDateOnView(){
        SimpleDateFormat sdfBefore = new SimpleDateFormat( "MM/dd/yyyy", Locale.US );
        editTextPrefBeforeDate.setText( sdfBefore.format( c1.getTime() ) );
    }

    private void setPrefAfterDateOnView(){
        SimpleDateFormat stfAfter = new SimpleDateFormat( "MM/dd/yyyy", Locale.US);
        editTextPrefAfterDate.setText( stfAfter.format( c2.getTime() ) );
    }

    public void BeforePrefDateOnClick( View v){
        DatePickerDialog beforeDPD = new DatePickerDialog( this, dateBefore, c1.get( Calendar.YEAR), c1.get(Calendar.MONTH), c1.get(Calendar.DAY_OF_MONTH) );
        beforeDPD.show();
    }

    public void AfterPrefDateOnClick( View v){
        DatePickerDialog afterDPD = new DatePickerDialog( this, dateAfter, c2.get( Calendar.YEAR), c2.get(Calendar.MONTH), c2.get(Calendar.DAY_OF_MONTH));
        afterDPD.show();
        Log.i("after", "after datePicker");
    }

}
