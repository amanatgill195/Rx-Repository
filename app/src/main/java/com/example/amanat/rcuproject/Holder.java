package com.example.amanat.rcuproject;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class Holder extends RecyclerView.ViewHolder {
    public TextView txtRank, txtPopulation, txtCountry;
    public ImageView imgFlag;

    public Holder(@NonNull View itemView) {
        super(itemView);

        txtRank = itemView.findViewById(R.id.txtRank);
        txtCountry = itemView.findViewById(R.id.txtCountry);
        txtPopulation = itemView.findViewById(R.id.txtPopulation);
        imgFlag = itemView.findViewById(R.id.imgFlag);
    }
}
