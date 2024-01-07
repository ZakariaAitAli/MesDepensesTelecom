package com.gi3.mesdepensestelecom.ui.logout;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.gi3.mesdepensestelecom.R;
import com.gi3.mesdepensestelecom.databinding.FragmentLogoutBinding;
import com.gi3.mesdepensestelecom.ui.login.LoginViewModel;

public class LogoutFragment extends Fragment {

    private FragmentLogoutBinding binding;
    private LoginViewModel loginViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        LogoutViewModel logoutViewModel =
                new ViewModelProvider(this).get(LogoutViewModel.class);

        binding = FragmentLogoutBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textLogout;
        logoutViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        final Button logoutButton = binding.logoutButton;
        logoutButton.setOnClickListener(view -> {
            // Perform logout actions here, such as clearing user sessions or preferences
            loginViewModel.logout();

            // Navigate back to the login screen
            NavController navController = Navigation.findNavController(view);
            navController.navigate(R.id.nav_login);
        });

        return root;
    }

    private void clearUserSession() {
        // Clear user session (remove stored data)
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("user_session", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("user_id");
        editor.remove("display_name");
        editor.clear();
        editor.apply();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
