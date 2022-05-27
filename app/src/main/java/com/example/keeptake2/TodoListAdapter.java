package com.example.keeptake2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.LinkedList;

public class TodoListAdapter extends RecyclerView.Adapter<TodoListAdapter.WordViewHolder> {
    LinkedList<String>todoLinkedList=new LinkedList<>();
    LayoutInflater layoutInflater;

    public TodoListAdapter(Context context,LinkedList<String>todoLinkedList){
        layoutInflater=LayoutInflater.from(context);
        this.todoLinkedList=todoLinkedList;
    }

    @NonNull
    @Override
    public WordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View ItemView=layoutInflater.inflate(R.layout.todo_list_layout,parent,false);

        return new WordViewHolder(ItemView,this);
    }

    @Override
    public void onBindViewHolder(@NonNull WordViewHolder holder, @SuppressLint("RecyclerView") int position) {
        String mCurrent=todoLinkedList.get(position);
        //holder.word.setText(mCurrent);
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    holder.word.setPaintFlags(holder.word.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    holder.word.setTextColor(Color.GRAY);
                }
                else {
                    holder.word.setPaintFlags(holder.word.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
                    holder.word.setTextColor(Color.BLACK);
                }
            }
        });
    }

    @Override
    public int getItemCount() {return todoLinkedList.size();}

    class WordViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public  TextView word;
        public  CheckBox checkBox;
        final TodoListAdapter todoListAdapter;
        public WordViewHolder(@NonNull View itemView, TodoListAdapter todoListAdapter) {
            super(itemView);
            word=itemView.findViewById(R.id.word);
            checkBox=itemView.findViewById(R.id.checkBoxBtn);
            this.todoListAdapter=todoListAdapter;
        }







        @Override
        public void onClick(View view) {

        }
    }
}
