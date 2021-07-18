package com.example.plansito;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class CompleteActivity extends AppCompatActivity {
    private String taskId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete);

        this.getIntentData();
    }

    private void getIntentData() {
        Intent intent = getIntent();
        taskId = intent.getStringExtra(CustomAdapter.TASK_ID);
    }

    public void onCompleteTask(View view) {
        Toast.makeText(this, "Your task has been completed", Toast.LENGTH_LONG).show();

        if (taskId != null && !taskId.isEmpty()) {
            SQLiteDatabase db = DataSource.dBConnection.getWritableDatabase();
            String strSQL = "UPDATE tasks SET is_complete = '" +
                    0 +
                    "' WHERE id = " +
                    Integer.parseInt(taskId);
            db.execSQL(strSQL);

            finish();
            return;
        }

    }

    public void onNotCompleteTask(View view) {
        finish();
    }
}