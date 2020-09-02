package com.abiyedanagogo.noteapp.notes;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.abiyedanagogo.noteapp.FirstActivity;
import com.abiyedanagogo.noteapp.R;

import java.util.Calendar;

/*
 * Created by Abiye Danagogo on 20/04/2020.
 * This is the Activity that is used when a user opens a note that has already been saved with the intention of either
 * updating or deleting the note
 * */

public class UpdateActivity extends AppCompatActivity {
    Toolbar toolbar;
    Note note;
    EditText noteDetails, noteTitle;
    Calendar c;
    String todaysDate;
    String currentTime;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        Intent intent = getIntent();
        note = intent.getParcelableExtra("Note");


        noteTitle = findViewById(R.id.noteTitle);
        noteDetails = findViewById(R.id.noteDetails);
        noteDetails.setSingleLine(false);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        getSupportActionBar().setTitle(note.getTitle());
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        noteTitle.setText(note.getTitle());
        noteDetails.setText(note.getContent());

        noteTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() != 0) {
                    getSupportActionBar().setTitle(s);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //get current date and time
        c = Calendar.getInstance();
        todaysDate = pad(c.get(Calendar.DAY_OF_MONTH)) + "/" + pad(c.get(Calendar.MONTH) + 1) + "/" + c.get(Calendar.YEAR);
        currentTime = pad(c.get(Calendar.HOUR_OF_DAY)) + ":" + pad(c.get(Calendar.MINUTE));

    }

    public String pad(int i) {
        if (i < 10)
            return "0" + i;
        return String.valueOf(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.save_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.delete) {
            NoteDatabase db = new NoteDatabase(this);
            //Toast.makeText(this, "Note Deleted", Toast.LENGTH_SHORT).show();
            Integer deletedRows = db.deleteData(note.getID().toString());
            if (deletedRows > 0) {
                Toast.makeText(this, "Note Deleted", Toast.LENGTH_SHORT).show();
                goToMain();
            } else
                Toast.makeText(this, "Note not deleted", Toast.LENGTH_SHORT).show();

        }
        if (item.getItemId() == R.id.save) {
            Note note1 = new Note(noteTitle.getText().toString(), noteDetails.getText().toString(), todaysDate, currentTime);
            NoteDatabase db = new NoteDatabase(this);
            boolean isupdated = db.updateData(note.getID().toString(), note1);
            if (isupdated) {
                Toast.makeText(this, "Note Saved", Toast.LENGTH_SHORT).show();
                goToMain();
            } else {
                Toast.makeText(this, "Note not Saved", Toast.LENGTH_SHORT).show();
            }
        }

        if (item.getItemId() == R.id.shareoption) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, noteTitle.getText().toString() + System.lineSeparator() + noteDetails.getText().toString());
            sendIntent.setType("text/plain");

            Intent shareIntent = Intent.createChooser(sendIntent, null);
            startActivity(shareIntent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void goToMain() {
        Intent i = new Intent(this, FirstActivity.class);
        i.putExtra("fragment", 1);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }
}
