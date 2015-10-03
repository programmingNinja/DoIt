package com.example.rshah4.doit;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Date;

public class EditItemActivity extends AppCompatActivity {

    Tasks t;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setUpViews();


        //String taskName = getIntent().getStringExtra("taskName");
        //String inReplyTo = getIntent().getStringExtra("in_reply_to");
        //int code = getIntent().getIntExtra("code", 0);


        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }

    private void setUpViews() {
        Tasks u = (Tasks) getIntent().getParcelableExtra("taskName");
        t = u;
        final EditText tvUser = (EditText) findViewById(R.id.editTaskText);
        final Spinner statusSpinner = (Spinner) findViewById(R.id.statusDropdown);
        final Spinner prioritySpinner = (Spinner) findViewById(R.id.priorityDropdown);
        final DatePicker datePicker = (DatePicker) findViewById(R.id.datePicker);

        statusSpinner.setSelection(u.getStatus());
        prioritySpinner.setSelection(u.getPriority());
        tvUser.setText(u.getTask());

        Button editButton = (Button) findViewById(R.id.saveTaskButton);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent result = new Intent();
                System.out.println("from edit form " + statusSpinner.getSelectedItemPosition() + " " + prioritySpinner.getSelectedItemPosition());
                Tasks task = new Tasks(tvUser.getText().toString(), new Date().getTime(), datePicker.getDrawingTime(), statusSpinner.getSelectedItemPosition(), prioritySpinner.getSelectedItemPosition());
                task.setId(t.id);
                result.putExtra("editedTask", task);
                setResult(RESULT_OK, result);
                finish();
            }
        });

    }

    public void onEditItem(View view) {

        //aToDoAdapter.add(editText.getText().toString());
        t.setTask(((EditText) findViewById(R.id.editTaskText)).getText().toString());
        t.setStatus(((Spinner) findViewById(R.id.statusDropdown)).getSelectedItemPosition());
        ((EditText) findViewById(R.id.editTaskText)).setText("");
        System.out.println("inedit id="+t.id);
    }
    public void onSubmit(View v) {
        // closes the activity and returns to first screen
        this.finish();
    }


}
