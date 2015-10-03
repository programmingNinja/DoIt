package com.example.rshah4.doit;

import android.util.Log;
import android.view.View;

import org.apache.commons.io.FileUtils;
import android.content.Context;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rshah4 on 9/24/15.
 */
public class TaskDAOIMPL extends MainActivity implements TaskDAO {

    String fileName = "todo.txt";
    DoItDatabase db;
    Context appContext ;

    @Override
    public void setAppContext(Context appContext) {
        this.appContext = appContext;
        db = DoItDatabase.getInstance(this.appContext);
    }


    @Override
    public List<Tasks> getTasks() {

        List<Tasks> allTasks = new ArrayList<Tasks>();


        allTasks = db.getAllTasks();
        /* For file input output

        File file = this.getFile(fileName);
        List<String> lines = null;
        try {
            lines = new ArrayList<String>(FileUtils.readLines(file));
        } catch (IOException e) {
            e.printStackTrace();
        }

        for(String line : lines) {
            Tasks t = new Tasks(line);
            allTasks.add(t);
        }
        */


        return allTasks;
    }

        @Override
    public Tasks getTask(int id) {
        return null;
    }

    @Override
    public void createTask(List<Tasks> tasks) {

        /* File operation
        File file = this.getFile(fileName);

        System.out.println("Adding items to file...");
        List<String> items = new ArrayList<String>();
        for(Tasks t : tasks) {
            items.add(t.getTask());
            System.out.println("Adding items "+t.getTask());
        }
        try {
            FileUtils.writeLines(file, items, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Items added to file...");
        return false;*/
        db.addTasks(tasks);
    }

    @Override
    public void updateTask(Tasks t) {
        db.updateTask(t);

    }
    public File getFile(String filename) {

        File fileDir = appContext.getFilesDir();
        File file = appContext.getFileStreamPath(filename);

        if(file.exists()) {
            System.out.println("file exist");
            return file;
        }
        else {
            System.out.println("creating new file");
            return new File(fileDir,fileName);
        }
    }
    @Override
    public void removeTask(Tasks task) {

        db.deleteTask(task);

        /* for file operation
        File file = this.getFile(fileName);
        // locate the lines
        // remove the lines from file
        return true;
        */
    }

}
