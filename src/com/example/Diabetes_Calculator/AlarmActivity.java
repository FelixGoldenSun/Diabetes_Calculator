package com.example.Diabetes_Calculator;

import android.app.*;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TimePicker;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * Created by benajminw5409 on 2/25/2016.
 */
public class AlarmActivity extends BaseActivity {

    private AlarmManager am;
    Calendar c = Calendar.getInstance();
    private BroadcastReceiver br;
    private Alarm[] alarms = new Alarm[10];

    Alarm currentAlarm;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm);

        EditText editTextDate1 = (EditText) findViewById(R.id.editTextDate1);
        EditText editTextNotes1 = (EditText) findViewById(R.id.editTextNotes1);
        Switch switchActive1 = (Switch) findViewById(R.id.switchActive1);
        Switch switchRecurring1 = (Switch) findViewById(R.id.switchRecurring1);

        EditText editTextDate2 = (EditText) findViewById(R.id.editTextDate2);
        EditText editTextNotes2 = (EditText) findViewById(R.id.editTextNotes2);
        Switch switchActive2 = (Switch) findViewById(R.id.switchActive2);
        Switch switchRecurring2 = (Switch) findViewById(R.id.switchRecurring2);

        EditText editTextDate3 = (EditText) findViewById(R.id.editTextDate3);
        EditText editTextNotes3 = (EditText) findViewById(R.id.editTextNotes3);
        Switch switchActive3 = (Switch) findViewById(R.id.switchActive3);
        Switch switchRecurring3 = (Switch) findViewById(R.id.switchRecurring3);

        EditText editTextDate4 = (EditText) findViewById(R.id.editTextDate4);
        EditText editTextNotes4 = (EditText) findViewById(R.id.editTextNotes4);
        Switch switchActive4 = (Switch) findViewById(R.id.switchActive4);
        Switch switchRecurring4 = (Switch) findViewById(R.id.switchRecurring4);

        EditText editTextDate5 = (EditText) findViewById(R.id.editTextDate5);
        EditText editTextNotes5 = (EditText) findViewById(R.id.editTextNotes5);
        Switch switchActive5 = (Switch) findViewById(R.id.switchActive5);
        Switch switchRecurring5 = (Switch) findViewById(R.id.switchRecurring5);

        EditText editTextDate6 = (EditText) findViewById(R.id.editTextDate6);
        EditText editTextNotes6 = (EditText) findViewById(R.id.editTextNotes6);
        Switch switchActive6 = (Switch) findViewById(R.id.switchActive6);
        Switch switchRecurring6 = (Switch) findViewById(R.id.switchRecurring6);

        EditText editTextDate7 = (EditText) findViewById(R.id.editTextDate7);
        EditText editTextNotes7 = (EditText) findViewById(R.id.editTextNotes7);
        Switch switchActive7 = (Switch) findViewById(R.id.switchActive7);
        Switch switchRecurring7 = (Switch) findViewById(R.id.switchRecurring7);

        EditText editTextDate8 = (EditText) findViewById(R.id.editTextDate8);
        EditText editTextNotes8 = (EditText) findViewById(R.id.editTextNotes8);
        Switch switchActive8 = (Switch) findViewById(R.id.switchActive8);
        Switch switchRecurring8 = (Switch) findViewById(R.id.switchRecurring8);

        EditText editTextDate9 = (EditText) findViewById(R.id.editTextDate9);
        EditText editTextNotes9 = (EditText) findViewById(R.id.editTextNotes9);
        Switch switchActive9 = (Switch) findViewById(R.id.switchActive9);
        Switch switchRecurring9 = (Switch) findViewById(R.id.switchRecurring9);

        EditText editTextDate10 = (EditText) findViewById(R.id.editTextDate10);
        EditText editTextNotes10 = (EditText) findViewById(R.id.editTextNotes10);
        Switch switchActive10 = (Switch) findViewById(R.id.switchActive10);
        Switch switchRecurring10 = (Switch) findViewById(R.id.switchRecurring10);






        am = (AlarmManager)this.getSystemService(Context.ALARM_SERVICE);
        Intent alarmIntent = new Intent(getPackageName());
        PendingIntent pi = PendingIntent.getBroadcast(this, 0 , alarmIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        c.add(Calendar.SECOND, 5);
        am.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pi);
        alarms[0] = new Alarm(editTextDate1, editTextNotes1, switchActive1, switchRecurring1, 0, this, Calendar.getInstance(), getPackageName());
        alarms[1] = new Alarm(editTextDate2, editTextNotes2, switchActive2, switchRecurring2, 1, this, Calendar.getInstance(), getPackageName());
        alarms[2] = new Alarm(editTextDate3, editTextNotes3, switchActive3, switchRecurring3, 2, this, Calendar.getInstance(), getPackageName());
        alarms[3] = new Alarm(editTextDate4, editTextNotes4, switchActive4, switchRecurring4, 3, this, Calendar.getInstance(), getPackageName());
        alarms[4] = new Alarm(editTextDate5, editTextNotes5, switchActive5, switchRecurring5, 4, this, Calendar.getInstance(), getPackageName());
        alarms[5] = new Alarm(editTextDate6, editTextNotes6, switchActive6, switchRecurring6, 5, this, Calendar.getInstance(), getPackageName());
        alarms[6] = new Alarm(editTextDate7, editTextNotes7, switchActive7, switchRecurring7, 6, this, Calendar.getInstance(), getPackageName());
        alarms[7] = new Alarm(editTextDate8, editTextNotes8, switchActive8, switchRecurring8, 7, this, Calendar.getInstance(), getPackageName());
        alarms[8] = new Alarm(editTextDate9, editTextNotes9, switchActive9, switchRecurring9, 8, this, Calendar.getInstance(), getPackageName());
        alarms[9] = new Alarm(editTextDate10, editTextNotes10, switchActive10, switchRecurring10, 9, this, Calendar.getInstance(), getPackageName());

        File dir = getFilesDir();
        File file = new File(dir, alarmFileName);
        if(file.isFile()){
            readAlarm();

        }else{//Populates the date fields if there is no file.
            int index = 0;
            while(index < alarms.length){
                currentAlarm = alarms[index];
                setCurrentDateOnView();

                index += 1;
            }

        }

        br = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                int alarmID = 0;

                Bundle extras = intent.getExtras();
                if (extras != null) {
                    alarmID = extras.getInt( "alarm" );
                    toastIt(alarms[alarmID].noteText.getText().toString());
                    if( alarms[alarmID].recurring.isChecked()){
                        alarms[ alarmID ].cal.add( Calendar.HOUR, 24 );
                        SimpleDateFormat sdf = new SimpleDateFormat( "MM/dd/yyyy hh:mm a", Locale.US );
                        alarms[alarmID].dateText.setText(sdf.format(alarms[alarmID].cal.getTime()));
                        am.set( AlarmManager.RTC_WAKEUP, alarms[ alarmID ].cal.getTimeInMillis(), alarms[ alarmID ].pi );
                    }else{
                        alarms[alarmID].active.setChecked(false);
                    }
                }

                createNotification(alarmID);
            }
        };

        registerReceiver( br, new IntentFilter(getPackageName()));

    }

    public void createNotification(int alarmID){
        Intent intent = new Intent( this, AlarmActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent,0);
        Notification n = new Notification.Builder(this)
            .setContentTitle("Alarm " + (alarmID + 1) )
            .setContentText(alarms[alarmID].noteText.getText().toString())
            .setSmallIcon(R.drawable.ic_alarm)
            .setContentIntent(pIntent)
            .build();

        NotificationManager notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0, n);
    }

    public void activeOnClick(View v) {
        Switch activeAlarm = (Switch) findViewById(v.getId());
        Alarm al = (Alarm)v.getTag();

        if (activeAlarm.isChecked()) {
            toastIt("Alarm " + (al.alarmID + 1) + " active");
            setAlarm(al.alarmID);


        }else if(!activeAlarm.isChecked()){
            toastIt("Alarm " + (al.alarmID + 1) + " off");
            am.cancel(alarms[al.alarmID].pi);

        }
    }

    public void setAlarm(int alarmID){
        am.set(AlarmManager.RTC_WAKEUP, alarms[alarmID].cal.getTimeInMillis(), alarms[alarmID].pi);

    }

    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener(){
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth ){
            currentAlarm.cal.set(Calendar.YEAR, year);
            currentAlarm.cal.set(Calendar.MONTH, month);
            currentAlarm.cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            setCurrentDateOnView();
        }
    };

    TimePickerDialog.OnTimeSetListener time = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            currentAlarm.cal.set(Calendar.HOUR, hourOfDay);
            currentAlarm.cal.set(Calendar.MINUTE, minute);
            setCurrentDateOnView();
        }
    };

    public void dateTimeOnClick( View v){
        currentAlarm = (Alarm)v.getTag();
        new DatePickerDialog( this, date, currentAlarm.cal.get( Calendar.YEAR), currentAlarm.cal.get(Calendar.MONTH), currentAlarm.cal.get(Calendar.DAY_OF_MONTH) ).show();
        new TimePickerDialog( this, time, currentAlarm.cal.get(Calendar.HOUR), currentAlarm.cal.get(Calendar.MINUTE), false).show();
    }


    private void setCurrentDateOnView(){
        SimpleDateFormat sdf = new SimpleDateFormat( "MM/dd/yyyy", Locale.US );
        SimpleDateFormat stf = new SimpleDateFormat( "hh:mm a", Locale.US);

        currentAlarm.dateText.setText( sdf.format( currentAlarm.cal.getTime() ) + " " + stf.format( currentAlarm.cal.getTime() ) );

    }

    public void saveAlarm(){
        File dir = getFilesDir();
        File file = new File(dir, alarmFileName);
        file.delete();

        String dataPoint = "";

        int index = 0;
        while(index < alarms.length){
            dataPoint += alarms[index].dateText.getText().toString() + "<=>"
                    + alarms[index].noteText.getText().toString() + "<=>"
                    + alarms[index].active.isChecked() + "<=>"
                    + alarms[index].recurring.isChecked() + "\n";

            index += 1;
        }


        try {
            FileOutputStream out = openFileOutput(alarmFileName, Context.MODE_APPEND);
            out.write( dataPoint.getBytes() );
            out.close();


        }catch (Exception ex){
            ex.printStackTrace();

        }

    }

    private boolean readAlarm(){
        FileInputStream inputStream = null;
        Boolean readSuccess = false;

        try{
            inputStream = openFileInput( alarmFileName );
            byte[] rawData = new byte[ inputStream.available() ];
            while (  inputStream.read( rawData ) != -1){} //sets raw data with the log file


            Scanner s = new Scanner( new String( rawData ) );
            s.useDelimiter("\\n");

           int index = 0;
            while ( s.hasNext() ){
                String temp = s.next();
                String a[] = temp.split("<=>");

                alarms[index].dateText.setText(a[0]);
                alarms[index].noteText.setText(a[1]);
                alarms[index].cal.setTime(stringToDate(alarms[index].dateText.getText().toString()));

                if(c.getTime().compareTo( alarms[index].cal.getTime()) < 0){
                    alarms[index].active.setChecked(Boolean.parseBoolean(a[2]));
                    if(alarms[index].active.isChecked()){
                        setAlarm(index);
                    }
                }

                alarms[index].recurring.setChecked(Boolean.parseBoolean(a[3]));

                index += 1;
            }

            s.close();
            readSuccess = true;

        }catch ( Exception e ){
            Log.e("Alarm ", e.getMessage());

        }finally {
            if(inputStream != null){
                try{
                    inputStream.close();

                }catch (IOException e){
                    Log.e("Alarm", e.getMessage());

                }
            }
        }

        return readSuccess;
    }

    public Date stringToDate(String dateString){
        Date date = new Date();
        DateFormat format = new SimpleDateFormat("MM/dd/yyyy hh:mm a", Locale.ENGLISH);

        try {
            date = format.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveAlarm();
    }

}
