package com.alfadasApps.Virus_Infection_Predictor;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class QuestionsActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String RISK = "com.alfadasApps.Virus_Infection_Predictor.RISK";
    public static final String SHARED_PREFS = "com.alfadasApps.Virus_Infection_Predictor.sharedPrefs";
    public static final String LOW_RISK = "com.alfadasApps.Virus_Infection_Predictor.low_risk";
    public static final String HIGH_RISK = "com.alfadasApps.Virus_Infection_Predictor.high_risk";

    //private int real_risk;

    TextView tv;
    Button submitbutton, quitbutton;
    RadioGroup radio_g;
    RadioButton rb1,rb2,rb3,rb4;

    String questions[] = {
                            "Do you have symptoms of fever or increased temperature?",
                            "Do you feel tired or exhausted?",
                            "Are you sneezing more then usual?",
                            "Can you feel a kind of body ache or pain?",
                            "Is your throat sore?",
                            "Do you have a runny or stuffy nose?",
                            "Problems with digestions (Diarrhea)?",
                            "Do you have watery or red eyes?",
                            "Do you need to cough more than usual?",
                            "Can you feel a shortness in your breath while inhaling?",
                         };
    //String answers[] = {"yes","more than usual","not sure","no","not sure","more than usual","yes","more than usual","not sure","no"};
    String opt[] = {
                    "no","not sure","more than usual","yes",
                    "no","not sure","more than usual","yes",
                    "no","not sure","more than usual","yes",
                    "no","not sure","more than usual","yes",
                    "no","not sure","more than usual","yes",
                    "no","not sure","more than usual","yes",
                    "no","not sure","more than usual","yes",
                    "no","not sure","more than usual","yes",
                    "no","not sure","more than usual","yes",
                    "no","not sure","more than usual","yes",
                   };

    int flag=0;
    int lowrisk = 0;
    int highrisk = 0;
    int risk = 0;


    public static int marks=0,correct=0,wrong=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);

        TextView textView=(TextView)findViewById(R.id.DispName);
        Intent intent = getIntent();
        final String name= intent.getStringExtra("myname");

        if (name.toString().equals(""))
            textView.setText("Hello User. Please answer the questions honestly and take your time!");
        else
        textView.setText("Hello " + name + ". Please answer the questions honestly and take your time!");

        submitbutton=(Button)findViewById(R.id.button3);
        quitbutton=(Button)findViewById(R.id.buttonquit);
        tv=(TextView) findViewById(R.id.tvque);

        radio_g=(RadioGroup)findViewById(R.id.answersgrp);
        rb1=(RadioButton)findViewById(R.id.radioButton);
        rb2=(RadioButton)findViewById(R.id.radioButton2);
        rb3=(RadioButton)findViewById(R.id.radioButton3);
        rb4=(RadioButton)findViewById(R.id.radioButton4);
        tv.setText(questions[flag]);
        rb1.setText(opt[0]);
        rb2.setText(opt[1]);
        rb3.setText(opt[2]);
        rb4.setText(opt[3]);

        rb1.setOnClickListener(this);
        rb2.setOnClickListener(this);
        rb3.setOnClickListener(this);
        rb4.setOnClickListener(this);

        submitbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //int color = mBackgroundColor.getColor();
                //mLayout.setBackgroundColor(color);

                if(radio_g.getCheckedRadioButtonId()==-1)
                {
                    Toast.makeText(getApplicationContext(), "Please select one choice " + name, Toast.LENGTH_SHORT).show();
                    return;
                }

                RadioButton uans = (RadioButton) findViewById(radio_g.getCheckedRadioButtonId());
                String ansText = uans.getText().toString();
                Toast.makeText(getApplicationContext(), ansText, Toast.LENGTH_SHORT).show();
                //if(ansText.equals(answers[flag])) {
                //    correct++;
                //    Toast.makeText(getApplicationContext(), "Correct", Toast.LENGTH_SHORT).show();
                //}
                //else {
                //    wrong++;
                //    Toast.makeText(getApplicationContext(), "Wrong", Toast.LENGTH_SHORT).show();
                //}

                flag++;



                if(flag<questions.length)
                {
                    tv.setText(questions[flag]);
                    rb1.setText(opt[flag*4]);
                    rb2.setText(opt[flag*4 +1]);
                    rb3.setText(opt[flag*4 +2]);
                    rb4.setText(opt[flag*4 +3]);
                }
                else
                {
                    marks=correct;
                    //Intent in = new Intent(getApplicationContext(),ResultActivity.class);
                    Intent intent=new Intent(getApplicationContext(),photoActivity.class);
                    startActivity(intent);
                    //startActivity(in);
                }
                radio_g.clearCheck();
            }
        });

        quitbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),ResultActivity.class);
                startActivity(intent);
            }
        });
    }



    private void get_shared_values(String blubb){
        SharedPreferences sharedPreferences = getSharedPreferences(QuestionsActivity.SHARED_PREFS, QuestionsActivity.MODE_PRIVATE);
        int real_risk = sharedPreferences.getInt(blubb, 0);
    }

    private void toast_message(String message){
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.radioButton:
                lowrisk = lowrisk + 1;
                risk = risk + 0;
                break;

            case R.id.radioButton2:
                lowrisk = lowrisk + 1;
                risk = risk + 4;
                break;

            case R.id.radioButton3:
                highrisk = highrisk + 1;
                risk = risk + 7;
                break;

            case R.id.radioButton4:
                highrisk = highrisk + 1;
                risk = risk + 10;
                break;
        }
        share_risk(risk);
        share_low_risk(lowrisk);
        share_high_risk(highrisk);
    }

    private void share_risk(int risk) {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(RISK, risk);
        editor.apply();
    }
    private void share_low_risk(int risk) {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(LOW_RISK, risk);
        editor.apply();
    }
    private void share_high_risk(int risk) {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(HIGH_RISK, risk);
        editor.apply();
    }
}