package com.abiyedanagogo.noteapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

/*
 * Created by Abiye Danagogo on 20/04/2020.
 * */

public class FirstActivity extends AppCompatActivity {

    Button reminderButton, noteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        int fragmentId = getIntent().getIntExtra("fragment", 1);

        if (fragmentId == 1) {
            makeNoteFragment();
        } else {
            makeNotificationsFragment();
        }

        reminderButton = findViewById(R.id.reminderButton);
        reminderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeNotificationsFragment();
            }
        });

        noteButton = findViewById(R.id.noteButton);
        noteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeNoteFragment();
            }
        });

    }

    public void makeNoteFragment() {
        NoteFragment noteFragment = new NoteFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.placeholder, noteFragment);
        fragmentTransaction.commit();
    }

    public void makeNotificationsFragment() {
        NotificationsFragment notificationsFragment = new NotificationsFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.placeholder, notificationsFragment);
        fragmentTransaction.commit();
    }
}
