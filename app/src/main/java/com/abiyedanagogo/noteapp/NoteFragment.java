package com.abiyedanagogo.noteapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.abiyedanagogo.noteapp.notes.Adapter;
import com.abiyedanagogo.noteapp.notes.AddNote;
import com.abiyedanagogo.noteapp.notes.Note;
import com.abiyedanagogo.noteapp.notes.NoteDatabase;
import com.abiyedanagogo.noteapp.notes.UpdateActivity;

import java.util.List;

public class NoteFragment extends Fragment implements Adapter.OnNoteListener {
    Toolbar toolbar;
    RecyclerView recyclerView;
    Adapter adapter;
    List<Note> notes;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_main, container, false);

        toolbar = getActivity().findViewById(R.id.toolbarFirstActivity);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        setHasOptionsMenu(true);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setTitle("Notes");


        NoteDatabase db = new NoteDatabase(getActivity());
        notes = db.getNotes();

        recyclerView = view.findViewById(R.id.listOfNotes);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new Adapter(getActivity(), notes, this);
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.add_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.add) {
            Intent i = new Intent(getActivity(), AddNote.class);
            startActivity(i);
            //Toast.makeText(this, "Add button is clicked", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onNoteClick(int position) {
        Intent intent = new Intent(getActivity(), UpdateActivity.class);
        intent.putExtra("Note", notes.get(position));
        startActivity(intent);

    }
}
