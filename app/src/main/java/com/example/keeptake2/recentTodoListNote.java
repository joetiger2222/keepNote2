package com.example.keeptake2;

import static com.example.keeptake2.RecentTodoListAdapter.newLine;
import static com.example.keeptake2.fontSizeDialogRcntNote.rcntNoteFntSize;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.LinkedList;

public class recentTodoListNote extends AppCompatActivity {
    LinkedList<String> RecentTodoLinkedList = new LinkedList<>();
    LinkedList<String> todoLines = new LinkedList<>();
    static LinkedList<Integer> todoCheckBoxStatus = new LinkedList<>();
    RecyclerView RecentRecyclerView;
    RecentTodoListAdapter RecentTodoListAdapter;
    EditText RecentTitle;
     boolean isFirstAdd=true;
    static FloatingActionButton addLineRecentTodoList;
    private String lastWordText="";
    int index=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent_todo_list_note);
        RecentTodoLinkedList.clear();
        loadNoteFromDB();
        RecentRecyclerView=findViewById(R.id.recViewRecentTodoList);
        addLineRecentTodoList=findViewById(R.id.addLineRecentTodoList);
        RecentTitle=findViewById(R.id.RecentTodoListTitle);
        RecentTodoListAdapter = new RecentTodoListAdapter(this, RecentTodoLinkedList);
        RecentRecyclerView.setAdapter(RecentTodoListAdapter);
        RecentRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        RecentTitle.setText(getNoteTitle(getNoteId()));
        notifyAdapter();
        index=TodoListFragment.todoListTitles.indexOf(RecentTitle.getText().toString());

    }



    public void notifyAdapter(){RecentTodoListAdapter.notifyDataSetChanged();}

    public void loadNoteFromDB(){
        try {
            SQLiteDatabase sqLiteDatabase = openOrCreateDatabase("WritingNoteDB", MODE_PRIVATE, null);
            sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS todoTable(checkBoxStatus int,todoText VARCHAR,title VARCHAR)");
            Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM todoTable", null);
            cursor.moveToFirst();
            int checkBoxStatus = cursor.getColumnIndex("checkBoxStatus");
            int todoText=cursor.getColumnIndex("todoText");
            int title=cursor.getColumnIndex("title");
            while (cursor != null) {
                if((getNoteTitle(getNoteId())).equals(cursor.getString(title))){
                    RecentTodoLinkedList.add(cursor.getString(todoText));
                    todoCheckBoxStatus.add(cursor.getInt(checkBoxStatus));
                }
                cursor.moveToNext();
            }
            sqLiteDatabase.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }



    public int getNoteId(){
        Intent intent=getIntent();
        return intent.getIntExtra("noteId",-1);
    }

    public String getNoteTitle(int noteId){
        if(noteId!=-1)
            return TodoListFragment.todoListTitles.get(noteId);
        else
            return "error happened";
    }



    public void addNewLineRecentTodoList(View view){//not working properly yet

        View view1= RecentRecyclerView.getChildAt(RecentTodoLinkedList.size()-1);
        EditText editText=view1.findViewById(R.id.word);
        lastWordText=editText.getText().toString();
        if(isFirstAdd){
            isFirstAdd=false;
            RecentTodoLinkedList.add("");
            todoCheckBoxStatus.add(0);
            notifyAdapter();
        }else{
            if(lastWordText.equals("")){

            }else {
                RecentTodoLinkedList.set(RecentTodoLinkedList.size() - 1, lastWordText);
                RecentTodoLinkedList.add("");
                todoCheckBoxStatus.add(0);
                notifyAdapter();
            }
        }

    }


    public void updateTodoNoteList(){
        TodoListFragment.todoListTitles.set(index,RecentTitle.getText().toString());
    }

    public void updateDB() {
        try {
            SQLiteDatabase sqLiteDatabase = openOrCreateDatabase("WritingNoteDB", MODE_PRIVATE, null);
            sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS todoTable(checkBoxStatus int,todoText VARCHAR,title VARCHAR)");
            for (int i = 0; i < RecentTodoLinkedList.size(); i++) {
                sqLiteDatabase.execSQL("UPDATE todoTable SET checkBoxStatus= " + "'" + todoCheckBoxStatus.get(i) + "'"+" , todoText = " +  "'" + RecentTodoLinkedList.get(i) + "'" + " ,title = " + "'" + RecentTitle.getText().toString() + "'"+"WHERE title ="+"'"+getNoteTitle(getNoteId())+"'");
            }
            sqLiteDatabase.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



/*
    public void addToDB() {
        try {
            SQLiteDatabase sqLiteDatabase = openOrCreateDatabase("WritingNoteDB", MODE_PRIVATE, null);
            sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS todoTable(checkBoxStatus int,todoText VARCHAR,title VARCHAR)");
            for (int i = 0; i < RecentTodoLinkedList.size(); i++) {
*//*
                sqLiteDatabase.execSQL("UPDATE todoTable SET checkBoxStatus= " + "'" + todoCheckBoxStatus.get(i) + "'"+" , todoText = " +  "'" + RecentTodoLinkedList.get(i) + "'" + " ,title = " + "'" + RecentTitle.getText().toString() + "'"+"WHERE title ="+"'"+getNoteTitle(getNoteId())+"'");
*//*
                sqLiteDatabase.execSQL("INSERT INTO todoTable (checkBoxStatus , todoText , title) values (" + "'" + todoCheckBoxStatus.get(i) + "'" + " , " + "'" + todoLines.get(i) + "'" + " , " + "'" + RecentTitle.getText().toString() + "'" + ")");
            }
            sqLiteDatabase.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void removeFrmDB(){
        try {
            SQLiteDatabase sqLiteDatabase = openOrCreateDatabase("WritingNoteDB", MODE_PRIVATE, null);
            sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS todoTable(checkBoxStatus int,todoText VARCHAR,title VARCHAR)");
            sqLiteDatabase.execSQL("DELETE  FROM todoTable WHERE title="+"'"+recentTitleBeforeEdit+"'");
            sqLiteDatabase.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    */



    @Override
    public void onBackPressed() {
        updateDB();
        updateTodoNoteList();
        TodoListFragment.arrayAdapter.notifyDataSetChanged();
        super.onBackPressed();
    }
}