package com.itaakash.android.demoapp;

import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.itaakash.android.demoapp.adapter.TodotaskAdapter;
import com.itaakash.android.demoapp.model.TodotaskModel;

import java.util.ArrayList;
import java.util.List;

public class TodotaskActivity  extends AppCompatActivity {
    private Toolbar toolbar;
    private FloatingActionButton fab_add;
    private RecyclerView recyclerView;
    List<TodotaskModel> todotaskModelList = new ArrayList<>();
    private TodotaskAdapter projectAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todotask);
        init();

    }

    private void init() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setTitleTextColor(Color.WHITE);
        fab_add = findViewById(R.id.fab_add);


        //placing toolbar in place of actionbar
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Task List");

        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTask();
            }
        });


        setdataList();
       // swipeDelete();

    }

    private void swipeDelete() {
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT | ItemTouchHelper.DOWN | ItemTouchHelper.UP) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                Toast.makeText(TodotaskActivity.this, "on Move", Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                Toast.makeText(TodotaskActivity.this, "on Swiped ", Toast.LENGTH_SHORT).show();
                //Remove swiped item from list and notify the RecyclerView
                int position = viewHolder.getAdapterPosition();
                todotaskModelList.remove(position);
                projectAdapter.notifyDataSetChanged();


            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

    }

    private void setdataList() {

         projectAdapter = new TodotaskAdapter(TodotaskActivity.this, todotaskModelList);
        recyclerView.setAdapter(projectAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(TodotaskActivity.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {

                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                new AlertDialog.Builder(TodotaskActivity.this)
                        .setTitle(R.string.delet)
                        .setMessage(R.string.delete_message)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                TodotaskModel deletedCourse = todotaskModelList.get(viewHolder.getAdapterPosition());


                                int position = viewHolder.getAdapterPosition();


                                todotaskModelList.remove(viewHolder.getAdapterPosition());


                                projectAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());

                                // below line is to display our snackbar with action.
                                Snackbar.make(recyclerView, deletedCourse.getTaskname(), Snackbar.LENGTH_LONG).setAction("Undo", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        todotaskModelList.add(position, deletedCourse);


                                        projectAdapter.notifyItemInserted(position);
                                    }
                                }).show();
                            }})
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                TodotaskModel deletedCourse = todotaskModelList.get(viewHolder.getAdapterPosition());


                                int position = viewHolder.getAdapterPosition();
                                todotaskModelList.add(position, deletedCourse);


                                projectAdapter.notifyItemInserted(position);
                                dialog.dismiss();
                            }
                        }).show();


            }
            // at last we are adding this
            // to our recycler view.
        }).attachToRecyclerView(recyclerView);


    }

    private void addTask() {
        Intent i =new Intent(TodotaskActivity.this,AddTodotaskActivity.class);

        startActivityForResult(i,1000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == 1000) {
                String task = data.getStringExtra("task");
                String taskdate = data.getStringExtra("taskdate");

                TodotaskModel todotaskModel = new TodotaskModel();
                todotaskModel.setTaskname(task);
                todotaskModel.setTasktime(taskdate);
                todotaskModelList.add(todotaskModel);
                projectAdapter.notifyDataSetChanged();


            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        // check if the request code is same as what is passed  here it is 2

    }

    }

