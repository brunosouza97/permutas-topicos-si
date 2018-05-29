package com.example.brunosouza.forcemusic.fragments;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.example.brunosouza.forcemusic.R;
import com.example.brunosouza.forcemusic.adapter.MyAdapter;
import com.example.brunosouza.forcemusic.database.DBAdapter;
import com.example.brunosouza.forcemusic.pojo.Historico;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;


public class HistoricoFragment extends Fragment{

    private static final String TAG = "HistoricoFragment";

    EditText dataTxt, timeTxt;
    RecyclerView rv;
    MyAdapter adapter;
    ArrayList<Historico> historicos = new ArrayList<>();

    SwipeRefreshLayout swipeRefreshLayout;

    public HistoricoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle(R.string.historico_frag);
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_historico, container, false);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab_historico_fragment);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });

        rv = (RecyclerView) view.findViewById(R.id.myRecycler);

        //SET ITS PROPS
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setHasFixedSize(true);

        adapter = new MyAdapter(getContext(), historicos);
        rv.setAdapter(adapter);

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swiper);

        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getUpdates();
                    }
                }, 3000);
            }
        });

        return view;
    }

    private void showDialog() {
        Dialog d = new Dialog(getContext());

        final Calendar myCalendar = Calendar.getInstance();
        final int hora = myCalendar.get(Calendar.HOUR_OF_DAY);
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

        //NO TITLE
        d.requestWindowFeature(Window.FEATURE_NO_TITLE);

        //layout of dialog
        d.setContentView(R.layout.custom_layout);

        dataTxt = (EditText) d.findViewById(R.id.dataEditTxt);
        timeTxt = (EditText) d.findViewById(R.id.horaEditTxt);

        Button saveBtn= (Button) d.findViewById(R.id.saveBtn);

        //ONCLICK LISTENERS
        dataTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getContext(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        timeTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog tpd = new TimePickerDialog (getContext(),
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                timeTxt.setText(hourOfDay + ":" + minute);
                            }
                        }, hora, minuto, true);
                tpd.show();
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validaCampos()){
                    save(dataTxt.getText().toString(), timeTxt.getText().toString());
                    getUpdates();
                }
            }
        });

        //SHOW DIALOG
        d.show();
    }

    public boolean validaCampos(){
        if (dataTxt.getText().toString().length() == 0) {
            dataTxt.requestFocus();
            dataTxt.setError(getString(R.string.erroData));
            return false;
        } if (timeTxt.getText().toString().length() == 0){
            timeTxt.requestFocus();
            timeTxt.setError(getString(R.string.erroHora));
            return false;
        } else {
            dataTxt.setError(null);
            timeTxt.setError(null);
        }
        return true;
    }

    //SAVE
    private void save(String data, String hora) {
        DBAdapter db = new DBAdapter(getContext());

        //OPEN
        db.openDB();

        //INSERT
        long result = db.Insert(data, hora);

        if(result > 0) {
            dataTxt.setText("");
            timeTxt.setText("");
        } else {
            Snackbar.make(dataTxt,"Unable To Insert",Snackbar.LENGTH_SHORT).show();
        }

        //CLOSE
        db.close();

    }

    private void getUpdates(){
        historicos.clear();

        DBAdapter db = new DBAdapter(getContext());
        db.openDB();
        Cursor c = db.getAllHistoricos();

        while (c.moveToNext()){
            int id = c.getInt(0);
            String data = c.getString(1);
            String hora = c.getString(2);

            Historico h = new Historico(data, hora, id);

            historicos.add(h);
        }

        db.close();

        adapter.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(TAG, "onAttach");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "onActivityCreated");
    }

    @Override
    public void onStart(){
        super.onStart();
        Log.d(TAG, "onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
        getUpdates();
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "onDetach");
    }

}
