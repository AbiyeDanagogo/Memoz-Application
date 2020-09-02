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

import com.abiyedanagogo.noteapp.notification.NotificationClass;
import com.abiyedanagogo.noteapp.notification.NotificationAdapter;
import com.abiyedanagogo.noteapp.notification.NotificationDatabase;
import com.abiyedanagogo.noteapp.notification.NotificationUpdateActivity;
import com.abiyedanagogo.noteapp.notification.AddNotificationActivity;

import java.util.List;

/*
 * Created by Abiye Danagogo on 20/04/2020.
 * */

public class NotificationsFragment extends Fragment implements NotificationAdapter.OnNotificationListener {
    Toolbar toolbar;
    RecyclerView recyclerView;
    NotificationAdapter notificationAdapter;
    List<NotificationClass> notificationClass;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_notification_recycler_view, container, false);

        toolbar = getActivity().findViewById(R.id.toolbarFirstActivity);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        setHasOptionsMenu(true);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setTitle("Reminders");

        NotificationDatabase db = new NotificationDatabase(getActivity());
        notificationClass = db.getNotifications();

        recyclerView = view.findViewById(R.id.listOfNotifications);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        notificationAdapter = new NotificationAdapter(getActivity(), notificationClass, this);
        recyclerView.setAdapter(notificationAdapter);


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
            Intent i = new Intent(getActivity(), AddNotificationActivity.class);
            startActivity(i);
            //Toast.makeText(this, "Add button is clicked", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onNotificationClick(int position) {
        Intent intent = new Intent(getActivity(), NotificationUpdateActivity.class);
        intent.putExtra("ID", notificationClass.get(position).getID());
        startActivity(intent);

    }
}
