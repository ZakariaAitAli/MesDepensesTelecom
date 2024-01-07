package com.gi3.mesdepensestelecom;

import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.gi3.mesdepensestelecom.database.AbonnementRepository;
import com.gi3.mesdepensestelecom.database.DatabaseHelper;
import com.gi3.mesdepensestelecom.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    DatabaseHelper databaseHelper ;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get the shared preferences
        sharedPreferences = getSharedPreferences("user_session", MODE_PRIVATE);

        //MIGRATIONS:
        databaseHelper = new DatabaseHelper(this);
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        databaseHelper.onUpgrade(db,0,0);
        databaseHelper.onCreate(db);

        // Inflating the layout using View Binding
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        // Set the toolbar as the action bar
        setSupportActionBar(binding.appBarMain.toolbar);

        // Set up a Floating Action Button (FAB) with a SnackBar
        if (binding.appBarMain.fab != null) {
            binding.appBarMain.fab.setOnClickListener(view -> {
               /* String displayName = sharedPreferences.getString("display_name", "");
                String userId = sharedPreferences.getString("user_id", "");
                String snackbarMessage = "Welcome, " + displayName + "!" + "\n" + "User ID: " + userId;
                Snackbar.make(binding.appBarMain.fab, snackbarMessage, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                */
            });
        }

        // Find the NavHostFragment for navigation
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_content_main);
        assert navHostFragment != null;
        NavController navController = navHostFragment.getNavController();

        // Set up navigation drawer with NavigationView
        NavigationView navigationView = binding.navView;
        if (navigationView != null) {
            mAppBarConfiguration = new AppBarConfiguration.Builder(
                    R.id.nav_transform, R.id.nav_reflow, R.id.nav_slideshow, R.id.nav_settings, R.id.nav_login, R.id.nav_register)
                    .setOpenableLayout(binding.drawerLayout)
                    .build();
            NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
            NavigationUI.setupWithNavController(navigationView, navController);
        }

        // Set up bottom navigation with BottomNavigationView
        BottomNavigationView bottomNavigationView = binding.appBarMain.contentMain.bottomNavView;
        if (bottomNavigationView != null) {
            mAppBarConfiguration = new AppBarConfiguration.Builder(
                    R.id.nav_transform, R.id.nav_reflow, R.id.nav_slideshow, R.id.nav_login, R.id.nav_register)
                    .build();
            NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
            NavigationUI.setupWithNavController(bottomNavigationView, navController);

            // Initially hide BottomNavigationView
            bottomNavigationView.setVisibility(View.GONE);

            // Add a destination changed listener
            navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
                // Show BottomNavigationView only when the user is logged in
                if (destination.getId() == R.id.nav_login || destination.getId() == R.id.nav_register) {
                    bottomNavigationView.setVisibility(View.GONE);
                    // Hide the FAB in the login fragment
                    if (binding.appBarMain.fab != null) {
                        binding.appBarMain.fab.setVisibility(View.GONE);
                    }
                } else {
                    bottomNavigationView.setVisibility(View.VISIBLE);
                    // Show the FAB in other fragments
                    if (binding.appBarMain.fab != null) {
                        binding.appBarMain.fab.setVisibility(View.VISIBLE);
                    }
                }
            });
        }
      /*  AbonnementRepository abo = new AbonnementRepository(this) ;
        abo.GetAbonnements("2023") ;*/
    }

    // Inflate the overflow menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        boolean result = super.onCreateOptionsMenu(menu);
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
    if (Objects.requireNonNull(navController.getCurrentDestination()).getId() == R.id.nav_transform
                || navController.getCurrentDestination().getId() == R.id.nav_reflow
                || navController.getCurrentDestination().getId() == R.id.nav_slideshow) {
            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.overflow, menu);
        }
    if (navController.getCurrentDestination().getId() == R.id.nav_login
                || navController.getCurrentDestination().getId() == R.id.nav_register) {
            // invisible the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.overflow, menu);
            menu.findItem(R.id.nav_settings).setVisible(false);
            menu.findItem(R.id.nav_logout).setVisible(false);

        }
        return result;
    }

    // Handle item selection from the overflow menu
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.nav_settings) {
            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
            navController.navigate(R.id.nav_settings);
        }
        if (item.getItemId() == R.id.nav_logout) {
            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
            navController.navigate(R.id.nav_login);
        }
        return super.onOptionsItemSelected(item);
    }

    // Handle navigation up action
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}