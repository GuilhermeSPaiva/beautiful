package com.example.guilhermepaiva.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.util.Log;


public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

//        Button but = (Button) findViewById(R.id.next);
//        but.setOnClickListener(new View.OnClickListener() {
//                public void onClick(View v) {
//                    Intent it = new Intent(Main2Activity.this, MainActivity.class);
//                    startActivity(it);
//                }
//        });
    }

    public void startSecondActivity(View view) {

        Log.d("Main2Activity", "Botão");

        Intent secondActivity = new Intent(this, MainActivity.class);
        startActivity(secondActivity);
    }
}
