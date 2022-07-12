package com.example.keeptake2;

import static com.example.keeptake2.FontSizeCustomDialog.fontSizeNewNote;
import static com.example.keeptake2.WritingNoteFragment.writingNotesList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;


public class noteActivity extends AppCompatActivity {
    private static int newNoteColor=0;
    EditText writingNoteTitle;
    public static EditText writingNote;
    public static ConstraintLayout noteActivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        writingNoteTitle=findViewById(R.id.writingNoteTitle);
        writingNote=findViewById(R.id.writingNote);
        noteActivity=findViewById(R.id.noteActivity);
    }


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
                FontSizeCustomDialog fontSizeCustomDialog=new FontSizeCustomDialog(noteActivity.this);
                fontSizeCustomDialog.show();
                break;
            case R.id.backGroundColor:
                BackGroundColor backGroundColor=new BackGroundColor(noteActivity.this);
                backGroundColor.show();
                break;
        }
        return true;
    }


    public static void changeFontSize(int fontSize){writingNote.setTextSize(fontSize);}

    public static void changeWritingNoteToBlue(){
        noteActivity.setBackgroundColor(Color.BLUE);
        writingNote.setTextColor(Color.WHITE);
    }
    public static void changeWritingNoteToOrange(){noteActivity.setBackgroundColor(Color.YELLOW);}

    public static void changeWritingNoteToRed(){
        noteActivity.setBackgroundColor(Color.RED);
        writingNote.setTextColor(Color.WHITE);
    }

    public static void changeWritingNoteToWhite(){
        noteActivity.setBackgroundColor(Color.WHITE);
        writingNote.setTextColor(Color.BLACK);
    }


    public static void getNoteColor(){newNoteColor = ((ColorDrawable)noteActivity.getBackground()).getColor();}


    public void saveNoteToDB(){
        try {
            SQLiteDatabase sqLiteDatabase = openOrCreateDatabase("WritingNoteDB", MODE_PRIVATE, null);
            sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS noteTable(title VARCHAR,noteText VARCHAR,noteFontSize int,noteBackGround int)");
            sqLiteDatabase.execSQL("INSERT INTO noteTable (title , noteText , noteFontSize , noteBackGround) values (" +"'"+writingNoteTitle.getText() +"'"+" , " +"'"+writingNote.getText()+"'"+ " , "+"'"+fontSizeNewNote+"'"+" , "+"'"+newNoteColor+"'"+")");
            sqLiteDatabase.close();
        }catch(Exception e){e.printStackTrace();}
    }


    public void updateWritingNoteList(){
        writingNotesList.add(writingNoteTitle.getText().toString());}


    public void createUnAcceptableTitleDialog(){
        new AlertDialog.Builder(noteActivity.this)
                .setTitle("Un Accepable Title")
                .setMessage("there is another note with the same title please rename your note title")
                .setCancelable(true)
                .setNegativeButton("Delete Note", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {noteActivity.super.onBackPressed();}
                })
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {}}).show();
    }

    
    @Override
    public void onBackPressed() {
        if(writingNotesList.contains(writingNoteTitle.getText().toString())){
            createUnAcceptableTitleDialog();
        }else if(writingNoteTitle.getText().toString().equals("")){super.onBackPressed();}
        else {
            getNoteColor();
            saveNoteToDB();
            updateWritingNoteList();
            WritingNoteFragment.notifyAdapter();
            super.onBackPressed();
        }
    }
}


