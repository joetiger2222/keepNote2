package com.example.keeptake2;

import static com.example.keeptake2.FontSizeCustomDialog.fontSizeNewNote;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import java.util.LinkedList;

public class todoList extends AppCompatActivity {
    LinkedList<String> todoLinkedList = new LinkedList<>();
    LinkedList<String> todoLines = new LinkedList<>();
    LinkedList<Integer> todoCheckBoxStatus = new LinkedList<>();
    private String lastWordText="";
    boolean isFirstAdd=true;

    RecyclerView recyclerView;
    TodoListAdapter todoListAdapter;
    EditText title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list);
        todoLinkedList.add("testing");
        recyclerView = findViewById(R.id.recView);
        title=findViewById(R.id.todoListTitle);
        todoListAdapter = new TodoListAdapter(this, todoLinkedList);
        recyclerView.setAdapter(todoListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);


    }



    public void addNewLineTodoList(View view){

        View view1= recyclerView.getChildAt(todoLinkedList.size()-1);
        EditText editText=view1.findViewById(R.id.word);
        lastWordText=editText.getText().toString();
        if(isFirstAdd){
            isFirstAdd=false;
            todoLinkedList.add("");
            todoCheckBoxStatus.add(0);
            todoListAdapter.notifyDataSetChanged();
        }else{
            if(lastWordText.equals("")){

            }else {
                todoLinkedList.set(todoLinkedList.size() - 1, lastWordText);
                todoLinkedList.add("");
                todoCheckBoxStatus.add(0);
                todoListAdapter.notifyDataSetChanged();
            }
        }

    }


    public void addLinesToList() {
        for (int i = 0; i < recyclerView.getChildCount(); i++) {
            TodoListAdapter.WordViewHolder wordViewHolder = (TodoListAdapter.WordViewHolder) recyclerView.findViewHolderForAdapterPosition(i);
            todoLines.add(wordViewHolder.word.getText().toString());
            if (wordViewHolder.checkBox.isChecked()) todoCheckBoxStatus.add(1);
            else todoCheckBoxStatus.add(0);
        }

    }



    public void updateTodoNoteList(){TodoListFragment.todoListTitles.add(title.getText().toString());}


    public void createUnAcceptableTitleDialog(){
        new AlertDialog.Builder(todoList.this)
                .setTitle("Un Accepable Title")
                .setMessage("there is another note with the same title please rename your note title")
                .setCancelable(true)
                .setNegativeButton("Delete Note", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {todoList.super.onBackPressed();}
                })
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {}}).show();
    }



    @Override
    public void onBackPressed() {
        if(TodoListFragment.todoListTitles.contains(title.getText().toString())){
            createUnAcceptableTitleDialog();
        }else {
            addLinesToList();
            saveNoteToDB();
            updateTodoNoteList();
            TodoListFragment.arrayAdapter.notifyDataSetChanged();
            super.onBackPressed();
        }
    }

    public void saveNoteToDB() {
        try {
            SQLiteDatabase sqLiteDatabase = openOrCreateDatabase("WritingNoteDB", MODE_PRIVATE, null);
            sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS todoTable(checkBoxStatus int,todoText VARCHAR,title VARCHAR)");
            for (int i = 0; i < todoLines.size(); i++) {
                sqLiteDatabase.execSQL("INSERT INTO todoTable (checkBoxStatus , todoText , title) values (" + "'" + todoCheckBoxStatus.get(i) + "'" + " , " + "'" + todoLines.get(i) + "'" + " , " + "'" + title.getText().toString() + "'" + ")");
            }
            sqLiteDatabase.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}