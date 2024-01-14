package com.example.madapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.google.firebase.analytics.FirebaseAnalytics;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private ActionBarDrawerToggle toggle;
    private DrawerLayout drawerLayout;
    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        Toolbar toolbar = findViewById(R.id.TBMainAct);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.DLMain);
        toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavHostFragment host = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.NHFMain);
        NavController navController = host.getNavController();

        Set<Integer> destinationsWithoutUpAndBottomNav = new HashSet<>(Arrays.asList(
                R.id.DestQuizQuestion,
                R.id.DestProfileEnhancedSurveyQuestion
        ));

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();

        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        NavigationUI.setupWithNavController(toolbar, navController, new AppBarConfiguration
                .Builder(R.id.DestHome, R.id.DestCarbonFootprint, R.id.DestReforestation, R.id.DestProfile)
                .setDrawerLayout(drawerLayout).build());

        setupNavMenu(navController);

        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            if (destinationsWithoutUpAndBottomNav.contains(destination.getId())) {
                getSupportActionBar().hide();
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                hideBottomNavigationView();
            }
            else if(destination.getId() == R.id.DestCertificate) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            }
            else{
                getSupportActionBar().show();
                showBottomNavigationView();
            }
        });

        setupBottomNavMenu(navController);
    }

    private void setupNavMenu(NavController navController) {
        NavigationView sideNav = findViewById(R.id.sideNav);
        NavigationUI.setupWithNavController(sideNav, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        NavController navController = ((NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.NHFMain)).getNavController();
        if (item.getItemId() == R.id.DestLogout) {
            showLogoutConfirmationDialog();
            return true;
        } else if (item.getItemId() == R.id.DestInfo) {
            navController.navigate(R.id.DestInfo);
            return true;
        } else if (item.getItemId() == R.id.DestFeedback) {
            navController.navigate(R.id.DestFeedback);
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void showLogoutConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirm Logout");
        builder.setMessage("Are you sure you want to log out?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                performLogout();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void performLogout() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void setupBottomNavMenu(NavController navController) {
        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav_view);
        NavigationUI.setupWithNavController(bottomNav, navController);
    }

    private void hideBottomNavigationView() {
        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav_view);
        bottomNav.setVisibility(View.GONE);
    }

    private void showBottomNavigationView() {
        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav_view);
        bottomNav.setVisibility(View.VISIBLE);
    }
}
