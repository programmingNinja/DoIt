package com.example.rshah4.doit;

/**
 * Created by rshah4 on 9/24/15.
 */
public class Tasks {
    int id;
    String task;

    Tasks() {
    }

    Tasks(String task) {
        this.task = task;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }


}
