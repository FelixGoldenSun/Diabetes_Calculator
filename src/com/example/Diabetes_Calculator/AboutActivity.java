package com.example.Diabetes_Calculator;

/**
 * Created by benajminw5409 on 4/5/2016.
 */


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AboutActivity extends BaseActivity  {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);

    }

    public void onclickWeb(View v){ //for the other activity in the assignment
        Button currentButton = (Button) findViewById(v.getId());
        String url;

        if(currentButton.getText().toString().equals("  About Us   ")){
            url = "http://www.dbcalc.chesthighwalls.com/aboutus.php"; //about

        }else{
            url = "http://www.dbcalc.chesthighwalls.com/contactus.php"; //contact us


        }
        Intent browserIntent  = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(browserIntent);
    }


}
