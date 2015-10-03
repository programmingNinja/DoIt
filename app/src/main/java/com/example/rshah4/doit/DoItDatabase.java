package com.example.rshah4.doit;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by rshah4 on 9/30/15.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DoItDatabase extends SQLiteOpenHelper {
    private static final String TAG = "InstagramClientDatabase";

    // Database info
    private static final String DATABASE_NAME = "doItDatabase";
    private static final int DATABASE_VERSION = 1;

    // Tables
    private static final String TABLE_TASKS = "tasks";

    // Constraints
    private static final String CONSTRAINT_TASK_PK = "task_pk";

    // Tasks table columns
    private static final String KEY_TASK_ID = "id";
    private static final String KEY_TASK_NAME = "taskName";
    private static final String KEY_TASK_CREATED_TIME = "createdTime";
    private static final String KEY_TASK_PRIORITY = "priority";
    private static final String KEY_TASK_STATUS = "status";
    private static final String KEY_TASK_COMPLETE_TIME = "completeTime";


    // Singleton instance
    private static DoItDatabase sInstance;

    /**
     * Constructor should be private to prevent direct instantiation.
     * make call to static method "getInstance()" instead.
     */
    private DoItDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized DoItDatabase getInstance(Context context) {
        if (sInstance == null) {
            // Use the application context, which will ensure that you
            // don't accidentally leak an Activity's context.
            // See this article for more information: http://bit.ly/6LRzfx
            sInstance = new DoItDatabase(context.getApplicationContext());
        }

        return sInstance;
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASKS);
        String CREATE_TASKS_TABLE = "CREATE TABLE " + TABLE_TASKS +
                "(" +
                KEY_TASK_ID + " INTEGER PRIMARY KEY," +
                KEY_TASK_NAME + " VARCHAR," +
                KEY_TASK_CREATED_TIME + " INTEGER, " +
                KEY_TASK_COMPLETE_TIME + " INTEGER, " +
                KEY_TASK_PRIORITY + " INTEGER, " +
                KEY_TASK_STATUS + " INTEGER " +
                ")";

        db.execSQL(CREATE_TASKS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASKS);

            onCreate(db);
        }
    }

    public void emptyAllTables() {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            db.delete(TABLE_TASKS, null, null);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.wtf(TAG, "Error while trying to empty tables in database");
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
    }

    public void addTasks(List<Tasks> tasks) {
        if (tasks == null) {
            throw new IllegalArgumentException(
                    String.format("Attemping to add a null list of tasks to %s", DATABASE_NAME));
        }

        // should be done off UI thread
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            for (Tasks task : tasks) {
                //long userId = updateTask(task);

                ContentValues values = new ContentValues();
                values.put(KEY_TASK_CREATED_TIME, task.createdTime);
                values.put(KEY_TASK_COMPLETE_TIME, task.completeTime);
                values.put(KEY_TASK_NAME, task.task);
                values.put(KEY_TASK_PRIORITY, task.priority);
                values.put(KEY_TASK_STATUS, task.status);

                long taskId = db.insert(TABLE_TASKS, null, values);
                System.out.println("new task "+task.task+" taskid "+taskId);
                task.setId((int)taskId);

            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.wtf(TAG, "Error while trying to add tasks to database");
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
    }

    public long updateTask(Tasks task) {
        if (task == null) {
            throw new IllegalArgumentException(
                    String.format("Attemping to add a null task to %s", DATABASE_NAME));
        }

        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TASK_CREATED_TIME, task.createdTime);
        values.put(KEY_TASK_COMPLETE_TIME, task.completeTime);
        values.put(KEY_TASK_NAME, task.task);
        values.put(KEY_TASK_PRIORITY, task.priority);
        values.put(KEY_TASK_STATUS, task.status);
        long taskId = -1;

        // First try to update the user in case the user already exists in DB
        System.out.println("updating "+ task.toString());
        int rows = 0;
        try {
            rows = db.update(
                    TABLE_TASKS,
                    values,
                    KEY_TASK_ID + "= ?",
                    new String[]{Long.toString(task.id)});
        } catch (SQLException e) {
            Log.e("Error writing new Task", e.toString());
        }

        // Check if update succeeded
        if (rows < 1) {
            System.out.println(" newinserting in update "+task.task);
            taskId = db.insert(TABLE_TASKS, null, values);
        }
        else {
            System.out.println("updated "+task.toString());
        }

        return taskId;
    }

    public void deleteTask(Tasks task) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TASKS, KEY_TASK_ID + " = ?",
                new String[] { String.valueOf(task.id) });
        db.close();
    }

    public List<Tasks> getAllTasks() {
        List<Tasks> tasks = new ArrayList<>();

        String tasksSelectQuery = "SELECT * FROM " + TABLE_TASKS ;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor tasksCursor = db.rawQuery(tasksSelectQuery, null);

        try {
            if (tasksCursor.moveToFirst()) {
                do {
                    Tasks task = new Tasks();
                    task.id =
                            tasksCursor.getInt(
                                    tasksCursor.getColumnIndexOrThrow(KEY_TASK_ID));
                    task.task =
                            tasksCursor.getString(
                                    tasksCursor.getColumnIndexOrThrow(KEY_TASK_NAME));
                    task.createdTime =
                            tasksCursor.getLong(
                                    tasksCursor.getColumnIndexOrThrow(KEY_TASK_CREATED_TIME));

                    task.completeTime =
                            tasksCursor.getLong(
                                    tasksCursor.getColumnIndexOrThrow(KEY_TASK_COMPLETE_TIME));
                    task.status =
                            tasksCursor.getInt(
                                tasksCursor.getColumnIndexOrThrow(KEY_TASK_STATUS));
                    task.priority =
                            tasksCursor.getInt(
                                tasksCursor.getColumnIndexOrThrow(KEY_TASK_PRIORITY));

                    tasks.add(task);
                } while (tasksCursor.moveToNext());
            }
        } catch (Exception e) {
            Log.wtf(TAG, "Error while trying to get posts from database");
            e.printStackTrace();
        } finally {
            closeCursor(tasksCursor);
        }

        return tasks;
    }

    private void closeCursor(Cursor cursor) {
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
    }
}

