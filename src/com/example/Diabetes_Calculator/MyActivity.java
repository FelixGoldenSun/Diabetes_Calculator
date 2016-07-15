package com.example.Diabetes_Calculator;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class MyActivity extends BaseActivity {
    /**
     * Called when the activity is first created.
     */
    RadioButton radioButtonADAG;
    RadioButton radioButtonDCCT;
    RadioGroup radioGroupFormulas;
    EditText editTextGlucose;
    EditText editTextA1C;
    Boolean formulaSwitch = true;
    Double A1C;
    public static Integer eAG;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        radioButtonADAG = (RadioButton) findViewById(R.id.radioButtonADAG);
        radioButtonDCCT = (RadioButton) findViewById(R.id.radioButtonDCCT);
        radioGroupFormulas = (RadioGroup) findViewById(R.id.radioGroupFormulas);
        editTextGlucose = (EditText) findViewById(R.id.editTextGlucose);
        editTextA1C = (EditText) findViewById(R.id.editTextA1C);

        //Create focus listeners
        editTextGlucose.setOnFocusChangeListener( new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View v, boolean hasFocus){
                handleOnFocusChange(v, hasFocus);
            }
        });

        editTextA1C.setOnFocusChangeListener( new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View v, boolean hasFocus){
                handleOnFocusChange(v, hasFocus);
            }
        });

        setFormula();

    }

    private void handleOnFocusChange(View v, boolean hasFocus){
        if(v == editTextGlucose && hasFocus){
            editTextA1C.setText("");

        }else if(v == editTextA1C && hasFocus){
            editTextGlucose.setText("");
        }

    }

    public void radioButtonClicked( View v ){
        int selectedId = radioGroupFormulas.getCheckedRadioButtonId();
        RadioButton selected = (RadioButton)findViewById(selectedId);

        if(selected.getId() == radioButtonADAG.getId()){
            formulaSwitch = true;

        }else if(selected.getId() == radioButtonDCCT.getId()){
            formulaSwitch = false;
        }

    }

    public Boolean tryParse(String value){
        try {
            Double.parseDouble(value);
            return true;

        } catch (NumberFormatException e) {
            return false;
        }
    }

    public void calculate( View v ){
        Double calculation = 0.0;

        if(tryParse(editTextGlucose.getText().toString()) && editTextGlucose.hasFocus()){//calculate A1C from glucose
            Double glucose = Double.parseDouble(editTextGlucose.getText().toString());

            if(formulaSwitch == true){ //uses ADAG formula
                calculation = ((glucose / 18.05) + 2.52 ) / 1.583;

            }else if(formulaSwitch == false){//Uses DCCT formula
                calculation = (glucose + 77.3) / 35.6;
            }

            A1C = calculation;

            String output = String.format("%.1f", calculation);
            editTextA1C.setText(output);
            editTextGlucose.setText("");


        }else if(tryParse(editTextA1C.getText().toString()) && editTextA1C.hasFocus()){//calculate glucose from A1C
            Double A1C = Double.parseDouble(editTextA1C.getText().toString());

            if(formulaSwitch == true){ //uses ADAG formula
                calculation = (1.583 * A1C - 2.52) * 18.05;

            }else if(formulaSwitch == false){ //Uses DCCT formula
                calculation = (A1C * 35.6) - 77.3;

            }

            int intTemp = (int)Math.round(calculation);
            eAG = intTemp;
            String output = Integer.toString(intTemp);
            editTextGlucose.setText(output);
            editTextA1C.setText("");

        }else{
            toastIt(getString(R.string.bad_input));
        }

    }

    public void setFormula(){
        SharedPreferences sp = getSharedPreferences("calcPref", Context.MODE_PRIVATE);
        String formula = sp.getString("formula", "");

        if(formula.equals("ADAG")){
            radioButtonADAG.setChecked(true);
            radioButtonDCCT.setChecked(false);
            formulaSwitch = true;

        }else if(formula.equals("DCCT")){
            radioButtonADAG.setChecked(false);
            radioButtonDCCT.setChecked(true);
            formulaSwitch = false;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        setFormula();

    }


}
