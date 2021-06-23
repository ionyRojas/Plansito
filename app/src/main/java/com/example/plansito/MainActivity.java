package com.example.plansito;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView tv;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("Plancito");

        tv = findViewById(R.id.textView);
    }

    public void goToCreateTask(View view) {
        Intent intent = new Intent(this, CreateTask.class);
        startActivity(intent);
    }
}