package com.example.amanat.rcuproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Holder> {
    private Context context;
    private ArrayList<Worldpopulation> worldpopulationArrayList;
    private MainActivity mainActivity;

    public Adapter(ArrayList<Worldpopulation> worldpopulationArrayList, MainActivity mainActivity) {
        this.worldpopulationArrayList = worldpopulationArrayList;
        this.mainActivity = mainActivity;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(mainActivity.getBaseContext());
        View view = layoutInflater.inflate(R.layout.world_view, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, final int position) {
        holder.txtCountry.setText(String.valueOf(worldpopulationArrayList.get(position).getCountry()));
        holder.txtRank.setText(String.valueOf(worldpopulationArrayList.get(position).getRank()));
        holder.txtPopulation.setText(String.valueOf(worldpopulationArrayList.get(position).getPopulation()));
        Picasso.get().load(worldpopulationArrayList.get(position).getFlag()).fit().into(holder.imgFlag);
        holder.imgFlag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mainActivity, ImageFullScreen.class);
                Bundle bundle = new Bundle();
                bundle.putString("image", worldpopulationArrayList.get(position).getFlag());
                intent.putExtras(bundle);
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return worldpopulationArrayList.size();
    }
}
