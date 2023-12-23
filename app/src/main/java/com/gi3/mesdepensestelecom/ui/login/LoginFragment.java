package com.gi3.mesdepensestelecom.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.gi3.mesdepensestelecom.MainActivity;
import com.gi3.mesdepensestelecom.R;

public class LoginFragment extends Fragment {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private View signupRedirectText;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_login, container, false);

        usernameEditText = root.findViewById(R.id.editTextUsername);
        passwordEditText = root.findViewById(R.id.editTextPassword);
        loginButton = root.findViewById(R.id.buttonLogin);
        signupRedirectText = root.findViewById(R.id.signupRedirectText);

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

        // Add your authentication logic here
        // For simplicity, let's assume a successful login for any non-empty username and password
        if (!username.isEmpty() && !password.isEmpty()) {
            // Navigate to the home activity or the next screen after login
            startActivity(new Intent(getActivity(), MainActivity.class));
            requireActivity().finish(); // Finish the current activity so the user can't go back to it without logging out
        } else {
            // Show an error message or handle unsuccessful login
            // For simplicity, we will show a toast message for now
            showToast("Invalid username or password");
        }
    }

    private void showToast(String message) {
        // Implement your showToast method or use another mechanism to display messages to the user
        // For example: Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }
}
