package com.abiyedanagogo.noteapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

/*
 * Created by Abiye Danagogo on 20/04/2020.
 * This is the first activity that is displayed to the user when the application is opened
 * */

public class FirstActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        //Checks for the fragment id with intent to determine which of the two fragments will be shown
        int fragmentId = getIntent().getIntExtra("fragment", 1);

        //If the fragment id is 1 the note fragment is shown else the notification fragment is shown
        if (fragmentId == 1) {
            makeNoteFragment();
        } else {
            makeNotificationsFragment();
        }

        //Button to switch to notification fragment is defined and an onclicklistener is set
        Button reminderButton = findViewById(R.id.reminderButton);
        reminderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeNotificationsFragment();
            }
        });

        //Button to switch to notes fragment is defined and an onclicklistener is set
        Button noteButton = findViewById(R.id.noteButton);
        noteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeNoteFragment();
            }
        });

    }

    //Function to create the notes fragment is defined
    public void makeNoteFragment() {
        NoteFragment noteFragment = new NoteFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.placeholder, noteFragment);
        fragmentTransaction.commit();
    }

    //Function to create the notifications fragment is defined
    public void makeNotificationsFragment() {
        NotificationsFragment notificationsFragment = new NotificationsFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.placeholder, notificationsFragment);
        fragmentTransaction.commit();
    }
}
