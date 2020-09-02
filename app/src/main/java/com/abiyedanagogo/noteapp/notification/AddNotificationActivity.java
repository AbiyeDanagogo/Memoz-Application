package com.abiyedanagogo.noteapp.notification;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

import com.abiyedanagogo.noteapp.FirstActivity;
import com.abiyedanagogo.noteapp.R;

import java.text.DateFormat;
import java.util.Calendar;

public class AddNotificationActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {
    Toolbar toolbar;
    Calendar c;
    private TextView chosenTimeText;
    private EditText messageText;
    Long ID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_picker);

        toolbar = findViewById(R.id.toolbarNotification1);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("New Reminder");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));


        chosenTimeText = findViewById(R.id.textChooseTime);
        messageText = findViewById(R.id.messageTextMultiline);
        messageText.setSingleLine(false);


        ImageButton timePickerButton = findViewById(R.id.imageButtonTimePicker);

        timePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c = Calendar.getInstance();
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });


    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        DialogFragment timePicker = new TimePickerFragment();
        timePicker.show(getSupportFragmentManager(), "time picker");
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.SECOND, 0);
        updateTimeText(c);
    }

    private void updateTimeText(Calendar c) {
        String timeText = "Set a Reminder for " + DateFormat.getDateInstance(DateFormat.SHORT).format(c.getTime()) + " ";
        timeText += DateFormat.getTimeInstance(DateFormat.SHORT).format(c.getTime());
        chosenTimeText.setText(timeText);
    }

    public void startAlarm(Calendar c) {

        if (c.before(Calendar.getInstance())) {
            Toast.makeText(this, "Please choose a later time", Toast.LENGTH_SHORT).show();
        } else {
            NotificationClass notificationClass = new NotificationClass(messageText.getText().toString(), c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH), c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE));
            NotificationDatabase db = new NotificationDatabase(this);
            ID = db.addNotification(notificationClass);
            //int ID1 = ID.intValue();


            String timeText = "Reminder set for " + DateFormat.getDateInstance(DateFormat.SHORT).format(c.getTime()) + " ";
            timeText += DateFormat.getTimeInstance(DateFormat.SHORT).format(c.getTime());


            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(this, AlertReceiver.class);
            intent.putExtra("message", messageText.getText().toString());
            intent.putExtra("alarmid", ID.intValue());
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, ID.intValue(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
            Toast.makeText(this, timeText, Toast.LENGTH_SHORT).show();


            alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);


            goToMain();
        }

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
            //Toast.makeText(this, "Delete button clicked", Toast.LENGTH_SHORT).show();
            onBackPressed();
        }
        if (item.getItemId() == R.id.save) {
            if (c == null) {
                Toast.makeText(AddNotificationActivity.this, "Please set Time", Toast.LENGTH_SHORT).show();
            } else {
                startAlarm(c);
            }
        }
        if (item.getItemId() == R.id.shareoption) {
            //Toast.makeText(this, "This is share button", Toast.LENGTH_SHORT).show();
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, messageText.getText().toString());
            sendIntent.setType("text/plain");

            Intent shareIntent = Intent.createChooser(sendIntent, null);
            startActivity(shareIntent);
        }
        return super.onOptionsItemSelected(item);
    }


    private void goToMain() {
        Intent i = new Intent(this, FirstActivity.class);
        i.putExtra("fragment", 2);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }
}
