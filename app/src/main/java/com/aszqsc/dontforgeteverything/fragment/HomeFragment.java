package com.aszqsc.dontforgeteverything.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.aszqsc.dontforgeteverything.R;
import com.aszqsc.dontforgeteverything.model.MyDatabaseHelper;

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
