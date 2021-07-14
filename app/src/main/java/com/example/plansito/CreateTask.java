package com.example.plansito;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Locale;

public class CreateTask extends AppCompatActivity {
    private DatePickerDialog datePickerDialog;
    private Button dateButton, timeButton;
    private TextView task, description;
    private String taskId, taskName, taskDescription, taskDueDate;
    int hour, minute;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);

        setTitle("New Task");

        initDatePicker();
        dateButton = findViewById(R.id.datePickerButton);
        dateButton.setText(getTodaysDate());

        timeButton = findViewById(R.id.cgChooseTime);

        task = findViewById(R.id.task);
        description = findViewById(R.id.description);

        this.getIntentData();
    }

    private void getIntentData() {
        Intent intent = getIntent();
        taskId = intent.getStringExtra(CustomAdapter.TASK_ID);
        taskName = intent.getStringExtra(CustomAdapter.TASK_NAME);
        taskDescription = intent.getStringExtra(CustomAdapter.TASK_DESCRIPTION);
        taskDueDate = intent.getStringExtra(CustomAdapter.TASK_DUE_DATE);

        if (taskId != null && !taskId.isEmpty()) {
            task.setText(taskName);
            description.setText(taskDescription);
        }
    }

    private String getTodaysDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(dayOfMonth, month, year);
    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date = makeDateString(dayOfMonth, month, year);
                dateButton.setText(date);
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);

        datePickerDialog = new DatePickerDialog(this, dateSetListener, year, month, dayOfMonth);
    }

    private String makeDateString(int dayOfMonth, int month, int year){
        return getMonthFormat(month) + " " + dayOfMonth + " " + year;
    }

    private String getMonthFormat(int month) {
        if(month == 2)
            return "FEB";
        if(month == 3)
            return "MAR";
        if(month == 4)
            return "APR";
        if(month == 5)
            return "MAY";
        if(month == 6)
            return "JUN";
        if(month == 7)
            return "JUL";
        if(month == 8)
            return "AUG";
        if(month == 9)
            return "SEP";
        if(month == 10)
            return "OCT";
        if(month == 11)
            return "NOV";
        if(month == 12)
            return "DEC";


        return "JAN";
    }

    public void openDatePicker(View view) {
        datePickerDialog.show();
    }

    public void popTimePicker(View view) {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int selectedHour, int selectedMinute) {
                hour = selectedHour;
                minute = selectedMinute;
                timeButton.setText(String.format(Locale.getDefault(), "%02d:%02d",hour,minute));
            }
        };

        int style = AlertDialog.THEME_HOLO_DARK;

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, style, onTimeSetListener, hour, minute, false);

        timePickerDialog.setTitle("Select Time");
        timePickerDialog.show();
    }

    public void createTask(View view) {
        SQLiteDatabase db = DataSource.dBConnection.getWritableDatabase();
        String nameMessage = task.getText().toString();
        String descriptionMessage = description.getText().toString();
        String dateText = dateButton.getText().toString();
        String timeText = timeButton.getText().toString();

        if (taskId != null) {
            String strSQL = "UPDATE tasks SET name = '" +
                    nameMessage +
                    "', description = '" +
                    descriptionMessage +
                    "' WHERE id = " +
                    Integer.parseInt(taskId);
            db.execSQL(strSQL);

            finish();
            return;
        }

        if (!nameMessage.isEmpty() && !descriptionMessage.isEmpty() && !dateText.isEmpty() && !timeText.isEmpty() ) {
            ContentValues newTask = new ContentValues();

            newTask.put("name", nameMessage);
            newTask.put("description", descriptionMessage);
            newTask.put("due_date", dateText + " " + timeText);
            db.insert("tasks", null, newTask);
            task.setText("");
            description.setText("");
        } else {
            Toast.makeText(this, "Error: you need to fill all the fields", Toast.LENGTH_LONG).show();
        }
    }
}