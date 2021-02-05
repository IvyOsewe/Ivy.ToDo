package com.example.ivytodo;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ivytodo.Adapter.ToDoAdapter;
import com.example.ivytodo.Model.ToDoModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class MainActivity extends AppCompatActivity implements DialogCloseListener {

    private DatabaseHandler db;

    private RecyclerView tasksRecyclerView;
    private ToDoAdapter tasksAdapter;
    private FloatingActionButton fab;


    private List<ToDoModel>taskList;

    //Initializing  string array list;
    List<String> Values = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        db = new DatabaseHandler(this);
        db.openDatabase();

        tasksRecyclerView = findViewById(R.id.tasksRecyclerView);
        tasksRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        tasksAdapter = new ToDoAdapter(db, this);
        tasksRecyclerView.setAdapter(tasksAdapter);

        ItemTouchHelper itemTouchHelper = new
                ItemTouchHelper(new RecyclerItemTouchHelper(tasksAdapter));
        itemTouchHelper.attachToRecyclerView(tasksRecyclerView);

        fab = findViewById(R.id.fab);


        taskList = db.getAllTasks();
        Collections.reverse(taskList);

        tasksAdapter.setTasks(taskList);

        Toast.makeText(this, String.valueOf(taskList), Toast.LENGTH_LONG).show();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddNewTask.newInstance().show(getSupportFragmentManager(), AddNewTask.TAG);
            }
        });
    }

     @Override
    public void handleDialogClose(DialogInterface dialog){
        taskList = ((DatabaseHandler) db).getAllTasks();
        Collections.reverse(taskList);
        tasksAdapter.setTasks(taskList);
        tasksAdapter.notifyDataSetChanged();

    }
}
