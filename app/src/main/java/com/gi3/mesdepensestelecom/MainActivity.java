package com.gi3.mesdepensestelecom;

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

import com.gi3.mesdepensestelecom.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflating the layout using View Binding
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Set the toolbar as the action bar
        setSupportActionBar(binding.appBarMain.toolbar);

        // Set up a Floating Action Button (FAB) with a SnackBar
        if (binding.appBarMain.fab != null) {
            binding.appBarMain.fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show());
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
    }

    // Inflate the overflow menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        boolean result = super.onCreateOptionsMenu(menu);
        NavigationView navView = findViewById(R.id.nav_view);
        if (navView == null && mAppBarConfiguration != null && !mAppBarConfiguration.getTopLevelDestinations().contains(R.id.nav_login) && !mAppBarConfiguration.getTopLevelDestinations().contains(R.id.nav_register)) {
            getMenuInflater().inflate(R.menu.overflow, menu);
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
