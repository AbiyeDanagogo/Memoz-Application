package com.abiyedanagogo.noteapp.notification;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.abiyedanagogo.noteapp.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {
    LayoutInflater inflater;
    List<NotificationClass> notificationClasses;
    OnNotificationListener onNotificationListener;

    public NotificationAdapter(Context context, List<NotificationClass> notificationClasses, OnNotificationListener onNotificationListener) {
        this.inflater = LayoutInflater.from(context);
        this.notificationClasses = notificationClasses;
        this.onNotificationListener = onNotificationListener;
    }

    @NonNull
    @Override
    public NotificationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.activity_notification_list, parent, false);
        return new ViewHolder(view, onNotificationListener);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationAdapter.ViewHolder holder, int position) {
        String name = notificationClasses.get(position).getName();
        int year = notificationClasses.get(position).getYear();
        int month = notificationClasses.get(position).getMonth();
        int day = notificationClasses.get(position).getDay();
        int hour = notificationClasses.get(position).getHour();
        int minute = notificationClasses.get(position).getMinute();

        holder.nName.setText(name);
        holder.nYear.setText(String.valueOf(year));
        //holder.nMonth.setText(String.valueOf(month)+"/");
        holder.nMonth.setText(getMonth(month) + ",");
        holder.nDay.setText(String.valueOf(day));
        holder.nHour.setText(pad(hour) + ":");
        holder.nMinute.setText(pad(minute));


    }

    @Override
    public int getItemCount() {
        return notificationClasses.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView nHour, nName, nMinute, nYear, nMonth, nDay;
        OnNotificationListener onNotificationListener;

        public ViewHolder(@NonNull View itemView, OnNotificationListener onNotificationListener) {
            super(itemView);
            nName = itemView.findViewById(R.id.textView);
            nYear = itemView.findViewById(R.id.textView2);
            nMonth = itemView.findViewById(R.id.textView3);
            nDay = itemView.findViewById(R.id.textView4);
            nHour = itemView.findViewById(R.id.textView5);
            nMinute = itemView.findViewById(R.id.textView6);
            this.onNotificationListener = onNotificationListener;

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            onNotificationListener.onNotificationClick(getAdapterPosition());
        }
    }

    public interface OnNotificationListener {
        void onNotificationClick(int position);
    }

    public String pad(int i) {
        if (i < 10)
            return "0" + i;
        return String.valueOf(i);
    }

    public String getMonth(int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, month);
        return new SimpleDateFormat("MMM").format(calendar.getTime());
    }
}
