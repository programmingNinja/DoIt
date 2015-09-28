package com.example.rshah4.doit;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rshah4 on 9/24/15.
 */
public interface TaskDAO {

    List<Tasks> getTasks();
    Tasks getTask(int id);
    boolean createTask(List<Tasks> task);
    boolean updateTask(int id);
    boolean removeTask(String task);

    void setAppContext(Context applicationContext);
}
