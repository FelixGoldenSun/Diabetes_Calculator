package com.example.Diabetes_Calculator;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by benajminw5409 on 2/9/2016.
 */
public class ChartActivity extends BaseActivity{

    ArrayList<Float> entries = new ArrayList<>();
    ArrayList<String> labels = new ArrayList<String>();
    ArrayList<Entry> selectedEntries = new ArrayList<>();
    ArrayList<String> selectedLabels = new ArrayList<String>();

    LineChart chart;
    TextView lblBeforeDate;
    TextView lblAfterDate;
    Date beforeDate;
    Date afterDate;
    TextView lblTo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chart);
        chart = (LineChart) findViewById(R.id.viewLineChart);
        chart.setNoDataText(getString(R.string.chart_no_data));
        lblBeforeDate = (TextView) findViewById(R.id.lblBeforeDate);
        lblAfterDate = (TextView) findViewById(R.id.lblAfterDate);
        lblTo = (TextView) findViewById(R.id.lblTo);

        setDates();

    }

    public Date stringToDate(String dateString){
        Date date = new Date();
        DateFormat format = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);

        try {
            date = format.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

    private Calendar c1 = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener dateBefore = new DatePickerDialog.OnDateSetListener(){
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth ){
            c1.set(Calendar.YEAR, year);
            c1.set(Calendar.MONTH, month);
            c1.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            setBeforeDateOnView();
            sortDates();
        }
    };

    private Calendar c2 = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener dateAfter = new DatePickerDialog.OnDateSetListener(){
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth ){
            c2.set(Calendar.YEAR, year);
            c2.set(Calendar.MONTH, month);
            c2.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            setAfterDateOnView();
            sortDates();
        }
    };

    private void setBeforeDateOnView(){
        SimpleDateFormat sdfBefore = new SimpleDateFormat( "MM/dd/yyyy", Locale.US );
        lblBeforeDate.setText( sdfBefore.format( c1.getTime() ) );
    }

    private void setAfterDateOnView(){
        SimpleDateFormat stfAfter = new SimpleDateFormat( "MM/dd/yyyy", Locale.US);
        lblAfterDate.setText( stfAfter.format( c2.getTime() ) );
    }

    public void BeforeDateOnClick( View v){
        DatePickerDialog beforeDPD = new DatePickerDialog( this, dateBefore, c1.get( Calendar.YEAR), c1.get(Calendar.MONTH), c1.get(Calendar.DAY_OF_MONTH) );
        DatePicker beforeDP = beforeDPD.getDatePicker();
        beforeDP.setMinDate(beforeDate.getTime());
        beforeDP.setMaxDate(afterDate.getTime());
        beforeDPD.show();
    }

    public void AfterDateOnClick( View v){
        DatePickerDialog afterDPD = new DatePickerDialog( this, dateAfter, c2.get( Calendar.YEAR), c2.get(Calendar.MONTH), c2.get(Calendar.DAY_OF_MONTH));
        DatePicker afterDP = afterDPD.getDatePicker();
        afterDP.setMinDate(beforeDate.getTime());
        afterDP.setMaxDate(afterDate.getTime());
        afterDPD.show();
        Log.i("after", "after datePicker");
    }

    public void sortDates(){
        selectedEntries.clear();
        selectedLabels.clear();

        Date selectedDateBefore = stringToDate( lblBeforeDate.getText().toString());
        Date selectedDateAfter = stringToDate( lblAfterDate.getText().toString());

        int dateCounter = 0;
        int entriesIndex = 0;
        while(labels.size() > dateCounter){
            Date currentDate = stringToDate(labels.get(dateCounter));

            if(selectedDateBefore.compareTo(currentDate) < 0 &&
                selectedDateAfter.compareTo(currentDate) > 0 ||
                selectedDateBefore.compareTo(currentDate) < 0 &&
                selectedDateAfter.compareTo(currentDate) == 0 ||
                selectedDateBefore.compareTo(currentDate) == 0 &&
                selectedDateAfter.compareTo(currentDate) > 0  ||
                selectedDateBefore.compareTo(currentDate) == 0 &&
                selectedDateAfter.compareTo(currentDate) == 0 ){

                selectedLabels.add( labels.get(dateCounter));
                selectedEntries.add(new Entry(entries.get(dateCounter), entriesIndex));
                entriesIndex += 1;

            }


            dateCounter += 1;
        }

        LineDataSet dataSet = new LineDataSet( selectedEntries, getString(R.string.chart_eAG));
        LineData data = new LineData(selectedLabels, dataSet);
        chart.setData(data);
        chart.setDescription(getString(R.string.chart_description));
        chart.invalidate();
    }

    private boolean readWeightLog(){
        FileInputStream inputStream = null;
        Boolean readSuccess = false;

        try{
            inputStream = openFileInput( logFileName );
            byte[] rawData = new byte[ inputStream.available() ];
            while (  inputStream.read( rawData ) != -1){} //sets raw data with the log file


            Scanner s = new Scanner( new String( rawData ) );
            s.useDelimiter("\\n");

            while ( s.hasNext() ){
                String temp = s.next();
                String a[] = temp.split("<=>");

                //date is[0] eAG[6]
                entries.add(Float.parseFloat(a[6])); //y axis
                labels.add(a[0]); //x axsis

            }

            s.close();
            readSuccess = true;

        }catch ( Exception e ){
            Log.e("Chart ", e.getMessage());

        }finally {
            if(inputStream != null){
                try{
                    inputStream.close();

                }catch (IOException e){
                    Log.e("Chart ", e.getMessage());

                }
            }
        }

        return readSuccess;
    }

    public void setDates(){
        SharedPreferences sp = getSharedPreferences("calcPref", Context.MODE_PRIVATE);
        String before = sp.getString("beforeDate", "");
        String after = sp.getString("afterDate", "");

        if(before != "" && after != ""){
            lblBeforeDate.setText(before);
            beforeDate = stringToDate(before);
            lblAfterDate.setText(after);
            afterDate = stringToDate(after);

        }else{

            lblBeforeDate.setText("N/A");
            lblBeforeDate.setEnabled(false);
            lblAfterDate.setText( "N/A");
            lblAfterDate.setEnabled(false);
            lblTo.setEnabled(false);

        }

        if(readWeightLog()){
            sortDates();
        }

   /* if(readWeightLog()){
        int counter = 0;
        for(String date : labels){

            Date currentDate = stringToDate(date);

            if(counter == 0){
                beforeDate = currentDate;
                afterDate = currentDate;
                counter += 1;

            }else{
                if(beforeDate.compareTo(currentDate) > 0){ //greater than
                    beforeDate = currentDate;

                }else if(afterDate.compareTo(currentDate) < 0){ //less than
                    afterDate = currentDate;
                }
            }
        }

        lblBeforeDate.setText( sdf.format(beforeDate));
        lblAfterDate.setText( sdf.format(afterDate));
*/
    }

    @Override
    protected void onResume() {
        super.onResume();

        setDates();

    }
}
