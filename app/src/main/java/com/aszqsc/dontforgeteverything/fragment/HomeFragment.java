package com.aszqsc.dontforgeteverything.fragment;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;

import com.aszqsc.dontforgeteverything.MainActivity;
import com.aszqsc.dontforgeteverything.Notify.AlarmReceiver_SendOn;
import com.aszqsc.dontforgeteverything.Notify.NotificationReciever;
import com.aszqsc.dontforgeteverything.R;
import com.aszqsc.dontforgeteverything.model.Category;
import com.aszqsc.dontforgeteverything.model.MyDatabaseHelper;
import com.aszqsc.dontforgeteverything.model.Note;

import java.util.Calendar;


import static androidx.core.content.ContextCompat.getSystemService;

public class HomeFragment extends Fragment {
    MyDatabaseHelper db;

    public HomeFragment(MyDatabaseHelper db) {
        super();
        this.db = db;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frament_homee, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

    }


}
