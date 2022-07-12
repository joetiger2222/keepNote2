package com.example.keeptake2;

import static com.example.keeptake2.WritingNoteFragment.writingNotesList;
import static com.example.keeptake2.fontSizeDialogRcntNote.rcntNoteFntSize;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

public class recentNoteActivity extends AppCompatActivity {
    private static int recentNoteColor =0;
    public static EditText recentNoteTitle,recentNote;
    public  static ConstraintLayout recentNoteLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent_note);
        recentNoteLayout=findViewById(R.id.recentNoteActivity);
        recentNoteTitle=findViewById(R.id.recentNoteTitle);
        recentNote=findViewById(R.id.recentNote);
        recentNoteTitle.setText(getNoteTitle(getNoteId()));
        loadNoteFromDB();
    }


    public int getNoteId(){
        Intent intent=getIntent();
        return intent.getIntExtra("noteId",-1);
    }

    public String getNoteTitle(int noteId){
        if(noteId!=-1)
            return WritingNoteFragment.writingNotesList.get(noteId);
        else
            return "error happened";
    }


    public void loadNoteFromDB(){
        try {
            SQLiteDatabase sqLiteDatabase = openOrCreateDatabase("WritingNoteDB", MODE_PRIVATE, null);
            sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS noteTable(title VARCHAR,noteText VARCHAR,noteFontSize int,noteBackGround int)");
            Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM noteTable", null);
            cursor.moveToFirst();
            int titleIndex = cursor.getColumnIndex("title");
            int noteTextIndex=cursor.getColumnIndex("noteText");
            int noteFontSizeIndex=cursor.getColumnIndex("noteFontSize");
            int noteBackGroundColorIndex=cursor.getColumnIndex("noteBackGround");
            while (cursor != null) {
                if((getNoteTitle(getNoteId())).equals(cursor.getString(titleIndex))){
                    recentNote.setText(cursor.getString(noteTextIndex));
                    rcntNoteFntSize=cursor.getInt(noteFontSizeIndex);
                    recentNote.setTextSize(rcntNoteFntSize);
                    recentNoteLayout.setBackgroundColor(cursor.getInt(noteBackGroundColorIndex));
                    break;
                }
                cursor.moveToNext();
            }
            sqLiteDatabase.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }




    public static void changeRcntNoteFontSze(int i){recentNote.setTextSize(i);}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.note_options_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.fontSizeSeekBar:
                fontSizeDialogRcntNote fontSizeDialogRcntNote=new fontSizeDialogRcntNote(recentNoteActivity.this);
                fontSizeDialogRcntNote.show();
                break;
            case R.id.backGroundColor:recentNoteBackGroundColor recentNoteBackGroundColor=new recentNoteBackGroundColor(recentNoteActivity.this);
            recentNoteBackGroundColor.show();

        }
        return true;
    }




    public static void changeWritingNoteToBlue(){
        recentNoteLayout.setBackgroundColor(Color.BLUE);
        recentNote.setTextColor(Color.WHITE);
    }
    public static void changeWritingNoteToOrange(){recentNoteLayout.setBackgroundColor(Color.YELLOW);}

    public static void changeWritingNoteToRed(){
        recentNoteLayout.setBackgroundColor(Color.RED);
        recentNote.setTextColor(Color.WHITE);
    }
    public static void changeWritingNoteToWhite(){
        recentNoteLayout.setBackgroundColor(Color.WHITE);
        recentNote.setTextColor(Color.BLACK);
    }


    public static void getNoteColor(){
        recentNoteColor = ((ColorDrawable)recentNoteLayout.getBackground()).getColor();}









    public void updateDB(){
        try {
            SQLiteDatabase sqLiteDatabase = openOrCreateDatabase("WritingNoteDB", MODE_PRIVATE, null);
            sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS noteTable(title VARCHAR,noteText VARCHAR,noteFontSize int,noteBackGround int)");
            String update = "UPDATE noteTable SET title = " +"'"+recentNoteTitle.getText().toString()+"'" + " , noteText = "+"'" + recentNote.getText().toString()+"'"+" , noteFontSize = "+"'"+rcntNoteFntSize+"'"+", noteBackGround = "+"'"+ recentNoteColor +"'" + " WHERE title = " +"'"+getNoteTitle(getNoteId())+"'";
            sqLiteDatabase.execSQL(update);
            sqLiteDatabase.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public int getRecentNoteTitleIndex(){return writingNotesList.indexOf(getNoteTitle(getNoteId()));}
    public void replaceNewRecentNoteTitle(int index){writingNotesList.set(index,recentNoteTitle.getText().toString());}


    @Override
    public void onBackPressed() {
        getNoteColor();
        updateDB();
        replaceNewRecentNoteTitle(getRecentNoteTitleIndex());
        WritingNoteFragment.notifyAdapter();
        super.onBackPressed();
    }
}
