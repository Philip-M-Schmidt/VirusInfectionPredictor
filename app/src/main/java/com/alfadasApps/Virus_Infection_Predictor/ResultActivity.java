package com.alfadasApps.Virus_Infection_Predictor;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity {
    TextView tv, tv2, tv3;
    Button btnRestart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        tv = (TextView)findViewById(R.id.tvres);
        tv2 = (TextView)findViewById(R.id.tvres2);
        tv3 = (TextView)findViewById(R.id.tvres3);
        btnRestart = (Button) findViewById(R.id.btnRestart);

        SharedPreferences sharedPreferences = getSharedPreferences(QuestionsActivity.SHARED_PREFS, MODE_PRIVATE);
        int risk = sharedPreferences.getInt(QuestionsActivity.RISK, 0);
        int lowrisk = sharedPreferences.getInt(QuestionsActivity.LOW_RISK, 0);
        int highrisk = sharedPreferences.getInt(QuestionsActivity.HIGH_RISK, 0);

        tv.setText("Low risk responses: " + lowrisk);
        tv2.setText("High risk responses: " + highrisk);
        tv3.setText("Final infection risk: " + risk + "%");

        btnRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(in);
            }
        });
    }

}
