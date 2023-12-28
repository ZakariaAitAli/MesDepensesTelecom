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
import com.gi3.mesdepensestelecom.database.DatabaseHelper;

public class RegisterFragment extends Fragment {

    private EditText usernameEditText;
    private EditText passwordEditText;

    // Instantiate DatabaseHelper
    private DatabaseHelper databaseHelper;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_register, container, false);

        usernameEditText = root.findViewById(R.id.editTextUsername);
        passwordEditText = root.findViewById(R.id.editTextPassword);
        Button registerButton = root.findViewById(R.id.buttonRegister);
        View loginRedirectText = root.findViewById(R.id.loginRedirectText);

        // Instantiate DatabaseHelper
        databaseHelper = new DatabaseHelper(requireContext());

        registerButton.setOnClickListener(v -> attemptRegister());

        // Set an OnClickListener to navigate to the login fragment
        loginRedirectText.setOnClickListener(v -> Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_main)
                .navigate(R.id.nav_login));

        return root;
    }

    private void attemptRegister() {
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        if (username.isEmpty() || password.isEmpty()) {
            showToast("All fields are mandatory");
        } else {
            boolean checkUserEmail = databaseHelper.checkUsername(username);

            if (!checkUserEmail) {
                boolean insert = databaseHelper.insertData(username, password);

                if (insert) {
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
