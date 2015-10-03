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
    void createTask(List<Tasks> task);
    void updateTask(Tasks t);
    void removeTask(Tasks task);

    void setAppContext(Context applicationContext);
}
