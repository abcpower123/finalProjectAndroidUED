package com.aszqsc.dontforgeteverything.Notify;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.aszqsc.dontforgeteverything.MainActivity;
import com.aszqsc.dontforgeteverything.R;

import java.util.Calendar;

public class AlarmReceiver_SendOn extends BroadcastReceiver
{
    private final int NOTIFICATION_ID = 001;
    private final String CHANNEL_ID = "TheNotification";

    private final String CHANNEL_NAME = "The Notification";
    private final String CHANNEL_DESC = "The Notification System for The App";
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("TAG", "Started >>>>>>>");
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT
            );
            channel.setDescription(CHANNEL_DESC);
            NotificationManager manager = context.getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        String title=intent.getStringExtra("TitleMessage");

        //and doing something
        Intent landingIntent= new Intent(context, MainActivity.class);
//        landingIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent landingPendingIntent = PendingIntent.getActivity(context,
                0,landingIntent,PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(
                context,CHANNEL_ID);
        builder.setSmallIcon(R.drawable.iconapp);
        builder.setContentTitle("Don't forget everythings!");
        builder.setContentText(title);
        //builder.setContentText("Asd");
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);

        builder.setAutoCancel(true);
        builder.setContentIntent(landingPendingIntent);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat
                .from(context);
        notificationManagerCompat.notify(NOTIFICATION_ID,builder.build());
    }
    public static  void sendMess(int day,int month, int year, int hour, int minute,Context context,String title) {

        AlarmManager m_alarmMgr = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver_SendOn.class);
        intent.putExtra("TitleMessage",title);
        PendingIntent m_alarmIntent = PendingIntent.getBroadcast(context, (int) System.currentTimeMillis(), intent, 0);

        // Set the alarm to start at 21:32 PM
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());

        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.YEAR,year-1);
        calendar.set(Calendar.MONTH,month-1);
        calendar.set(Calendar.DAY_OF_MONTH,day);
        //calendar.set(2019,12,21,17,49);

        m_alarmMgr.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), m_alarmIntent);
    }
}
