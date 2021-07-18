package com.example.plansito;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView listViewTask;
    List<Task> taskList;


    DataSource dataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Plancito");
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
        //Get all tasks items from the SQLiteDatabase and display them in the list view
        //If there are no active Items in the Database Create Toast

        SQLiteDatabase db = DataSource.dBConnection.getWritableDatabase();
        Cursor row = db.rawQuery
                ("select * from tasks WHERE is_active IS NOT 0 AND is_complete IS NOT 0", null);

        taskList=new ArrayList<>();

        if (row.moveToFirst()) {
            do {
                taskList.add(new Task(row.getString(0), row.getString(1), row.getString(2), row.getString(3)));
            } while(row.moveToNext());
        } else {
            Toast.makeText(this, "you don't have any tasks", Toast.LENGTH_LONG).show();
        }

        CustomAdapter arrayAdapter=new CustomAdapter(this, taskList);

        listViewTask.setAdapter(arrayAdapter);

        listViewTask.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Task task = taskList.get(i);
                Log.d("jonatest", task.name);
                Toast.makeText(getBaseContext(), task.description, Toast.LENGTH_LONG).show();
            }
        });
    }


    public void goToCreateTask(View view) {
        // When Create Task button is clicked this takes you to the CreateTask View

        Intent intent = new Intent(this, CreateTask.class);
        startActivity(intent);
    }


}