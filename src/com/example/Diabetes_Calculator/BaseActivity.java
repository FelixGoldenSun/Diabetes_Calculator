package com.example.Diabetes_Calculator;

/**
 * Created by benajminw5409 on 1/20/2016.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class BaseActivity extends Activity {

    String logFileName = "log.txt";
    String alarmFileName = "alarm.txt";
    public static String drEmail;


    public void toastIt(String message){

        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mastermenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        Log.i("Menu", item.getTitle().toString());
        switch(item.getItemId()){
            case R.id.switchToMain :
                switchToMain( null );
                break;
            case R.id.switchToLog :
                switchToLog( null );
                break;
            case R.id.switchToChart :
                switchToChart( null );
                break;
            case R.id.switchToAlarm :
                switchToAlarm( null );
                break;
            case R.id.switchToPreferences :
                switchToPreferences( null );
                break;
            case R.id.switchToAbout :
                switchToAbout( null );
                break;
            default :
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    public void switchToMain( View v){

        startActivity( new Intent( this, MyActivity.class));
    }

    public void switchToLog( View v) {
        Intent newActivity1 = new Intent( this, LogActivity.class);
        newActivity1.putExtra("eAG", MyActivity.eAG);
        newActivity1.setFlags(newActivity1.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(newActivity1);
    }

    public void switchToChart( View v) {
        Intent newActivity1 = new Intent( this, ChartActivity.class);
        newActivity1.setFlags(newActivity1.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(newActivity1);
    }

    public void switchToAlarm( View v) {
        Intent newActivity1 = new Intent( this, AlarmActivity.class);
        startActivity(newActivity1);
    }

    public void switchToPreferences( View v) {
        Intent newActivity1 = new Intent( this, PreferencesActivity.class);
        startActivity(newActivity1);
    }

    public void switchToAbout( View v) {
        Intent newActivity1 = new Intent( this, AboutActivity.class);
        startActivity(newActivity1);
    }

    public File copyFileToExternal( String fileName){
        File file = null;
        String newPath = Environment.getExternalStorageDirectory() + "/calcLog/";

        try{
            File f = new File(newPath);
            f.mkdirs();
            FileInputStream fin = openFileInput(fileName);
            FileOutputStream fout = new FileOutputStream( newPath + fileName);

            byte[] buffer = new byte[1024];
            int length = 0;

            while((length = fin.read(buffer)) != -1){
                fout.write(buffer, 0, length);
            }

            fin.close();
            fout.close();

            file = new File( newPath + fileName);
            if(file.exists()){
                return file;
            }

        }catch(Exception e){
            toastIt("Options file - Error 404");
        }

        return file;

    }
}
