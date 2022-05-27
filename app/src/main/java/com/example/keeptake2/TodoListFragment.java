package com.example.keeptake2;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.LinkedList;

public class TodoListFragment extends Fragment {
    @Nullable
    FloatingActionButton addBtn;
    static ArrayList<String> todoListTitles= new ArrayList<>();
    ListView todoTitlesListView;
    static ArrayAdapter arrayAdapter;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.todolist_fragment_layout,container,false);
        addBtn=view.findViewById(R.id.addTodoList);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(),todoList.class);
                startActivity(intent);
            }
        });
        todoTitlesListView=view.findViewById(R.id.todoListListView);
        settodoListAdapter();
        if(todoListTitles.isEmpty())loadFromDBIntoWritingNotes();
        todoTitlesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                openExistTodoList(i);
            }
        });
        return view;

    }




    public void openExistTodoList(int noteId){
        Intent intent=new Intent(getActivity(),recentTodoListNote.class);
        intent.putExtra("noteId",noteId);
        startActivity(intent);
    }






    public void settodoListAdapter() {
        arrayAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, todoListTitles);
        todoTitlesListView.setAdapter(arrayAdapter);
    }




       public void loadFromDBIntoWritingNotes(){
        try {
            SQLiteDatabase sqLiteDatabase = getActivity().openOrCreateDatabase("WritingNoteDB", MODE_PRIVATE, null);
            sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS todoTable(checkBoxStatus int,todoText VARCHAR,title VARCHAR)");
            Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM todoTable", null);
            cursor.moveToFirst();
            int titleIndex = cursor.getColumnIndex("title");
            String title="";
            while (cursor != null) {
                title=cursor.getString(titleIndex);
                if(!todoListTitles.contains(title))todoListTitles.add(title);
                cursor.moveToNext();

            }
            sqLiteDatabase.close();
        }catch (Exception e){
            e.printStackTrace();
        }

    }


}
