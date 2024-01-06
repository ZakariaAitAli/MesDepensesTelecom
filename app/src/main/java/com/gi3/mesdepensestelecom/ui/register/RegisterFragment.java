package com.gi3.mesdepensestelecom.ui.register;

import android.content.Intent;
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

import com.gi3.mesdepensestelecom.MainActivity;
import com.gi3.mesdepensestelecom.R;
import com.gi3.mesdepensestelecom.Models.User;
import com.gi3.mesdepensestelecom.database.UserRepository;

public class RegisterFragment extends Fragment {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private EditText confirmPasswordEditText;

    // Instantiate UserRepository
    private UserRepository userRepository;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_register, container, false);

        usernameEditText = root.findViewById(R.id.username);
        passwordEditText = root.findViewById(R.id.password);
        confirmPasswordEditText = root.findViewById(R.id.confirm_password);

        Button registerButton = root.findViewById(R.id.register);
        View loginRedirectText = root.findViewById(R.id.loginRedirect);

        // Instantiate UserRepository
        userRepository = new UserRepository(requireContext());

        registerButton.setOnClickListener(v -> attemptRegister());

        // Set an OnClickListener to navigate to the login fragment
        loginRedirectText.setOnClickListener(v -> Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_main)
                .navigate(R.id.nav_login));

        return root;
    }

    private void attemptRegister() {
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String confirmPassword = confirmPasswordEditText.getText().toString();

        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            showToast("All fields are mandatory");
        } else if (!confirmPassword.equals(password)) {
            showToast("Passwords do not match");
//        } else if (password.length() < 6) {
//            showToast("Password must be at least 6 characters");
        } else {
            // Create a User object with the entered username and password
            User user = new User();
            user.username = username;
            user.password = password;

            boolean checkUserExists = userRepository.checkUsername(username);

            if (!checkUserExists) {
                long insertResult = userRepository.addUser(user);

                if (insertResult != -1) {
                    showToast("Signup Successfully!");
                    // Navigate to the home activity or the next screen after registration
                    startActivity(new Intent(getActivity(), MainActivity.class));
                    requireActivity().finish();
                } else {
                    showToast("Signup Failed!");
                }
            } else {
                showToast("User already exists! Please login");
            }
        }
    }

    private void showToast(String message) {
        // Create and show a toast message
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }
}
