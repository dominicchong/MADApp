package com.example.wia2007_project;

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

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.TBMainAct);
        setSupportActionBar(toolbar);

        DrawerLayout drawerLayout = findViewById(R.id.DLMain);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.NHFMain);
        NavController navController = navHostFragment.getNavController(); //new add

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build(); //Appbar configuration will help to define which destination should have an Up button (back arrow) in app bar
//      to automatically handle Up button behavior based on the navigation graph.

        //set up action bar to work with NavController, means up button will navigate to appropriate destination define in navigation graph
        NavigationUI.setupActionBarWithNavController(this,navController,appBarConfiguration); //this: Typically, in an activity, this refers to the current activity context.

        //setDrawerLayout(drawerLayout): This is configuring the builder to work with a DrawerLayout. It means that if you have a drawer layout in your app, the configured destinations will show the hamburger icon in the Toolbar, indicating that there's a navigation drawer.
        NavigationUI.setupWithNavController(toolbar, navController, new AppBarConfiguration.Builder(R.id.DestHome).setDrawerLayout(drawerLayout).build());
        //only write the home and about app so that top level destination will show hamburger icon, and other fragment show up hbutton

        setupBottomNavMenu(navController);

        setupNavmenu(navController);

        /*
        NavigationUI.setupWithNavController:
        This method is used when you want to set up a custom Toolbar for navigation.
        It can be used with any Toolbar instance, whether it's a part of the app's layout or created programmatically.
I       t doesn't assume that the Toolbar is the default ActionBar.

        NavigationUI.setupActionBarWithNavController:
        This method is specifically designed for cases where you want to use the default ActionBar (the built-in app bar) for navigation.
         */
    }

    //for side navigation
    private void setupNavmenu(NavController navController){
        NavigationView sideNav = findViewById(R.id.sideNav);
        NavigationUI.setupWithNavController(sideNav,navController);
    }

    //for bottom navigation
    private void setupBottomNavMenu(NavController navController){
        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav_view);
        NavigationUI.setupWithNavController(bottomNav,navController);
    }


    //from line 53 to 74 is for optional menu
    //use to create an option menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_overflow, menu);
        return true;
    }

    //to allow the item in option menu to be selected and can navigate to that page like button
    // handling the selection of menu items. It attempts to navigate to a destination associated with the selected menu item using the
    // Navigation component, and if there's an exception, it falls back to the default behavior.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        try{
            Navigation.findNavController(this,R.id.NHFMain).navigate(item.getItemId());
            return true;
        }
        catch(Exception ex)
        {
            return super.onOptionsItemSelected(item);
        }
    }
}