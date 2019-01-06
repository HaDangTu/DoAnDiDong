package com.example.hadan.todolist;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
public class MyRecyclerViewAdapter extends
        RecyclerView.Adapter<MyRecyclerViewAdapter.RecyclerViewHolder> {

    private List<Note> data;


    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        TextView tileTV;
        TextView contentTV;
        Button btnDelete;

        public RecyclerViewHolder(View itemView){
            super(itemView);
            tileTV = itemView.findViewById(R.id.tile);
            contentTV = itemView.findViewById(R.id.content);
            btnDelete = itemView.findViewById(R.id.btnDel);
        }
    }

    public MyRecyclerViewAdapter(List<Note> data){
        this.data = data;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType){
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.list_row, viewGroup, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder viewHolder, int position){
        Note note = data.get(position);
        viewHolder.tileTV.setText(note.getTile());
        viewHolder.contentTV.setText(note.getContent());
    }

    @Override
    public int getItemCount(){
        return data.size();
    }
}

