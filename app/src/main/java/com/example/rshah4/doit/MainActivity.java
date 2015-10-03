package com.example.rshah4.doit;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 1;

    // for items to insert in db
    //ArrayList<Tasks> addItems;

    // for items already in db
    ArrayList<Tasks> existingItems;
    //ArrayAdapter<Tasks> aToDoAdapter;
    customAdapter cAdapter;
    ListView lvItems;
    EditText editText;
    TaskDAO taskDAO;
    Tasks editTask;
    int editPos = -1;
    public void populateArrayItems() {
        System.out.println("populate array entered ");
        if(existingItems == null)
            existingItems = new ArrayList<Tasks>(taskDAO.getTasks());

        for (Tasks t : existingItems) {
            System.out.println("existing " + t.getTask() + " " + t.getId());
        }

        System.out.println("populate array leaving ");

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("on create called");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        taskDAO = new TaskDAOIMPL();
        //addItems = new ArrayList<Tasks>();
        taskDAO.setAppContext(this.getApplicationContext());
        populateTableView();

        /*lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                //aToDoAdapter.notifyDataSetChanged();
                cAdapter.notifyDataSetChanged();
                Tasks rem = existingItems.remove(position);
                System.out.println(rem.getTask() + " removed from Task list");
                taskDAO.removeTask(rem);
                return true;
            }
        });*/

        /*lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(MainActivity.this, EditItemActivity.class);
                editTask = existingItems.get(position);
                i.putExtra("taskName", existingItems.get(position));
                editPos = position;
                //i.putExtra("taskName", editTask);
                //i.putExtra("in_reply_to", "george");
                //i.putExtra("code", 400);
                //startActivity(i);
                startActivityForResult(i, REQUEST_CODE);

                System.out.println(editTask.getTask() + " edited from string list");
                //aToDoAdapter.notifyDataSetChanged();
                cAdapter.notifyDataSetChanged();

                }
            });*/

    }

    public void onItemClick(int position) {
        System.out.println("main onlcik");
        Intent i = new Intent(MainActivity.this, EditItemActivity.class);
        editTask = existingItems.get(position);
        System.out.println("sending task id "+editTask.id);
        i.putExtra("taskName", existingItems.get(position));
        editPos = position;
        //i.putExtra("taskName", editTask);
        //i.putExtra("in_reply_to", "george");
        //i.putExtra("code", 400);
        //startActivity(i);
        startActivityForResult(i, REQUEST_CODE);

        System.out.println(editTask.getTask() + " edited from string list");
        //aToDoAdapter.notifyDataSetChanged();
        cAdapter.notifyDataSetChanged();

    }

    public boolean onItemLongClick(int position) {
        //aToDoAdapter.notifyDataSetChanged();
        cAdapter.notifyDataSetChanged();
        Tasks rem = existingItems.remove(position);
        System.out.println("Existing item size " + existingItems.size());
        System.out.println(rem.getTask() + " removed from Task list");
        taskDAO.removeTask(rem);
        return true;
    }

    private void populateTableView() {
        System.out.println("populate table entered");
        populateArrayItems();
        //aToDoAdapter = new ArrayAdapter<Tasks>(this, android.R.layout.simple_list_item_1, existingItems);
        cAdapter = new customAdapter(this, existingItems, getResources());
        System.out.println("exisiting item size " + existingItems.size());
        lvItems = (ListView) findViewById(R.id.itemsListView);
        lvItems.setAdapter(cAdapter);
        editText = (EditText) findViewById(R.id.etEditText);
        System.out.println("populate table leaving");

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        System.out.println("add activityresult entered");

        System.out.println(requestCode + "  " + resultCode);
        if(REQUEST_CODE == requestCode && resultCode == RESULT_OK) {
            Tasks updated = (Tasks)data.getParcelableExtra("editedTask");
            System.out.println("updated value " + updated.getTask() + " "+updated.getId());
            existingItems.get(editPos).setTask(updated.getTask());
            existingItems.get(editPos).setPriority(updated.getPriority());
            existingItems.get(editPos).setStatus(updated.getStatus());
            existingItems.get(editPos).setCompleteTime(updated.getCompleteTime());
            existingItems.get(editPos).setCreatedTime(updated.getCreatedTime());
            taskDAO.updateTask(updated);
            populateTableView();
            System.out.println("on activityresult leaving");

        }
    }

    /*public void launchComposeView() {
        // first parameter is the context, second is the class of the activity to launch
        Intent i = new Intent(MainActivity.this, EditItemActivity.class);
        i.putExtra("taskName", (Serializable) editTask);
        //i.putExtra("in_reply_to", "george");
        //i.putExtra("code", 400);
        startActivity(i); // brings up the second activity
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
        System.out.println("on resume called");
    }
    @Override
    public void onRestart() {
        super.onRestart();
        System.out.println("on restart called");
        //populateArrayItems();
        //aToDoAdapter.notifyDataSetChanged();
        cAdapter.notifyDataSetChanged();

    }
    @Override
    public void onPause() {
        super.onPause();
        System.out.println("on pause called");
    }
    @Override
    public void onStart() {
        super.onStart();
        System.out.println("on start called");
    }

    @Override
    public void onStop() {
        super.onStop();

        System.out.println("on stop entered");

        /*if(addItems.size() > 0) {
            for(Tasks t : addItems) {
                System.out.println("adding "+t.getTask());
            }
            taskDAO.createTask(addItems);
            addItems.clear();
            toDoItems.clear();
        }*/

        System.out.println("on stop left");
    }

    public void onAddItem(View view) {

        System.out.println("add item entered");
        //aToDoAdapter.add(editText.getText().toString());
        System.out.println("adding item");
        Tasks t = new Tasks();
        t.setTask(editText.getText().toString());
        //addItems.add(t);

        List<Tasks> temp = new ArrayList<Tasks>(){};
        temp.add(t);
        taskDAO.createTask(temp);
        existingItems.add(t);
        //aToDoAdapter.notifyDataSetChanged();
        cAdapter.notifyDataSetChanged();
        editText.setText("");
        System.out.println("add item leaving");

    }

}
