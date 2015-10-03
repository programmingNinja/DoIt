package com.example.rshah4.doit;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rshah4 on 10/1/15.
 */
public class customAdapter extends BaseAdapter {

    List<Tasks> tasksList = new ArrayList<Tasks>();
    private Activity activity;
    private static LayoutInflater inflater = null;
    public Resources res;

    public customAdapter(Activity a, ArrayList d,Resources resLocal) {

        /********** Take passed values **********/
        activity = a;
        tasksList = d;
        res = resLocal;

        /***********  Layout inflator to call external xml layout () ***********/
        inflater = ( LayoutInflater )activity.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return tasksList.size();
    }

    @Override
    public Tasks getItem(int position) {
        return tasksList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if(convertView==null)
        {
            convertView = inflater.inflate(R.layout.taskitem, parent,false);
        }
        TextView taskName = (TextView)convertView.findViewById(R.id.taskNameTextView);
        TextView priority = (TextView)convertView.findViewById(R.id.taskPriorityTextView);
        TextView status = (TextView)convertView.findViewById(R.id.taskStatusTextView);
        final Tasks task = tasksList.get(position);

        taskName.setText(task.getTask());
        priority.setText(PRIORITIES.values()[task.getPriority()]+"");
        status.setText(STATUS.values()[task.getStatus()]+"");

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("string: " + task.getTask());
                MainActivity sct = (MainActivity) activity;

                /****  Call  onItemClick Method inside CustomListViewAndroidExample Class ( See Below )****/

                sct.onItemClick(position);
                /*System.out.println("int: " + dataModel.getAnInt());
                System.out.println("double: " + dataModel.getaDouble());
                System.out.println("otherData: " + dataModel.getOtherData());*/
                //Toast.makeText(parent.getContext(), "view clicked: " + dataModel.getOtherData(), Toast.LENGTH_SHORT).show();
            }
        });

        convertView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
        public boolean onLongClick(View view) {
                System.out.println("string: " + task.getTask());
                MainActivity sct = (MainActivity) activity;

                /****  Call  onItemClick Method inside CustomListViewAndroidExample Class ( See Below )****/

                sct.onItemLongClick(position);
                return true;
            }
        });

        return convertView;

    }

}
