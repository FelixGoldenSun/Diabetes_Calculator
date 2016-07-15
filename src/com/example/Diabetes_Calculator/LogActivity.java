package com.example.Diabetes_Calculator;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class LogActivity extends BaseActivity {
    /**
     * Called when the activity is first created.
     */
    TextView editEAG;
    TextView textViewDate;
    TextView textViewTime;
    RadioGroup radioGroupMeal;
    RadioButton rbBeforeMeal;
    Boolean beforeMeal = true;
    Boolean afterMeal = false;
    Boolean other = false;
    RadioButton rbAfterMeal;
    RadioButton rbOther;
    EditText editTextNotes;
    Integer eAG;


    private Calendar c = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener(){
        @Override
        public void onDateSet( DatePicker view, int year, int month, int dayOfMonth ){
            c.set(Calendar.YEAR, year);
            c.set(Calendar.MONTH, month);
            c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            setCurrentDateOnView();
        }
    };

    TimePickerDialog.OnTimeSetListener time = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            c.set(Calendar.HOUR, hourOfDay);
            c.set(Calendar.MINUTE, minute);
            setCurrentDateOnView();
        }
    };



    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log);
        editEAG = (EditText) findViewById(R.id.editEAG);
        textViewDate = (TextView) findViewById(R.id.textViewDate);
        textViewTime = (TextView) findViewById(R.id.textViewTime);
        radioGroupMeal = (RadioGroup) findViewById(R.id.radioGroupMeal);
        rbBeforeMeal = (RadioButton) findViewById(R.id.rbBeforeMeal);
        rbAfterMeal = (RadioButton) findViewById(R.id.rbAfterMeal);
        rbOther = (RadioButton) findViewById(R.id.rbOther);
        editTextNotes = (EditText) findViewById(R.id.editTextDate1);
        setCurrentDateOnView();

        getData();

    }

    public void dateOnClick( View v){
        new DatePickerDialog( this, date, c.get( Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH) ).show();
    }

    public void timeOnClick( View v){
        new TimePickerDialog( this, time, c.get(Calendar.HOUR), c.get(Calendar.MINUTE), false).show();

    }

    public void radioButtonClicked( View v ){
        int selectedId = radioGroupMeal.getCheckedRadioButtonId();
        RadioButton selected = (RadioButton)findViewById(selectedId);

        if(selected.getId() == rbBeforeMeal.getId()){
            beforeMeal = true;
            afterMeal = false;
            other = false;

        }else if(selected.getId() == rbAfterMeal.getId()){
            beforeMeal = false;
            afterMeal = true;
            other = false;
        }else{
            beforeMeal = false;
            afterMeal = false;
            other = true;
        }

    }

    public void getData(){
        Intent newIntent = getIntent();
        Bundle extras = newIntent.getExtras();
        eAG = extras.getInt("eAG");
        editEAG.setText(eAG.toString());
    }

    @Override
    protected void onResume(){
        super.onResume();
        getData();
    }

    public void savedData( View v ){
        String dataPoint = textViewDate.getText().toString() + "<=>"
                + textViewTime.getText().toString() + "<=>"
                + beforeMeal.toString() + "<=>"
                + afterMeal.toString() + "<=>"
                + other.toString() + "<=>"
                + editTextNotes.getText().toString() + "<=>"
                + editEAG.getText() + "\n";

        try {
            FileOutputStream out = openFileOutput(logFileName, Context.MODE_APPEND);
            out.write( dataPoint.getBytes() );
            out.close();
            toastIt(getString(R.string.save_toast_it));

        }catch (Exception ex){
            ex.printStackTrace();
            toastIt(getString(R.string.save_error));
        }

    }

    public void deleteData(View v){
        File dir = getFilesDir();
        File file = new File(dir, logFileName);

        if(file.delete()){
            toastIt(getString(R.string.delete_toast_success));
        }else{
            toastIt(getString(R.string.delete_toast_no_file));
        }
    }

    public void onclickEmailLogToDoctor(View v){
        SharedPreferences sp = getSharedPreferences("calcPref", Context.MODE_PRIVATE);

        //String subject = "SUBJECT LINE";
        //String message = "DOCTOR PERSON, \n\n Log file here!\n\n GEORGE";

        //Intent uses existing email service on the device.

        final Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("plain/text");
        emailIntent.putExtra( Intent.EXTRA_EMAIL, new String[]{sp.getString("drEmail", "")});
        emailIntent.putExtra( Intent.EXTRA_CC, new String[]{"dave.jones@scc.spokane.edu"}); //Needs to have dave's email when turned in.
        //emailIntent.putExtra( Intent.EXTRA_BCC, new String[]{drEmail});
        emailIntent.putExtra( Intent.EXTRA_SUBJECT, sp.getString("subject", ""));
        emailIntent.putExtra( Intent.EXTRA_TEXT, sp.getString("drNotes", ""));

        File copy = copyFileToExternal(logFileName);
        if(copy != null){
            Uri path = Uri.fromFile(copy);
            emailIntent.putExtra(Intent.EXTRA_STREAM, path);

        }else{
            toastIt("Error - 404 file not found");
        }

        startActivityForResult(Intent.createChooser( emailIntent, "Send Email.."), 42);

        toastIt("Email sent to: " + sp.getString("drEmail", ""));
    }

    private void setCurrentDateOnView(){
        SimpleDateFormat sdf = new SimpleDateFormat( "MM/dd/yyyy", Locale.US );
        textViewDate.setText( sdf.format( c.getTime() ) );

        SimpleDateFormat stf = new SimpleDateFormat( "hh:mm a", Locale.US);
        textViewTime.setText( stf.format( c.getTime() ) );
    }

}