package com.example.plansito;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class CustomAdapter extends BaseAdapter {
    //This class is used to as an adapter for the Edit Delete and Ready Buttons
    Context context;
    List<Task> lst;
    public static final String TASK_ID = "com.example.plansito.TASK_ID";
    public static final String TASK_NAME = "com.example.plansito.TASK_NAME";
    public static final String TASK_DESCRIPTION = "com.example.plansito.TASK_DESCRIPTION";
    public static final String TASK_DUE_DATE = "com.example.plansito.TASK_DUE_DATE";

    public CustomAdapter(Context context, List<Task> lst) {
        this.context = context;
        this.lst = lst;
    }

    @Override
    public int getCount() {
        return lst.size();
    }

    @Override
    public Object getItem(int position) {
        return lst.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //gets the view of the different elements in the app
        TextView textViewTitle;
        TextView textViewDesc;
        TextView textViewDueDate;
        Button delButton;
        Button editButton;
        Button readyButton;

        Task task = lst.get(position);

        if (convertView==null)
            convertView= LayoutInflater.from(context).inflate(R.layout.list_view_task ,null);

        textViewTitle= convertView.findViewById(R.id.textViewTitle);
        textViewDesc=convertView.findViewById(R.id.textViewDesc);
        textViewDueDate=convertView.findViewById(R.id.textViewDueDate);
        delButton=convertView.findViewById(R.id.delButton);
        editButton=convertView.findViewById(R.id.editButton);
        readyButton=convertView.findViewById(R.id.readyButton);

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Initiates the edit task view
                Log.d("jonaTest", task.id);
                Intent intent = new Intent(context, CreateTask.class);
                intent.putExtra(TASK_ID, task.id );
                intent.putExtra(TASK_NAME, task.name);
                intent.putExtra(TASK_DESCRIPTION, task.description);
                intent.putExtra(TASK_DUE_DATE, task.dueDate);

                context.startActivity(intent);
            }
        });

        delButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Initiates the delete task view
                Log.d("jonaTest", task.id);
                Intent intent = new Intent(context, DeleteActivity.class);
                intent.putExtra(TASK_ID, task.id );

                context.startActivity(intent);
            }
        });

        readyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Initiates the ready task view
                Log.d("jonaTest", task.id);
                Intent intent = new Intent(context, CompleteActivity.class);
                intent.putExtra(TASK_ID, task.id );

                context.startActivity(intent);
            }
        });

        textViewTitle.setText(task.name);
        textViewDesc.setText(task.description);
        textViewDueDate.setText(task.dueDate);

        return convertView;
    }
}
