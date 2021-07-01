package com.example.plansito;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView tv;
    private ListView listViewTask;


    DataSource dataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Plancito");
        tv = findViewById(R.id.textView);

        dataSource = new DataSource(this);
        dataSource.open();
        listViewTask = findViewById(R.id.listViewTask);
        this.getAllTask();
    }

    @Override
    protected void onPause() {
        super.onPause();
        dataSource.close();
    }

    @Override
    protected void onResume() {
        super.onResume();
        dataSource.open();
        this.getAllTask();
    }

    private void getAllTask() {
        SQLiteDatabase db = DataSource.dBConnection.getWritableDatabase();
        Cursor row = db.rawQuery
                ("select * from tasks ", null);
        if (!row.moveToFirst()) {
            Toast.makeText(this, "Error: yo don't have any tasks", Toast.LENGTH_LONG).show();
        } else { row.moveToPrevious(); }

        List<String> taskList = new ArrayList<String>();

        while  (row.moveToNext()) {
            taskList.add(row.getString(0) + " " + row.getString(1) + " " + row.getString(2) + " " + row.getString(3));

        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                taskList
        );
        listViewTask.setAdapter(arrayAdapter);
    }

    public void goToCreateTask(View view) {
        Intent intent = new Intent(this, CreateTask.class);
        startActivity(intent);
    }


}