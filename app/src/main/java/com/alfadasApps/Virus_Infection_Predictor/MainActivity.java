package com.alfadasApps.Virus_Infection_Predictor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button startbutton=(Button)findViewById(R.id.button);
        Button aboutbutton=(Button)findViewById(R.id.button2);
        final EditText nametext=(EditText)findViewById(R.id.editName);

                startbutton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String name=nametext.getText().toString();
                        Intent intent=new Intent(getApplicationContext(),QuestionsActivity.class);
                        intent.putExtra("myname",name);
                        startActivity(intent);
                    }
                });

                aboutbutton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    Intent intent=new Intent(getApplicationContext(),DeveloperActivity.class);
                    startActivity(intent);
            }
        });
    }
}
