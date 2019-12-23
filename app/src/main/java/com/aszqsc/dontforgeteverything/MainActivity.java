package com.aszqsc.dontforgeteverything;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import com.aszqsc.dontforgeteverything.fragment.FavFragment;
import com.aszqsc.dontforgeteverything.fragment.HomeFragment;
import com.aszqsc.dontforgeteverything.model.MyDatabaseHelper;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.LabelVisibilityMode;

public class MainActivity extends AppCompatActivity {

    ViewPager viewPager;
    BottomNavigationView bottomNav;

    HomeFragment homeFragment;
    FavFragment workFragment;
    FavFragment familyFragment;
    FavFragment friendsFragment;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        MyDatabaseHelper db = new MyDatabaseHelper(this);
        homeFragment=new HomeFragment(db);
        workFragment=new FavFragment(db,0);
        familyFragment=new FavFragment(db,1);
        friendsFragment=new FavFragment(db,2);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        ViewpagerAdapter viewPagerAdapter = new ViewpagerAdapter(getSupportFragmentManager());


        viewPagerAdapter.addFragment(workFragment);
        viewPagerAdapter.addFragment(familyFragment);
        viewPagerAdapter.addFragment(friendsFragment);
        viewPagerAdapter.addFragment(homeFragment);

        viewPager.setAdapter(viewPagerAdapter);
         bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setLabelVisibilityMode(LabelVisibilityMode.LABEL_VISIBILITY_LABELED);

        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
              //  Fragment selectedFragment = null;

                switch (menuItem.getItemId()) {
                    case R.id.nav_work:
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.nav_family:
                        viewPager.setCurrentItem(1);
                        break;
                    case R.id.nav_friend:
                        viewPager.setCurrentItem(2);
                        break;
                    case R.id.nav_about:
                        viewPager.setCurrentItem(3);
                        break;


                  //      selectedFragment = new FavFragment();


                }

              //  getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                //        selectedFragment).commit();

                return true;
            }

        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                bottomNav.getMenu().getItem(position).setChecked(true);
                loadFragment(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void loadFragment(int position) {
        switch (position){
            case 0:
                bottomNav.setBackground(getDrawable(R.color.note_c4));
                workFragment.loadList();
                break;

            case 1:
                bottomNav.setBackground(getDrawable(R.color.note_c2));
                familyFragment.loadList();
                break;
            case 2:
                bottomNav.setBackground(getDrawable(R.color.note_c5));
                friendsFragment.loadList();
                break;
            case 3:
                bottomNav.setBackground(getDrawable(R.color.note_c1));
                break;

        }
    }

    public void NewTask(View view) {
        Intent t =new Intent(this,NoteInfo.class);
        startActivity(t);
    }

}
