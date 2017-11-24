package com.example.milan.milanscholtentodo;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Milan on 21-11-2017.
 */

public class TodoDatabase extends SQLiteOpenHelper {
    private static TodoDatabase instance;
    //Database info
    private static final String DATABASE_NAME ="TodoDatabase";
    private static final Integer DATABASE_VERSION = 1;
    private static final String TABLE_TODOS = "todos";

    private static final String KEY_TODO_ID = "_id";
    private static final String KEY_TODO_TITLE = "title";
    private static final String KEY_TODO_COMPLETED = "completed";

    private TodoDatabase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTableTodo = "CREATE TABLE " + TABLE_TODOS +
                "(" +
                KEY_TODO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                KEY_TODO_TITLE + " TEXT , " +
                KEY_TODO_COMPLETED + " INTEGER DEFAULT 0" +
                ")";
        sqLiteDatabase.execSQL(createTableTodo);
        sqLiteDatabase.execSQL("insert INTO todos (title) VALUES('houd')");
        sqLiteDatabase.execSQL("insert INTO todos (title) VALUES('je')");
        sqLiteDatabase.execSQL("insert INTO todos (title) VALUES('bek')");
        onUpgrade(sqLiteDatabase,1,1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        if ( i != i1) {
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_TODOS);
            onCreate(sqLiteDatabase);
        }
    }

    public Cursor selectAll() {
        String selectall = "SELECT * FROM todos";
        SQLiteDatabase db = instance.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectall, null);
        return cursor;
        }

    public static TodoDatabase getInstance(Context context) {
        if (instance == null) {
            return instance = new TodoDatabase(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
        else {
            return instance;
        }
    }

    public void insert(String item) {
        //String addItemtodo = "ÏNSERT INTO todos (title) VALUES('" + INPUTBLABLA +"')"';
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_TODO_TITLE, item);
        SQLiteDatabase db = instance.getWritableDatabase();
        Log.d("adding", "addData: Adding " + item + " to " + KEY_TODO_TITLE);
        db.insert(TABLE_TODOS, null, contentValues);
    }

    public void update(int id, int completed) {
        SQLiteDatabase db = instance.getWritableDatabase();
        String updateCompleted = "UPDATE " + TABLE_TODOS +
                " SET completed = " + completed + " WHERE _id = " + id + ";";
        db.execSQL(updateCompleted);
        Log.d("klaar", updateCompleted);
    }

    public void delete(int id) {
        SQLiteDatabase db = instance.getWritableDatabase();
        String DeleteTD = "DELETE FROM " + TABLE_TODOS + " WHERE _id = " + id + ";";
        db.execSQL(DeleteTD);
    }
    }