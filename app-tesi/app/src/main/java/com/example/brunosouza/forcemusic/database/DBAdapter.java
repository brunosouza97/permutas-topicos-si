package com.example.brunosouza.forcemusic.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DBAdapter {
    Context c;
    SQLiteDatabase db;
    DBHelper helper;

    public DBAdapter(Context ctx) {
        this.c = ctx;
        helper = new DBHelper(c);
    }

    //OPEN DB
    public DBAdapter openDB() {
        try {
            db = helper.getWritableDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return this;
    }

    //CLOSE
    public void close() {
        try {
            helper.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    //INSERT DATA TO DB
    public long Insert(String data, String hora) {
        try {
            ContentValues cv = new ContentValues();
            cv.put(Constants.DATA, data);
            cv.put(Constants.HORA, hora);

            return db.insert(Constants.TB_NAME, Constants.ROW_ID, cv);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    //RETRIEVE ALL PLAYERS
    public Cursor getAllHistoricos() {
        String[] columns = {Constants.ROW_ID, Constants.DATA, Constants.HORA};

        return db.query(Constants.TB_NAME, columns,null,null,null,null,null);
    }

    //UPDATE
    public long Update(int id, String data, String hora) {
        try {
            ContentValues cv = new ContentValues();
            cv.put(Constants.DATA,data);
            cv.put(Constants.HORA, hora);

            return db.update(Constants.TB_NAME, cv,Constants.ROW_ID+" = ?", new String[]{String.valueOf(id)});

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    //DELETE
    public long Delete(int id) {
        try {
            return db.delete(Constants.TB_NAME,Constants.ROW_ID+" = ?", new String[]{String.valueOf(id)});

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }
}

