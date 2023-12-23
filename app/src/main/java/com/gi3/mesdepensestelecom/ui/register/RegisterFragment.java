package com.gi3.mesdepensestelecom.ui.register;

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

public class RegisterFragment extends Fragment {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button RegisterButton;
    private View loginRedirectText;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_register, container, false);

        usernameEditText = root.findViewById(R.id.editTextUsername);
        passwordEditText = root.findViewById(R.id.editTextPassword);
        RegisterButton = root.findViewById(R.id.buttonRegister);
        loginRedirectText = root.findViewById(R.id.loginRedirectText);

        RegisterButton.setOnClickListener(v -> attemptLogin());

        // Set an OnClickListener to navigate to the login fragment
        loginRedirectText.setOnClickListener(v -> {
            Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_main)
                    .navigate(R.id.nav_login);
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
            getActivity().finish(); // Finish the current activity so the user can't go back to it without logging out
        } else {
            // Show an error message or handle unsuccessful login
            // For simplicity, we will show a toast message for now
            // You may want to replace this with a more sophisticated error handling mechanism
            // or integrate with Firebase Authentication, etc.
            showToast("Invalid username or password");
        }
    }

    private void showToast(String message) {
        // Implement your showToast method or use another mechanism to display messages to the user
        // For example: Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}
