package com.example.lab2.dao;

import static androidx.core.content.PackageManagerCompat.LOG_TAG;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.example.lab2.MainActivity;
import com.example.lab2.R;
import com.example.lab2.database.DbHelper;
import com.example.lab2.model.ToDoModel;

import java.util.ArrayList;

public class ToDoDao {
    private DbHelper dbHelper;
    private SQLiteDatabase database;
    private  Context context;
    private  ArrayList<ToDoModel> list;

    public ToDoDao(Context context) {
        dbHelper = new DbHelper(context); // goi lenh tao db
        // database = dbHelper.getWritableDatabase();
    }

    public long Add(ToDoModel td) {
        ContentValues cv = new ContentValues();
        cv.put("id", td.getId());
        cv.put("title", td.getTitle());
        cv.put("content", td.getContent());
        cv.put("date", td.getDate());
        cv.put("type", td.getType());

        long check = database.insert("TODO", null, cv);
        return check;
    }

    public boolean delete(int id) {
        SQLiteDatabase data = dbHelper.getWritableDatabase();
        int row = data.delete("TODO", "id=?", new String[]{String.valueOf(id)});
        return row != -1;

    }

    public boolean update(ToDoModel td) {
        database = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("TITLE", td.getTitle());
        cv.put("CONTENT", td.getContent());
        cv.put("DATE", td.getDate());
        cv.put("TYPE", td.getType());

        int check = database.update("TODO", cv, "id=?", new String[]{String.valueOf(td.getId())});

        return check != -1;
    }

    public boolean updateStatus(Integer id, boolean check) {
        SQLiteDatabase sql = dbHelper.getWritableDatabase();
        // 1 la true, 0 la false
        int status = check ? 1 : 0;
        // sd contenvalue de update
        ContentValues cv = new ContentValues();
        cv.put("TYPE", status);
        long row = sql.update("TODO", cv, "id=?", new String[]{String.valueOf(id)});
        return row != -1;
    }

    public ArrayList<ToDoModel> getAll() {
        ArrayList<ToDoModel> list = new ArrayList<>();
        database = dbHelper.getWritableDatabase();
        database.beginTransaction();
        try {
            Cursor cursor = database.rawQuery("SELECT * FROM TODO", null);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    list.add(new ToDoModel(cursor.getInt(0),
                            cursor.getString(1),
                            cursor.getString(2),
                            cursor.getString(3),
                            cursor.getString(4),
                            cursor.getInt(5)));

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e("abc", e.getMessage());
        } finally {
            database.endTransaction();
        }
        return list;

    }




}
