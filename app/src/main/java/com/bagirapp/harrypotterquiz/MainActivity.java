package com.bagirapp.harrypotterquiz;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickEvaluate(View view) {
        int counter = 0;
        boolean answers[] = new boolean[8];

        //set the input variables

        RadioButton firstRadioGroup = (RadioButton) findViewById(R.id.harryPotter);
        RadioButton secondRadioGroup = (RadioButton) findViewById(R.id.QQ);
        RadioButton thirdRadioGroup = (RadioButton) findViewById(R.id.privateDrive);
        RadioButton fourthRadioGroup = (RadioButton) findViewById(R.id.TMR);
        EditText fifthEditText = (EditText) findViewById(R.id.numberOfHit);
        CheckBox sixthCheckBox1 = (CheckBox) findViewById(R.id.checkbox1);
        CheckBox sixthCheckBox2 = (CheckBox) findViewById(R.id.checkbox2);
        CheckBox sixthCheckBox3 = (CheckBox) findViewById(R.id.checkbox3);
        CheckBox sixthCheckBox4 = (CheckBox) findViewById(R.id.checkbox4);
        EditText seventhEditText = (EditText) findViewById(R.id.suggestedAge);
        RadioButton eighthRadioGroup = (RadioButton) findViewById(R.id.mirror);

        // count the score

        for (int i = 0; i < 8; i++) {
            answers[i] = false;
        }
        if (firstRadioGroup.isChecked()) {
            answers[0] = true;
        }
        if (secondRadioGroup.isChecked()) {
            answers[1] = true;
        }
        if (thirdRadioGroup.isChecked()) {
            answers[2] = true;
        }
        if (fourthRadioGroup.isChecked()) {
            answers[3] = true;
        }
        if (fifthEditText.getText().toString().equals("45")) {
            answers[4] = true;
        }
        if (sixthCheckBox1.isChecked() && sixthCheckBox3.isChecked() && !sixthCheckBox2.isChecked() && !sixthCheckBox4.isChecked()) {
            answers[5] = true;
        }
        if (seventhEditText.getText().toString().equals("11")) {
            answers[6] = true;
        }
        if (eighthRadioGroup.isChecked()) {
            answers[7] = true;
        }

        for (int i = 0; i < 8; i++) {
            if (answers[i] == true) {
                counter++;
            }
        }

        // create a toast message

        String evaluateText;

        if (counter > 5) {
            evaluateText = "Great job, you achieved " + counter + " points.";
        }else {
            evaluateText = "You should read again the book, you achieved only " + counter + " points";
        }

        Context context = getApplicationContext();
        CharSequence text = evaluateText;
        int duration = Toast.LENGTH_LONG;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

}

