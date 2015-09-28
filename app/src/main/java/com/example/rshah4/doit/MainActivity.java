package com.example.rshah4.doit;

import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> toDoItems;
    ArrayList<Tasks> addItems;
    ArrayList<Tasks> existingItems;
    ArrayAdapter<String> aToDoAdapter;
    ListView lvItems;
    EditText editText;
    TaskDAO taskDAO;
    public void populateArrayItems() {
        toDoItems = new ArrayList<String>();
        existingItems = new ArrayList<Tasks>(taskDAO.getTasks());
        for (Tasks t : existingItems) {
            System.out.println("exisiting "+t.getTask());
            toDoItems.add(t.getTask());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("on create called");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        taskDAO = new TaskDAOIMPL();
        taskDAO.setAppContext(this.getApplicationContext());
        populateArrayItems();
        aToDoAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, toDoItems);
        lvItems = (ListView) findViewById(R.id.itemsListView);
        lvItems.setAdapter(aToDoAdapter);
        editText = (EditText) findViewById(R.id.etEditText);
        addItems = new ArrayList<Tasks>();

        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String removed = toDoItems.remove(position);
                System.out.println(removed + " removed from string list");
                aToDoAdapter.notifyDataSetChanged();
                Tasks rem = existingItems.remove(position);
                System.out.println(rem.getTask() + " removed from Task list");
                taskDAO.removeTask(rem.getTask());
                return true;
            }
        });

        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }

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
        populateArrayItems();
        aToDoAdapter.notifyDataSetChanged();

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

        if(addItems.size() > 0) {
            for(Tasks t : addItems) {
                System.out.println("adding "+t.getTask());
            }
            taskDAO.createTask(addItems);
            addItems.clear();
            toDoItems.clear();
        }

        System.out.println("on stop left");
    }

    public void onAddItem(View view) {

        //aToDoAdapter.add(editText.getText().toString());
        Tasks t = new Tasks();
        t.setTask(editText.getText().toString());
        addItems.add(t);
        toDoItems.add(editText.getText().toString());
        aToDoAdapter.notifyDataSetChanged();
        editText.setText("");
    }

}
