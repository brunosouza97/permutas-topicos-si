package com.example.brunosouza.forcemusic.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.brunosouza.forcemusic.R;
import com.example.brunosouza.forcemusic.componentes.ItemClickListener;

public class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    ImageView img;
    TextView dataTxt, timeTxt;

    ItemClickListener itemClickListener;

    public MyHolder(View itemView) {
        super(itemView);

        //ASSIGN
        img = (ImageView) itemView.findViewById(R.id.displayModel);
        dataTxt = (TextView) itemView.findViewById(R.id.dataTxt);
        timeTxt = (TextView) itemView.findViewById(R.id.horaTxt);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        this.itemClickListener.onItemClick(v, getLayoutPosition());
    }

    public void setItemClickListener(ItemClickListener ic) {
        this.itemClickListener = ic;
    }
}
