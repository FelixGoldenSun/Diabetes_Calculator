package com.example.Diabetes_Calculator;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.EditText;
import android.widget.Switch;

import java.util.Calendar;

/**
 * Created by benajminw5409 on 3/1/2016.
 */
public class Alarm {

    Intent alarmIntent;
    PendingIntent pi;
    EditText noteText, dateText;
    Switch active;
    Switch recurring;
    Integer alarmID;
    Context context;
    Calendar cal;
    String packageName;

    public Alarm(EditText dateText, EditText noteText, Switch active, Switch recurring, Integer alarmID, Context context, Calendar cal, String packageName) {
        this.dateText = dateText;
        this.noteText = noteText;
        this.active = active;
        this.recurring = recurring;
        this.alarmID = alarmID;
        this.context = context;
        this.cal = cal;
        this.packageName = packageName;

        this.alarmIntent = new Intent(packageName);
        this.alarmIntent.putExtra("alarm", this.alarmID);
        this.alarmIntent.setFlags(this.alarmIntent.FLAG_ACTIVITY_CLEAR_TASK);
        this.pi = PendingIntent.getBroadcast(context, alarmID, alarmIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        setTags();

    }

    public void setTags(){
        dateText.setTag( this );
        noteText.setTag( this );
        active.setTag( this );
        recurring.setTag( this );
    }

    @Override
    public String toString() {
        return "Alarm{" +
            "alarmIntent=" + alarmIntent +
            ", pi=" + pi +
            ", noteText=" + noteText +
            ", dateText=" + dateText +
            ", recurring=" + recurring +
            ", alarmID=" + alarmID +
            ", context=" + context +
            ", cal=" + cal +
            ", packageName='" + packageName + '\'' +
            '}';
    }


}
