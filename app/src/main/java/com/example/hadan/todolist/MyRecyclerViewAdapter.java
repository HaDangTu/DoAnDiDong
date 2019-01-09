package com.example.hadan.todolist;


import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MyRecyclerViewAdapter extends
        RecyclerView.Adapter<MyRecyclerViewAdapter.RecyclerViewHolder> {

    private List<Note> data;
    private Context mContext;

    public final int VALUE_TILE = 1;
    public final int VALUE_CONTENT = 2;

    public void setData(List<Note> data) {this.data = data;}
    public List<Note> getData() {return this.data;}

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        TextView tileTV;
        TextView contentTV;
        Button btnDelete;
        LinearLayout noteItem;

        public RecyclerViewHolder(View itemView){
            super(itemView);

            tileTV = itemView.findViewById(R.id.tile);
            contentTV = itemView.findViewById(R.id.content);
            //btnDelete = itemView.findViewById(R.id.btnDel);
            noteItem = itemView.findViewById(R.id.note_item);
        }
    }

    public MyRecyclerViewAdapter(Context context, List<Note> data){

        this.data = data;
        this.mContext = context;

    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType){
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.list_row, viewGroup, false);

        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerViewHolder viewHolder, int position){
        Note note = data.get(position);
        viewHolder.tileTV.setText(note.getTile());

        viewHolder.noteItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int position = viewHolder.getLayoutPosition();
                Note note = data.get(position);
                Intent intent = new Intent(mContext, ViewNoteActivity.class);
                intent.putExtra("Tile", viewHolder.tileTV.getText().toString());
                intent.putExtra("Content", note.getContent());
                intent.putExtra("ID", note.getId());


                if (onItemClickedListenter != null)
                    onItemClickedListenter.onItemClick(intent);
            }
        });

        /*viewHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = viewHolder.getLayoutPosition();
                Note note = data.get(position);

                MyDatabaseAdapter myDatabaseAdapter;
                myDatabaseAdapter = new MyDatabaseAdapter(mContext);

                if (myDatabaseAdapter.Delete(note.getId()) == -1)
                    Toast.makeText(mContext, "Delete failed", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(mContext, "Delete successful", Toast.LENGTH_LONG).show();
                data.remove(position);
                notifyDataSetChanged();
            }
        });*/
    }

    public interface OnItemClickedListenter{
        void onItemClick(Intent intent);
    }

    private OnItemClickedListenter onItemClickedListenter;

    public void setOnItemClickedListenter(OnItemClickedListenter onItemClickedListenter) {
        this.onItemClickedListenter = onItemClickedListenter;
    }

    @Override
    public int getItemCount(){
        return data.size();
    }
}

