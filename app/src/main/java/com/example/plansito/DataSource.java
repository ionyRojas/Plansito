package com.example.plansito;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataSource {
    private Context context;
    private SQLiteDatabase db;
    public static SQLiteOpenHelper dBConnection;

    public DataSource(Context context) {
        this.context = context;
        dBConnection = new DBConnection(this.context);
        db = dBConnection.getWritableDatabase();
    }

    public void open () {
        db = dBConnection.getWritableDatabase();
    }

    public void close() {
        dBConnection.close();
    }

}
