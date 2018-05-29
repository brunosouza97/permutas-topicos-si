package com.example.brunosouza.forcemusic;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;


import com.example.brunosouza.forcemusic.database.DBAdapter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class DetailActivity extends AppCompatActivity {

    private static final String TAG = "DetailActivity";

    EditText dataTxt, timeTxt;
    Button updateBtn, deleteBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);
        setTitle(R.string.detalhes);

        FloatingActionButton fab = findViewById(R.id.fab_detail);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //RECEIVE DATA FROM HISTORICOFRAGMENT
        Intent i = getIntent();

        final int id = i.getExtras().getInt("ID");
        final String data = i.getExtras().getString("DATA");
        final String hora = i.getExtras().getString("HORA");

        updateBtn = (Button) findViewById(R.id.updateBtn);
        deleteBtn = (Button) findViewById(R.id.deleteBtn);

        dataTxt = (EditText) findViewById(R.id.dataEditTxt);
        timeTxt = (EditText) findViewById(R.id.horaEditTxt);

        //ASSIGN DATA TO THOSE VIEWS
        dataTxt.setText(data);
        timeTxt.setText(hora);

        /*                                                */
        final Calendar myCalendar = Calendar.getInstance();
        final int hour = myCalendar.get(Calendar.HOUR_OF_DAY);
        final int minuto = myCalendar.get(Calendar.MINUTE);

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

            private void updateLabel() {
                String myFormat = "MM/dd/yyyy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, new Locale("pt", "BR"));

                dataTxt.setText(sdf.format(myCalendar.getTime()));
            }

        };

        //ONCLICK
        dataTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(DetailActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        timeTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog tpd = new TimePickerDialog (DetailActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                timeTxt.setText(hourOfDay + ":" + minute);
                            }
                        }, hour, minuto, true);
                tpd.show();
            }
        });

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update(id, dataTxt.getText().toString(), timeTxt.getText().toString());
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View view = v;
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle(R.string.confirmar)
                        .setMessage(R.string.pergunta)
                        .setPositiveButton(R.string.deleteBtn, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                delete(id);
                            }
                        })
                        .setNegativeButton(R.string.cancelar, null).create().show();
            }
        });

    }

    private void update (int id, String newData, String newHora) {
        DBAdapter db = new DBAdapter(this);
        db.openDB();
        long result = db.Update(id, newData, newHora);

        if(result > 0) {
            dataTxt.setText(newData);
            timeTxt.setText(newHora);

            this.finish();
        } else {
            Snackbar.make(dataTxt, R.string.erroUpdate, Snackbar.LENGTH_SHORT)
                    .setAction("Action", null).show();
        }

        db.close();
    }

    //DELETE
    private void delete(int id) {
        DBAdapter db = new DBAdapter(this);
        db.openDB();
        long result = db.Delete(id);

        if(result > 0) {
            Snackbar.make(dataTxt, R.string.sucessoDelete, Snackbar.LENGTH_SHORT)
                    .setAction("Action", null).show();
            this.finish();
        } else {
            Snackbar.make(dataTxt, R.string.erroDelete, Snackbar.LENGTH_SHORT)
                    .setAction("Action", null).show();;
        }

        db.close();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "onRestart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }
}

