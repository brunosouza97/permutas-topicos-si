package com.example.brunosouza.forcemusic.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.brunosouza.forcemusic.DetailActivity;
import com.example.brunosouza.forcemusic.R;
import com.example.brunosouza.forcemusic.componentes.ItemClickListener;
import com.example.brunosouza.forcemusic.pojo.Historico;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyHolder> {
    Context c;
    ArrayList<Historico> historicos;
    //SwipeRefreshLayout swipeRefreshLayout;

    public MyAdapter(Context ctx, ArrayList<Historico> historicos) {
        //ASSIGN THEM LOCALLY
        this.c = ctx;
        this.historicos = historicos;

        //this.swipeRefreshLayout = swipeRefreshLayout;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //VIEW OBJ FROM XML
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.model,null);

        //holder
        MyHolder holder = new MyHolder(v);

        return holder;
    }

    //BIND DATA TO VIEWS
    @Override
    public void onBindViewHolder(MyHolder holder, int position) {

        holder.dataTxt.setText(historicos.get(position).getData());
        holder.timeTxt.setText(historicos.get(position).getHora());

        //HANDLE ITEMCLICKS
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                //OPEN DETAIL ACTIVITY
                //PASS DATA

                //CREATE INTENT
                Intent i = new Intent(c, DetailActivity.class);

                //LOAD DATA
                i.putExtra("DATA", historicos.get(pos).getData());
                i.putExtra("HORA", historicos.get(pos).getHora());
                i.putExtra("ID", historicos.get(pos).getId());

                //START ACTIVITY
                c.startActivity(i);
            }
        });
    }



    @Override
    public int getItemCount() {
        return historicos.size();
    }
}

