package com.example.lab2.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.lab2.model.ToDoModel;

import java.util.ArrayList;

public class DbHelper extends SQLiteOpenHelper {


    public DbHelper(Context context) {
        super(context, "ToDoDB", null, 1);
  
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE TODO (ID INTEGER PRIMARY KEY AUTOINCREMENT, " + "TITLE TEXT, CONTENT TEXT, DATE TEXT, TYPE TEXT, STATUS INTEGER)";
        db.execSQL(sql);

        String data = "INSERT INTO TODO VALUES (1, 'HOC JAVA', 'JAVA CO BAN', '27/2/2023','kho' , 0),"+
                "(2, 'HOC REACT', 'HOC REACT NATIVE CO BAN', '24/3/2023', 'de',0)," +
                "(3, 'HOC KOTLIN', 'HOC KTOLIN CO BAN', '1/3/1999', 'trung binh',0)";
        db.execSQL(data);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.execSQL("DROP TABLE IF EXISTS TODO");
            onCreate(db);
        }
       ;
    }
}
