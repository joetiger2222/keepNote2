package com.example.keeptake2;

import static com.example.keeptake2.recentTodoListNote.addLineRecentTodoList;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.LinkedList;

public class RecentTodoListAdapter extends RecyclerView.Adapter<RecentTodoListAdapter.WordViewHolder> {
    LinkedList<String> todoLinkedList=new LinkedList<>();
    LayoutInflater layoutInflater;
    public static String newLine="";

    public RecentTodoListAdapter(Context context,LinkedList<String>todoLinkedList){
        layoutInflater=LayoutInflater.from(context);
        this.todoLinkedList=todoLinkedList;
    }


    @NonNull
    @Override
    public RecentTodoListAdapter.WordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View ItemView=layoutInflater.inflate(R.layout.todo_list_layout,parent,false);
        return new RecentTodoListAdapter.WordViewHolder(ItemView,this);
    }

    public boolean isCheckBoxChecked(int position){return recentTodoListNote.todoCheckBoxStatus.get(position)==1;}


    @Override
    public void onBindViewHolder(@NonNull RecentTodoListAdapter.WordViewHolder holder, int position) {
        holder.word.setText(todoLinkedList.get(position));
        if(isCheckBoxChecked(position)){
            holder.checkBox.setChecked(true);
            holder.word.setPaintFlags(holder.word.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.word.setTextColor(Color.GRAY);
        }
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



    class WordViewHolder extends RecyclerView.ViewHolder{
        public TextView word;
        public CheckBox checkBox;
        final RecentTodoListAdapter recentTodoListAdapter;
        public WordViewHolder(@NonNull View itemView, RecentTodoListAdapter recentTodoListAdapter) {
            super(itemView);
            word=itemView.findViewById(R.id.word);
            checkBox=itemView.findViewById(R.id.checkBoxBtn);
            this.recentTodoListAdapter=recentTodoListAdapter;
        }

    }


}
