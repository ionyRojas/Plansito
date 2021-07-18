package com.example.plansito;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
        createNotificationChannel();
        setContentView(R.layout.activity_create_task);

        setTitle("New Task");

        initDatePicker();
        dateButton = findViewById(R.id.datePickerButton);
        dateButton.setText(getTodaysDate());
        timeButton = findViewById(R.id.cgChooseTime);
        timeButton.setText(currentTime());
        task = findViewById(R.id.task);
        description = findViewById(R.id.description);

        this.getIntentData();
    }

    public String currentTime() {
        //Gets the Current Time, and sets it to a simple Format

        Date currentDate = new Date();
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a");
        return timeFormat.format(currentDate);
    }

    private void getIntentData() {
        //Get the initial data for the ID, Name, Description, and DueDate

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
        //Gets The Current Date

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(dayOfMonth, month, year);
    }

    private void initDatePicker() {
        //Creates the popUp Date Time Picker.

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
        //Formats the Date into Month Day Year Format

        return getMonthFormat(month) + " " + dayOfMonth + " " + year;
    }

    private String getMonthFormat(int month) {
        //Format The Month to display the abbreviated Month Name

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
        //Opens the popUp Date Picker
        datePickerDialog.show();
    }

    public void popTimePicker(View view) {
        //Creates the PopUp Time Picker
        //Format The Time to include AM or PM
        TimePickerDialog.OnTimeSetListener onTimeSetListener = (view1, selectedHour, selectedMinute) -> {
            String am_pm;
            String minute;
            Calendar datetime = Calendar.getInstance();
            datetime.set(Calendar.HOUR_OF_DAY, selectedHour);
            datetime.set(Calendar.MINUTE, selectedMinute);
            if (datetime.get(Calendar.AM_PM) == Calendar.AM) {
                am_pm = "AM";
            } else {
                am_pm = "PM";
            }
            if (datetime.get(Calendar.MINUTE) < 10) {
                minute = "0" + Integer.toString(datetime.get(Calendar.MINUTE));
            } else {
                minute = Integer.toString(datetime.get(Calendar.MINUTE));
            }
            String timeFormat = (datetime.get(Calendar.HOUR) == 0) ? "12" : datetime.get(Calendar.HOUR) + "";
            timeButton.setText(timeFormat + ":" + minute + " " + am_pm);
        };

        int style = AlertDialog.THEME_HOLO_DARK;

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, style, onTimeSetListener, hour, minute, false);

        timePickerDialog.setTitle("Select Time");
        timePickerDialog.show();
    }

    public void createTask(View view) {
        //Inputs the New Task into the SQLite DataBase

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
                    "', due_date = '" +
                    dateText + " " + timeText +
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
            return;
        }


        Intent intent = new Intent(CreateTask.this, ReminderBroadcast.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(CreateTask.this, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        long timeAtButtonClick = System.currentTimeMillis();
        long tenSeconds = 100 * 10;
        alarmManager.set(AlarmManager.RTC_WAKEUP,
                timeAtButtonClick + tenSeconds,
                pendingIntent);
        finish();
    }

    private void createNotificationChannel() {
        //Creates the Notification Channel for our Notifications

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "PlancitoChannel";
            String description = "Plancito Description";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("notifyPlancito", name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}