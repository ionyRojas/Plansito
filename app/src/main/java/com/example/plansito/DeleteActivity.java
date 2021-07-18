package com.example.plansito;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class DeleteActivity extends AppCompatActivity {
    //Deletes task from list displayed on main screen
    private String taskId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);

        this.getIntentData();
    }

    private void getIntentData() {
        //Receives data
        Intent intent = getIntent();
        taskId = intent.getStringExtra(CustomAdapter.TASK_ID);
    }

    public void onDeleteTask(View view) {
        //Shows a toast upon deletion
        Toast.makeText(this, "Your task has been deleted", Toast.LENGTH_LONG).show();

        if (taskId != null && !taskId.isEmpty()) {
            //Updates database
            SQLiteDatabase db = DataSource.dBConnection.getWritableDatabase();
            String strSQL = "UPDATE tasks SET is_active = '" +
                    0 +
                    "' WHERE id = " +
                    Integer.parseInt(taskId);
            db.execSQL(strSQL);

            finish();
            return;
        }

    }

    public void onNotDeleteTask(View view) {
        //Ends view and returns to main view.
        finish();
    }
}
