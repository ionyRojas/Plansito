package com.example.plansito;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class CustomAdapter extends BaseAdapter {
    Context context;
    List<Task> lst;

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
        TextView textViewTitle;
        TextView textViewDesc;
        Button delButton;
        Button editButton;

        Task task = lst.get(position);

        if (convertView==null)
            convertView= LayoutInflater.from(context).inflate(R.layout.list_view_task ,null);

        textViewTitle= convertView.findViewById(R.id.textViewTitle);
        textViewDesc=convertView.findViewById(R.id.textViewDesc);
        delButton=convertView.findViewById(R.id.delButton);
        editButton=convertView.findViewById(R.id.editButton);

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("jonaTest", task.id);
            }
        });

        delButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("jonaTest", task.id);
            }
        });

        textViewTitle.setText(task.name);
        textViewDesc.setText(task.description);

        return convertView;
    }
}
