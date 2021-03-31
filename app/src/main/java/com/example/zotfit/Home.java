package com.example.zotfit;



import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.zotfit.search.Search_Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

    public class Home extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
        Toolbar toolbar;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_home);
            toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            toolbar.setLogo(R.drawable.zotfit);

            BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
            bottomNavigationView.setOnNavigationItemSelectedListener(this);
            if (savedInstanceState == null) {
                bottomNavigationView.setSelectedItemId(R.id.action_home); // change to whichever id should be default
            }

        }

        @Override
        public boolean onNavigationItemSelected( @NonNull MenuItem item){
            // Handle navigation view item clicks here.
            int id = item.getItemId();
            Fragment fragment = null;
            Class fragmentClass = null;
            if (id == R.id.action_friends) {
                fragmentClass = Friends_Fragment.class;
            } else if (id == R.id.action_progress) {
                fragmentClass = Progress_Fragment.class;
            }
            else if (id==R.id.action_search){
                fragmentClass= Search_Fragment.class;
            }
            else if(id==R.id.action_home){
                fragmentClass=Home_Fragment.class;
            }

            try {
                fragment = (Fragment) fragmentClass.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }

            if(fragment!=null){
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.fragment_layout, fragment).commit();}


            return true;

        }
        @Override
        public boolean onCreateOptionsMenu(Menu menu){
            MenuInflater inflater=getMenuInflater();
            inflater.inflate(R.menu.menufile,menu);
            return true;


        }

    }

