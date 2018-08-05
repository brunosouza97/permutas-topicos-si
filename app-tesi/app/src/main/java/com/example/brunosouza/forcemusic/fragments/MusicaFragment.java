/*
 * Espaço para JAVADOC
 * */

package com.example.brunosouza.forcemusic.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.example.brunosouza.forcemusic.R;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;


public class MusicaFragment extends Fragment {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference historic = database.getReference("Historic");

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle(R.string.musica_frag);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_musica, container, false);

        Button button = (Button) view.findViewById(R.id.playButton);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                // OU
                SimpleDateFormat dateFormat_hora = new SimpleDateFormat("HH:mm:ss");

                Date data = new Date();

                Calendar cal = Calendar.getInstance();
                cal.setTime(data);
                Date data_atual = cal.getTime();

                String data_completa = dateFormat.format(data_atual);

                String hora_atual = dateFormat_hora.format(data_atual);

                historic.push().setValue("Data: " + data_completa + ", Hora: " + hora_atual);
            }
        });


        return view ;
    }

}
