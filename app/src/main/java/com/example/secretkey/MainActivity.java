package com.example.secretkey;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.secretkey.fragments.AboutUsFragment;
import com.example.secretkey.fragments.HomeFragment;
import com.example.secretkey.fragments.MessageFragment;
import com.example.secretkey.fragments.SecretChangeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bnView);
        relativeLayout = findViewById(R.id.layout_main);
        relativeLayout.setVisibility(View.VISIBLE);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.nav_home) {
                    loadFragment(new HomeFragment(), false);
                    return true;
                } else if (id == R.id.nav_message) {
                    loadFragment(new MessageFragment(), false);
                    return true;
                } else if (id == R.id.nav_aboutUs) {
                    loadFragment(new AboutUsFragment(), false);
                    return true;
                } else if (id == R.id.nav_changekey) {
                    loadFragment(new SecretChangeFragment(), false);
                    return true;
                } else {
                    Toast.makeText(MainActivity.this, "Default clicked", Toast.LENGTH_SHORT).show();
                }

                return false;
            }
        });

        bottomNavigationView.setSelectedItemId(R.id.nav_home);
    }

    private void loadFragment(Fragment fragment, boolean flag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (flag) fragmentTransaction.add(R.id.container, fragment);
        else fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.commit();

    }
}