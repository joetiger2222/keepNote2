package com.example.keeptake2;

import static android.content.Context.MODE_PRIVATE;
import static android.database.sqlite.SQLiteDatabase.findEditTable;
import static android.database.sqlite.SQLiteDatabase.openOrCreateDatabase;




import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.zip.Inflater;

public class WritingNoteFragment extends Fragment {
    public static MenuItem deleteBtn;
    static MenuItem pinBtn;
    static ListView listView;
    FloatingActionButton addBtn;
    static ArrayList<String> writingNotesList=new ArrayList<>();
    public static LinkedList<Integer> grayLinkedList=new LinkedList<>();
    static ArrayAdapter arrayAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.writitng_fragment_layout,container,false);
        listView=view.findViewById(R.id.listView);
        addBtn=view.findViewById(R.id.addButton);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(),noteActivity.class);
                startActivity(intent);}});


        setWritingNotesAdapter();
        if(writingNotesList.isEmpty())loadFromDBIntoWritingNotes();


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(grayLinkedList.isEmpty()) {openExistingNote(i);}
                else{
                    if(isGray(i))changeItemColorWhite(i);
                    else changeItemColorGray(i);
                    showeDeleteBtn(deleteBtn);showPinBtn(pinBtn);
                    if(grayLinkedList.isEmpty())hideDeleteBtn(deleteBtn);hidePinBtn(pinBtn);}}});
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(isGray(i))changeItemColorWhite(i);
                else changeItemColorGray(i);
                if(!grayLinkedList.isEmpty())
                {
                    showeDeleteBtn(deleteBtn);
                    showPinBtn(pinBtn);
                }
                else{
                    hideDeleteBtn(deleteBtn);
                    hidePinBtn(pinBtn);
                }
                return true;}});

    setHasOptionsMenu(true);
    return  view;
    }


    public void setWritingNotesAdapter(){
        arrayAdapter=new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1,writingNotesList);
        listView.setAdapter(arrayAdapter);}


    public void openExistingNote(int noteId){
        Intent intent=new Intent(getActivity(),recentNoteActivity.class);
        intent.putExtra("noteId",noteId);
        startActivity(intent);}


    public void loadFromDBIntoWritingNotes(){
        try {
            SQLiteDatabase sqLiteDatabase = getActivity().openOrCreateDatabase("WritingNoteDB", MODE_PRIVATE, null);
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

    }



    public static void hideDeleteBtn(MenuItem item){item.setVisible(false);}
    public static void showeDeleteBtn(MenuItem item){item.setVisible(true);}
    public static void hidePinBtn(MenuItem item){item.setVisible(false);}
    public static void showPinBtn(MenuItem item){item.setVisible(true);}
    public boolean isGray(int position){return grayLinkedList.contains(position);}
    public void changeItemColorGray(int position){
        addToGrayList(position);
        listView.getChildAt(position).setBackgroundColor(Color.GRAY);}

    public void changeItemColorWhite(int position){
        removeFrmGrayList(position);
        listView.getChildAt(position).setBackgroundColor(Color.WHITE);}

    public void addToGrayList(int position){grayLinkedList.add(position);}

    public void removeFrmGrayList(int position){
        int index=grayLinkedList.indexOf(position);
        grayLinkedList.remove(index);}

    public static void chngAllPstnsWhite(){
        for(int i=0;i<grayLinkedList.size();i++){
            if(grayLinkedList.get(i)<writingNotesList.size()){
                listView.getChildAt(grayLinkedList.get(i)).setBackgroundColor(Color.WHITE);}}}


    public void createDeleteDialog(){
        addToWillbeDeleted();
        new AlertDialog.Builder(getActivity())
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
                    }}).show();}




    public void moveNoteToTop(){

    }



    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu,menu);
        deleteBtn=menu.getItem(0);
        deleteBtn.setVisible(false);
        pinBtn=menu.getItem(1);
        pinBtn.setVisible(false);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.delete:createDeleteDialog();
            case R.id.pin:moveNoteToTop();
        }
        return true;
    }

    public static LinkedList<String>willbeDeleted=new LinkedList<>();
    public static void addToWillbeDeleted(){
        for(int i=0;i<grayLinkedList.size();i++){
            willbeDeleted.add(writingNotesList.get(grayLinkedList.get(i)));}}

    public void deleteFrmDB(){
        try {
            SQLiteDatabase sqLiteDatabase =getActivity().openOrCreateDatabase("WritingNoteDB", MODE_PRIVATE, null);
            sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS noteTable(title VARCHAR,noteText VARCHAR,noteFontSize int,noteBackGround int)");
            for(int i=0;i<willbeDeleted.size();i++) {
                String delete = "DELETE FROM noteTable WHERE title = " + "'"+willbeDeleted.get(i)+ "'";
                sqLiteDatabase.execSQL(delete);
            }
            sqLiteDatabase.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void deleteFrmWritingNoteList(){
        for(int i=0;i<willbeDeleted.size();i++){writingNotesList.remove(willbeDeleted.get(i));}
        notifyAdapter();}


    public static void notifyAdapter(){arrayAdapter.notifyDataSetChanged();}


}
