package com.example.madapp;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private ActionBarDrawerToggle toggle;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.TBMainAct);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.DLMain);
        toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavHostFragment host = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.NHFMain);
        NavController navController = host.getNavController();

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build(); //Appbar configuration will help to define which destination should have an Up button (back arrow) in app bar
//      to automatically handle Up button behavior based on the navigation graph.

        //set up action bar to work with NavController, means up button will navigate to appropriate destination define in navigation graph
        NavigationUI.setupActionBarWithNavController(this,navController,appBarConfiguration); //this: Typically, in an activity, this refers to the current activity context.

        //setDrawerLayout(drawerLayout): This is configuring the builder to work with a DrawerLayout. It means that if you have a drawer layout in your app, the configured destinations will show the hamburger icon in the Toolbar, indicating that there's a navigation drawer.
        NavigationUI.setupWithNavController(toolbar, navController, new AppBarConfiguration.Builder(R.id.DestReforestation,R.id.DestHome).setDrawerLayout(drawerLayout).build());
        //only write the home and about app so that top level destination will show hamburger icon, and other fragment show up hbutton


        setupBottomNavMenu(navController);
        setupNavMenu(navController);

    }

    private void setupNavMenu(NavController navController){
        NavigationView sideNav = findViewById(R.id.sideNav);
        NavigationUI.setupWithNavController(sideNav, navController);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_options, menu);
        return true;
    }


    // the method below is used to configure the action of the overflow menu which
    // navigates back to 'home' or 'about app' page
    public boolean onOptionsItemSelected(MenuItem item){
        try{
            Navigation.findNavController(this, R.id.NHFMain).navigate(item.getItemId());
            return true;
        }
        catch(Exception ex){
            return super.onOptionsItemSelected(item);
        }
    }

    public boolean onSupportNavigateUp(){
        return Navigation.findNavController(this, R.id.NHFMain).navigateUp();
    }

    private void setupBottomNavMenu(NavController navController){
        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav_view);
        NavigationUI.setupWithNavController(bottomNav, navController);
    }
}
