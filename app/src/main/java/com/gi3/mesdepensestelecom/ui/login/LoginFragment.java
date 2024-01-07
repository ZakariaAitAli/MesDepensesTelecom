package com.gi3.mesdepensestelecom.ui.login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.gi3.mesdepensestelecom.R;
import com.gi3.mesdepensestelecom.database.DatabaseHelper;

public class LoginFragment extends Fragment {

    private EditText usernameEditText;
    private EditText passwordEditText;

    // Instantiate DatabaseHelper
    private DatabaseHelper databaseHelper;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_login, container, false);

            usernameEditText = root.findViewById(R.id.editTextUsername);
        passwordEditText = root.findViewById(R.id.editTextPassword);
        Button loginButton = root.findViewById(R.id.buttonLogin);
        View signupRedirectText = root.findViewById(R.id.signupRedirectText);

        // Instantiate DatabaseHelper
        databaseHelper = new DatabaseHelper(requireContext());

        loginButton.setOnClickListener(v -> attemptLogin());

        // Set an OnClickListener to navigate to the register fragment
        signupRedirectText.setOnClickListener(v -> {
            Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_main)
                    .navigate(R.id.nav_register);
        });

        return root;
    }

    private void attemptLogin() {
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        // Check if the email and password match in the SQLite database
        if (databaseHelper.checkUsernamePassword(username, password)) {
            // Navigate to the nav_transform fragment after successful login
            Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_main)
                    .navigate(R.id.nav_transform);
        } else {
            showToast("Invalid username or password");
        }
    }

    private void showToast(String message) {
        // Create and show a toast message
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }
}
