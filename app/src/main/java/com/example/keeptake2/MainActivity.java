package com.example.keeptake2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {
    public MenuItem deleteBtn;
    ListView listView;
    static ArrayList<String> writingNotesList=new ArrayList<>();
    public static LinkedList<Integer> grayLinkedList=new LinkedList<>();
    static ArrayAdapter arrayAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView=findViewById(R.id.listView);
       // setWritingNotesAdapter();

        /*if(writingNotesList.isEmpty())loadFromDBIntoWritingNotes();*/

        /*listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(grayLinkedList.isEmpty()) {*//*openExistingNote(i);*//*}
                else{
                    if(isGray(i))changeItemColorWhite(i);
                    else changeItemColorGray(i);
                    showeDeleteBtn(deleteBtn);
                    if(grayLinkedList.isEmpty())hideDeleteBtn(deleteBtn);
                }
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(isGray(i))changeItemColorWhite(i);
                else changeItemColorGray(i);
                if(!grayLinkedList.isEmpty())showeDeleteBtn(deleteBtn);
                else hideDeleteBtn(deleteBtn);
                return true;}});*/
    }

/*    public void hideDeleteBtn(MenuItem item){item.setVisible(false);}
    public void showeDeleteBtn(MenuItem item){item.setVisible(true);}

    public boolean isGray(int position){return grayLinkedList.contains(position);}


    public void changeItemColorGray(int position){
        addToGrayList(position);
        listView.getChildAt(position).setBackgroundColor(Color.GRAY);
    }

    public void changeItemColorWhite(int position){
        removeFrmGrayList(position);
        listView.getChildAt(position).setBackgroundColor(Color.WHITE);
    }
    public void addToGrayList(int position){grayLinkedList.add(position);}
    public void removeFrmGrayList(int position){
        int index=grayLinkedList.indexOf(position);
        grayLinkedList.remove(index);
    }
    public void chngAllPstnsWhite(){
        for(int i=0;i<grayLinkedList.size();i++){
            if(grayLinkedList.get(i)<writingNotesList.size()){
                listView.getChildAt(grayLinkedList.get(i)).setBackgroundColor(Color.WHITE);
            }
        }
    }*/





/*    public void OpenNewNote(View view){
        Intent intent=new Intent(getApplicationContext(),noteActivity.class);
        startActivity(intent);
    }*/

 /*   public void openExistingNote(int noteId){
        Intent intent=new Intent(getApplicationContext(),recentNoteActivity.class);
        intent.putExtra("noteId",noteId);
        startActivity(intent);
    }*/



 /*   public void setWritingNotesAdapter(){
        arrayAdapter=new ArrayAdapter(this, android.R.layout.simple_list_item_1,writingNotesList);
        listView.setAdapter(arrayAdapter);
    }*/





/*    public void loadFromDBIntoWritingNotes(){
        try {
            SQLiteDatabase sqLiteDatabase = openOrCreateDatabase("WritingNoteDB", MODE_PRIVATE, null);
            sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS noteTable(title VARCHAR,noteText VARCHAR,noteFontSize int,noteBackGround int)");
            Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM noteTable", null);
            cursor.moveToFirst();
            int titleIndex = cursor.getColumnIndex("title");
            while (cursor != null) {
                writingNotesList.add(cursor.getString(titleIndex));
                cursor.moveToNext();

            }
            sqLiteDatabase.close();
        }catch (Exception e){
            e.printStackTrace();
        }

    }*/


/*    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        deleteBtn=menu.getItem(0);
        hideDeleteBtn(deleteBtn);
        return true;
    }*/


/*    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.delete:createDeleteDialog();
        }
        return true;
    }*/

   /* public void createDeleteDialog(){
        addToWillbeDeleted();
        new AlertDialog.Builder(MainActivity.this)
                .setTitle("delete alert")
                .setMessage("You are about to delete "+grayLinkedList.size()+" elements are you sure?")
                .setCancelable(true)
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {}})
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteFrmDB();
                        deleteFrmWritingNoteList();
                        chngAllPstnsWhite();
                        grayLinkedList.clear();
                        willbeDeleted.clear();
                        hideDeleteBtn(deleteBtn);
                    }}).show();
    }*/


/*
    public LinkedList<String>willbeDeleted=new LinkedList<>();
    public void addToWillbeDeleted(){
        for(int i=0;i<grayLinkedList.size();i++){
            willbeDeleted.add(writingNotesList.get(grayLinkedList.get(i)));
        }
    }

    public void deleteFrmDB(){
        try {
            SQLiteDatabase sqLiteDatabase = openOrCreateDatabase("WritingNoteDB", MODE_PRIVATE, null);
            sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS noteTable(title VARCHAR,noteText VARCHAR,noteFontSize int,noteBackGround int)");
            for(int i=0;i<willbeDeleted.size();i++) {
                String delete = "DELETE FROM noteTable WHERE title = " + "'"+willbeDeleted.get(i)+ "'";
                sqLiteDatabase.execSQL(delete);
            }
            sqLiteDatabase.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }*/

/*    public void deleteFrmWritingNoteList(){
        for(int i=0;i<willbeDeleted.size();i++){writingNotesList.remove(willbeDeleted.get(i));}
        notifyAdapter();
    }*/

    //public static void notifyAdapter(){arrayAdapter.notifyDataSetChanged();}

  /*  @Override
    public void onBackPressed() {
        if(grayLinkedList.isEmpty()) super.onBackPressed();
        else{
            //chngAllPstnsWhite();
            //hideDeleteBtn(deleteBtn);
            grayLinkedList.clear();
           // willbeDeleted.clear();
        }
    }
*/

  /*  public void openTodoListActivity(View view) {
        Intent intent=new Intent(this,todoList.class);
        startActivity(intent);

    }*/


}
