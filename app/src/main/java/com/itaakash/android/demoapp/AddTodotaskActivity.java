package com.itaakash.android.demoapp;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.itaakash.android.demoapp.model.TodotaskModel;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Calendar;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class AddTodotaskActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{

    private Toolbar toolbar;
    private EditText et_task_name,date_picker_actions;
    int day, month, year, hour, minute;
    int myday, myMonth, myYear, myHour, myMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_task_activity);
        init();


    }

    private void init() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setTitleTextColor(Color.WHITE);
         et_task_name = findViewById(R.id.et_task_name);
        date_picker_actions = findViewById(R.id.date_picker_actions);
        Button btnSubmit = findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });Button btnClear = findViewById(R.id.btnClear);
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_task_name.setText("");
                date_picker_actions.setText("");
            }
        });

        date_picker_actions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveDatatime();
            }
        });


        //placing toolbar in place of actionbar
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Add Task");

        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    private void saveDatatime() {
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(AddTodotaskActivity.this, AddTodotaskActivity.this,year, month,day);
        datePickerDialog.show();




        datePickerDialog.show();

    }

    private void saveData() {
        if(et_task_name.getText().toString().isEmpty()){
            Toast.makeText(AddTodotaskActivity.this,"Please Enter Task Title",Toast.LENGTH_LONG).show();
            return;
        }if(date_picker_actions.getText().toString().isEmpty()){
            Toast.makeText(AddTodotaskActivity.this,"Please Enter Task Date and Time",Toast.LENGTH_LONG).show();
            return;
        }
        Intent intent=new Intent();
        intent.putExtra("task",et_task_name.getText().toString());
        intent.putExtra("taskdate",date_picker_actions.getText().toString());
        setResult(1000,intent);
        finish();//finishing activity
    }
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        myYear = year;
        myday = dayOfMonth;
        myMonth = month+1;
        Calendar c = Calendar.getInstance();
        hour = c.get(Calendar.HOUR);
        minute = c.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(AddTodotaskActivity.this, AddTodotaskActivity.this, hour, minute, DateFormat.is24HourFormat(this));
        timePickerDialog.show();
    }
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        myHour = hourOfDay;
        myMinute = minute;
        String dateTime =myday+"/"+myMonth+"/"+myYear +"    "+ myHour+":"+myMinute ;

        date_picker_actions.setText(dateTime);
    }


}