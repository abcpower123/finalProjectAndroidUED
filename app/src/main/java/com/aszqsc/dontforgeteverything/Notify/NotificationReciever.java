package com.aszqsc.dontforgeteverything.Notify;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

import com.aszqsc.dontforgeteverything.MainActivity;
import com.aszqsc.dontforgeteverything.R;

public class NotificationReciever extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent intent1 = new Intent(context, MainActivity.class);
        intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        //if we want ring on notifcation then uncomment below line//
//        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,100,intent1,PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context).
                setSmallIcon(R.drawable.iconapp).
                setContentIntent(pendingIntent).
                setContentText("this is my notification").
                setContentTitle("my notificaton").
//                setSound(alarmSound).
        setAutoCancel(true);
        if (intent.getAction().equals("MY_NOTIFICATION_MESSAGE")) {
            notificationManager.notify(100,builder.build());
//            Log.i("Notify", "Alarm"); //Optional, used for debuging.
        }
    }
}
